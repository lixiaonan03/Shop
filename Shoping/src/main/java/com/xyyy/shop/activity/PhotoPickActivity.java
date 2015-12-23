package com.xyyy.shop.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.baidu.mobstat.StatService;
import com.xyyy.shop.toolUtil.DateUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;


public class PhotoPickActivity extends BaseActivity {
	public static final String PHOTO_TYPEKEY = "photo_typekey";
	public static final String PHOTO_STORAGEKEY = "photo_storagekey";
	public static final String PHOTO_STORAGE_FILENAMEKEY = "photo_storage_filenamekey";
	public static final String PHOTO_CROPKEY = "photo_cropkey";
	public static final int PHOTO_BACK_NULL = 0;
	public static final int PHOTO_BACK_URI = 10;
	public static final int PHOTO_BACK_BMP = 11;
	public static final int PHOTO_REQ_ALBUM = 1;
	public static final int PHOTO_REQ_CAPTURE = 2;
	private static final int PHOTO_REQ_CROP = 3;
	
	private String photoName;
	private int photo_type;
	private String photo_storage,photo_storage_filename;
	private PhotoCrop photo_crop;
	private Uri uri;
	private Uri output_uri;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}
	
	private void init() {
		photoName = DateUtil.getNowDateDetail();
		photo_type = getIntent().getIntExtra(PHOTO_TYPEKEY, PHOTO_REQ_ALBUM);
		photo_storage = getIntent().getStringExtra(PHOTO_STORAGEKEY);
		photo_storage_filename = getIntent().getStringExtra(PHOTO_STORAGE_FILENAMEKEY);
		photo_crop = (PhotoCrop)getIntent().getSerializableExtra(PHOTO_CROPKEY);
		if(photo_storage_filename!=null) photoName = photo_storage_filename;
		
		if(photo_type == PHOTO_REQ_ALBUM){
			initPick();
		}
		if(photo_type == PHOTO_REQ_CAPTURE){
			initCapture();
		}
	}
	
	private void initPick() {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
		startActivityForResult(intent, PhotoPickActivity.PHOTO_REQ_ALBUM);
	}
	
	private void initCapture() {
		File vFile = new File(photo_storage,""+photoName+".jpg"); 
		uri = Uri.fromFile(vFile); 
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		startActivityForResult(intent, PhotoPickActivity.PHOTO_REQ_CAPTURE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		// 如果是直接从相册获取
		case PHOTO_REQ_ALBUM:
			if(resultCode == RESULT_OK && data != null){
				startPhotoZoom(data.getData());
			}else{
				backInforNull();
			}
			break;
		// 如果是调用相机拍照时
		case PHOTO_REQ_CAPTURE:
			if(resultCode == RESULT_OK){
				startPhotoZoom(uri);
			}else{
				backInforNull();
			}
			break;
		// 取得裁剪后的图片
		case PHOTO_REQ_CROP:
			if(resultCode == RESULT_OK){
				String return_uri = photo_crop.getReturnOutput();
				if(return_uri == null || return_uri.equals("")){
					Bitmap photo = data.getParcelableExtra("data");
					backInforBitmap(photo);
				}else{
					backInforUri(output_uri);
				}
			}else{
				backInforNull();
			}
			break;
		default:
			backInforNull();
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void backInforNull(){
        Intent resdata = new Intent();
        setResult(PhotoPickActivity.PHOTO_BACK_NULL, resdata);  
        finish();
	}
	
	private void backInforUri(Uri uri){
		if (uri != null){
	        Intent resdata = new Intent();
	        resdata.putExtra("data", uri);  
	        setResult(PhotoPickActivity.PHOTO_BACK_URI, resdata);  
	        finish();
		}else{
			backInforNull();
		}
	}
	
	private void backInforBitmap(Bitmap photo){
		if (photo != null){
	        Intent resdata = new Intent();
	        resdata.putExtra("data", photo);
	        setResult(PhotoPickActivity.PHOTO_BACK_BMP, resdata);  
	        finish();
		}else{
			backInforNull();
		}
	}
	
	/**
	 * 裁剪图片方法实现
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		if(photo_crop == null) {
			backInforUri(uri);
		}else{
			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(uri, "image/*");
			//下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
			intent.putExtra("crop", "true");
			// aspectX aspectY 是宽高的比例
			intent.putExtra("aspectX", photo_crop.getAspectX());
			intent.putExtra("aspectY", photo_crop.getAspectY());
			// outputX outputY 是裁剪图片宽高
			intent.putExtra("outputX", photo_crop.getOutputX());
			intent.putExtra("outputY", photo_crop.getOutputY());
			
			String return_uri = photo_crop.getReturnOutput();
			if(return_uri == null || return_uri.equals("")){
				intent.putExtra("return-data", true);
			}else{
				String new_photoName = DateUtil.getNowDateDetail();
				File vFile = new File(return_uri,""+new_photoName+".jpg");
				output_uri = Uri.fromFile(vFile); 
				intent.putExtra("return-data", false);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, output_uri);
			}
			
			startActivityForResult(intent, PhotoPickActivity.PHOTO_REQ_CROP);
		}
	}
	
	public static Bitmap decodeUriAsBitmap(ContentResolver resolver,Uri uri) {
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(resolver.openInputStream(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}
	
	public static class PhotoCrop implements Serializable {
		private static final long serialVersionUID = -305274577202017260L;
		private int aspectX; //裁剪框 X方向的比例
		private int aspectY; //裁剪框 Y方向的比例
		private int outputX; //返回数据X像素
		private int outputY; //返回数据 Y像素
		private String returnOutput; //returnData为false时 输出路径 有效
		
		public int getAspectX() {
			return aspectX;
		}

		public int getAspectY() {
			return aspectY;
		}

		public int getOutputX() {
			return outputX;
		}

		public int getOutputY() {
			return outputY;
		}

		public String getReturnOutput() {
			return returnOutput;
		}

		public PhotoCrop(int aspectX, int aspectY, int outputX, int outputY) {
			this.aspectX = aspectX;
			this.aspectY = aspectY;
			this.outputX = outputX;
			this.outputY = outputY;
		}

		public PhotoCrop(int aspectX, int aspectY, int outputX, int outputY,String returnoutput) {
			this.aspectX = aspectX;
			this.aspectY = aspectY;
			this.outputX = outputX;
			this.outputY = outputY;
			this.returnOutput = returnoutput;
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		StatService.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		/**
		 * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
		 * 不能与StatService.onPageStart一级onPageEnd函数交叉使用
		 */
		StatService.onPause(this);
	}
}
