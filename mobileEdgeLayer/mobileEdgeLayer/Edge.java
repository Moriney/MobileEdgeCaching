package mobileEdgeLayer;

import mobileEdgeLayer.globalCache.GlobalCache;
import mobileEdgeLayer.localCache.LocalCache;

public class Edge {

	
	private static GlobalCache globalCache=new GlobalCache();
	private static LocalCache localCache=new LocalCache();
	
    public static GlobalCache getGlobalCache(){
	     return globalCache;
	}

	public static LocalCache getLocalCache(){
	     return localCache;
	}
	
	private Edge() {
					
	}
	
}
