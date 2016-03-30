package mobileEdgeLayer.globalCache;

import java.util.List;
import java.io.IOException;

import org.apache.zookeeper.AsyncCallback.StringCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mobileEdgeLayer.Config;


/**
 * @author Morteza Neishaboori
 *
 */
public class ZooKeeperNodeManager implements Watcher{
	
	private static ZooKeeperNodeManager singletonInstance =new ZooKeeperNodeManager();
	ZooKeeper zk;
	String hostPort;
	
	//Get the singleton instance
		public static ZooKeeperNodeManager getInstance(){
		     return singletonInstance;
		}
	
	private static final Logger LOG = LoggerFactory.getLogger(ZooKeeperNodeManager.class);
	
	ZooKeeperNodeManager() {
	this.hostPort = Config.getInstance().getHostPort();
	}
		
	
	/**
	 * @throws IOException
	 */
	void startZK() throws IOException {
	zk = new ZooKeeper(hostPort, 15000, this);
	}
	
	void stopZK() throws Exception { zk.close(); }

	@Override
	public void process(WatchedEvent event) {
		System.out.println(event);
	}

	// znode : "/cache"
	byte[] getData(String znode) throws KeeperException, InterruptedException {
		
		Stat stat = new Stat();
		byte data[] = zk.getData(znode, false, stat);
		return data;

		}
	
	 /**
     * Registering the new node, which consists of adding a node data
     * znode to /cache.
     */
    public void insert(String znodePath, String entryName, byte[] data){
      
        zk.create(znodePath + entryName, //overall path
        		data,  //data
                Ids.OPEN_ACL_UNSAFE, //ACL
                CreateMode.PERSISTENT_SEQUENTIAL, //znode mode
                createEntryCallback, //Asynchronous callback
                nodeChangeWatcher); //set watcher on the created node
    }
	
    StringCallback createEntryCallback = new StringCallback() {
        public void processResult(int rc, String path, Object ctx, String name) {
            switch (Code.get(rc)) { 
            
            //TO DO: CHeck the path and object parameters to fix the connection loss
            case CONNECTIONLOSS:
                /*
                 * Try again. Note that registering again is not a problem.
                 * If the znode has already been created, then we get a 
                 * NODEEXISTS event back.
                 */
               // insertCacheEntry();
                
                break;
            case OK:
                LOG.info("Registered successfully: " );
                
                break;
            case NODEEXISTS:
                LOG.warn("Already registered: " );
                
                break;
            default:
                LOG.error("Something went wrong: ", 
                            KeeperException.create(Code.get(rc), path));
            }
        }
    };
    
    Watcher nodeChangeWatcher = new Watcher() {
        public void process(WatchedEvent e) {
            if(e.getType() == EventType.NodeChildrenChanged) {
               // assert "/cache".equals( e.getPath() );
            	
            	//TO DO: use node data changed
            	//TO DO: apply the change
            	//TO DO: update the global cache

               // getCacheEntries();
            }
        }
    };
    
    //getting the sub hierarchy of the znode :"/cache"
    List<String> getEntries(String znode) throws KeeperException, InterruptedException{
    	return zk.getChildren(znode, false);
    }
    
    public void delete(String znodePath, String entryName) throws InterruptedException, KeeperException
    {
    	zk.delete(znodePath + entryName, -1);
    }
    
    public boolean exists(String znodePath, String key) throws KeeperException, InterruptedException{
    	if(zk.exists(znodePath+ key, false)!=null)
    		return true;
    	else
    		return false;
    }


}
