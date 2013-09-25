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


public class GetCategoryGridItemsTask extends AsyncTask< JSONObject, Void, Gridable>{
		protected Gridable doInBackground( JSONObject... jobjs ){
			JSONObject jobj = jobjs[0];
			
			Image img = null;
			HashMap<String, String> infoMap = new HashMap<String,String>();
			infoMap.putAll(JSONParser.getKeyValues( jobj ));
			try{
				if( jobj.has("_bb_image" ) ){
					JSONObject imgJson = (JSONObject) jobj.get("_bb_image");
					String src = "http://placehold.it/150/c11b17/ffffff&text=category";
					String alt = "Image text";
					if( imgJson.has("src" ) )
						src = imgJson.getString("src");
					if( imgJson.has("alt") )
						alt = imgJson.getString("alt");
					img = new Image( src, alt );
				}
				else if( jobj.has("image") ){
					img = new Image( jobj.getString("image") );
				}
			}catch( JSONException e ){
				e.printStackTrace();
			}

			return new Gridable( img, infoMap, Const.CATEGORY_GRID_LAYOUT );
		}
	}

