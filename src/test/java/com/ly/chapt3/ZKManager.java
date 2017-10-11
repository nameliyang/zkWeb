package com.ly.chapt3;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public class ZKManager {
	
	
	public ZooKeeper getZookeeper(String hostPort) throws IOException, InterruptedException{
		final CountDownLatch latch = new CountDownLatch(1);
		ZooKeeper zk = new ZooKeeper(hostPort, 15000, new MyWatch(latch));
		latch.await();
		return zk;
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		ZKManager manager = new ZKManager();
		ZooKeeper zk = manager.getZookeeper("127.0.0.1:2181");
		System.out.println(zk);
	}
}

class MyWatch implements Watcher{
	
	CountDownLatch latch;
	
	public MyWatch( CountDownLatch latch ){
		this.latch = latch;
	}
	
	@Override
	public void process(WatchedEvent event) {
		
		if(event.getState() == KeeperState.SyncConnected){
			latch.countDown();
		}
		
	}
	
}