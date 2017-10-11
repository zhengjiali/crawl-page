package com.risk.auto.pages;

import java.io.File;

public class util {
	
	static public Boolean debug = true; 
	public static void new_dir(String dir){
		File file = new File(dir);
		if( !file.exists() && !file.isDirectory()){
			file.mkdir();
		}
	}
	public static void log(String s){
		if(debug)
			System.out.println(s);
	}
}
