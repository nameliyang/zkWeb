package com.ly.chapt3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public class Test {
	
	public static void main(String[] args) throws IOException {
		
//		ZooKeeper zoo = new ZooKeeper("127.0.0.1:2181", 10000, new Watcher(){
//			@Override
//			public void process(WatchedEvent event) {
//				if(event.getState() == KeeperState.SyncConnected){
//					System.out.println("..........................");
//				}
//			}
//		});
//      System.out.println(zoo);
		
		ByteBuffer byteBuf = ByteBuffer.allocate(10);
		byteBuf.put("hello".getBytes());
		System.out.println(byteBuf);
	}
}
