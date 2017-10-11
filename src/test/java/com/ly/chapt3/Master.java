package com.ly.chapt3;

import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.ConnectionLossException;
import org.apache.zookeeper.KeeperException.NoNodeException;
import org.apache.zookeeper.KeeperException.NodeExistsException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;


public class Master {
	
	private String host;
	
	private ZooKeeper zk;
	private String serverId;
	
	public Master(String host,String serverId){
		this.host = host;
		this.serverId = serverId;
	}
	
	public void startZK() throws IOException, InterruptedException{
		ZKManager manager = new ZKManager();
		zk = manager.getZookeeper(host);
	}
	
	boolean isLeader = false;
	
	public void runForMaster()  throws InterruptedException, KeeperException{
		
		while(true){
			try{
				try {
					barrier.await();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
				zk.create("/master", serverId.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
				isLeader = true;
			}catch(NodeExistsException e){
				isLeader = false;
				break;
			}catch(ConnectionLossException e){
				System.out.println("connection lose...");
			}
//			}finally{
			if(checkMaster()){
				System.out.println(String.format("%s is master", serverId));
				break;
			}else{
				System.out.println(String.format("%s is not master", serverId));
			}
//			}
		}
	}
	
    boolean checkMaster() throws KeeperException, InterruptedException {
        while (true) {
            try {
                Stat stat = new Stat();
                byte data[] = zk.getData("/master", false, stat); 
                isLeader = new String(data).equals(serverId); 
                return isLeader;
            } catch (NoNodeException e) {
                // no master, so try create again
                return false;
            } catch (ConnectionLossException e) {
            	System.out.println("connection lose...");
            }
        }
    }
    
	final static CyclicBarrier  barrier = new CyclicBarrier(2);
	
	public static void main(String args[]) throws Exception {
	    
	
		Thread threadA = new Thread(){
			@Override
			public void run() {
				Master master = new Master("127.0.0.1:2181", "threadA");
				try {
					master.startZK();
					master.runForMaster();
				} catch (IOException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (KeeperException e) {
					e.printStackTrace();
				}
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		Thread threadB = new Thread(){
			@Override
			public void run() {
				Master master = new Master("127.0.0.1:2181", "threadB");
				try {
					master.startZK();
					master.runForMaster();
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				} catch (KeeperException e) {
					e.printStackTrace();
				}
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		threadA.start();
		threadB.start();
		
		System.out.println("------------------------------------------------------");
	
	}
}