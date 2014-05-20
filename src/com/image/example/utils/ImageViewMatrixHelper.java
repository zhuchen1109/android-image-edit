package com.image.example.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.View;

public class ImageViewMatrixHelper {
	
	/** 用Matrix方式实现FitCenter的显示方式。
	 * @param v ImageView
	 * @param srcBm ImageView的Bitmap
	 * @return 用Matrix方式实现FitCenter的显示方式，ImageView所需设置的Matrix。
	 */
	public static Matrix getFitCenterMatrix(View v, Bitmap srcBm) {
		Matrix matrix= new Matrix();
		matrix.reset();
		float scaleRatio = 1.0f;
		float traslatex = 0;
		float traslatey = 0;
		
		
		float scaleRatioH = (float)v.getHeight()/(float)srcBm.getHeight();
		float scaleRatioW = (float)v.getWidth()/(float)srcBm.getWidth();
		
		
		if (scaleRatioH > scaleRatioW) {
			scaleRatio = scaleRatioW;
			traslatex = 0;
			traslatey = (v.getHeight() - (float)srcBm.getHeight()*scaleRatio)/2.0f;	
		} else {
			scaleRatio = scaleRatioH;
			traslatey = 0;
			traslatex = (v.getWidth() - (float)srcBm.getWidth()*scaleRatio)/2.0f;	
		}
		
		matrix.preTranslate(traslatex, traslatey);
		matrix.preScale(scaleRatio, scaleRatio);
		
		return matrix;
	}
	
	/** 用Matrix方式实现CenterCrop的显示方式。
	 * @param v ImageView
	 * @param srcBm ImageView的Bitmap
	 * @return 用Matrix方式实现CenterCrop的显示方式，ImageView所需设置的Matrix。
	 */
	public static Matrix getCenterCropMatrix(View v, Bitmap srcBm) {
		Matrix matrix= new Matrix();
		matrix.reset();
		float scaleRatio = 1.0f;
		float traslatex = 0;
		float traslatey = 0;
		
		
		float scaleRatioH = (float)v.getHeight()/(float)srcBm.getHeight();
		float scaleRatioW = (float)v.getWidth()/(float)srcBm.getWidth();
		
		
		if (scaleRatioW > scaleRatioH) {
			scaleRatio = scaleRatioW;
			traslatex = 0;
			traslatey = (v.getHeight() - (float)srcBm.getHeight()*scaleRatio)/2.0f;
		} else {
			scaleRatio = scaleRatioH;
			traslatey = 0;
			traslatex = (v.getWidth() - (float)srcBm.getWidth()*scaleRatio)/2.0f;		
		}
		
		matrix.preTranslate(traslatex, traslatey);
		matrix.preScale(scaleRatio, scaleRatio);
		
		return matrix;
	}

}
