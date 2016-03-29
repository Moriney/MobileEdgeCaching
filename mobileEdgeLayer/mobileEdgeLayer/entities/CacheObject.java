package mobileEdgeLayer.entities;

import mobileEdgeLayer.Config;


public class CacheObject implements ICacheObject {
	
	private long creationTime;
	private long timeToLeave;
	private String key;
	private String value;
	
	public long getTimeToLeave() {
		return timeToLeave;
	}

	public void setTimeToLeave(long timeToLeave) {
		this.timeToLeave = timeToLeave;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Returns an instance of CacheObject class. The timeToLeave is set by reading from Config.
	 *
	 * @param  key  the unique key to the cache object in the system
	 * @param  value the local address to the content
	 * @return CacheObject instance
	 */
	public CacheObject(String key,String value) {
		
		this.key=key;
		this.value=value;
		this.creationTime=System.currentTimeMillis();
		this.timeToLeave=Config.getInstance().getTimeToLeave();
	}

	/**
	 * Returns an instance of CacheObject class. The timeToLeave is set directly.
	 *
	 * @param  key  the unique key to the cache object in the system
	 * @param  value the local address to the content
	 * @param  timeToLeave the validity time of the object in milliseconds
	 * @return CacheObject instance
	 */
	public CacheObject(String key, String value, long timeToLeave) {
		
		this.key=key;
		this.value=value;
		this.creationTime=System.currentTimeMillis();
		this.timeToLeave=timeToLeave;
	}
	
	/**
	 * Returns the cached object validity. 
	 * @return true if valid and false if not
	 */
	@Override
	public boolean isValid(){
		
		if((System.currentTimeMillis()-this.creationTime) < this.timeToLeave)
			return true;
		else
		return false;
		
	}
	
}
