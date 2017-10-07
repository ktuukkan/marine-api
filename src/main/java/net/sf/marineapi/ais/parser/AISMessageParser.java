/*
 * AISMessageParser.java
 * Copyright (C) 2015 Lázár József
 *
 * This file is part of Java Marine API.
 * <http://ktuukkan.github.io/marine-api/>
 *
 * Java Marine API is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * Java Marine API is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Java Marine API. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.marineapi.ais.parser;

import java.util.ArrayList;
import java.util.List;

import net.sf.marineapi.ais.message.AISMessage;
import net.sf.marineapi.ais.util.Sixbit;
import net.sf.marineapi.ais.util.Violation;

/**
 * Base class for all AIS messages.
 *
 * @author Lázár József, Kimmo Tuukkanen
 */
public class AISMessageParser implements AISMessage {

    // Common AIS message part
    private static final int MESSAGE_TYPE = 0;
    private static final int REPEAT_INDICATOR = 1;
    private static final int MMSI = 2;
    private static final int[] FROM = { 0, 6, 8 };
    private static final int[] TO = { 6, 8, 38 };

    private Sixbit decoder;
    private String message = "";
    private int fillBits = 0;
    private int lastFragmentNr = 0;

    protected List<Violation> fViolations = new ArrayList<Violation>();


    /**
     * Default constructor.
     */
    public AISMessageParser() {
    }

    /**
     * Constucor with Sixbit content decoder.
     *
     * @param sb Content decoder
     */
    protected AISMessageParser(Sixbit sb) {
        if (sb.length() <= 0) {
            throw new IllegalArgumentException("Sixbit decoder is empty!");
        }
        this.decoder = sb;
    }

    /**
     * Add a new rule violation to this message
     */
    public void addViolation(Violation v) {
        fViolations.add(v);
    }

    /**
     * Returns the number of violations.
     */
    public int getNrOfViolations() {
        return fViolations.size();
    }

    /**
     * Returns list of discoverd data violations.
     */
    public List<Violation> getViolations() {
        return fViolations;
    }

    @Override
    public int getMessageType() {
        return getSixbit().getInt(FROM[MESSAGE_TYPE], TO[MESSAGE_TYPE]);
    }

    @Override
    public int getRepeatIndicator() {
        return getSixbit().getInt(FROM[REPEAT_INDICATOR], TO[REPEAT_INDICATOR]);
    }

    @Override
    public int getMMSI() {
        return getSixbit().getInt(FROM[MMSI], TO[MMSI]);
    }

    /**
     * Returns the six-bit decoder of message.
     *
     * @return Sixbit decoder.
     * @throws IllegalStateException When message payload has not been appended
     *     or Sixbit decoder has not been provided as constructor parameter.
     */
    Sixbit getSixbit() {
        if (decoder == null && message.isEmpty()) {
            throw new IllegalStateException("Message is empty!");
        }
        return decoder == null ? new Sixbit(message, fillBits) : decoder;
    }

    /**
     * Append a paylod fragment to combine messages devivered over multiple
     * sentences.
     *
     * @param fragment Data fragment in sixbit encoded format
     * @param fragmentIndex Fragment number within the fragments sequence (1-based)
     * @param fillBits Number of additional fill-bits
     */
    void append(String fragment, int fragmentIndex, int fillBits) {
        if (fragment == null || fragment.isEmpty()) {
            throw new IllegalArgumentException("Message fragment cannot be null or empty");
        }
        if (fragmentIndex < 1 || fragmentIndex != (lastFragmentNr + 1)) {
            throw new IllegalArgumentException("Invalid fragment index or sequence order");
        }
        if (fillBits < 0) {
            throw new IllegalArgumentException("Fill bits cannot be negative");
        }
        this.lastFragmentNr = fragmentIndex;
        this.message += fragment;
        this.fillBits = fillBits; // we always use the last
    }
}
