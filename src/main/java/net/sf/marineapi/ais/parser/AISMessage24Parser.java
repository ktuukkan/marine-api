/*
 * AISMessage24Parser.java
 * Copyright (C) 2016 Henri Laurent
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

import net.sf.marineapi.ais.message.AISMessage24;
import net.sf.marineapi.ais.util.ShipType;
import net.sf.marineapi.ais.util.Sixbit;

/**
 *
 * AIS Message 24 implementation: Ship Static Data - Class B
 *
 * Equivalent of a Type 5 message for ships using Class B equipment.
 * Also used to associate an MMSI with a name on either class A or class B equipment.
 *
 * According to the standard, both the A and B parts are supposed to be 168 bits.
 * A parts are often transmitted with only 160 bits, omitting the spare 7 bits at the end.
 *
 * May be in part A or part B format
 *
 *
 * <pre>
 * Part A
 * Field  Name                                      Bits    (from, to )
 * ------------------------------------------------------------------------
 *  1	  messageID                               	   6	(   1,   6)
 *  2	  repeatIndicator                         	   2	(   7,   8)
 *  3	  userID                                  	  30	(   9,  40)
 *  5	  name                                    	 120	( 41,  160)
 *  6     spare                                        8    ( 161, 168)
 *                                                  ---- +
 *                                               sum 168
 *
 * Part B
 * Field  Name                                      Bits    (from, to )
 * ------------------------------------------------------------------------
 *  1	  messageID                               	   6	(   1,   6)
 *  2	  repeatIndicator                         	   2	(   7,   8)
 *  3	  userID                                  	  30	(   9,  40)
 *  4	  shiptype                                	   8	(  41,  48)
 *  5     vendorid                                    18    (  49,  66)
 *  6     model                                        4    ( 67,   70)
 *  7     serial                                      20    ( 71,   90)
 *  8     callsign                                    42    ( 90,  132)
 *  9	  dimension                               	  30	( 133, 162)
 * 15	  spare                                   	   1	( 163, 168)
 *                                                  ---- +
 *                                               sum 168
 * </pre>
 *
 * @author Henri Laurent
 */
class AISMessage24Parser extends AISMessageParser implements AISMessage24 {
    private static final int PARTNUMBER = 0;
    // Part A
    private static final int NAME = 1;
    // Part B
    private static final int TYPEOFSHIPANDCARGO = 1;
    private static final int VENDORID = 2;
    private static final int UNITMODELCODE = 3;
    private static final int SERIALNUMBER = 4;
    private static final int CALLSIGN = 5;
    private static final int BOW = 6;
    private static final int STERN = 7;
    private static final int PORT = 8;
    private static final int STARBOARD = 9;

    private static final int[] FROM_A = { 38, 40, 160 };
    private static final int[] TO_A = { 40, 160, 168 };
    private static final int[] FROM_B = { 38, 40, 48, 66, 70, 90, 132, 141, 150, 156 };
    private static final int[] TO_B = { 40, 48, 66, 70, 90, 132, 141, 150, 156, 162 };

    private int fPartNumber;
    private String fName;
    private int fShipAndCargoType;
    private String fVendorId;
    private int fUnitModelCode;
    private int fSerialNumber;
    private String fCallSign;
    private int fBow;
    private int fStern;
    private int fPort;
    private int fStarboard;

    /**
     * Constructor.
     *
     * @param content Six-bit message content.
     */
    public AISMessage24Parser(Sixbit content) {
        super(content, 160, 168);
        this.fPartNumber = content.getInt(FROM_A[PARTNUMBER], TO_A[PARTNUMBER]);
        this.fPartNumber = content.getInt(FROM_A[PARTNUMBER], TO_A[PARTNUMBER]);
        if(this.fPartNumber == 0 && (content.length() == 160 || content.length() == 168) ){
            // Part A
            this.fName = content.getString(FROM_A[NAME], TO_A[NAME]);
        } else if(this.fPartNumber == 1 && content.length() == 168){
            //Part B
            this.fShipAndCargoType = content.getInt(FROM_B[TYPEOFSHIPANDCARGO], TO_B[TYPEOFSHIPANDCARGO]);
            this.fVendorId = content.getString(FROM_B[VENDORID], TO_B[VENDORID]);
            this.fUnitModelCode = content.getInt(FROM_B[UNITMODELCODE], TO_B[UNITMODELCODE]);
            this.fSerialNumber = content.getInt(FROM_B[SERIALNUMBER], TO_B[SERIALNUMBER]);
            this.fCallSign = content.getString(FROM_B[CALLSIGN], TO_B[CALLSIGN]);
            this.fBow = content.getInt(FROM_B[BOW], TO_B[BOW]);
            this.fStern = content.getInt(FROM_B[STERN], TO_B[STERN]);
            this.fPort = content.getInt(FROM_B[PORT], TO_B[PORT]);
            this.fStarboard = content.getInt(FROM_B[STARBOARD], TO_B[STARBOARD]);
        } else {
            throw new IllegalArgumentException("Invalid part number or message length");
        }
    }

    public int getPartNumber() {
        return this.fPartNumber;
    }

    public String getName() {
        return this.fName;
    }

    public int getTypeOfShipAndCargoType() {
        return this.fShipAndCargoType;
    }

    public String getVendorId() {
        return this.fVendorId;
    }

    public int getUnitModelCode() {
        return this.fUnitModelCode;
    }

    public int getSerialNumber() {
        return this.fSerialNumber;
    }

    public String getCallSign() {
        return this.fCallSign;
    }

    public int getBow() {
        return this.fBow;
    }

    public int getStern() {
        return this.fStern;
    }

    public int getPort() {
        return this.fPort;
    }

    public int getStarboard() {
        return this.fStarboard;
    }



    public String toString() {
        String result = "\tName:      " + this.fName;
        result = result + "\n\tType:      " + ShipType.shipTypeToString(this.fShipAndCargoType);
        result = result + "\n\tVendor id:      " + this.fVendorId;
        result = result + "\n\tUnit Model Code:      " + this.fUnitModelCode;
        result = result + "\n\tSerial Number:      " + this.fSerialNumber;
        result = result + "\n\tCall sign: " + this.fCallSign;
        String dim = "Bow: " + this.fBow + ", Stern: " + this.fStern + ", Port: " + this.fPort + ", Starboard: " + this.fStarboard + " [m]";
        result = result + "\n\tDim:       " + dim;
        return result;
    }
}
