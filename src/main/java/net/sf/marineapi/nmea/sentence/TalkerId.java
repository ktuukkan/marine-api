/*
 * TalkerId.java
 * Copyright (C) 2010 Kimmo Tuukkanen
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
package net.sf.marineapi.nmea.sentence;

/**
 * The enumeration of Talker IDs, i.e. the first two characters in sentence's
 * address field. For example, <code>GP</code> in <code>$GPGGA</code>. Notice
 * that proprietary sentences are identified by single character {@link #P}.
 * <p>
 * This enum contains also non-NMEA IDs to enable parsing AIS messages, see for
 * more details in <a href="http://catb.org/gpsd/AIVDM.html">AIVDM/AIVDO
 * protocol decoding</a> by Eric S. Raymond.
 * 
 * @author Kimmo Tuukkanen
 * @see net.sf.marineapi.nmea.sentence.SentenceId
 */
public enum TalkerId {

	/** NMEA 4.0 Base AIS station */
	AB,
	/** MMEA 4.0 Dependent AIS Base Station */
	AD,
	/** Autopilot - General */
	AG,
	/** Mobile AIS station */
	AI,
	/** NMEA 4.0 Aid to Navigation AIS station */
	AN,
	/** Autopilot - Magnetic */
	AP,
	/** NMEA 4.0 AIS Receiving Station */
	AR,
	/** NMEA 4.0 Limited AIS Base Station */
	AS,
	/** NMEA 4.0 AIS Transmitting Station */
	AT,
	/** NMEA 4.0 Repeater AIS station */
	AX,
	/** BeiDou - Chinese satellite navigation system */
	BD,
	/** Base AIS station (deprecated in NMEA 4.0) */
	BS,
	/** Computer - Programmed Calculator (obsolete) */
	@Deprecated
	CC,
	/** Communications - Digital Selective Calling (DSC) */
	CD,
	/** Computer - Memory Data (obsolete) */
	@Deprecated
	CM,
	/** Channel Pilot (Navicom Dynamics proprietary) */
	CP,
	/** Communications - Satellite */
	CS,
	/** Communications - Radio-Telephone (MF/HF) */
	CT,
	/** Communications - Radio-Telephone (VHF) */
	CV,
	/** Communications - Scanning Receiver */
	CX,
	/** DECCA Navigation (obsolete) */
	@Deprecated
	DE,
	/** Direction Finder */
	DF,
	/** Velocity Sensor, Speed Log, Water, Magnetic */
	DM,
	/** Electronic Chart Display &lt; Information System (ECDIS) */
	EC,
	/** Emergency Position Indicating Beacon (EPIRB) */
	EP,
	/** Engine Room Monitoring Systems */
	ER,
	/** GLONASS (according to IEIC 61162-1) */
	GL,
	/** Mixed GLONASS and GPS data (according to IEIC 61162-1) */
	GN,
	/** Global Positioning System (GPS) */
	GP,
	/** Heading - Magnetic Compass */
	HC,
	/** Heading - North Seeking Gyro */
	HE,
	/** Heading - Non North Seeking Gyro */
	HN,
	/** Integrated Instrumentation */
	II,
	/** Integrated Navigation */
	IN,
	/** Loran A (obsolete) */
	@Deprecated
	LA,
	/** Loran C (obsolete) */
	@Deprecated
	LC,
	/** Microwave Positioning System (obsolete) */
	@Deprecated
	MP,
	/** OMEGA Navigation System (obsolete) */
	@Deprecated
	OM,
	/** Distress Alarm System (obsolete) */
	@Deprecated
	OS,
	/** Proprietary sentence (does not define the talker device). */
	P,
	/** RADAR and/or ARPA */
	RA,
	/** NMEA 4.0 Physical Shore AIS Station */
	SA,
	/** Sounder, Depth */
	SD,
	/** Electronic Positioning System, other/general */
	SN,
	/** Sounder, Scanning */
	SS,
	/** Turn Rate Indicator */
	TI,
	/** TRANSIT Navigation System */
	TR,
	/** Velocity Sensor, Doppler, other/general */
	VD,
	/** Velocity Sensor, Speed Log, Water, Mechanical */
	VW,
	/** Weather Instruments */
	WI,
	/** Transducer - Temperature (obsolete) */
	@Deprecated
	YC,
	/** Transducer - Displacement, Angular or Linear (obsolete) */
	@Deprecated
	YD,
	/** Transducer - Frequency (obsolete) */
	@Deprecated
	YF,
	/** Transducer - Level (obsolete) */
	@Deprecated
	YL,
	/** Transducer - Pressure (obsolete) */
	@Deprecated
	YP,
	/** Transducer - Flow Rate (obsolete) */
	@Deprecated
	YR,
	/** Transducer - Tachometer (obsolete) */
	@Deprecated
	YT,
	/** Transducer - Volume (obsolete) */
	@Deprecated
	YV,
	/** Transducer */
	YX,
	/** Timekeeper - Atomic Clock */
	ZA,
	/** Timekeeper - Chronometer */
	ZC,
	/** Timekeeper - Quartz */
	ZQ,
	/** Timekeeper - Radio Update, WWV or WWVH */
	ZV;

	/**
	 * Parses the talker id from specified sentence String and returns the
	 * corresponding TalkerId enum using the {@link #valueOf(String)} method.
	 * 
	 * @param nmea Sentence String
	 * @return TalkerId enum
	 * @throws IllegalArgumentException If specified String is not recognized as
	 *             NMEA sentence
	 */
	public static TalkerId parse(String nmea) {

		if (!SentenceValidator.isSentence(nmea)) {
			throw new IllegalArgumentException("String is not a sentence");
		}

		String tid = "";
		if (nmea.startsWith("$P")) {
			tid = "P";
		} else {
			tid = nmea.substring(1, 3);
		}
		return TalkerId.valueOf(tid);
	}
}
