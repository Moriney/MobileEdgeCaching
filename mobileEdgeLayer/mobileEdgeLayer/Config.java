package mobileEdgeLayer;

public class Config {

	private String hostPort;
	private String cacheRoot;
	private String myIP;
	
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
