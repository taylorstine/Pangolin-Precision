package com.tstine.pangolinprecision;

import java.net.URI;

import android.util.Log;

import java.util.List;
import java.util.ArrayList;

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



public class HttpGetter{
	private static DefaultHttpClient mClient;
	
	public static BasicManagedEntity get( URI uri ){
		if( mClient == null )
			mClient = new DefaultHttpClient();
		
		HttpGet httpGet = new HttpGet( uri );
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

	public static void post( String form, String content_type ){
		if( mClient == null )
			mClient = new DefaultHttpClient();
		HttpPost post = new HttpPost("https://emsapi.bbhosted.com/checkout/cart");
		try{
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			pairs.add(new BasicNameValuePair( "form", form) );
			pairs.add( new BasicNameValuePair( "content-type", content_type) );
			post.setEntity( new UrlEncodedFormEntity( pairs ) );
			BasicHttpResponse response = (BasicHttpResponse) mClient.execute( post );
			BasicManagedEntity ent = (BasicManagedEntity)response.getEntity();
			String responseText = EntityUtils.toString( ent );
		}catch( ClientProtocolException e){
			e.printStackTrace();
		}catch( IOException e ){
			e.printStackTrace();
		}
	}
}