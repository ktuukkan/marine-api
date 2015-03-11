package net.sf.marineapi.ais.message;

/**
 * Common interface for all messages providing position reports.
 * 
 * @author Lázár József
 */
public interface AISPositionReport extends AISPositionInfo {

	/**
	 * Returns the navigational status.
	 */
	public int getNavigationalStatus();

	/**
	 * Returns the rate of turn.
	 */
	public int getRateOfTurn();

	/**
	 * Returns the speed over ground.
	 */
	public int getSpeedOverGround();

	/**
	 * Returns the course over ground.
	 */
	public int getCourseOverGround();

	/**
	 * Returns the true heading.
	 */
	public int getTrueHeading();

	/**
	 * Returns the time stamp contained in the message.
	 */
	public int getTimeStamp();

	/**
	 * Returns the manouver indicator.
	 */
	public int getManouverIndicator();
}
