package net.sf.marineapi.ais.message;

/**
 * Extended Class B Equipment Position Report.
 *  
 * @author Lázár József
 */
public interface AISMessage19 extends AISPositionReportB {

	/**
	 * Returns the name of the transmitting ship.
	 * 
	 * @return maximum 20 characters, representing the name
	 */
	String getName();

	/**
	 * Returns the type of ship and cargo.
	 * 
	 * @return an integer value representing the type of ship and cargo
	 */
	int getTypeOfShipAndCargoType();

	/**
	 * Returns the distance from the reference point to the bow.
	 */
	int getBow();

	/**
	 * Returns the distance from the reference point to the stern of the ship.
	 */
	int getStern();

	/**
	 * Returns the distance from the reference point to the port side of the
	 * ship.
	 */
	int getPort();

	/**
	 * Returns the type of electronic position fixing device.
	 * 
	 * @return an integer value the the type of EPFD
	 */
	int getTypeOfEPFD();
}
