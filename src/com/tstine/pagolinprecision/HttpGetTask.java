package com.tstine.pangolinprecision;

import java.net.URI;

import android.util.Log;

import java.util.List;
import java.util.ArrayList;

import android.os.AsyncTask;

import java.io.IOException;

import org.apache.http.util.EntityUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.util.EntityUtils;
import org.apache.http.ParseException;
import org.apache.http.conn.BasicManagedEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;


public class HttpGetTask extends AsyncTask<URI,Void, BasicManagedEntity>{
	private final DefaultHttpClient mClient = new DefaultHttpClient();
	private Activity mParentActivity;
	private ProgressDialog mDialog;

	public HttpGetTask(){
		mParentActivity = null;
		mDialog = null;
	}

	public HttpGetTask( Activity activity ){
		mParentActivity = activity;
		mDialog = new ProgressDialog( activity );
	}
	@Override
	protected void onPreExecute(){
		if( mDialog!= null ){
			mDialog.setMessage("Loading...");
			mDialog.show();
		}
	}

	@Override
	protected void onPostExecute( final BasicManagedEntity ent ){
		if( mDialog != null && mDialog.isShowing() ){
			mDialog.dismiss();
		}
	}

	protected BasicManagedEntity doInBackground( URI... uris ){
		HttpGet httpGet = new HttpGet( uris[0] );
		BasicHttpResponse response = null;
		try{
			response = (BasicHttpResponse ) mClient.execute( httpGet );
		}catch( ClientProtocolException e){
			e.printStackTrace();
		}catch( IOException e ){
			e.printStackTrace();
		}
		return (BasicManagedEntity) response.getEntity();
	}
	
}