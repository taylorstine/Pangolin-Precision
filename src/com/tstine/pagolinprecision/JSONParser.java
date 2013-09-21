package com.tstine.pangolinprecision;

import java.io.IOException;

import org.json.JSONObject;
import org.json.JSONException;

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
		}catch( IOException e ){
			e.printStackTrace();
		}
		JSONObject jsonObj = null;
		try{
			jsonObj = new JSONObject( jsonText );
		}catch( JSONException e ){
			e.printStackTrace();
		}
		return jsonObj;
	}
}