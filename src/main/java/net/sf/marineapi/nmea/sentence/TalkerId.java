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
 * This enum contains also non-NMEA IDs to enable parsing AIS messages;
 * {@link #AI}, {@link #AB} and {@link #BS}. However, the correct meaning of
 * these are still unconfirmed.
 * 
 * @author Kimmo Tuukkanen
 * @see net.sf.marineapi.nmea.sentence.SentenceId
 */
public enum TalkerId {

	/** AIS - Mobile AIS station */
	AI,
	/** AIS - NMEA 4.0 Base AIS station */
	AB,
	/** AIS - NMEA 4.0 Dependent AIS Base Station */
	AD,
	/** AIS - NMEA 4.0 Aid to Navigation AIS station */
	AN,
	/** AIS - NMEA 4.0 AIS Receiving Station */
	AR,
	/** AIS - NMEA 4.0 Limited Base Station */
	AS,
	/** AIS - NMEA 4.0 AIS Transmitting Station */
	AT,
	/** AIS - NMEA 4.0 Repeater AIS station */
	AX,
	/** AIS - Base AIS station (deprecated in NMEA 4.0) */
	BS,
	/** Autopilot - General */
	AG,
	/** Autopilot - Magnetic */
	AP,
	/** Beidou satellite navigation system (Chinese) */
	BD,
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
	/** Galileo satellite navigation system (European) */
	GA,
	/** Beidou satellite navigation system (Chinese) */
	GB,
	/** Indian Regional Navigation Satellite System (IRNSS) */
	GI,
	/** GLONASS (according to IEIC 61162-1) */
	GL,
	/** Mixed GLONASS and GPS data (according to IEIC 61162-1) */
	GN,
	/** Global Positioning System (GPS) */
	GP,
	/** Quasi Zenith Satellite System (QXSS, Japanese) */
	GQ,
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
	/** OpenPlotter calculated */
	OC,
	/** OMEGA Navigation System (obsolete) */
	@Deprecated
	OM,
	/** Distress Alarm System (obsolete) */
	@Deprecated
	OS,
	/** Proprietary sentence format (does not define the talker device). */
	P,
	/** QZSS regional GPS augmentation system (Japan) */
	QZ,
	/** RADAR and/or ARPA */
	RA,
	/** Propulsion Remote Control System */
	RC,
	/** Indian Regional Navigation Satellite System (IRNSS) */
	IR,
	/** AIS - NMEA 4.0 Physical Shore AIS Station */
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
	/** Velocity Sensor, Speed Log, Water, Magnetic */
	VM,
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
