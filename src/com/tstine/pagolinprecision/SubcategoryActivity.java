package com.tstine.pangolinprecision;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

import java.util.ArrayList;

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

import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.util.EntityUtils;
import org.apache.http.ParseException;
import org.apache.http.conn.BasicManagedEntity;

import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONArray;

import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;


public class SubcategoryActivity extends Activity {
	@Override
	public void onCreate( Bundle ringtail ){
		super.onCreate( ringtail );
		setContentView( R.layout.grid_view );
		Intent intent = getIntent();
		Uri uri = intent.getData();
		ArrayList<Subcategory> subCats = getSubcategories( uri );
		GridView subGrid = (GridView) findViewById( R.id.grid );
		subGrid.setAdapter( new ImgAdapter( this, subCats ) );
	}

	
	public ArrayList<Subcategory> getSubcategories( Uri uri ){
		ArrayList<Subcategory> subCategories = new ArrayList<Subcategory>();
		JSONObject jobj = null;
		try{
			jobj = JSONParser.parse(new URI(uri.toString()));
		}catch( URISyntaxException e){
			e.printStackTrace();
		}
		try{
			JSONArray subCatArray = (JSONArray) jobj.get("categories");
			for( int i = 0; i < subCatArray.length(); i++ ){
				JSONObject subCatJobj = (JSONObject) subCatArray.get(i);
				String subCatTitle = subCatJobj.getString("title");
				String subCatId = subCatJobj.getString("id");
				String subHref = subCatJobj.getString("href");
				JSONObject imgArray = (JSONObject) subCatJobj.get("_bb_image");
				String imgSrc = imgArray.getString("src");
				String imgAlt = imgArray.getString("alt");
				Image img = new Image( imgSrc, imgAlt );
				Subcategory subCat = new Subcategory(  subCatTitle, subCatId, subHref, img );
				Log.d(Const.APP_TAG, subCat.toString() );
				subCategories.add( subCat );
			}
		}
		catch( JSONException e){
			e.printStackTrace();
		}
		return subCategories;
	}
}