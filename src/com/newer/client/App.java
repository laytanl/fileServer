package com.newer.client;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * 客户端
 * 
 * @author LIUTAN
 *
 */
public class App {
	private int port=8800;//定义服务端的端口号
	private String fileServer="127.0.0.1";//主机地址
	private FileInputStream fis;
	private OutputStream out;
	private InputStream in;
	
	public void start(){
		
		Scanner sc=new Scanner(System.in);
		System.out.println("请输入你需要上传的文件");
		String s=sc.next();
		sc.close();
		Socket socket;
		try {
			socket = new Socket(fileServer, port);//与指定服务器建立连接
			 fis=new FileInputStream(s);//定义一个文件输入流
			out=socket.getOutputStream();//得到套接字的输出流，将其写入服务器
			byte[] b=new byte[1024*4];
			int size;
			while(-1!=(size=fis.read(b))){
				out.write(b, 0, size);
			    out.flush();
			}	
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}//建立连接
		finally{
			try {
				fis.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}
	public static void main(String[] args) {
		App app=new App();
		app.start();
	}

}
