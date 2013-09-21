package com.tstine.pangolinprecision;

import java.net.URI;

import java.io.IOException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.util.EntityUtils;
import org.apache.http.ParseException;
import org.apache.http.conn.BasicManagedEntity;


public class HttpGetter{
	public static BasicManagedEntity get( URI uri ){
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet( uri );
		BasicHttpResponse response = null;
		try{
			response = (BasicHttpResponse ) client.execute( httpGet );
		}catch( ClientProtocolException e){
			e.printStackTrace();
		}catch( IOException e ){
			e.printStackTrace();
		}
		return (BasicManagedEntity) response.getEntity();
	}
}