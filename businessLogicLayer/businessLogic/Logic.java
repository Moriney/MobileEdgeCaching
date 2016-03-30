package businessLogic;

import mobileEdgeLayer.Edge;

public class Logic {
	
	public Logic() {
		// TODO Auto-generated constructor stub
		
	}

	public boolean readyToReceive(String key){
		
		try{
			
			if(Edge.getLocalCache().exists(key))
				return true;
					
			else if(Edge.getGlobalCache().exists(key))
			{
				// TODO transfer to local cache
				
				return true;
			}
			else
			{
				// TODO transfer to local cache
				// TODO transfer to global cache
				return true;
			}
		}
		
		catch (Exception e)
		{
			// TODO
			return false;
		}
		
				
	}
	
}
