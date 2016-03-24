package main.java;
import java.io.IOException;
import java.sql.Date;
import java.util.Random;
import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.zookeeper.AsyncCallback.ChildrenCallback;
import org.apache.zookeeper.AsyncCallback.DataCallback;
import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.AsyncCallback.StringCallback;
import org.apache.zookeeper.AsyncCallback.VoidCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.NoNodeException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.book.ChildrenCache;
import org.apache.zookeeper.book.recovery.RecoveredAssignments;
import org.apache.zookeeper.book.recovery.RecoveredAssignments.RecoveryCallback;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class myServer implements Watcher {
	
	ZooKeeper zk;
	String hostPort;
	
	protected ChildrenCache entriesLocalCache;
	private static final Logger LOG = LoggerFactory.getLogger(myServer.class);
	
	myServer(String hostPort) {
	this.hostPort = hostPort;
	}
		
	void startZK() throws IOException {
	zk = new ZooKeeper(hostPort, 15000, this);
	}
	
	public void process(WatchedEvent e) {
	System.out.println(e);
	System.out.println("---------------------------------");
	}
		
	void stopZK() throws Exception { zk.close(); }

	/*String znodeContent = "ThisISaTEST!";
	Boolean checkNode() throws InterruptedException, KeeperException {
		while (true) {
		try {
			
			//LOG.info("Running for master");
	        zk.create("/cache", 
	        		znodeContent.getBytes(), 
	                Ids.OPEN_ACL_UNSAFE, 
	                CreateMode.PERSISTENT
	                );
			
		
	        return  true;
		
		} 
		
		catch (KeeperException.NodeExistsException e) 
		{
			return false;
		
		} 
		
		catch (KeeperException.ConnectionLossException e) 
		{
			return false;
			
		}
		
		}
	}*/
	
	
	byte[] checkData() throws KeeperException, InterruptedException {
		
		Stat stat = new Stat();
		byte data[] = zk.getData("/cache", false, stat);
		return data;

		
		}

	
		
	String name;

    /**
     * Registering the new node, which consists of adding a node data
     * znode to /cache.
     */
    public void insertCacheEntry(String number){
        name = "entryNode-" + number;
        zk.create("/cache/" + name, //path
                "Idle".getBytes(),  //data
                Ids.OPEN_ACL_UNSAFE, //ACL
                CreateMode.PERSISTENT_SEQUENTIAL, //znode mode
                createEntryCallback, //Asynchronous callback
                cacheChangeWatcher); //set watcher on the created node
    }
    
    StringCallback createEntryCallback = new StringCallback() {
        public void processResult(int rc, String path, Object ctx, String name) {
            switch (Code.get(rc)) { 
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
    
    
    Watcher cacheChangeWatcher = new Watcher() {
        public void process(WatchedEvent e) {
            if(e.getType() == EventType.NodeChildrenChanged) {
                assert "/cache".equals( e.getPath() );

                getCacheEntries();
            }
        }
    };


    void getCacheEntries(){
        zk.getChildren("/cache", //get cache entries
                cacheChangeWatcher,  
                cacheGetChildrenCallback,
                null);
    }

    ChildrenCallback cacheGetChildrenCallback = new ChildrenCallback() {
        public void processResult(int rc, String path, Object ctx, List<String> children){
            switch(Code.get(rc)) {
                case CONNECTIONLOSS:
                    getCacheEntries();

                    break;
                case OK:
                    System.out.print("-----------> Change detected!!! ");
                    List<String> toProcess;
                    if(entriesLocalCache == null) {
                        entriesLocalCache = new ChildrenCache(children);

                        toProcess = children;
                    } else {
                        toProcess = entriesLocalCache.addedAndSet( children );
                        System.out.print("Nodes are loaded! ");
                    }

                    if(toProcess != null){
                       // assignTasks(toProcess);
                    }

                    break;
                default:
                    LOG.error("getChildren failed.",
                            KeeperException.create(Code.get(rc), path));
            }
        }
    };
	
	
	//Admin Client
	//============================================================
    
    void listState() throws KeeperException, InterruptedException {
    	try {
    	
    	Stat stat = new Stat();
    	
    	byte masterData[] = zk.getData("/cache", false, stat);
    	Date startDate = new Date(stat.getCtime());
    	System.out.println("cache1: " + new String(masterData) +
    	" since " + startDate);
    	} catch (NoNodeException e) {
    	System.out.println("No cache");
    	}
    	
    	
    	System.out.println("EntryNodes:");
    	for (String w: zk.getChildren("/cache", false)) {   //get all the cache entry names
    	byte data[] = zk.getData("/cache/" + w, false, null); //get cache entry data 
    	String state = new String(data);
    	System.out.println("\t" + w + ": " + state);
    	}
    	
    	
    	/*System.out.println("Tasks:");
    	for (String t: zk.getChildren("/assign", false)) {
    	System.out.println("\t" + t);
    	}*/
    	
    	}

    List<String> getRecords() throws KeeperException, InterruptedException {
    	try {
    	    	
    	return zk.getChildren("/cache", false);
    	
    	}
    	catch (NoNodeException e) {
        	System.out.println("No cache");
        	return null;
    	}
    }
    
    

    boolean existRecord(String key) throws KeeperException, InterruptedException {
    {
    	
    	for (String w: zk.getChildren("/cache", false)) {   //get all the cache entry names
        	if(key==w)
        		{
        		return true;

        		}
       	}
    	return false;
    	}
    
    
    }
    
    
    //============================================================
	
	public static void main(String args[])
	throws Exception {
		myServer m = new myServer(args[0]);
	
		m.startZK();
	// wait for a bit
	
	//System.out.println("test");
	
	
	m.insertCacheEntry("111-");
	
	
	Thread.sleep(6000);
	
	m.insertCacheEntry("222-");
	
	
	m.listState();
	
	/*System.out.println(m.checkNode().toString());

	Thread.sleep(6000);
	System.out.println(m.checkData().toString());
	*/
	
	
	
	m.stopZK();

	
	}
	}



