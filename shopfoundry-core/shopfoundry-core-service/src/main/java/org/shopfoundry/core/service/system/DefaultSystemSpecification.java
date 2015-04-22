package org.shopfoundry.core.service.system;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation of system specification interface.
 * 
 * @author Bojan Bijelic
 */
public class DefaultSystemSpecification implements SystemSpecification {

	private static final Logger logger = LoggerFactory
			.getLogger(DefaultSystemSpecification.class);

	public DefaultSystemSpecification() {
		// Get available processors
		availableProcessors = Runtime.getRuntime().availableProcessors();
		// Get free memory
		freeMemory = Runtime.getRuntime().freeMemory();
		// Max memory
		maxMemory = (Runtime.getRuntime().maxMemory() == Long.MAX_VALUE ? 0
				: Runtime.getRuntime().maxMemory());
		// Get total memory
		totalMemory = Runtime.getRuntime().totalMemory();

		try {
			// Try to resolve hostname
			hostname = Inet4Address.getLocalHost().getHostName();

		} catch (UnknownHostException e) {
			if (logger.isWarnEnabled())
				logger.warn(e.getMessage());

			// set dafault hostname
			hostname = "localhost";
		}
	}

	private int availableProcessors;

	@Override
	public int getAvailableProcessors() {
		return availableProcessors;
	}

	private long freeMemory;

	@Override
	public long getFreeMemory() {
		return freeMemory;
	}

	private long maxMemory;

	@Override
	public long getMaxMemory() {
		return maxMemory;
	}

	private long totalMemory;

	@Override
	public long getTotalMemory() {
		return totalMemory;
	}

	private String hostname;

	@Override
	public String getHostname() {
		return hostname;
	}

	@Override
	public String toString() {
		return "DefaultSystemSpecification [availableProcessors="
				+ availableProcessors + ", freeMemory=" + freeMemory
				+ ", maxMemory=" + maxMemory + ", totalMemory=" + totalMemory
				+ ", hostname=" + hostname + "]";
	}

}
