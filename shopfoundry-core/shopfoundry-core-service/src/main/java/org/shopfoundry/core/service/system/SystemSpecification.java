package org.shopfoundry.core.service.system;

/**
 * System specification interface
 * 
 * @author Bojan Bijelic
 */
public interface SystemSpecification {

	/**
	 * Available processors number getter.
	 * 
	 * @return the list of available processors
	 */
	public int getAvailableProcessors();

	/**
	 * Total amount of free memory available to the JVM (bytes).
	 * 
	 * @return the total amount of free memory available to the JVM (bytes)
	 */
	public long getFreeMemory();
	
	/**
	 * Maximum amount of memory the JVM will attempt to use.
	 * This will return Long.MAX_VALUE if there is no preset limit.
	 * 
	 * @return the maximum amount of memory the JVM will attempt to use
	 */
	public long getMaxMemory();
	
	/**
	 * Total memory currently available to the JVM (bytes)
	 * @return the total memory currently available to the JVM (bytes)
	 */
	public long getTotalMemory();
	
	/**
	 * Host name of the machine
	 * @return the host name of the machine
	 */
	public String getHostname();
}
