package net.sf.marineapi.ais.message;

/**
 * Common interface for all UTC time and position providing AIS messages.
 * 
 * @author Lázár József
 */
public interface AISUTCReport extends AISMessage {

	/**
	 * Returns the UTC year.
	 * @return an integer value representing the UTC year (1-9999)
	 */
	public int getUtcYear();

	/**
	 * Returns the UTC month.
	 * @return an integer value representing the UTC month (1-12)
	 */
	public int getUtcMonth();

	/**
	 * Returns the UTC day.
	 * @return an integer value representing the UTC day (1-31)
	 */
	public int getUtcDay();

	/**
	 * Returns the UTC hour.
	 * @return an integer value representing the UTC hour (0-23)
	 */
	public int getUtcHour();

	/**
	 * Returns the UTC minute.
	 * @return an integer value representing the UTC minute (0-59)
	 */
	public int getUtcMinute();

	/**
	 * Returns the UTC second.
	 * @return an integer value representing the UTC second (0-59)
	 */
	public int getUtcSecond();

	/**
	 * Returns the type of electronic position fixing device.
	 * @return an integer value of the position device
	 */
	public int getTypeOfEPFD();
}
