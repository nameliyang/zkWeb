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
	public static void main(String[] args) throws IOException, InterruptedException {
		
//		ZooKeeper zoo = new ZooKeeper("127.0.0.1:2181", 10000, new Watcher(){
//			@Override
//			public void process(WatchedEvent event) {
//				if(event.getState() == KeeperState.SyncConnected){
//					System.out.println("..........................");
//				}
//			}
//		});
//      System.out.println(zoo);
		
		
		TcpClient client = new TcpClient() {
			@Override
			protected void onRead(ByteBuffer buf) throws Exception {
				System.out.println(buf);
			}
			
			@Override
			protected void onDisconnected() {
			}
			
			@Override
			protected void onConnected() throws Exception {
				
			}
		};
		client.setAddress(new InetSocketAddress("127.0.0.1", 20001));
		client.start();
		client.send(ByteBuffer.wrap("helloworld".getBytes()));
	}
}
