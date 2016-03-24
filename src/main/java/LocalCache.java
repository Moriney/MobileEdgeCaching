package main.java;

import java.util.ArrayList;
import java.util.List;

public class LocalCache {

	private static List<String> ownBeaconRecords;
	
	private LocalCache (){
		ownBeaconRecords =new ArrayList<String>();;
	}
	
	private static LocalCache singletonInstance;
	
	// Providing Global point of access
    public static LocalCache getSingletonInstance() {
        if (null == singletonInstance) {
            singletonInstance = new LocalCache();
        }
        return singletonInstance;
    }
    
    public boolean existLocal(String key){
    	if (ownBeaconRecords.contains(key))
    		return true;
    	else
    		return false;
    }
    
    public void addLocal(String key){
    	ownBeaconRecords.add(key);
    	
    }
}
