package com.tstine.pangolinprecision;

import java.lang.RuntimeException;
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
		Log.d(Const.APP_TAG, "text length: " + jsonText.length() );
		JSONObject jsonObj = null;
		try{
			jsonObj = new JSONObject( jsonText );
		}catch( JSONException e ){
			e.printStackTrace();
		}
		return jsonObj;
	}

	public static ArrayList<Gridable> getGridItems( Uri uri ){
		ArrayList<Gridable> gridItems = new ArrayList<Gridable>();
		JSONObject jobj = null;
		try{
			jobj = JSONParser.parse( new URI(uri.toString() ));
		}catch( URISyntaxException e ){
			e.printStackTrace();
		}
		try{
			if( jobj.has("categories") ){
				JSONArray catArray = (JSONArray) jobj.get( "categories" );
				for( int i = 0; i < catArray.length(); i++ ){
					gridItems.add( getCategoryGridItem( (JSONObject) catArray.get(i)) );
				}
			}
			if( jobj.has("products" ) ){
				JSONArray prodArray = (JSONArray) jobj.get("products");
				for( int i = 0; i < prodArray.length(); i++ ) {
					gridItems.add( getProductGridItem( (JSONObject) prodArray.get(i) ) );
				}
			}
		}catch( JSONException e ){
			e.printStackTrace();
		}
		return gridItems;
	}

	public static Gridable getProductGridItem( JSONObject jobj){
		Image img = null;
		HashMap<String,String> infoMap = getKeyValues(jobj);
		HashMap<String,String> priceMap = new HashMap<String,String>();
		String src= "http://placehold.it/100/c11b17/ffffff&text=thumb";
		String alt= "Image text";
		try{
			if( jobj.has("price" ) ){
				priceMap = getKeyValues( jobj.getJSONObject("price") );
			}
			if(!priceMap.containsKey("currency" ) )
				priceMap.put("currency","USD");

			if( jobj.has("image" ) ){
				JSONObject imgObj = jobj.getJSONObject("image");
				if( imgObj.has("thumbs") ){
					HashMap<String, String> imgSrc = getKeyValues( imgObj.getJSONObject("thumbs") );
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
	
	public static Gridable getCategoryGridItem( JSONObject jobj ){
		Image img = null;
		HashMap<String, String> infoMap = new HashMap<String,String>();

		infoMap.putAll(getKeyValues( jobj ));
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
	/*	
	public static ArrayList<Category> getCategories( Uri uri ){
		ArrayList<Category> categories = new ArrayList<Category>();

		try{
			if( !jobj.has("categories") )
				return categories;
			JSONArray catArray = (JSONArray) jobj.get( "categories" );
			for( int i = 0; i < catArray.length(); i++ ){
				categories.add( getCategory( (JSONObject) catArray.get(i)) );
			}
		}catch( JSONException e ){
			e.printStackTrace();
		}
		return categories;
	}

	public static Category getCategory( JSONObject jobj ){
		HashMap<String,String> base = getBaseInfo( jobj );
		Image img = null;
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
		return new Category( base, img );
		}*/

	public static HashMap<String,String> getKeyValues( JSONObject jobj ){
		HashMap<String, String> map = new HashMap<String, String>();
		Iterator<Object> keys = jobj.keys();
		while( keys.hasNext() ){
			String keyString = String.valueOf(keys.next() );
			String valueString = null;
			try{
				valueString = jobj.getString( keyString );
			}catch( JSONException e ){
				e.printStackTrace();
			}
			map.put( keyString, valueString );
		}
		return map;
	}

	public static HashMap<String,String> getBaseInfo( JSONObject jobj ){
		HashMap< String, String > infoMap = getKeyValues( jobj );
		return infoMap;
	}


}