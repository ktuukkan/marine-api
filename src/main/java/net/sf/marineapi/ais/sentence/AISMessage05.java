package net.sf.marineapi.ais.sentence;

/**
 *
 * @author Lázár József
 */
public interface AISMessage05 {

	/**
	 * Returns the AIS version indicator for the current message.
	 * @return AIS version indicator
	 */
	public int getAISVersionIndicator();

	/**
	 * Returns the IMO number of the transmitting ship.
	 * @return an integer value representing the IMO number (1-999999999)
	 */
	public int getIMONumber();

	/**
	 * Returns the call sign of the transmitting ship.
	 * @return at most 7 characters, representing the call sign
	 */
	public String getCallSign();

	/**
	 * Returns the name of the transmitting ship.
	 * @return maximum 20 characters, representing the name
	 */
	public String getName();

	/**
	 * Returns the type of ship and cargo.
	 * @return an integer value representing the type of ship and cargo
	 */
	public int getTypeOfShipAndCargoType();

	/**
	 * Returns the distance from the reference point to the bow.
	 */
	public int getBow();

	/**
	 * Returns the distance from the reference point to the stern of the ship.
	 */
	public int getStern();

	/**
	 * Returns the distance from the reference point to the port side of the ship.
	 */
	public int getPort();

	/**
	 * Returns the distance from the reference point to the starboard side of the ship.
	 */
	public int getStarboard();

	/**
	 * Returns the type of electronic position fixing device.
	 * @return an integer value the the type of EPFD
	 */
	public int getTypeOfEPFD();

	/**
	 * Returns the month, day, hour and minute parts of the estimated time of arrival (ETA).
	 */
	public int getETAMonth();

	public int getETADay();
	
	public int getETAHour();
	
	public int getETAMinute();
	
	/**
	 * Returns the maximum draught.
	 * @return an integer value of the maximum static draught in 1/10 m
	 */
	public int getMaximumDraught();

	/**
	 * Returns the destination.
	 * @return maximum 20 characters, representing the destination
	 */
	public String getDestination();
}
