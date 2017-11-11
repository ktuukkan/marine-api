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

        assertNotNull(bl.received);
        assertEquals(BODTest.EXAMPLE, bl.received.toSentence());
    }

    @Test
    public void testBasicListenerWithOtherSentence() {

        Sentence bod = factory.createParser(GGATest.EXAMPLE);
        SentenceEvent evt = new SentenceEvent(this, bod);
        BasicListener bl = new BasicListener();
        bl.sentenceRead(evt);

        assertNull(bl.received);
    }

    @Test
    public void testExtendedBasicListenerWithExpectedSentence() {

        Sentence bod = factory.createParser(BODTest.EXAMPLE);
        SentenceEvent evt = new SentenceEvent(this, bod);
        ExtendedBasicListener ebl = new ExtendedBasicListener();
        ebl.sentenceRead(evt);

        assertNotNull(ebl.getReceived());
        assertEquals(BODTest.EXAMPLE, ebl.getReceived().toSentence());
    }

    @Test
    public void testExtendedBasicListenerWithUnexpectedSentence() {

        Sentence bod = factory.createParser(GGATest.EXAMPLE);
        SentenceEvent evt = new SentenceEvent(this, bod);
        ExtendedBasicListener ebl = new ExtendedBasicListener();
        ebl.sentenceRead(evt);

        assertNull(ebl.getReceived());
    }

    @Test
    public void testGenericsListenerWithExpectedSentence() {

        Sentence gga = factory.createParser(GGATest.EXAMPLE);
        SentenceEvent evt = new SentenceEvent(this, gga);
        GenericsListener<Integer, GGASentence> gl = new GenericsListener<>();
        gl.sentenceRead(evt);

        assertNotNull(gl.received);
        assertEquals("1", gl.stringify(1));
        assertEquals(GGATest.EXAMPLE, gl.received.toSentence());
    }

    @Test
    public void testGenericsListenerWithUnexpectedSentence() {

        Sentence gga = factory.createParser(GLLTest.EXAMPLE);
        SentenceEvent evt = new SentenceEvent(this, gga);
        GenericsListener<Integer, GGASentence> sl = new GenericsListener<>();
        sl.sentenceRead(evt);

        assertNull(sl.received);
    }

    @Test
    public void testExtendedGenericsListenerWithExpectedSentence() {

        Sentence gga = factory.createParser(GGATest.EXAMPLE);
        SentenceEvent evt = new SentenceEvent(this, gga);
        ExtendedGenericsListener<String, Integer, GGASentence> egl = new ExtendedGenericsListener<>();
        egl.sentenceRead(evt);

        assertEquals("3", egl.stringify(3));
        assertEquals(3556498, egl.hashify("test"));
        assertNotNull(egl.received);
        assertEquals(GGATest.EXAMPLE, egl.received.toSentence());
    }

    @Test
    public void testExtendedGenericsListenerWithUnexpectedSentence() {

        Sentence gga = factory.createParser(BODTest.EXAMPLE);
        SentenceEvent evt = new SentenceEvent(this, gga);
        ExtendedGenericsListener<String, Integer, GLLSentence> egl = new ExtendedGenericsListener<>();
        egl.sentenceRead(evt);

        assertNull(egl.received);
    }

    @Test
    public void testGenericsHidingListenerWithExpectedSentence() {

        Sentence bod = factory.createParser(BODTest.EXAMPLE);
        SentenceEvent evt = new SentenceEvent(this, bod);
        GenericsHidingListener<Double> ghl = new GenericsHidingListener<>();
        ghl.sentenceRead(evt);

        assertEquals("4.5", ghl.dummy(4.5));
        assertEquals("5", ghl.stringify(5));
        assertNotNull(ghl.received);
        assertEquals(BODTest.EXAMPLE, ghl.received.toSentence());
    }

    @Test
    public void testGenericsHidingListenerWithUnexpectedSentence() {

        Sentence bod = factory.createParser(GGATest.EXAMPLE);
        SentenceEvent evt = new SentenceEvent(this, bod);
        GenericsHidingListener<Double> ghl = new GenericsHidingListener<>();
        ghl.sentenceRead(evt);

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
