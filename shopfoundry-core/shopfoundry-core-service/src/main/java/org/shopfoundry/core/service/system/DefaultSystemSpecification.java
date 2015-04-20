package org.shopfoundry.core.service.system;

/**
 * Default implementation of system specification interface.
 * 
 * @author Bojan Bijelic
 */
public class DefaultSystemSpecification implements SystemSpecification {
	
	public DefaultSystemSpecification(){
		// Get available processors
		availableProcessors = Runtime.getRuntime().availableProcessors();
		// Get free memory
		freeMemory = Runtime.getRuntime().freeMemory();
		// Max memory
		maxMemory = (Runtime.getRuntime().maxMemory() == Long.MAX_VALUE ? 0 : Runtime.getRuntime().maxMemory());
		// Get total memory
		totalMemory = Runtime.getRuntime().totalMemory();
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[availableProcessors=");
		builder.append(availableProcessors);
		builder.append(" (cores), freeMemory=");
		builder.append(freeMemory);
		builder.append(" (bytes), maxMemory=");
		builder.append(maxMemory);
		builder.append(" (bytes), totalMemory=");
		builder.append(totalMemory);
		builder.append(" (bytes)]");
		return builder.toString();
	}
	
	

}
