package net.sf.marineapi.nmea.event;

import net.sf.marineapi.nmea.parser.BODTest;
import net.sf.marineapi.nmea.parser.GGATest;
import net.sf.marineapi.nmea.parser.SentenceFactory;
import net.sf.marineapi.nmea.sentence.BODSentence;
import net.sf.marineapi.nmea.sentence.GGASentence;
import net.sf.marineapi.nmea.sentence.Sentence;
import org.junit.Test;

import static org.junit.Assert.*;

public class AbstractSentenceListenerTest {

    private final SentenceFactory factory = SentenceFactory.getInstance();
    private final Sentence BOD = factory.createParser(BODTest.EXAMPLE);
    private final Sentence GGA = factory.createParser(GGATest.EXAMPLE);
    private final SentenceEvent BOD_EVENT = new SentenceEvent(this, BOD);
    private final SentenceEvent GGA_EVENT = new SentenceEvent(this, GGA);

    @Test
    public void testDefaultConstructor() {
        BasicListener bl = new BasicListener();
        assertEquals(BODSentence.class, bl.sentenceType);
    }

    @Test
    public void testDefaultConstructorWhenExtended() {
        ExtendedBasicListener ebl = new ExtendedBasicListener();
        assertEquals(BODSentence.class, ebl.sentenceType);
    }

    @Test
    public void testParametrizedConstructor() {
        GenericsListener<String, BODSentence> gl = new GenericsListener<>(BODSentence.class);
        assertEquals(BODSentence.class, gl.sentenceType);
    }

    @Test
    public void testParametrizedConstructorWhenExtended() {
        ExtendedGenericsListener<String, Integer, BODSentence> gl = new ExtendedGenericsListener<>(BODSentence.class);
        assertEquals(BODSentence.class, gl.sentenceType);
    }

    @Test
    public void testDefaultConstructorThrows() {
        try {
            GenericsListener<String, BODSentence> gl = new GenericsListener<>();
            fail("default constructor didn't throw, resolved to " + gl.sentenceType);
        } catch (IllegalStateException ise) {
            String msg = "Cannot resolve generic type <T>, use constructor with Class<T> param.";
            assertEquals(msg, ise.getMessage());
        } catch (Exception e) {
            fail("unexpected exception was thrown: " + e.getMessage());
        }
    }

    @Test
    public void testDefaultConstructorThrowsWhenExtended() {
        try {
            ExtendedGenericsListener<String, Integer, GGASentence> egl = new ExtendedGenericsListener<>();
            fail("default constructor didn't throw, resolved to " + egl.sentenceType);
        } catch (IllegalStateException ise) {
            String msg = "Cannot resolve generic type <T>, use constructor with Class<T> param.";
            assertEquals(msg, ise.getMessage());
        } catch (Exception e) {
            fail("unexpected exception was thrown: " + e.getMessage());
        }
    }

    @Test
    public void testBasicListenerWithExpectedSentence() {
        BasicListener bl = new BasicListener();
        bl.sentenceRead(BOD_EVENT);
        assertNotNull(bl.received);
        assertEquals(BOD.toSentence(), bl.received.toSentence());
    }

    @Test
    public void testBasicListenerWithOtherSentence() {
        BasicListener bl = new BasicListener();
        bl.sentenceRead(GGA_EVENT);
        assertNull(bl.received);
    }

    @Test
    public void testExtendedBasicListenerWithExpectedSentence() {
        ExtendedBasicListener ebl = new ExtendedBasicListener();
        ebl.sentenceRead(BOD_EVENT);
        assertNotNull(ebl.getReceived());
        assertEquals(BOD.toSentence(), ebl.getReceived().toSentence());
    }

    @Test
    public void testExtendedBasicListenerWithUnexpectedSentence() {
        ExtendedBasicListener ebl = new ExtendedBasicListener();
        ebl.sentenceRead(GGA_EVENT);
        assertNull(ebl.getReceived());
    }

    @Test
    public void testGenericsListenerWithExpectedSentence() {
        GenericsListener<Integer, GGASentence> gl = new GenericsListener<>(GGASentence.class);
        gl.sentenceRead(GGA_EVENT);
        assertNotNull(gl.received);
        assertEquals(GGA.toSentence(), gl.received.toSentence());
        assertEquals("1", gl.stringify(1));
    }

    @Test
    public void testGenericsListenerWithUnexpectedSentence() {
        GenericsListener<Integer, GGASentence> sl = new GenericsListener<>(GGASentence.class);
        sl.sentenceRead(BOD_EVENT);
        assertNull(sl.received);
    }

    @Test
    public void testExtendedGenericsListenerWithExpectedSentence() {
        ExtendedGenericsListener<String, Integer, GGASentence> egl = new ExtendedGenericsListener<>(GGASentence.class);
        egl.sentenceRead(GGA_EVENT);
        assertNotNull(egl.received);
        assertEquals(GGA.toSentence(), egl.received.toSentence());
        assertEquals(3556498, egl.hashify("test"));
        assertEquals("3", egl.stringify(3));
    }

    @Test
    public void testExtendedGenericsListenerWithUnexpectedSentence() {
        ExtendedGenericsListener<String, Integer, GGASentence> egl = new ExtendedGenericsListener<>(GGASentence.class);
        egl.sentenceRead(BOD_EVENT);
        assertNull(egl.received);
    }

    @Test
    public void testGenericsHidingListenerWithExpectedSentence() {
        GenericsHidingListener<Double> ghl = new GenericsHidingListener<>();
        ghl.sentenceRead(BOD_EVENT);
        assertNotNull(ghl.received);
        assertEquals(BOD.toSentence(), ghl.received.toSentence());
        assertEquals("4.5", ghl.dummy(4.5));
        assertEquals("5", ghl.stringify(5));
    }

    @Test
    public void testGenericsHidingListenerWithUnexpectedSentence() {
        GenericsHidingListener<Double> ghl = new GenericsHidingListener<>();
        ghl.sentenceRead(GGA_EVENT);
        assertNull(ghl.received);
    }


    /** Listeners **/

    class BasicListener extends AbstractSentenceListener<BODSentence> {
        BODSentence received;
        @Override
        public void sentenceRead(BODSentence sentence) {
            received = sentence;
        }
    }

    class ExtendedBasicListener extends BasicListener {
        BODSentence getReceived() { return super.received; }
    }

    class GenericsListener<A, B extends Sentence> extends AbstractSentenceListener<B> {
        B received;
        GenericsListener() { }
        GenericsListener(Class<B> type) {
            super(type);
        }
        String stringify(A obj) {
            return obj.toString();
        }
        @Override
        public void sentenceRead(B sentence) {
            received = sentence;
        }
    }

    class ExtendedGenericsListener<A, B, C extends Sentence> extends GenericsListener<B, C> {
        ExtendedGenericsListener() { }
        ExtendedGenericsListener(Class<C> type) {
            super(type);
        }
        int hashify(A obj) { return obj.hashCode(); }
        C getReceived() { return super.received; }
    }

    class GenericsHidingListener<A> extends GenericsListener<Integer, BODSentence> {
        String dummy(A a) { return a.toString(); }
    }

}
