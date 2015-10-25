package com.test;

import com.congxiaoyao.utils.TextReader;

public class HelloWorld {

	public static void main(String[] args){
	
		TextReader reader = new TextReader("c:\\text.txt"){
			@Override
			public void onReadLine(String line) {
				if(line.length() < 3) return;
				line = line.substring(3, line.length()).trim();
				System.out.println(line);
			}
		};
		
		reader.read();
		
	}
}
