/* 
 * Position.java
 * Copyright (C) 2010 Kimmo Tuukkanen
 * 
 * This file is part of Java Marine API.
 * <http://sourceforge.net/projects/marineapi/>
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
package net.sf.marineapi.nmea.util;

/**
 * Represents a geographic position. The default datum is WGS84, unless some
 * other datum is explicitly specified.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public class Position {

    // latitude degrees
    private double latitude;
    // longitude degrees
    private double longitude;
    // hemisphere of latitude
    private Direction lathem;
    // hemisphere of longitude
    private Direction lonhem;
    // altitude
    private double altitude = -0.0;
    // datum/coordinate system
    private Datum datum = Datum.WGS84;

    /**
     * Creates a new instance of Position. Notice that altitude defaults to 0.0,
     * unless set with <code>setAltitude(int)</code> method.
     * 
     * @param lat Latitude degrees
     * @param lath Hemisphere of latitude
     * @param lon Longitude degrees
     * @param lonh Hemisphere of longitude
     */
    public Position(double lat, Direction lath, double lon, Direction lonh) {
        setLatitude(lat);
        setLongitude(lon);
        setLatHemisphere(lath);
        setLonHemisphere(lonh);
    }

    /**
     * Creates new instance of Position. Notice that altitude defaults to 0.0,
     * unless set with <code>setAltitude(int)</code> method.
     * 
     * @param lat Latitude degrees
     * @param lath Hemisphere of latitude
     * @param lon Longitude degrees
     * @param lonh Hemisphere of longitude
     * @param datum Datum
     */
    public Position(double lat, Direction lath, double lon, Direction lonh,
            Datum datum) {
        this(lat, lath, lon, lonh);
        this.datum = datum;
    }

    /**
     * Calculates the distance to specified Position.
     * <p>
     * Calculation is done by using the <a
     * href="http://en.wikipedia.org/wiki/Haversine_formula">Haversine
     * formula</a>. The mean <a
     * href="http://en.wikipedia.org/wiki/Earth_radius#Mean_radius">earth
     * radius</a> used in calculation is <code>6371.009</code> km.
     * <p>
     * The implementation is based on example found at <a href=
     * "http://www.codecodex.com/wiki/Calculate_Distance_Between_Two_Points_on_a_Globe"
     * >codecodex.com</a>.
     * 
     * @param p Position to which the distance is calculated.
     * @return Distance to <code>p</code> in meters.
     */
    public double distanceTo(Position p) {
        return haversine(getLatitude(), getLongitude(), p.getLatitude(), p
                .getLongitude());
    }

    /**
     * Gets the position altitude from mean sea level.
     * 
     * @return Altitude value in meters
     */
    public double getAltitude() {
        return altitude;
    }

    /**
     * Gets the datum, i.e. the coordinate system used to define geographic
     * position. Default is <code>Datum.WGS84</code>, unless datum is specified
     * in the constructor. Notice also that datum cannot be set afterwards.
     * 
     * @return Datum enum
     */
    public Datum getDatum() {
        return datum;
    }

    /**
     * Get the hemisphere of latitude (North/South).
     * 
     * @return Direction.NORTH or Direction.SOUTH
     */
    public Direction getLatHemisphere() {
        return this.lathem;
    }

    /**
     * Get latitude value of Position
     * 
     * @return latitude degrees
     */
    public double getLatitude() {
        return this.latitude;
    }

    /**
     * Get longitude value of Position
     * 
     * @return longitude degrees
     */
    public double getLongitude() {
        return this.longitude;
    }

    /**
     * Get the hemisphere of longitude (East/West).
     * 
     * @return CompassPoint.E or CompassPoint.W
     */
    public Direction getLonHemisphere() {
        return this.lonhem;
    }

    /**
     * Sets the altitude of position above mean sea level. Defaults to zero.
     * 
     * @param altitude Altitude value to set, in meters.
     */
    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    /**
     * Set the hemisphere of latitude (North/South).
     * 
     * @param lathem The hemisphere to set
     * @throws IllegalArgumentException If specified hemisphere is other than
     *             NORTH or SOUTH.
     */
    public void setLatHemisphere(Direction lathem) {
        if (Direction.NORTH.equals(lathem) || Direction.SOUTH.equals(lathem)) {
            this.lathem = lathem;
        } else {
            throw new IllegalArgumentException(
                    "Valid hemisphere for latitude is N or S");
        }
    }

    /**
     * Set the latitude degrees of Position
     * 
     * @param latitude the latitude to set
     * @throws IllegalArgumentException If specified latitude value is out of
     *             range 0..90 degrees.
     */
    public void setLatitude(double latitude) {
        if (latitude < 0 || latitude > 90) {
            throw new IllegalArgumentException(
                    "Latitude out of bounds 0..90 degrees");
        }
        this.latitude = latitude;
    }

    /**
     * Set the longitude degrees of Position
     * 
     * @param longitude the longitude to set
     * @throws IllegalArgumentException If specified longitude value is out of
     *             range 0..180 degrees.
     */
    public void setLongitude(double longitude) {
        if (longitude < 0 || longitude > 180) {
            throw new IllegalArgumentException(
                    "Longitude out of bounds 0..180 degrees");
        }
        this.longitude = longitude;
    }

    /**
     * Set the hemisphere of longitude (East/West).
     * 
     * @param lonhem The hemisphere to set
     * @throws IllegalArgumentException If specified hemisphere is other than
     *             EAST or WEST.
     */
    public void setLonHemisphere(Direction lonhem) {
        if (Direction.EAST.equals(lonhem) || Direction.WEST.equals(lonhem)) {
            this.lonhem = lonhem;
        } else {
            throw new IllegalArgumentException(
                    "Valid hemisphere for longitude is E or W");
        }
    }

    /**
     * Convenience method for creating a waypoint based in the Position.
     * 
     * @param id Waypoint ID or name
     * @return the created Waypoint
     */
    public Waypoint toWaypoint(String id) {
        return new Waypoint(id, getLatitude(), getLatHemisphere(),
                getLongitude(), getLonHemisphere());
    }

    /**
     * Haversine formulae implementation based on example at
     * http://www.codecodex
     * .com/wiki/Calculate_Distance_Between_Two_Points_on_a_Globe
     * 
     * @param lat1 Origin latitude
     * @param lon1 Origin longitude
     * @param lat2 Destination latitude
     * @param lon2 Destination longitude
     * @return Distance in meters
     */
    private double haversine(double lat1, double lon1, double lat2, double lon2) {

		// Meridional Earth radius
		// final double earthRadius = 6367.4491;
		// a bit tweaked radius seems to produce more accurate results..?
		final double earthRadius = 6366.70702;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double result = earthRadius * c * 1000;

        return result;
    }

    /**
     * Vincenty formulae implementation based on example at
     * http://www.movable-type.co.uk/scripts/latlong-vincenty.html
     * 
     * @param lat1 Origin latitude
     * @param lon1 Origin longitude
     * @param lat2 Destination latitude
     * @param lon2 Destination longitude
     * @return Distance in meters
     */
    private double vincenty(double lat1, double lon1, double lat2, double lon2) {

        // WGS-84 ellipsoid params
        int a = 6378137;
        double b = 6356752.3142;
        double f = 1 / 298.257223563;

        double L = Math.toRadians(lon2 - lon1);
        double U1 = Math.atan((1 - f) * Math.tan(Math.toRadians(lat1)));
        double U2 = Math.atan((1 - f) * Math.tan(Math.toRadians(lat2)));
        double sinU1 = Math.sin(U1), cosU1 = Math.cos(U1);
        double sinU2 = Math.sin(U2), cosU2 = Math.cos(U2);

        double lambda = L;
        double lambdaP;
        double sinSigma;
        double cosSqAlpha;
        double cos2SigmaM;
        double sinAlpha;
        double sigma;
        double cosSigma;

        int iterLimit = 100;

        do {
            double sinLambda = Math.sin(lambda), cosLambda = Math.cos(lambda);
            sinSigma = Math.sqrt((cosU2 * sinLambda) * (cosU2 * sinLambda)
                    + (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda)
                    * (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda));

            if (sinSigma == 0) {
                // co-incident points
                return 0;
            }

            cosSigma = sinU1 * sinU2 + cosU1 * cosU2 * cosLambda;
            sigma = Math.atan2(sinSigma, cosSigma);
            sinAlpha = cosU1 * cosU2 * sinLambda / sinSigma;
            cosSqAlpha = 1 - sinAlpha * sinAlpha;
            cos2SigmaM = cosSigma - 2 * sinU1 * sinU2 / cosSqAlpha;

            if (cos2SigmaM == Double.NaN) {
                // equatorial line: cosSqAlpha=0 (§6)
                cos2SigmaM = 0;
            }

            double C = f / 16 * cosSqAlpha * (4 + f * (4 - 3 * cosSqAlpha));
            lambdaP = lambda;
            lambda = L
                    + (1 - C)
                    * f
                    * sinAlpha
                    * (sigma + C
                            * sinSigma
                            * (cos2SigmaM + C * cosSigma
                                    * (-1 + 2 * cos2SigmaM * cos2SigmaM)));

        } while (Math.abs(lambda - lambdaP) > 1e-12 && --iterLimit > 0);

        if (iterLimit == 0) {
            return Double.NaN; // formula failed to converge
        }

        double uSq = cosSqAlpha * (a * a - b * b) / (b * b);
        double A = 1 + uSq / 16384
                * (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)));
        double B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)));
        double deltaSigma = B
                * sinSigma
                * (cos2SigmaM + B
                        / 4
                        * (cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM) - B
                                / 6 * cos2SigmaM
                                * (-3 + 4 * sinSigma * sinSigma)
                                * (-3 + 4 * cos2SigmaM * cos2SigmaM)));
        double s = b * A * (sigma - deltaSigma);

        s = Math.round(s * 1000); // round to 1mm precision (#.###)

        return s / 1000;
    }
}
