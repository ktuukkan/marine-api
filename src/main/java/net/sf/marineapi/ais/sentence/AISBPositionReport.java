package net.sf.marineapi.ais.sentence;

/**
 * Common interface for all Class B messages providing position reports.
 * 
 * @author Lázár József
 */
public interface AISBPositionReport extends AISPositionInfo {

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
}
