package com.congxiaoyao.utils;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * �ı���ȡ�������ļ����ı�����ʽ��ȡ
 * ÿһ�ж�ȡ�����ı����Իص�����ʽ֪ͨ���
 * ʹ��������ʹ��ʱ��д����onReadLine����
 * ��scaner��һ��
 * �ṩ�ڲ���PathBuilder ���԰�ȫ�Ĳ��ÿ�����б�߻��Ƿ�б�ߵĹ���һ��·��
 * ������ֱ�ӴӼ��а��ȡ���б����·������Ȼ���Ƽ�ʹ�ã�ֻ���Ա���ʱ֮��
 * @version 1.1
 * @date 2015.10.04
 * @author congxiaoyao
 */
public class TextReader {

	private int bufferSize;
	private File file;
	private BufferedReader reader;
	private FileInputStream fileInputStream;
	private InputStreamReader inputStreamReader;

	public TextReader(){}

	public TextReader(String path){
		file = new File(path);
		this.bufferSize = 8192;
		initReader();
	}

	public TextReader(String path,int bufferSize) {
		file = new File(path);
		this.bufferSize = bufferSize;
		initReader();
	}

	public void read(){
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				onReadLine(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		closeReader();
		onReadFinished();
	}

	public void onReadLine(String line){}

	public void onReadFinished(){}

	public void setPath(String path) {
		file = new File(path);
		if(reader != null) closeReader();
		initReader();
	}

	public File getFile(){
		return file;
	}

	private void initReader(){
		try {
			String charsetName = codeString(file.getAbsolutePath());
			fileInputStream = new FileInputStream(file);
			inputStreamReader = new InputStreamReader(fileInputStream, charsetName);
			reader = new BufferedReader(inputStreamReader,bufferSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void closeReader(){
		try {
			reader.close();
			inputStreamReader.close();
			fileInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String codeString(String fileName) throws Exception{
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(fileName));
		int p = (bin.read() << 8) + bin.read();
		String code = null;
		switch (p) {
		case 0xefbb:
			code = "UTF-8";
			break;
		case 0xfffe:  
			code = "Unicode";  
			break;  
		case 0xfeff:  
			code = "UTF-16BE";  
			break;  
		default:  
			code = "GBK";  
		}
		bin.close();
		return code;  
	} 

	public static class PathBuilder {

		public StringBuilder builder;

		public PathBuilder(char driveLetter){
			builder = new StringBuilder();
			builder.append(driveLetter+":\\");
		}

		/**
		 * ͨ�����캯�������̷��󣬴˷����Ὣ�ļ��л��ļ�׷���ں��棬֧����ʽ���
		 * @param folderName �������ļ��е�����Ҳ�������ļ���
		 * @return
		 */
		public PathBuilder append(String folderName){
			builder.append("\\").append(folderName);
			return this;
		}

		@Override
		public String toString() {
			return builder.toString();
		}

		/**
		 * ˵���˾��ǻ�ȡ���а��ϵ����ݣ���������õ�ĳ���ļ���path��ֻҪ���������ļ��е�Ŀ¼
		 * �����������������ļ����֣�����ͨ���˷�����ȡ����ļ���path
		 * @param fileName �㶮��
		 * @return �ַ�����path
		 */
		public static String getPathByAccessClipBoardAndFileName(String fileName){
			String path = getSysClipboardText() + "\\" + fileName;
			System.out.println(path);
			return path;
		}

		private static String getSysClipboardText() {
			String ret = "";
			Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
			// ��ȡ���а��е�����
			Transferable clipTf = sysClip.getContents(null);
			if (clipTf != null) {
				// ��������Ƿ����ı�����
				if (clipTf.isDataFlavorSupported(DataFlavor.stringFlavor)) {
					try {
						ret = (String) clipTf.getTransferData(DataFlavor.stringFlavor);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			return ret;
		}
	}

}
