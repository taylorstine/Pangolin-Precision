package com.tstine.pangolinprecision;

import java.lang.RuntimeException;
import java.lang.InterruptedException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.CancellationException;

import java.lang.InterruptedException;
import java.io.IOException;

import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONArray;

import android.util.Log;

import android.net.Uri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import java.net.URI;
import java.net.URISyntaxException;

import android.os.AsyncTask;

import org.apache.http.util.EntityUtils;
import org.apache.http.ParseException;
import org.apache.http.conn.BasicManagedEntity;

import android.app.Activity;
import android.app.ProgressDialog;

import android.content.Context;

public class GetGridItemsTask extends AsyncTask< Uri, Void, ArrayList<Gridable>>{
	private Context mCtx;
	private ProgressDialog mDialog;
	
	public GetGridItemsTask( Context ctx ){
		mCtx = ctx;
		mDialog = new ProgressDialog( mCtx );
		mDialog.setMessage("Loading...");
		mDialog.setIndeterminate(true);
		mDialog.setCancelable(false);
		mDialog.show();
	}
	@Override
	protected void onPreExecute(){
	}

	@Override
	protected void onPostExecute( final ArrayList<Gridable> grid){
		if( mDialog.isShowing() ){
			mDialog.dismiss();
		}
	}
	@Override
	protected ArrayList<Gridable> doInBackground( Uri... uris ){
		Uri uri = uris[0];
		ArrayList<Gridable> gridItems = new ArrayList<Gridable>();
		JSONObject jobj = null;
		try{
			jobj = JSONParser.parse( new URI(uri.toString() ));
			if( jobj == null )
				return gridItems;
		}catch( URISyntaxException e ){
			e.printStackTrace();
		}
		if( jobj.has("categories") ){
			JSONArray catArray = null;
			try{
				catArray = (JSONArray) jobj.get( "categories" );
			}	catch( JSONException e ){
				e.printStackTrace();
			}
			for( int i = 0; i < catArray.length(); i++ ){
				if( catArray.optJSONObject(i) != null )
					gridItems.add( JSONParser.getCategoryGridItem(catArray.optJSONObject(i)));
			}
		}

		if( jobj.has("products" ) ){
			JSONArray prodArray = null;
			try{
				prodArray = (JSONArray) jobj.get("products");
			}catch( JSONException e ){
				e.printStackTrace();
			}
			for( int i = 0; i < prodArray.length(); i++ ) {
				if( prodArray.optJSONObject(i) != null )
					gridItems.add( JSONParser.getProductGridItem( (JSONObject) prodArray.optJSONObject(i)));
			}
		}
		return gridItems;
	}
}
																			