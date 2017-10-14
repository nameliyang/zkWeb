package com.ly.chapt3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.concurrent.CountDownLatch;

public class Test {
	
	private static final CountDownLatch latch = new CountDownLatch(1);
	
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
		
		TcpClient client = getClient();
		latch.await();
		ByteBuffer buffer = ByteBuffer.wrap("helloworld".getBytes());
		client.send(buffer);
	}
	
	public static  TcpClient getClient() throws IOException{
		
		TcpClient client = new TcpClient() {
			@Override
			protected void onRead(ByteBuffer buf) throws Exception {
				System.out.println(buf);
				StringBuilder sb = new StringBuilder();
				while(buf.hasRemaining()){
					sb.append((char)buf.get());
				}
				System.out.print(sb);
			}
			@Override
			protected void onDisconnected() {
				
			}
			
			@Override
			protected void onConnected() throws Exception {
				System.out.println("client connnected ...");
				latch.countDown();
			}
		};
		client.setAddress(new InetSocketAddress("127.0.0.1", 20001));
		client.start();
		
		return client;
	}
}
