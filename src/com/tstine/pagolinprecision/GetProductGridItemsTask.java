package com.tstine.pangolinprecision;

import java.lang.RuntimeException;
import java.lang.InterruptedException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.CancellationException;

import android.os.AsyncTask;


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

import org.apache.http.util.EntityUtils;
import org.apache.http.ParseException;
import org.apache.http.conn.BasicManagedEntity;

public class GetProductGridItemsTask extends AsyncTask< JSONObject, Void, Gridable > {
		protected Gridable doInBackground( JSONObject... jobjs){
			JSONObject jobj = jobjs[0];

			Image img = null;
			HashMap<String,String> infoMap = JSONParser.getKeyValues(jobj);
			HashMap<String,String> priceMap = new HashMap<String,String>();
			String src= "http://placehold.it/100/c11b17/ffffff&text=thumb";
			String alt= "Image text";
			try{
				if( jobj.has("price" ) ){
					priceMap = JSONParser.getKeyValues( jobj.getJSONObject("price") );
				}
				if(!priceMap.containsKey("currency" ) )
					priceMap.put("currency","USD");

				if( jobj.has("image" ) ){
					JSONObject imgObj = jobj.getJSONObject("image");
					if( imgObj.has("thumbs") ){
						HashMap<String, String> imgSrc = JSONParser.getKeyValues( imgObj.getJSONObject("thumbs") );
						if( imgSrc.containsKey("small") ){
							src = imgSrc.get("small");
						}
						else if( imgSrc.containsKey("large") ){
							src = imgSrc.get("large");
						}
					}
				}
			}catch( JSONException e){
				e.printStackTrace();
			}
			img = new Image( src, alt );
			infoMap.putAll(priceMap);
			return new Gridable( img, infoMap, Const.PRODUCT_GRID_LAYOUT );
		}
	}
