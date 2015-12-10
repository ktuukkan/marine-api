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
	int getUtcYear();

	/**
	 * Returns the UTC month.
	 * @return an integer value representing the UTC month (1-12)
	 */
	int getUtcMonth();

	/**
	 * Returns the UTC day.
	 * @return an integer value representing the UTC day (1-31)
	 */
	int getUtcDay();

	/**
	 * Returns the UTC hour.
	 * @return an integer value representing the UTC hour (0-23)
	 */
	int getUtcHour();

	/**
	 * Returns the UTC minute.
	 * @return an integer value representing the UTC minute (0-59)
	 */
	int getUtcMinute();

	/**
	 * Returns the UTC second.
	 * @return an integer value representing the UTC second (0-59)
	 */
	int getUtcSecond();

	/**
	 * Returns the type of electronic position fixing device.
	 * @return an integer value of the position device
	 */
	int getTypeOfEPFD();
}
