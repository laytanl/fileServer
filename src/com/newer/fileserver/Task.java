package com.newer.fileserver;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

public class Task implements Runnable {

	Socket socket;// 定义套接字
	private InputStream in;// 定义从客户端传来的输入流
	private OutputStream out1;// 定义传向客户端的输出流
	private FileOutputStream out;// 定义将数据传入文件的输入流
	String filePath = "D:/名称";
	File file3 = null;

	public Task(Socket socket) {
		super();
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			// 得到从客户端传来的数据
			in = socket.getInputStream();
			ByteArrayOutputStream data = new ByteArrayOutputStream();
			byte[] b = new byte[1024 * 4];
			int size;
			while (-1 != (size = in.read(b))) {
				data.write(b, 0, size);
			}
			byte[] info = data.toByteArray();// 存储客户端传来的文件的所有数据
			String filename="";// 定义文件名
			// 获得文件的散列值
			byte[] hash = MessageDigest.getInstance("SHA-256").digest(info);
			filename = new BigInteger(1, hash).toString(16);
			File file = new File(filePath);// 得到需要放入的文件夹
			if (file.isDirectory()) {
				File[] file1 = file.listFiles();// 列出文件夹中的目录
				for (File file2 : file1) {// 遍历文件夹
					if (filename.equals(file2.getName())) {
						System.out.println("文件已经存在");
						file3 = new File(filePath, filename);
						break;
					}
				}
				if (file3 == null) {
					file3 = new File(filePath, filename);
					out = new FileOutputStream(file3);
					out.write(info);
					System.out.println("上传成功");
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} 

	}

}
