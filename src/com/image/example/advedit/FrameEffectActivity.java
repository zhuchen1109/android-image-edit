package com.image.example.advedit;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.image.example.BaseFilterActivity;
import com.image.example.activity.R;

import cn.imageedit.lib.BitmapConstructor;
import cn.imageedit.lib.OwnNinePatchImage;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;

public class FrameEffectActivity extends BaseFilterActivity{
	
	private OwnNinePatchImage ownNinePatchImage;
	private Bitmap mFrameBitmap;
	
	private static final ArrayList<String> FragmentFrameAssets = new ArrayList<String>();
	static {
		FragmentFrameAssets.add("70");
		FragmentFrameAssets.add("71");
		FragmentFrameAssets.add("72");
		FragmentFrameAssets.add("73");
		FragmentFrameAssets.add("74");
		FragmentFrameAssets.add("75");
		FragmentFrameAssets.add("76");
		FragmentFrameAssets.add("77");
		FragmentFrameAssets.add("78");
		FragmentFrameAssets.add("79");
	}
	
	private static final ArrayList<String> SingleFrameAssets = new ArrayList<String>();
	static {
		SingleFrameAssets.add("lf60");
		SingleFrameAssets.add("lf61");
		SingleFrameAssets.add("lf62");
		SingleFrameAssets.add("lf63");
		SingleFrameAssets.add("lf64");
	}
	
	private final float WIDTH = 600.0f;
	private final float HEIGHT = 600.0f;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		makeFragmentFilters(FragmentFrameAssets);
		makeSingleFilters(SingleFrameAssets);
	}
	
	private Bitmap loadAssetBitmap(String path) {
		AssetManager assetManager = this.getAssets();
		InputStream is;
		Bitmap bm = null;
		try {
			is = assetManager.open(path);
			bm = BitmapFactory.decodeStream(is);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bm;
	}
	
	private void merge(int borderWidth, int borderHeight) {
		
		if (mOriginalBitmap == null || mFrameBitmap == null) {
			return;
		}

		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setFilterBitmap(true);
		
		mPreparedBitmap = Bitmap.createBitmap(mOriginalBitmap.getWidth(), mOriginalBitmap.getHeight(), Config.ARGB_8888);

		Canvas canvas = new Canvas(mPreparedBitmap);
		
		Rect src = new Rect(0, 0, mOriginalBitmap.getWidth(), mOriginalBitmap.getHeight());
		Rect dst = new Rect(borderWidth, borderHeight, mOriginalBitmap.getWidth()-borderWidth, mOriginalBitmap.getHeight()-borderHeight);
		
		canvas.drawBitmap(mOriginalBitmap, src, dst, paint);
		
		src = new Rect(0, 0, mFrameBitmap.getWidth(), mFrameBitmap.getHeight());
		dst = new Rect(0, 0, mOriginalBitmap.getWidth(), mOriginalBitmap.getHeight());
		canvas.drawBitmap(mFrameBitmap, src, dst, paint);
		
	}
	
	private void makeFragmentFilters(ArrayList<String> labels) {

		for (int i = 0; i < labels.size(); i++) {

			final String label = labels.get(i);
			ImageView b = new ImageView(FrameEffectActivity.this);
			Bitmap bm = loadAssetBitmap("frame_n/" + label + ".png");
			
			b.setImageBitmap(bm);
			b.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (mOriginalBitmap == null) {
						return;
					}
					String path = "frame_n_img/" + label + "/";
					ownNinePatchImage = new OwnNinePatchImage(FrameEffectActivity.this,path,true);
					
					int groundImageWidth = mOriginalBitmap.getWidth();
					int groundImageHeight = mOriginalBitmap.getHeight();
					
					int borderWidthTop = ownNinePatchImage.mTopWidth;
					borderWidthTop = (int) (borderWidthTop * (groundImageHeight / HEIGHT));
					
					int borderWidthLeft = ownNinePatchImage.mLeftWidth;
					borderWidthLeft = (int) (borderWidthLeft * (groundImageWidth / WIDTH));
					
					int borderWidth = borderWidthTop>borderWidthLeft?borderWidthTop:borderWidthLeft;
					
					int[] borderWidths = new int[2];
					borderWidths[0]= borderWidthLeft;
					borderWidths[1]= borderWidthTop;
					
					if(ownNinePatchImage.mTopWidth == ownNinePatchImage.mLeftWidth)
					{
						borderWidths[0]= borderWidth;
						borderWidths[1]= borderWidth;
					}
					
					mFrameBitmap = BitmapConstructor.constructBitmap(ownNinePatchImage, borderWidths, groundImageWidth, groundImageHeight);
					
					merge(borderWidths[0]/4, borderWidths[1]/4);
					
					setImageView(mPreparedBitmap);
					
				}

			});
			
			LayoutParams p = new LayoutParams(getResources().getDimensionPixelSize(R.dimen.small_icon_size), 
					getResources().getDimensionPixelSize(R.dimen.small_icon_size));
			b.setLayoutParams(p);
			mFilterView.addView(b);
		}
	}
	
	
	private void makeSingleFilters(ArrayList<String> labels) {

		for (int i = 0; i < labels.size(); i++) {

			final String label = labels.get(i);
			ImageView b = new ImageView(FrameEffectActivity.this);
			Bitmap bm = loadAssetBitmap("frame_n/" + label + ".png");
			
			b.setImageBitmap(bm);
			b.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (mOriginalBitmap == null) {
						return;
					}
					String path = null;
					if (mOriginalBitmap.getWidth() < mOriginalBitmap.getHeight()) {
						path = "frame_n_img/" + "v"+ label + ".png";
					} else {
						path = "frame_n_img/" + "h"+ label + ".png";
					}
					
					mFrameBitmap = loadAssetBitmap(path);
					
					merge(0, 0);
					
					setImageView(mPreparedBitmap);
					
				}

			});
			LayoutParams p = new LayoutParams(getResources().getDimensionPixelSize(R.dimen.small_icon_size), 
					getResources().getDimensionPixelSize(R.dimen.small_icon_size));
			b.setLayoutParams(p);
			mFilterView.addView(b);
		}
	}

}




