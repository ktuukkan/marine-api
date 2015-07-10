package net.sf.marineapi.ais.message;

/**
 * Common interface for all Class B messages providing position reports.
 * 
 * @author Lázár József
 */
public interface AISPositionReportB extends AISPositionInfo {

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
}
