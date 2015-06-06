package com.example.cameraapp;

import java.io.FileNotFoundException;

import android.R.layout;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class ImagePreview extends Activity {

	//Pre view of the captured image
	ImageView imageView;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imagepreview);
		
		imageView = (ImageView) findViewById(R.id.preViewOfCapturedImage);
		
		Intent intent = getIntent();
		
		Uri imageFileUri =  intent.getData();
		
			int dw = 200; 
	      	int dh = 200; 

	      	try {
	        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
	        bmpFactoryOptions.inJustDecodeBounds = true;
	        
	        Bitmap bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageFileUri), null, bmpFactoryOptions);

	        int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight/ (float) dh);
	        int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth/ (float) dw);
	        
	        if (heightRatio > 1 && widthRatio > 1) {
	          if (heightRatio > widthRatio) {
	            bmpFactoryOptions.inSampleSize = heightRatio;
	          } else {
	            bmpFactoryOptions.inSampleSize = widthRatio;
	          }
	        }
	        
	        bmpFactoryOptions.inJustDecodeBounds = false;
	        bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageFileUri), null, bmpFactoryOptions);
	      
	        /*Log.i("Buzz", "Before compressing");
	        imageFile = new FileOutputStream( imageFileUri.toString());
	        bmp.compress(Bitmap.CompressFormat.JPEG, 0, imageFile);
	        Log.i("PhotoCapture", "After Compressing");
	        //bmp.compress
	      */
	        
	        imageView.setImageBitmap(bmp);
	        
	      } catch (FileNotFoundException e) {
	        Log.v("Buzz", "Error"+e.toString());
	      }
		
		
	}
	
}
