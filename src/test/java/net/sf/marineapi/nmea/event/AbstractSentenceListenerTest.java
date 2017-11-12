package net.sf.marineapi.nmea.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import net.sf.marineapi.nmea.parser.BODTest;
import net.sf.marineapi.nmea.parser.GGATest;
import net.sf.marineapi.nmea.parser.GLLTest;
import net.sf.marineapi.nmea.parser.SentenceFactory;
import net.sf.marineapi.nmea.sentence.BODSentence;
import net.sf.marineapi.nmea.sentence.GGASentence;
import net.sf.marineapi.nmea.sentence.GLLSentence;
import net.sf.marineapi.nmea.sentence.Sentence;
import org.junit.Test;

public class AbstractSentenceListenerTest {

    private final SentenceFactory factory = SentenceFactory.getInstance();

    @Test
    public void testBasicListenerWithExpectedSentence() {

        Sentence bod = factory.createParser(BODTest.EXAMPLE);
        SentenceEvent evt = new SentenceEvent(this, bod);
        BasicListener bl = new BasicListener();
        bl.sentenceRead(evt);

        assertEquals(BODSentence.class, bl.expectedType);
        assertNotNull(bl.received);
        assertEquals(BODTest.EXAMPLE, bl.received.toSentence());
    }

    @Test
    public void testBasicListenerWithOtherSentence() {

        Sentence bod = factory.createParser(GGATest.EXAMPLE);
        SentenceEvent evt = new SentenceEvent(this, bod);
        BasicListener bl = new BasicListener();
        bl.sentenceRead(evt);

        assertEquals(BODSentence.class, bl.expectedType);
        assertNull(bl.received);
    }

    @Test
    public void testExtendedBasicListenerWithExpectedSentence() {

        Sentence bod = factory.createParser(BODTest.EXAMPLE);
        SentenceEvent evt = new SentenceEvent(this, bod);
        ExtendedBasicListener ebl = new ExtendedBasicListener();
        ebl.sentenceRead(evt);

        assertEquals(BODSentence.class, ebl.expectedType);
        assertNotNull(ebl.getReceived());
        assertEquals(BODTest.EXAMPLE, ebl.getReceived().toSentence());
    }

    @Test
    public void testExtendedBasicListenerWithUnexpectedSentence() {

        Sentence bod = factory.createParser(GGATest.EXAMPLE);
        SentenceEvent evt = new SentenceEvent(this, bod);
        ExtendedBasicListener ebl = new ExtendedBasicListener();
        ebl.sentenceRead(evt);

        assertEquals(BODSentence.class, ebl.expectedType);
        assertNull(ebl.getReceived());
    }

    @Test
    public void testGenericsListenerWithExpectedSentence() {

        Sentence gga = factory.createParser(GGATest.EXAMPLE);
        SentenceEvent evt = new SentenceEvent(this, gga);
        GenericsListener<Integer, GGASentence> gl = new GenericsListener<Integer, GGASentence>(){};
        gl.sentenceRead(evt);

        assertEquals(GGASentence.class, gl.expectedType);
        assertNotNull(gl.received);
        assertEquals("1", gl.stringify(1));
        assertEquals(GGATest.EXAMPLE, gl.received.toSentence());
    }

    @Test
    public void testGenericsListenerWithUnexpectedSentence() {

        Sentence gga = factory.createParser(GLLTest.EXAMPLE);
        SentenceEvent evt = new SentenceEvent(this, gga);
        GenericsListener<Integer, GGASentence> sl = new GenericsListener<Integer, GGASentence>(){};
        sl.sentenceRead(evt);

        assertEquals(GGASentence.class, sl.expectedType);
        assertNull(sl.received);
    }

    @Test
    public void testExtendedGenericsListenerWithExpectedSentence() {

        Sentence gga = factory.createParser(GGATest.EXAMPLE);
        SentenceEvent evt = new SentenceEvent(this, gga);
        ExtendedGenericsListener<String, Integer, GGASentence> egl = new ExtendedGenericsListener<String, Integer, GGASentence>(){};
        egl.sentenceRead(evt);

        assertEquals(GGASentence.class, egl.expectedType);
        assertNotNull(egl.received);
        assertEquals("3", egl.stringify(3));
        assertEquals(3556498, egl.hashify("test"));
        assertEquals(GGATest.EXAMPLE, egl.received.toSentence());
    }

    @Test
    public void testExtendedGenericsListenerWithUnexpectedSentence() {

        Sentence gga = factory.createParser(BODTest.EXAMPLE);
        SentenceEvent evt = new SentenceEvent(this, gga);
        ExtendedGenericsListener<String, Integer, GLLSentence> egl = new ExtendedGenericsListener<String, Integer, GLLSentence>(){};
        egl.sentenceRead(evt);

        assertEquals(GLLSentence.class, egl.expectedType);
        assertNull(egl.received);
    }

    @Test
    public void testGenericsHidingListenerWithExpectedSentence() {

        Sentence bod = factory.createParser(BODTest.EXAMPLE);
        SentenceEvent evt = new SentenceEvent(this, bod);
        GenericsHidingListener<Double> ghl = new GenericsHidingListener<>();
        ghl.sentenceRead(evt);

        assertEquals(BODSentence.class, ghl.expectedType);
        assertNotNull(ghl.received);
        assertEquals(BODTest.EXAMPLE, ghl.received.toSentence());
        assertEquals("4.5", ghl.dummy(4.5));
        assertEquals("5", ghl.stringify(5));
    }

    @Test
    public void testGenericsHidingListenerWithUnexpectedSentence() {

        Sentence bod = factory.createParser(GGATest.EXAMPLE);
        SentenceEvent evt = new SentenceEvent(this, bod);
        GenericsHidingListener<Double> ghl = new GenericsHidingListener<>();
        ghl.sentenceRead(evt);

        assertEquals(BODSentence.class, ghl.expectedType);
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

        String stringify(A obj) {
            return obj.toString();
        }

        @Override
        public void sentenceRead(B sentence) {
            received = sentence;
        }
    }

    class ExtendedGenericsListener<A, B, C extends Sentence> extends GenericsListener<B, C> {
        int hashify(A obj) { return obj.hashCode(); }
        C getReceived() { return super.received; }
    }

    class GenericsHidingListener<A> extends GenericsListener<Integer, BODSentence> {
        String dummy(A a) { return a.toString(); }
    }
}
