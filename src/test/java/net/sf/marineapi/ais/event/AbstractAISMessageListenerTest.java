package net.sf.marineapi.ais.event;

import net.sf.marineapi.ais.message.AISMessage;
import net.sf.marineapi.ais.message.AISMessage01;
import net.sf.marineapi.ais.message.AISMessage05;
import net.sf.marineapi.ais.parser.AISMessageFactory;
import net.sf.marineapi.nmea.parser.SentenceFactory;
import net.sf.marineapi.nmea.sentence.AISSentence;
import net.sf.marineapi.util.GenericTypeResolver;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

public class AbstractAISMessageListenerTest {

    private final SentenceFactory sf = SentenceFactory.getInstance();
    private final AISMessageFactory mf = AISMessageFactory.getInstance();

    private final AISSentence AIS_01 = (AISSentence) sf.createParser("!AIVDM,1,1,,A,13aEOK?P00PD2wVMdLDRhgvL289?,0*26");
    private final AISMessage01 MSG_01 = (AISMessage01) mf.create(AIS_01);

    private final AISSentence AIS_05_1 = (AISSentence) sf.createParser("!AIVDM,2,1,3,B,55P5TL01VIaAL@7WKO@mBplU@<PDhh000000001S;AJ::4A80?4i@E53,0*3E");
    private final AISSentence AIS_05_2 = (AISSentence) sf.createParser("!AIVDM,2,2,3,B,1@0000000000000,2*55");
    private final AISMessage05 MSG_05 = (AISMessage05) mf.create(AIS_05_1, AIS_05_2);

    @Test
    public void testConstructor() {

        BasicListener bl = new BasicListener();

        assertNull(bl.received);
        assertEquals(bl.messageType, AISMessage01.class);
    }

    @Test
    public void testParametrizedConstructor() {

        ExtendedBasicListener ebl = new ExtendedBasicListener();

        assertNull(ebl.get());
        assertEquals(ebl.messageType, AISMessage01.class);
    }

    @Test
    public void testOnMessageWithExpectedMessage() {

        BasicListener bl = new BasicListener();

        bl.sentenceRead(AIS_01);
        assertEquals(bl.received.toString(), MSG_01.toString());
    }

    @Test
    public void testSequenceListener() {

        SequenceListener sl = new SequenceListener();

        sl.sentenceRead(AIS_05_1);
        assertNull(sl.received);

        sl.sentenceRead(AIS_05_2);
        assertEquals(sl.received.toString(), MSG_05.toString());
    }

    @Test
    public void testSequenceListenerWithIncorrectOrder() {

        SequenceListener sl = new SequenceListener();

        sl.sentenceRead(AIS_05_2);
        assertNull(sl.received);

        sl.sentenceRead(AIS_05_1);
        assertNull(sl.received);

        sl.sentenceRead(AIS_05_2);
        assertEquals(sl.received.toString(), MSG_05.toString());
    }

    @Test
    public void testSequenceListenerWithMixedOrder() {

        SequenceListener sl = new SequenceListener();
        
        sl.sentenceRead(AIS_05_1);
        assertNull(sl.received);

        sl.sentenceRead(AIS_01);
        assertNull(sl.received);

        sl.sentenceRead(AIS_05_2);
        assertNull(sl.received);
    }

    @Test
    public void testBasicListenerWithUnexpectedMessage() {

        BasicListener bl = new BasicListener();
        bl.sentenceRead(AIS_05_1);
        bl.sentenceRead(AIS_05_2);

        assertNull(bl.received);
    }

    @Test
    public void testGenericsListener() {

        GenericsListener<Integer, AISMessage01> gl = new GenericsListener<>(AISMessage01.class);
        gl.sentenceRead(AIS_01);

        assertEquals(gl.received.toString(), MSG_01.toString());
        assertEquals("1", gl.dummy(1));
    }

    @Test
    public void testGenericsListenerDefaultConstructorThrows() {
        try {
            GenericsListener<Integer, AISMessage01> gl = new GenericsListener<>();
            fail("exception not thrown, resolved to " + gl.messageType);
        } catch (IllegalStateException ise) {
            assertEquals("Cannot resolve generic type <T>, use constructor with Class<T> param.", ise.getMessage());
        } catch (Exception e) {
            fail("unexpected exception thrown: " + e.getMessage());
        }
    }


    /** Listeners **/

    class BasicListener extends AbstractAISMessageListener<AISMessage01> {
        AISMessage01 received;
        @Override
        public void onMessage(AISMessage01 msg) {
            this.received = msg;
        }
    }

    class ExtendedBasicListener extends BasicListener {
        AISMessage01 get() { return super.received; }
    }

    class SequenceListener extends AbstractAISMessageListener<AISMessage05> {
        AISMessage05 received;
        @Override
        public void onMessage(AISMessage05 msg) {
            this.received = msg;
        }
    }

    class GenericsListener<A, B extends AISMessage> extends AbstractAISMessageListener<B> {
        B received;
        public GenericsListener() { }
        public GenericsListener(Class<B> c) { super(c); }
        public String dummy(A obj) { return obj.toString(); }
        @Override
        public void onMessage(B msg) { this.received = msg; }
    }
}
