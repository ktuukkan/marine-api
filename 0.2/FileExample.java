import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import net.sf.marineapi.nmea.event.SentenceEvent;
import net.sf.marineapi.nmea.event.SentenceListener;
import net.sf.marineapi.nmea.io.SentenceReader;
import net.sf.marineapi.nmea.sentence.GGASentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.util.Position;

/**
 * Simple example application that takes a filename as command-line argument and
 * prints Position from received GGA sentences.
 */
public class FileExample implements SentenceListener {

    private SentenceReader reader;

    /**
     * Creates a new instance of FileExample
     * 
     * @param file File from which to read NMEA data
     */
    public FileExample(File file) throws IOException {

        // create sentence reader and provide input stream
        reader = new SentenceReader(new FileInputStream(file));

        // register self as a listener for GGA sentences
        reader.addSentenceListener(this, SentenceId.GGA);

        // to receive all sentences
        // reader.addSentenceListener(this);
    }

    /**
     * Implements SentenceReader interface for receiving NMEA updates from
     * SentenceReader.
     */
    public void sentenceRead(SentenceEvent event) {

        // Safe to cast as we are registered only for GGA updates, could
        // also cast to PositionSentence if interested only in position.
        // If you receiving all sentences without filtering, check the sentence
        // type before casting (e.g. switch-case Sentence.getSentenceId()).
        GGASentence s = (GGASentence) event.getSentence();

        // do something with the data..
        Position p = s.getPosition();
        double lat = p.getLatitude();
        double lon = p.getLongitude();
        String msg = String.format("Position: %02.06f / %03.06f", lat, lon);
        System.out.println(msg);
    }

    /**
     * Main method takes a single command-line argument, the name of the file
     * that is read.
     * 
     * @param args Command-line arguments
     */
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Example usage:\njava FileExample nmea.log");
            System.exit(1);
        }

        try {
            new FileExample(new File(args[0]));
            System.out.println("Running, press CTRL-C to stop..");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
