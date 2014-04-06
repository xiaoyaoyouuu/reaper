package com.reaper.common;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;


public class ChkImgProcessor {
	public static void downloadImg(String URL,String savePath) {
		try{
			// ����URL
			URL url = new URL(URL);
			// ������
			URLConnection con = url.openConnection();
			// ������
			InputStream is = con.getInputStream();
			// 1K����ݻ���
			byte[] bs = new byte[1024];
			// ��ȡ������ݳ���
			int len;
			// ������ļ���
			OutputStream os = new FileOutputStream(savePath);
			// ��ʼ��ȡ
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			// ��ϣ��ر���������
			os.close();
			is.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ChkImgProcessor.downloadImg("http://hwid1.vmall.com/casserver/randomcode", "img\\lashou.gif");
	}
}
