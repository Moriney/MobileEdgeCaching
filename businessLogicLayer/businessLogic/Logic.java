package businessLogic;

import mobileEdgeLayer.Edge;
import mobileEdgeLayer.transfer.Transfer;

public class Logic {
	
	public Logic() {
		// TODO Auto-generated constructor stub
		
	}

	public boolean isReadyToReceive(String key){
		
		try{
			
			if(!Edge.getLocalCache().exists(key))
				getFileToLocal(key);
					
			return true;
			
		}
		
		catch (Exception e)
		{
			// TODO
			return false;
		}
						
	}
	
	
	private void getFileToLocal(String key){
		
		Transfer transfer =new Transfer();
		if(Edge.getGlobalCache().exists(key))
		{
			transfer.getFile(key,Edge.getGlobalCache().getBeaconNodes(key));
			Edge.getLocalCache().add(key);
		}
		else
		{
			//getting the file from the real server
			transfer.getFile(key);
			Edge.getLocalCache().add(key);
			
			//Mark this station as the beacon node for this key in the global cache
			Edge.getGlobalCache().add(key);
			
		}
	
		
	}

	}
