package com.tstine.pangolinprecision;

import java.io.InputStream;
import java.io.IOException;

import java.lang.InterruptedException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.CancellationException;
import java.util.HashMap;

import org.apache.http.conn.BasicManagedEntity;
import org.apache.http.entity.BufferedHttpEntity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.util.Log;

import java.net.URI;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;


public class ImageLoader extends AsyncTask<URI, Void, HashMap<String,Bitmap>>{

	private Activity mParentActivity;
	private ProgressDialog mDialog;

	public ImageLoader( Activity activity ){
		mParentActivity = activity;
		mDialog = new ProgressDialog( activity );
	}
	public ImageLoader( Activity activity, boolean showLoading ){
		mParentActivity = activity;
		if( !showLoading){
			mDialog = null;
		}
	}


	@Override
	protected void onPreExecute(){
		if( mDialog!= null ){
			mDialog.setMessage("Loading...");
			mDialog.show();
		}
	}

	@Override
	protected void onPostExecute( final HashMap<String, Bitmap> grid){
		if( mDialog!= null && mDialog.isShowing() ){
			mDialog.dismiss();
		}
	}

	@Override
	protected HashMap<String,Bitmap> doInBackground( URI ... uris ){
		HashMap<String,Bitmap> imgMap = new HashMap<String,Bitmap>();
		for( int i = 0; i < uris.length; i++ ){
			BasicManagedEntity ent=null;
			try{
				ent = new HttpGetTask().execute( uris[i] ).get();
			}catch( InterruptedException e ){
				e.printStackTrace();
			}catch( ExecutionException e ){
				e.printStackTrace();
			}catch( CancellationException e ){
				e.printStackTrace();
			}
			BufferedHttpEntity bufferedEnt = null;
			try{
				bufferedEnt = new BufferedHttpEntity( ent );
			}catch( IOException e ){
				e.printStackTrace();
			}
			InputStream imgStream = null;
			try{
				imgStream = bufferedEnt.getContent();
			}catch( IOException e ){
				e.printStackTrace();
			}
			Bitmap bmp = BitmapFactory.decodeStream( imgStream );
			imgMap.put( uris[i].toString(), bmp );

		}
		return imgMap;
	}
}