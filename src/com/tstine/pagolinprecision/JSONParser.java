package com.tstine.pangolinprecision;

import java.lang.RuntimeException;
import java.lang.InterruptedException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.TimeoutException;

import java.util.concurrent.TimeUnit;

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


public class JSONParser{
	public static JSONObject parse( URI ... uris){
		BasicManagedEntity ent = null;
		try{
			ent = new HttpGetTask().execute( uris[0] ).get();
		}catch( InterruptedException e ){
			e.printStackTrace();
		}catch( ExecutionException e ){
			e.printStackTrace();
		}catch( CancellationException e ){
			e.printStackTrace();
		}
	
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

	public static ArrayList<Gridable> getGridItems( Uri uri ){
		ArrayList<Gridable> gridItems = new ArrayList<Gridable>();
		JSONObject jobj = null;
		try{
			jobj = JSONParser.parse( new URI(uri.toString() ));
			if( jobj == null )
				return gridItems;
			//jobj = new JSONParseTask().execute( new URI( uri.toString() ) ).get();
		}catch( URISyntaxException e ){
			e.printStackTrace();
		}/*catch( InterruptedException e ){
			e.printStackTrace();
		}catch( ExecutionException e ){
			e.printStackTrace();
		}catch( CancellationException e ){
			e.printStackTrace();
		}/*catch( TimeoutException e ){
			e.printStackTrace();
			}*/

		if( jobj.has("categories") ){
			JSONArray catArray = null;
			try{
				catArray = (JSONArray) jobj.get( "categories" );
			}	catch( JSONException e ){
				e.printStackTrace();
			}
			for( int i = 0; i < catArray.length(); i++ ){
					//try{
						//gridItems.add( new GetCategoryGridItemsTask().execute( (JSONObject) catArray.get(i)).get());
				if( catArray.optJSONObject(i) != null )
					gridItems.add( getCategoryGridItem(catArray.optJSONObject(i)));


													 /*}catch( InterruptedException e ){
						e.printStackTrace();
					}catch( ExecutionException e ){
						e.printStackTrace();
					}catch( CancellationException e ){
						e.printStackTrace();
						}*/
						//}
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
					gridItems.add( getProductGridItem( (JSONObject) prodArray.optJSONObject(i)));
			}

			/*
				try{
						
				gridItems.add(
				new GetProductGridItemsTask()
				.execute( (JSONObject) prodArray.get(i) ).get() );
				}catch( InterruptedException e ){
				e.printStackTrace();
				}catch( ExecutionException e ){
				e.printStackTrace();
				}catch( CancellationException e ){
				e.printStackTrace();
				}*/
		}
		return gridItems;
	}

	/*	private class GetProductGridItemsTask extends AsyncTask< JSONObject, Void, Gridable > {
		protected  Gridable doInBackground( JSONObject jobj){
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
		}*/

	/*	private class GetCategoryGridItemsTask extends AsyncTask< JSONObject, Void, Gridable>{
		protected Gridable doInBackground( JSONObject jobj ){
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
		}*/


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
			}else if (jobj.has("images") ){
				JSONArray imgArray = jobj.optJSONArray("images");
				JSONObject imgsObj = imgArray.optJSONObject(0);
				if( imgsObj!=null && imgsObj.has("thumb") ){
					src = imgsObj.getString("thumb");
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


	public static ProductDetail getProductDetail( Uri uri ){
		JSONObject jobj = null;
		HashMap<String,String> info = new HashMap<String,String>();
		ArrayList<String> bullets = new ArrayList<String>();
		ArrayList<String> promos = new ArrayList<String>();
		ArrayList<Variation> variations = new ArrayList<Variation>();
		ArrayList<Gridable> upsales = new ArrayList<Gridable>();
		ArrayList<Gridable> crossSales = new ArrayList<Gridable>();
		ArrayList<HashMap<String,String>> imageList = new ArrayList<HashMap<String,String>>();
		try{
			jobj = JSONParser.parse( new URI( uri.toString() ) );
		}catch( URISyntaxException e ){
			e.printStackTrace();
		}/*catch( InterruptedException e ){
			e.printStackTrace();
		}catch( ExecutionException e ){
			e.printStackTrace();
		}catch( CancellationException e ){
			e.printStackTrace();
			}*/
		
		try{
			
			info.putAll( getKeyValues( jobj ) );
			
			if( jobj.optJSONObject("identifiers") != null )
				info.putAll( getKeyValues( jobj.optJSONObject("identifiers") ));
			
			if( jobj.optJSONObject("description" ) != null ){
				JSONObject descripObj = jobj.optJSONObject("description");
				info.put( "leadingEquity",
									descripObj.optString("leadingEquity", "None") );
			}

			JSONArray bulletArray = jobj.optJSONObject("description").optJSONArray("bullets");
			for( int i = 0; i < bulletArray.length(); i++ ){
				bullets.add( (String) bulletArray.get(i) );
			}
			JSONArray promoArray = jobj.optJSONArray("promos");
			if( promoArray != null ){
				for( int i = 0; i < promoArray.length(); i++ ){
					promos.add( promoArray.optJSONObject(i)
											.optString("description", "none") );
				}
			}else{
					JSONObject promoObj = jobj.optJSONObject("promos");
					if( promoObj != null )
						promos.add( promoObj.optString("description", "none") );

			}
			
			JSONArray varArray = jobj.optJSONArray( "variations" );
			if( varArray != null ){
				for( int i = 0; i < varArray.length(); i++ ){
					variations.add( getVariation( varArray.optJSONObject(i) ) );
				}
			}

			JSONArray upsaleArray = jobj.optJSONArray( "up_sales" );
			if( upsaleArray != null ){
				for( int i = 0; i < upsaleArray.length(); i++ ){
					upsales.add( getProductGridItem( upsaleArray.optJSONObject(i ) ) );
				}
			}
			
			JSONArray crossSaleArray = jobj.optJSONArray("cross_sales" );
			if( crossSaleArray != null ){
				for( int i = 0; i < crossSaleArray.length(); i++ ){
					crossSales.add( getProductGridItem( crossSaleArray.optJSONObject(i ) ));
				}
			}


			if( jobj.has("images") && jobj.optJSONArray("images") != null ){
				imageList = getImageList( jobj.optJSONArray("images") );
			}
		}catch( JSONException e ){
			e.printStackTrace();
		}
		return new ProductDetail( info, bullets, promos,
															variations, upsales, crossSales,
															imageList);
	}

	private static Variation getVariation( JSONObject varObj ){
		HashMap<String, String> info = new HashMap<String,String>();
		HashMap<String,String> options = new HashMap<String,String>();
		ArrayList<HashMap<String,String>> imageList = new ArrayList<HashMap<String,String>>();
		info.putAll( getKeyValues( varObj ) );
		
		if( varObj.has("identifiers") && varObj.optJSONObject("identifiers")!= null )
			info.putAll( getKeyValues( varObj.optJSONObject("identifiers") ) );
		if( varObj.has("price" ) && varObj.optJSONObject("price") != null){
			info.putAll( getKeyValues(varObj.optJSONObject("price") ) );
		}
		if( varObj.has("images") && varObj.optJSONArray("images") != null )
			imageList = getImageList( varObj.optJSONArray("images") );
		if( varObj.has("options" ) && varObj.optJSONObject("options") != null)
			options.putAll( getKeyValues( varObj.optJSONObject( "options" ) ) );
		if( varObj.has("availability") && varObj.optJSONObject("availability") != null )
			info.putAll( getAvailabilityMap( varObj.optJSONObject("availability" ) ) );
		if( varObj.has("price") && varObj.optJSONObject("price") != null )
			info.putAll( getKeyValues( varObj.optJSONObject("price") ) );

		return new Variation( info, options, imageList );
	}

	private static HashMap<String, String> getAvailabilityMap( JSONObject availObj ){
		HashMap<String, String> availMap = new HashMap<String, String>();
		availMap.put( "availability", "N/A" );
		availMap.put( "shipping", "N/A" );
		if( availObj.has("online") ){
			JSONObject onlineObj = availObj.optJSONObject("online");
			availMap.put( "availability", onlineObj.optString("value", "N/A") );
			availMap.put( "shipping", onlineObj.optString("shipping", "N/A") );
		}
		return availMap;
	}
	
	private static ArrayList<HashMap<String,String>> getImageList(
		JSONArray imgArray ){
		ArrayList<HashMap<String,String>> imgList =
			new ArrayList<HashMap<String,String>>();
		for( int i=0 ; i< imgArray.length(); i++ ){
			imgList.add( getImageMap( imgArray.optJSONObject(i)) );
		}
		return imgList;
	}
	
	private static HashMap<String, String> getImageMap( JSONObject imgObj ){
		HashMap<String, String> imageMap = new HashMap<String,String>();
		imageMap.put( "thumb", imgObj.optString(
									 "thumb","http://placehold.it/100/c11b17/ffffff&text=thumb"));
		imageMap.put( "1x", imgObj.optString(
									 "1x","http://placehold.it/100/c11b17/ffffff&text=thumb"));
		imageMap.put( "2x", imgObj.optString(
									 "1x","http://placehold.it/100/c11b17/ffffff&text=thumb"));
		return imageMap;
	}

	public static HashMap<String,String> getKeyValues( JSONObject jobj ){
		HashMap<String, String> map = new HashMap<String, String>();
		Iterator<Object> keys = jobj.keys();
		while( keys.hasNext() ){
			String keyString = String.valueOf( keys.next() );
			if( keyString == null ){
				continue;
			}
			String valueString = null;
			Object obj = jobj.opt(keyString);
			if( obj != null && obj instanceof String ){
				valueString = jobj.optString( keyString, "none" );
				map.put( keyString, valueString );
			}
		}
		return map;
	}

}