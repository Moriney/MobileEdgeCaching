package mobileEdgeLayer.localCache;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LocalCache {

	private Map<String,String> localCacheTable = Collections.synchronizedMap(new HashMap<String, String>());
	
	
	public LocalCache() {
		// TODO Auto-generated constructor stub
	}
	
	public void cacheClean(){};
	
	public void add(String key){
		//localCacheTable.put(arg0, arg1)
	}
	//remove
	
	public boolean exists(String key){
		if(localCacheTable.containsKey(key))
			return true;
		else
			return false;
	}
	
}
