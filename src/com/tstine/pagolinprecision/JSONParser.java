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

	public ArrayList<Product> getProducts( Uri uri ){
		ArrayList<Product> products = new ArrayList<Product>();
		JSONObject jobj = null;
		try{
			jobj = JSONParser.parse(new URI(uri.toString()));
		}catch( URISyntaxException e){
			e.printStackTrace();
		}
		try{
			if( !jobj.has("products" ) )
				return products;
			JSONArray prodArray = (JSONArray) jobj.get("products");
			for( int i = 0; i < prodArray.length(); i++ ){
				Product prod = getProduct( (JSONObject) prodArray.get(i ) ) ;
				products.add( prod );
			}
		}catch(JSONException e) {
			e.printStackTrace();
		}
	}


	public Product getProduct( JSONObject prodObj ){
		Product prod;
		String prodTitle = "N/A", href = null, id = null,	condition = "N/A";
		ArrayList<ProductImage> prodImages = new ArrayList<ProductImage>();
		ArrayList<String> promos = new ArrayList<String>();
		HashMap<String,String> priceHash = new HashMap<String, String>();
		HashMap<String, String> baseInfoMap = new HashMap<String,String>();
		
		try{
			baseInfoMap = getBaseInfo( prodObj )
			if( prodObj.has("price" )){
				priceHash = getKeyValues( prodObj.getJSONObject("price") );
			}

			if( prodObj.has("images" ) ){
				prodImages = getImages( prodObj.getJSONArray("images") );
			}
			if( prodObj.has("promos") ){
				promos = getPromos( prodObj.getJSONArray( "promos" ) );
			}
			if( prodObj.has("condition") ) condition = prodObj.getString("condition");

			Product prod = new Product( baseInfoMap, prodImages, promos,
																	priceHash, condition );
		}catch(JSONException e) {
			e.printStackTrace();
		}
		return prod;
	}


	public HashMap<String,String> getKeyValues( JSONObject jobj ){
		HashMap<String, String> map = new HashMap<String, String>
		Iterator<Object> keys = jobj.keys();
		for( Object key : keys ){
			String keyString = String.valueOf(key );
			String valueString = jobj.getString( keyString );
			map.put( keyString, valueString );
		}
		return map;
	}

	public ArrayList<ProductImage> getImages( JSONArray imgArray ){
		ArrayList<String> productImages = new ArrayList<String>();
		for( int j=0 ; j < imgArray.size(); j++ ){
			JSONObject imgObj = (JSONObject) imgArray.get(j);
			String alt;
			Image thumb, x, xx;
			if( imgObj.has("alt") ) 
				alt = imgObj.getString("alt");
					
			if( imgObj.has("thumb") )
				thumb = new Image(imgObj.getString("thumb" ) );
			else
				thumb = new Image("http://placehold.it/100/c11b17/ffffff&text=thumb");
					
			if( imgObj.has("1x") )
				x = new Image( imgObj.getString("1x") );
			else
				x = new Image("http://placehold.it/300/c11b17/ffffff&text=1x");
					
			if( imgObj.has("2x") )
				xx = new Image( imgObj.getString("2x"));
			else
				x = new Image("http://placehold.it/600/c11b17/ffffff&text=2x");
			productImages.add( new ProductImage( thumb, x, xx, alt ) );
		}
		return productImages;
	}

	public ArrayList<String> getPromos( JSONArray promoArray ){
		ArrayList<String> promos = new ArrayList<String>();
		for( int j = 0; j < promoArray.size(); j++ ) {
			JSONObject promoObj = (JSONObject) promoArray.get(j);
			if( promoObj.has("description") )
				promos.add( promoObj.getString("description"));
		}
		return promos;
	}

	public HashMap<String,String> getBaseInfo( JSONObject jobj ){
		HashMap< String, String > infoMap = new HashMap<String,String>();
		infoMap.put("title", "N/A");
		infoMap.put("href", "N/A" );
		infoMap.put("id", "N/A" );
		if( prodObj.has("title") ) infoMap.put("title", prodObj.getString("title"));
		if( prodObj.has("href" ) ) infoMap.put("href", prodObj.getString("href") );
		if( prodObj.has("id" ) ) infoMap.put( "id", prodObj.getString("id") );
		return infoMap;
	}


}