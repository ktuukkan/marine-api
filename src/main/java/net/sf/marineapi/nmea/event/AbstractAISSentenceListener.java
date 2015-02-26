package net.sf.marineapi.nmea.event;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import net.sf.marineapi.ais.parser.AISMessageFactory;
import net.sf.marineapi.ais.sentence.AISMessage;
import net.sf.marineapi.nmea.sentence.AISSentence;

/**
 * Abstract base listener for AIS messages. Extend this class to create listener
 * for specific AIS message type.
 * 
 * @author Kimmo Tuukkanen
 */
public abstract class AbstractAISSentenceListener<M extends AISMessage>
	extends AbstractSentenceListener<AISSentence> {

	private final Type expectedMessageType;
	private Queue<AISSentence> queue = new LinkedList<AISSentence>();

	/**
	 * Constructor
	 */
	public AbstractAISSentenceListener() {

		ParameterizedType superClass = (ParameterizedType) getClass()
				.getGenericSuperclass();
		
		Type[] superClassTypeArgs = superClass.getActualTypeArguments();

		this.expectedMessageType = superClassTypeArgs[0];
		
		System.out.println("expected message type: " + expectedMessageType);
	}

	@Override
	public void sentenceRead(AISSentence sentence) {
		
		System.out.println("sentenceRead: " + sentence.getSentenceId());
		
		if(sentence.isFirstFragment()) {
			queue.clear();
		}
		
		queue.add(sentence);
		
		if(sentence.isLastFragment()) {
			AISMessage mes = AISMessageFactory.getInstance().create(queue.poll());
			if(mes == null) return;
			
			while(!queue.isEmpty()) {
				AISSentence next = queue.poll();
				mes.append(next.getPayload(), next.getFragmentNumber(), next.getFillBits());
			}

			System.out.println("type " + mes.getMessageType());
			
			Class<?>[] interfaces = mes.getClass().getInterfaces();
			
			System.out.println("AIS message: " + interfaces[0]);
			
			if (Arrays.asList(interfaces).contains(this.expectedMessageType)) {
				onMessage((M) mes);
			}
		}
	}

	/**
	 * Invoked when AIS message has been parsed.
	 * 
	 * @param msg AISMessage of subtype <code>M</code>.
	 */
	public abstract void onMessage(M msg);
}
