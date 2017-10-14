package com.ly.chapt3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class MyNioClient {
	
	public static void main(String[] args) throws IOException {
		
		NioClient client =  new NioClient("127.0.0.1", 20001, new Watcher(){
			@Override
			public void onConnected() {
				System.out.println("connected ...");
			}
			@Override
			public void onRead(String msg) {
				System.out.println(msg);
			}
		});
		client.start();
	}


	
	static class NioClient implements Runnable{
		
		public volatile boolean isStopped = false;
		
		private Selector selector;
		
		private InetSocketAddress address;

		private SocketChannel socketChannel;
		
		private Watcher watcher;
		
		private ByteBuffer readBuffer = ByteBuffer.allocate(DEFAULT_BUFFER_SIZE);
		
		private ByteBuffer writeBuffer = ByteBuffer.allocate(DEFAULT_BUFFER_SIZE);
		
		private static final  Integer DEFAULT_BUFFER_SIZE = 10;
		
		public NioClient(String hostName,int port,Watcher watcher) throws IOException{
			this.selector = Selector.open();
			this.address = new InetSocketAddress(hostName,port);
			this.socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
			socketChannel.connect(address);
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
			this.watcher = watcher;
		}
		
		public void start(){
			new Thread(this).start();
		}

		@Override
		public void run() {
			try {
				while(!isStopped){
					selector.select();
					Set<SelectionKey> selectedKeys = selector.selectedKeys();
					Iterator<SelectionKey> iterator = selectedKeys.iterator();
					while(iterator.hasNext()){
						SelectionKey key = iterator.next();
						processKey(key);
						iterator.remove();
					}
				}
			
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private void processKey(SelectionKey key) throws IOException {
			SocketChannel socketChannel = (SocketChannel) key.channel();
			if(key.isReadable()){
				int read = socketChannel.read(readBuffer);
				if(read > 0){
					StringBuilder sb = new StringBuilder();
					while(readBuffer.hasRemaining()){
						sb.append((char)readBuffer.get());
					}
					watcher.onRead(sb.toString());
					readBuffer.compact();
				}
			}else if(key.isConnectable()){
				if(socketChannel.finishConnect()){
					key.interestOps(key.interestOps() & ~SelectionKey.OP_CONNECT);
					key.interestOps(key.interestOps() | SelectionKey.OP_READ    );
					watcher.onConnected();
				}
			}else if(key.isWritable()){
				
			}
			
		}
		
	}
	
	static interface Watcher{
		
		public  void onConnected();
		
		public void onRead(String buffer);
		
	}
}
