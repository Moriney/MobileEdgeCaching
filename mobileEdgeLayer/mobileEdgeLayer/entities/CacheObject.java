package mobileEdgeLayer.entities;

public class CacheObject implements ICacheObject {
	
	long timeToLeave;
	String key;
	String value;
	
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

	
	
	public CacheObject() {
		
		// TODO Auto-generated constructor stub
	}

}
