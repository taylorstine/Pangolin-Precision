package com.tstine.pangolinprecision;

import java.lang.RuntimeException;
import java.io.IOException;

import org.json.JSONObject;
import org.json.JSONException;

import android.util.Log;

import java.net.URI;
import org.apache.http.util.EntityUtils;
import org.apache.http.ParseException;
import org.apache.http.conn.BasicManagedEntity;


public class JSONParser{
	public static JSONObject parse( URI ... uris ){
		BasicManagedEntity ent = HttpGetter.get( uris[0] );
		String jsonText = "";
		try{
			jsonText = EntityUtils.toString( ent );
		}catch( ParseException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}catch( IOException e ){
			e.printStackTrace();
			throw new RuntimeException();
		}
		Log.d(Const.APP_TAG, "text length: " + jsonText.length() );
		JSONObject jsonObj = null;
		try{
			jsonObj = new JSONObject( jsonText );
		}catch( JSONException e ){
			//Log.d( Const.APP_TAG, jsonText );
			e.printStackTrace();
			throw new RuntimeException();
		}
		return jsonObj;
	}
}