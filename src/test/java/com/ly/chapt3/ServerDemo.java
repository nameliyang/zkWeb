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
			Socket socket = serverSocket.accept();
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			byte b ;
			while((b = (byte) inputStream.read())!=-1){
				Thread.sleep(2000);
				outputStream.write(b);
			}
			inputStream.close();
			outputStream.close();
		}
		
	}
}
