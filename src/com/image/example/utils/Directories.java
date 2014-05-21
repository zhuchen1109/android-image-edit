package com.image.example.utils;

import java.io.File;

import android.os.Environment;

public class Directories {
	
	private final static String ROOT = Environment.getExternalStorageDirectory().getAbsolutePath() + "/motusdk";
	
	public static void init() {
		new File(getRootDir()).mkdirs();
		new File(getTempDir()).mkdirs();
	}
	
	public static String getRootDir() {
		return ROOT;
	}
	
	public static String getTempDir() {
		return ROOT + "/temp";
	}
}
