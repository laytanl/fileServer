package com.newer.fileserver;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 服务端
 * 
 * @author
 *
 */
public class App {
	
	private int port = 8800;

	ExecutorService pool;


	public void start() {
		try {
			ServerSocket serversocket=new ServerSocket(port);
			pool=Executors.newCachedThreadPool();
			
			while(true){
			Socket socket=serversocket.accept();
			System.out.println("连接成功");
			Task a =new Task(socket);
			pool.execute(a);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}	
	public static void main(String[] args) {
		App app=new App();
		app.start();

	}
}
