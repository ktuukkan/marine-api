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
	int getNavigationalStatus();

	/**
	 * Returns the rate of turn.
	 */
	int getRateOfTurn();

	/**
	 * Returns the speed over ground.
	 */
	int getSpeedOverGround();

	/**
	 * Returns the course over ground.
	 */
	int getCourseOverGround();

	/**
	 * Returns the true heading.
	 */
	int getTrueHeading();

	/**
	 * Returns the time stamp contained in the message.
	 */
	int getTimeStamp();

	/**
	 * Returns the manouver indicator.
	 */
	int getManouverIndicator();
}
