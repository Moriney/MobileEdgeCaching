package mobileEdgeLayer.globalCache;

import mobileEdgeLayer.Config;
import mobileEdgeLayer.entities.CacheObject;


/**
 * @author Morteza Neishaboori
 *
 * A wrapper class to hide the complexities of ZooKeeperNodeManager class
 */
public class GlobalCache {

	//Who has the real cached object?
	
	ZooKeeperNodeManager zookeeper;
	
	public GlobalCache() {
		
		zookeeper = ZooKeeperNodeManager.getInstance();
	}
	
	private long Count;

	// Number of globally cached records
	public long getCount() {
		return Count;
	}

	// Beacon node IP addresses are separated using # character
	public String[] getBeaconNodes( CacheObject object) {
		
		try{
			 String stringBeaconNodes =new String (zookeeper.getData(object.getKey()));
			 return stringBeaconNodes.substring(1).split("#");
			
			}
		catch(Exception e)
		{// TODO: handle exception
			return null;
		}
	}
	
	public void add(CacheObject object) {
		
		zookeeper.insert(Config.getInstance().getCacheRoot(), 
				object.getKey(), 
				("#"+Config.getInstance().getMyIP()).getBytes());
	}
	
	public void remove(CacheObject object) {
		try {
			this.zookeeper.delete(Config.getInstance().getCacheRoot(),object.getKey());
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	public boolean exists(String key){
		
		try {
			if(this.zookeeper.exists(Config.getInstance().getCacheRoot(),key))
				return true;
			else
				return false;
			
		} catch (Exception e) {
			// TODO: handle exception
			
			return false;
		}
	}
	
	
}
