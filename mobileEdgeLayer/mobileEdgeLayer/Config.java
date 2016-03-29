package mobileEdgeLayer;

public class Config {

	private String hostPort;
	private String cacheRoot;
	private String myIP;
	private long timeToLeave;
	
	public long getTimeToLeave() {
		return timeToLeave;
	}

	public void setTimeToLeave(long timeToLeave) {
		this.timeToLeave = timeToLeave;
	}

	public String getMyIP() {
		return myIP;
	}

	public String getCacheRoot() {
		return cacheRoot;
	}

	public String getHostPort() {
		return hostPort;
	}

	public Config() {
		
		//TO DO: read the config file
	}

	private static Config singletonInstance =new Config();
	
	//Get the singleton instance
	public static Config getInstance(){
	     return singletonInstance;
	}

}
