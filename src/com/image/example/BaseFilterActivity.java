package com.image.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.imageedit.lib.file.ImageFile;
import cn.imageedit.lib.file.ImageFile.OnFileLoadedListener;
import cn.imageedit.lib.file.ImageFile.OnFileSavedListener;

import com.image.example.activity.R;
import com.image.example.utils.Constants;
import com.image.example.utils.ImageViewMatrixHelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

public abstract class BaseFilterActivity extends Activity {

	protected final static String TAG = "FILTER";

	private ProgressDialog mProgressDialog;
	protected ViewGroup mSeekbarContainer;
	protected List<SeekBar> mSeekBars;
	private Uri mUri;
	
	protected ImageView mImageView;
	protected LinearLayout mFilterView;
	private View mBtnLoad, mBtnComp, mBtnSave;
	protected Bitmap mOriginalBitmap, mPreparedBitmap;
	protected boolean mBlocking;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter);

		mImageView = (ImageView) findViewById(R.id.image);
		mSeekbarContainer = (ViewGroup) findViewById(R.id.seekbars);
		mFilterView = (LinearLayout) findViewById(R.id.filters);
		mBtnLoad = findViewById(R.id.btn_load);
		mBtnComp = findViewById(R.id.btn_comp);
		mBtnSave = findViewById(R.id.btn_save);
		mSeekBars = new ArrayList<SeekBar>();
		mBlocking = false;

		initViews();
		
	}
	
	private void initViews() {
		mBtnComp.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					setImageView(mOriginalBitmap);
					//return true;
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					if (mPreparedBitmap != null) {
						setImageView(mPreparedBitmap);
					}
					//return true;
				}
				return false;
			}
		});

		mBtnLoad.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				openGallery();
			}
		});
		
		mBtnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				save();
			}
		});
	}

	private void openGallery() {
		Intent editIntent = new Intent();
		editIntent.setType("image/*");
		editIntent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(editIntent, Constants.OPENGALLERY_REQUEST);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (resultCode != RESULT_OK) {
			return;
		}
		if (requestCode == Constants.OPENGALLERY_REQUEST) {
			mUri = intent.getData();
			if (mUri != null) {
				// Asynchronized load an image from Gallery into memory, the
				// image loaded will be smaller than IMAGE_SIZE. Avoiding
				// out of memory.
				new ImageFile().loadImageAsync(this, mUri,
						Constants.IMAGE_SIZE, Constants.IMAGE_SIZE,
						new OnFileLoadedListener() {

							@Override
							public void onFileLoaded(int rst, Bitmap bitmap,
									Object tag) {
								if (bitmap == null) {
									Toast.makeText(BaseFilterActivity.this,
											R.string.err_file,
											Toast.LENGTH_SHORT).show();
									return;
								}
								mOriginalBitmap = bitmap;

								Toast.makeText(
										BaseFilterActivity.this,
										"Width:" + mOriginalBitmap.getWidth()
												+ "  Height:"
												+ mOriginalBitmap.getHeight(),
										Toast.LENGTH_LONG).show();

								setImageView(mOriginalBitmap);
								if (mPreparedBitmap != null) {
									mPreparedBitmap.recycle();
									mPreparedBitmap = null;
								}

							}
						});
			} else {
				Toast.makeText(BaseFilterActivity.this, R.string.err_file,
						Toast.LENGTH_SHORT).show();
			}
				
				
				
		}  
		
	}
		
	private void save() {
		if (mPreparedBitmap != null) {
			String root = Environment.getExternalStorageDirectory()
					.getAbsolutePath();
			File file = new File(root + "/" + "motusdk");
			file.mkdirs();
			file = new File(file.getAbsolutePath(), String.valueOf(System
					.currentTimeMillis()) + ".jpg");
			showProgressDialog(R.string.saving);
			new ImageFile().saveImageAsync(this, mPreparedBitmap,
					file.getAbsolutePath(), ImageFile.TYPE_JPG,
					new OnFileSavedListener() {

						@Override
						public void onFileSaved(int rst, Uri uri) {
							// TODO Auto-generated method stub
							hideProgressDialog();
							switch (rst) {
							case ImageFile.SUCCESSED:
								Toast.makeText(
										BaseFilterActivity.this,
										getString(R.string.save_ok)
												+ uri.getPath(),
										Toast.LENGTH_SHORT).show();
								break;
							default:
								Toast.makeText(BaseFilterActivity.this,
										getString(R.string.save_fail),
										Toast.LENGTH_SHORT).show();
								break;
							}
						}
					});
		}
	}
	
	protected void showProgressDialog(int resId) {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			return;
		}
		mProgressDialog = ProgressDialog.show(this, null, getString(resId));
		mProgressDialog.setCancelable(true);
	}

	protected void hideProgressDialog() {
		if (mProgressDialog != null) {
			mProgressDialog.dismiss();
			mProgressDialog = null;
		}
	}
	
	/** 设置imageView的srcBitmp,同时更新其matrix。
	 * 
	 */
	protected void setImageView(Bitmap bm) {	
		if (bm == null) {
			return;
		}
		mImageView.setImageBitmap(bm);
		Matrix matrix = ImageViewMatrixHelper.getFitCenterMatrix(mImageView, bm);
		mImageView.setImageMatrix(matrix);
	}
	
}