package com.tstine.pangolinprecision;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;

import java.net.URI;
import java.net.URISyntaxException;
            
import android.app.Activity;
import android.os.Bundle;

import android.content.Context;
import android.content.Intent;

import android.util.Log;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONArray;

import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;

public class ProductDetailsActivity extends Activity{
	@Override
	public void onCreate(Bundle ringtail ){
		super.onCreate( ringtail  );
		setContentView( R.layout.product_details );
		Intent intent = getIntent();
		Uri uri = intent.getData();
		ProductDetails pd = getProductDetails( uri );
	}
	
	public ProductDetails getProductDetails( Uri uri ){
		ProductDetail details;
		JSONObject jobj = null;
		try{
			jobj = JSONParser.parse( new URI( uri.toString() ) );
		}catch( URISyntaxException e ){
			e.printStackTrace();
		}
		String title, brand, id, sku, upc, leadingEquity;
		ArrayList<String> bullets, promos;
		ArrayList<ProductDetails> variations;
		ArrayList<ProductImages> productImages;
		ArrayList<ProductDetails> upsales;
		
		try{
			if( jobj.has("identifiers") ){
				HashMap<String,String> identifiers = getIdentifiers((JSONObject) jobj.get("identifiers"));
				sku = identifiers.get("sku");
				upc = identifiers.get("upc");
				id = identifiers.get("id");
			}
			
			if( jobj.has("title") ) title = jobj.getString("title");
			if( jobj.has("brand" ) ) brand = jobj.getString("brand");
			if( jobj.has("description") ){
				JSONObject desObj = (JSONObject) jobj.get("description" );
				if( desObj.has("leadingEquity") )
					leadingEquity = desObj.getString("leadingEquity" );
				if( desObj.has("bullets" ) ){
					bullets = new ArrayList<String>();
						JSONArray bulletArray = (JSONArray) desObj.get("bullets");
						for( int j = 0; j < bulletObj.size(); j++ ){
							bullets.add( (String) bulletArray.get(j) );
						}
				}
			}
			
			if( jobj.has("images" )){
				productImages = getImages( (JSONArray) jobj.get("images") );
			}
			
			if( jobj.has("promos" )){
				promos = getPromos((JSONArray) jobj.get("promos") );
			}

			if( jobj.has("variations" )){
				variations = getVariations( (JSONArray) jobj.get("variations") ); 
			}

			if( jobj.has("up_sales" ) ){
				upsales = getUpsales( (JSONArray) jobj.get("up_sales" ) );
			}
					
		}catch( JSONException e){
			e.printStackTrace();
		}
	}

	public HashMap<String,String> getIdentifiers( JSONObject idObj ){
		HashMap<String,String> idMap = new HashMap<String, String>();
		if( idObj.has("id" ) )
			idMap.put("id", idObj.getString("id") );
		if( idObj.has("sku" ) )
			idMap.put("sku", idObj.getString("sku" ) );
		if( idObj.has("upc" ) )
			idMap.put("upc", idObj.getString("upc") );
		return idMap;
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

	public ArrayList<ProductDetails> getVariations( JSONArray varArray ){
		
		ArrayList<ProductDetails> variations = new ArrayList<ProductDetails>();
		
		for( int j = 0; j < varArray.size(); j++ ){
			
			JSONObject varObj = (JSONObject) varArray.get(j);
			HashMap<String, String> idMap = new HashMap<String,String>();
			ArrayList<ProductImages> productImages = new ArrayList<ProductImages>();
			HashMap<String,String> optionsMap = new HashMap<String,String>();
			HashMap<String,String> priceMap = new HashMap<String,String>();
			String swatch, availability, shipping, condition;
			if( varObj.has("identifiers" ))
				idMap = getIdentifiers( varObj );
			if( varObj.has("images" ) ){
				productImages = getImages( (JSONArray) varObj.get("images") );
			}
			if( varObj.has("swatch") ){
				swatch = varObj.getString("swatch");
			}
			if( varObj.has("options") ){
				optionsMap =  getKeyValues( (JSONObject) varObj.get("options"));
			}
			if( varObj.has("availability") && varObj.getJSONObject("availability").has("online") &&
					varObj.getJSONObject("availability").getJSONObject("online").has("value") )
				availability =varObj.getJSONObject("availability").getJSONObject("online").getString("value");
			
			if( varObj.has("availability") && varObj.getJSONObject("availability").has("online") &&
					varObj.getJSONObject("availability").getJSONObject("online").has("shipping") )
				shipping = varObj.getJSONObject("availability").getJSONObject("online").getString("shipping");

			if( varObj.has("condition" ))
				condition = varObj.getString("condition" );

			if( varObj.has("price") ){
				priceMap = getKeyValues( varObj.getJSONObject("price" ) );
			}

			//get up sales and cross sales
			
					
		}
	}

	
}