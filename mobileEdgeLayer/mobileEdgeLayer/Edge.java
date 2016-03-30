package mobileEdgeLayer;

import mobileEdgeLayer.globalCache.GlobalCache;
import mobileEdgeLayer.localCache.LocalCache;

public class Edge {

	
	private static GlobalCache globalCache=new GlobalCache();
	private static LocalCache localCache=new LocalCache();
	
    private static Edge singletonInstance =new Edge();
	
	//Get the singleton instance
	public static Edge getInstance(){
	     return singletonInstance;
	}
	
	public static GlobalCache getGlobalCache(){
	     return globalCache;
	}

	public static LocalCache getLocalCache(){
	     return localCache;
	}
	
	public Edge() {

		
			
	}
	
}
