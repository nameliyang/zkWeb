package com.ly.chapt3;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerDemo {
	public static void main(String[] args) throws IOException, InterruptedException {
		ServerSocket serverSocket = new ServerSocket(20001);
		while(true){
			final Socket socket = serverSocket.accept();
			new Thread(){
				public void run() {
					try {
						InputStream inputStream = socket.getInputStream();
						OutputStream outputStream = socket.getOutputStream();
						byte b ;
						while((b = (byte) inputStream.read())!=-1){
							outputStream.write(b);
							outputStream.flush();
							Thread.sleep(2000);
						}
						inputStream.close();
						outputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				};
			}.start();
		}
	}
}
