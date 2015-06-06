package com.example.cameraapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends ActionBarActivity{
	 
		private Camera mCamera;
	    private CameraPreview mPreview;
	    
	    //declare the type of the media 
	    public static final int mediaTypeImage = 1;

	    
private PictureCallback mPicture = new PictureCallback() {

	        public void onPictureTaken(byte[] data, Camera camera) {
	            new SaveImageTask().execute(data);
	        }

	    };

	    private class SaveImageTask extends AsyncTask<byte[], Void, Void> 
	    {

	    	
	    	@Override
	    	protected Void doInBackground(byte[]... data) 
	    	{

			// second edit 
	    		File pictureFile = getOutputMediaFile(mediaTypeImage);
	    		
	    		Log.d("CameraApp", "File is assigned to pictureFile");
	    		
	    		if (pictureFile == null) 
	    		{
	    			Log.d("CameraApp", " Error creating a media file, check storage permissions:");
	    			return null;
	    		}

	    		try 
	    		{
	    			
//	    			Bitmap bitmap=getCorrectlyOrientatedImage();
	    			
	    			FileOutputStream fos = new FileOutputStream(pictureFile);
	    			
	//    			fos.write(data);
	    			
	    			fos.close();
            
	    			File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "SELFie");
	    			
	    			String path = mediaStorageDir.getPath() + File.separator + "IMG_Some_name.jpg";
	    			
	    			//		SCamera.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
        
	    		} catch (FileNotFoundException e) 
	    		
	    		{
	    			Log.d("CameraApp", "File not found: " + e.getMessage());
        
	    		} catch (IOException e) 
	    		
	    		{
	    			Log.d("CameraApp", "Error accessing file: " + e.getMessage());
	    		}
	    		return null;
	    		
	    	}

	    }
/*	    private PictureCallback mPicture = new PictureCallback() 
	    {

	    	
	        @Override
	        public void onPictureTaken(byte[] data, Camera camera) {

	        	Log.i("CameraApp","Entered into onPictureTaken");
	        	
	            File pictureFile = getOutputMediaFile(mediaTypeImage);
	           
	            if (pictureFile == null){
	            	
	                Log.d("CameraApp", "Error creating media file, check storage permissions ");
	                
	                return;
	            }

	            try {
	                FileOutputStream fos = new FileOutputStream(pictureFile);
	                fos.write(data);
	                fos.close();
	            } catch (FileNotFoundException e) {
	                Log.d("CameraApp", "File not found: " + e.getMessage());
	            } catch (IOException e) {
	                Log.d("CameraApp", "Error accessing file: " + e.getMessage());
	            }
	        }
	    };
	*/    
	    //button for capturing the image
	    Button clickPhoto;
	    
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.camerapreview);

	        Log.i("CameraApp", "Started on create activity of the MainActivity");
	        
	        Boolean check = checkCameraHardware(this);
	        
	        if(check == false)
	        {
	        	Log.e("CameraApp","Camera Not available with device");
	        }
	        // Create an instance of Camera
	        mCamera = getCameraInstance();

	        // Create our Preview view and set it as the content of our activity.
	        mPreview = new CameraPreview(this, mCamera);
	        
	        FrameLayout preview = (FrameLayout) findViewById(R.id.cameraPreview);
	        
	        preview.addView(mPreview);
	        
	        clickPhoto = (Button) findViewById(R.id.capturePhoto);
	        
	        clickPhoto.setOnClickListener(new OnClickListener(){
	        	
	        	@Override
				public void onClick(View v) {
				
	        		mCamera.takePicture(null, null, mPicture);
	        		
	        		Log.i("CameraApp","Get the image URI");
	        		
	        		/*Uri imageUri = getOutputMediaFileUri(mediaTypeImage);
	        		Log.i("CameraApp","Error getting the image uri");
	        		if(imageUri != null)
	        		{
	        			        		
	        		Intent intent = new Intent(MainActivity.this,ImagePreview.class);
	        		
	        		intent.putExtra("Uri",imageUri);
	        		
	        		Log.i("CameraApp","Starting the intent");
	        		
	        		startActivity(intent);
	        		
	        		}
	        	*/	
				}
	        });
	        
	    }
	    
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	    	// Inflate the menu; this adds items to the action bar if it is present.
	    	getMenuInflater().inflate(R.menu.main, menu);
	    	return true;
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	    	// Handle action bar item clicks here. The action bar will
	    	// automatically handle clicks on the Home/Up button, so long
	    	// as you specify a parent activity in AndroidManifest.xml.
	    	int id = item.getItemId();
	    	if (id == R.id.action_settings) {
	    		return true;
	    	}
	    	return super.onOptionsItemSelected(item);
	    }
	
	
	
	
	    /** Check if this device has a camera */
	    private boolean checkCameraHardware(Context context) {
	    	if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
	    		// this device has a camera
	    		return true;
	    	} else {
	    		// no camera on this device
	    		return false;
	    	}
	    }


	    /** A safe way to get an instance of the Camera object. */
	    public static Camera getCameraInstance(){
	    	Camera c = null;
	    	try {
	    		c = Camera.open(); // attempt to get a Camera instance
	    	}
	    	catch (Exception e){
	    		// Camera is not available (in use or does not exist)
	    	}
	    	return c; // returns null if camera is unavailable
	    }




	    
	    /** Create a file Uri for saving an image or video */
	    private static Uri getOutputMediaFileUri(int type)
	    {
	    
	    	
	    		return Uri.fromFile(getOutputMediaFile(type));
	    	
	    	
	    }

	    /** Create a File for saving an image or video */
	    private static File getOutputMediaFile(int type){
	        
	    	// To be safe, you should check that the SDCard is mounted
	        // using Environment.getExternalStorageState() before doing this.
 		// This is a comment to check for the changes to be seen in the git repository
	        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	        		Environment.DIRECTORY_PICTURES), "MyCameraApp");
	        
	        // This location works best if you want the created images to be shared
	        // between applications and persist after your app has been uninstalled.

	        // Create the storage directory if it does not exist
	        if (! mediaStorageDir.exists()){
	            if (! mediaStorageDir.mkdirs()){
	                Log.d("MyCameraApp", "failed to create directory");
	                return null;
	            }
	        }

	        // Create a media file name
	        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	        
	        //File name of the image
	        File mediaFile;
	        
	        if (type == mediaTypeImage){
	            mediaFile = new File(mediaStorageDir.getPath() + File.separator +"IMG_"+ timeStamp + ".jpg");
	        }
	        else 
	        	return null;

	        
	        return mediaFile;  
	    }
}