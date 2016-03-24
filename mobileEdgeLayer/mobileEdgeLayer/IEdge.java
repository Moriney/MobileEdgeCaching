package mobileEdgeLayer;

import mobileEdgeLayer.globalCache.GlobalCache;
import mobileEdgeLayer.localCache.LocalCache;

public interface IEdge {
	
	GlobalCache globalCache=new GlobalCache();
	LocalCache localCache=new LocalCache();
	

}
