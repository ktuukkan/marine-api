package net.sf.marineapi.nmea.io;

interface DataReader extends Runnable {

	/**
	 * Tells if the reader is currently running, i.e. actively scanning the
	 * input stream for new data.
	 * 
	 * @return <code>true</code> if running, otherwise <code>false</code>.
	 */
	public abstract boolean isRunning();

	/**
	 * Stops the run loop.
	 */
	public abstract void stop();

}