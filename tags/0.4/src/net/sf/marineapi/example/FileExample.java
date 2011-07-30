package net.sf.marineapi.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.sf.marineapi.nmea.event.SentenceEvent;
import net.sf.marineapi.nmea.event.SentenceListener;
import net.sf.marineapi.nmea.io.SentenceReader;
import net.sf.marineapi.nmea.sentence.GGASentence;
import net.sf.marineapi.nmea.sentence.SentenceId;

/**
 * Simple example application that takes a filename as command-line argument and
 * prints Position from received GGA sentences.
 * 
 * @author Kimmo Tuukkanen
 */
public class FileExample implements SentenceListener {

    private SentenceReader reader;

    /**
     * Creates a new instance of FileExample
     * 
     * @param f File from which to read Checksum data
     */
    public FileExample(File file) throws IOException {

        // create sentence reader and provide input stream
        InputStream stream = new FileInputStream(file);
        reader = new SentenceReader(stream);

        // register self as a listener for GGA sentences
        reader.addSentenceListener(this, SentenceId.GGA);
        reader.start();
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.event.SentenceListener#readingPaused()
     */
    public void readingPaused() {
        System.out.println("-- Paused --");
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.event.SentenceListener#readingStarted()
     */
    public void readingStarted() {
        System.out.println("-- Started --");
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.event.SentenceListener#readingStopped()
     */
    public void readingStopped() {
        System.out.println("-- Stopped --");
    }

    /**
     * Implements SentenceReader interface for receiving NMEA updates from
     * SentenceReader.
     */
    public void sentenceRead(SentenceEvent event) {

        // Safe to cast as we are registered only for GGA updates, could
        // also cast to PositionSentence if interested only in position.
        // If you receiving all sentences without filtering, check the sentence
        // type before casting (e.g. with Sentence.getSentenceId()).
        GGASentence s = (GGASentence) event.getSentence();

        // do something with sentence data..
        System.out.println(s.getPosition());
    }

    /**
     * Main method takes one command-line argument, the name of the file to
     * read.
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
