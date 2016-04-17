/* 
 * Position.java
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
package net.sf.marineapi.nmea.util;

import java.text.DecimalFormat;

/**
 * Represents a geographic position. Default datum is WGS84 as generally in NMEA
 * 0183. Notice that datum is only informative and it does not affect
 * calculations or handling of other values.
 * 
 * @author Kimmo Tuukkanen
 */
public class Position {

	// latitude degrees
	private double latitude;
	// longitude degrees
	private double longitude;
	// altitude
	private double altitude = 0.0;
	// datum/coordinate system
	private Datum datum = Datum.WGS84;

	/**
	 * Creates a new instance of Position. Notice that altitude defaults to -0.0
	 * and may be set later.
	 * 
	 * @param lat Latitude degrees
	 * @param lon Longitude degrees
	 * @see #setAltitude(double)
	 */
	public Position(double lat, double lon) {
		setLatitude(lat);
		setLongitude(lon);
	}

	/**
	 * Creates a new instance of position with latitude, longitude and altitude.
	 * 
	 * @param lat Latitude degrees
	 * @param lon Longitude degrees
	 * @param alt Altitude value, in meters.
	 */
	public Position(double lat, double lon, double alt) {
		this(lat, lon);
		this.altitude = alt;
	}

	/**
	 * Creates new instance of Position with latitude, longitude and datum.
	 * Notice that altitude defaults to -0.0 and may be set later.
	 * 
	 * @param lat Latitude degrees
	 * @param lon Longitude degrees
	 * @param datum Datum to set
	 * @see #setAltitude(double)
	 */
	public Position(double lat, double lon, Datum datum) {
		this(lat, lon);
		this.datum = datum;
	}

	/**
	 * Creates new instance of Position with latitude, longitude, altitude and
	 * datum.
	 * 
	 * @param lat Latitude degrees
	 * @param lon Longitude degrees
	 * @param datum Datum to set
	 */
	public Position(double lat, double lon, double alt, Datum datum) {
		this(lat, lon, alt);
		this.datum = datum;
	}

	/**
	 * Calculates distance to specified <code>Position</code>.
	 * <p>
	 * The Distance is calculated using the <a
	 * href="http://en.wikipedia.org/wiki/Haversine_formula">Haversine
	 * formula</a>. Implementation is based on example found at <a href=
	 * "http://www.codecodex.com/wiki/Calculate_Distance_Between_Two_Points_on_a_Globe"
	 * >codecodex.com</a>.
	 * <p>
	 * Earth radius <a
	 * href="http://en.wikipedia.org/wiki/Earth_radius#Mean_radius">earth
	 * radius</a> used in calculation is <code>6366.70702</code> km, based on
	 * the assumption that 1 degrees is exactly 60 NM.
	 * 
	 * @param pos Position to which the distance is calculated.
	 * @return Distance to po<code>pos</code> in meters.
	 */
	public double distanceTo(Position pos) {
		return haversine(getLatitude(), getLongitude(), pos.getLatitude(),
			pos.getLongitude());
	}

	/**
	 * Gets the position altitude from mean sea level. Notice that most
	 * sentences with position don't provide this value. When missing, the
	 * default value in <code>Position</code> is 0.0.
	 * 
	 * @return Altitude value in meters
	 */
	public double getAltitude() {
		return altitude;
	}

	/**
	 * Gets the datum, i.e. the coordinate system used to define geographic
	 * position. Default is {@link Datum#WGS84}, unless datum is specified in
	 * the constructor. Notice also that datum cannot be set afterwards.
	 * 
	 * @return Datum enum
	 */
	public Datum getDatum() {
		return datum;
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
	 * Get the hemisphere of latitude, North or South.
	 * 
	 * @return CompassPoint.NORTH or CompassPoint.SOUTH
	 */
	public CompassPoint getLatitudeHemisphere() {
		return isLatitudeNorth() ? CompassPoint.NORTH : CompassPoint.SOUTH;
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
	 * Get the hemisphere of longitude, East or West.
	 * 
	 * @return CompassPoint.EAST or CompassPoint.WEST
	 */
	public CompassPoint getLongitudeHemisphere() {
		return isLongitudeEast() ? CompassPoint.EAST : CompassPoint.WEST;
	}

	/**
	 * Tells if the latitude is on northern hemisphere.
	 * 
	 * @return true if northern, otherwise false (south).
	 */
	public boolean isLatitudeNorth() {
		return getLatitude() >= 0.0;
	}

	/**
	 * Tells if the longitude is on eastern hemisphere.
	 * 
	 * @return true if eastern, otherwise false (west).
	 */
	public boolean isLongitudeEast() {
		return getLongitude() >= 0.0;
	}

	/**
	 * Sets the altitude of position above or below mean sea level. Defaults to
	 * zero (-0.0).
	 * 
	 * @param altitude Altitude value to set, in meters.
	 */
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	/**
	 * Set the latitude degrees of Position
	 * 
	 * @param latitude the latitude to set
	 * @throws IllegalArgumentException If specified latitude value is out of
	 *             range 0..90 degrees.
	 */
	public void setLatitude(double latitude) {
		if (latitude < -90 || latitude > 90) {
			throw new IllegalArgumentException(
				"Latitude out of bounds -90..90 degrees");
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
		if (longitude < -180 || longitude > 180) {
			throw new IllegalArgumentException(
				"Longitude out of bounds -180..180 degrees");
		}
		this.longitude = longitude;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("00.0000000");
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(df.format(Math.abs(getLatitude())));
		sb.append(" ");
		sb.append(getLatitudeHemisphere().toChar());
		sb.append(", ");
		df.applyPattern("000.0000000");
		sb.append(df.format(Math.abs(getLongitude())));
		sb.append(" ");
		sb.append(getLongitudeHemisphere().toChar());
		sb.append(", ");
		sb.append(getAltitude());
		sb.append(" m]");
		return sb.toString();
	}

	/**
	 * Convenience method for creating a waypoint based in the Position.
	 * 
	 * @param id Waypoint ID or name
	 * @return the created Waypoint
	 */
	public Waypoint toWaypoint(String id) {
		return new Waypoint(id, getLatitude(), getLongitude());
	}

	/**
	 * Haversine formulae, implementation based on example at <a href=
	 * "http://www.codecodex.com/wiki/Calculate_Distance_Between_Two_Points_on_a_Globe"
	 * >codecodex</a>.
	 * 
	 * @param lat1 Origin latitude
	 * @param lon1 Origin longitude
	 * @param lat2 Destination latitude
	 * @param lon2 Destination longitude
	 * @return Distance in meters
	 */
	private double haversine(double lat1, double lon1, double lat2, double lon2) {

		// Mean earth radius (IUGG) = 6371.009
		// Meridional earth radius = 6367.4491
		// Earth radius by assumption that 1 degree equals exactly 60 NM:
		// 1.852 * 60 * 360 / (2 * Pi) = 6366.7 km

		final double earthRadius = 6366.70702;

		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);

		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
			+ Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
			* Math.sin(dLon / 2) * Math.sin(dLon / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return (earthRadius * c * 1000);
	}
}
