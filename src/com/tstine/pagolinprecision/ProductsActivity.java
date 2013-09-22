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

public class ProductsActivity extends Activity{
	@Override
	public void onCreate( Bundle ringtail ){
		super.onCreate( ringtail );
		setContentView( R.layout.grid_view );
		Intent intent = getIntent();
		Uri uri = intent.getData();
		Log.d( Const.APP_TAG, uri.toString() );
		ArrayList<Product> products = getProducts( uri );
	}

	public ArrayList<Product> getProducts( Uri uri ){
		ArrayList<Product> products = new ArrayList<Product>();
		JSONObject jobj = null;
		try{
			jobj = JSONParser.parse(new URI(uri.toString()));
			//Log.d(Const.APP_TAG, "Json object: \n"+ jobj.toString(2) );
		}catch( URISyntaxException e){
			e.printStackTrace();
		}
			//}catch( JSONException e){
			//e.printStackTrace();
			//}

		try{
			JSONArray prodArray = (JSONArray) jobj.get("products");
			for( int i = 0; i < prodArray.length(); i++ ){
				String prodTitle = null, href = null, id = null;
				
				JSONObject prodObj = (JSONObject) prodArray.get(i);

				if( prodObj.has("title") ) prodTitle = prodObj.getString("title");
				if( prodObj.has("href" ) ) href = prodObj.getString("href");
				if( prodObj.has("id" ) ) id = prodObj.getString("id");

				HashMap<String,String> priceHash = new HashMap<String,String>();
				String retail=null, sale=null, value=null, currency=null;
				priceHash.put(  Const.RETAIL_KEY, retail );
				priceHash.put( Const.SALE_KEY, sale );
				priceHash.put( Const.VALUE_KEY, value );
				priceHash.put( Const.CURRENCY_KEY, currency );

				if( prodObj.has("price" )){
					JSONObject priceObj = (JSONObject) prodObj.get("price");
					
					if( priceObj.has("retail" ) ) retail = priceObj.getString("retail" );
					if( priceObj.has("sale" ) ) sale = priceObj.getString("sale" );
					if( priceObj.has("value" ) ) value = priceObj.getString("value");
					if( priceObj.has("currency")) currency = priceObj.getString("currency");
						
					priceHash.put(  Const.RETAIL_KEY, retail );
					priceHash.put( Const.SALE_KEY, sale );
					priceHash.put( Const.VALUE_KEY, value );
					priceHash.put( Const.CURRENCY_KEY, currency );
				}

				ArrayList<ProductImage> prodImages = new ArrayList<ProductImage>();
				if( prodObj.has("images" ) ){
					JSONArray imgArray = prodObj.getJSONArray("images");
					Image thumb = null, x = null, xx = null;
					String alt = null;

					for( int j = 0; j < imgArray.length(); j++ ){
						JSONObject imgObj = (JSONObject) imgArray.get(j);
						if( imgObj.has("thumb" ) ) thumb = new Image( imgObj.getString("thumb" ) );
						if( imgObj.has("1x") ) x = new Image( imgObj.getString("1x" ) );
						if( imgObj.has("2x") ) xx = new Image( imgObj.getString("2x") );
						if( imgObj.has("alt") ) alt = imgObj.getString("alt");
						prodImages.add( new ProductImage( thumb, x, xx, alt ) );
					}
				}

				ArrayList<String> promos = new ArrayList<String>();
				if( prodObj.has("promos") ){
					JSONArray promoArray = prodObj.getJSONArray( "promos" );
					for( int j = 0; j < promoArray.length(); j++ ){
						JSONObject promoObj = (JSONObject) promoArray.get(j);
						String description = null;
						if( promoObj.has("description") ) description = promoObj.getString("description");
						promos.add( description );
					}
				}
				
				String condition = null;
				if( prodObj.has("condition") ) condition = prodObj.getString("condition");
				Product prod = new Product( prodTitle, prodImages, promos,
																		priceHash, id, href, condition );

				products.add( prod );
				//Log.d(Const.APP_TAG, "Prod title: " + prodTitle );
				Log.d( Const.APP_TAG, "ProdInfo: " + prod.toString() );
				
			}
		}catch(JSONException e) {
			e.printStackTrace();
		}

		
		return products;
	}

}  
