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
		subGrid.setOnItemClickListener( new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick( AdapterView<?> parent, View v,
																 int position, long id ){
					Subcategory selected = (Subcategory) parent.getItemAtPosition( position );
					Intent intent = new Intent( v.getContext(), ProductsActivity.class );
					intent.setData( Uri.parse( Const.HOST + selected.getHref() ) );
					startActivity( intent );
				}
			});
	}

	
	public ArrayList<Subcategory> getSubcategories( Uri uri ){
		ArrayList<Subcategory> subCategories = new ArrayList<Subcategory>();
		JSONObject jobj = null;
		try{
			jobj = JSONParser.parse(new URI(uri.toString()));
			//Log.d( Const.APP_TAG, "Subcat output:\n" + jobj.toString(2) );
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
				JSONObject imgJson = (JSONObject) subCatJobj.get("_bb_image");
				//Log.d(Const.APP_TAG, imgJson.toString(2) );
				String imgSrc = imgJson.getString("src");
				String imgAlt = imgJson.getString("alt");
				Image img = new Image( imgSrc, imgAlt );
				Subcategory subCat = new Subcategory(  subCatTitle, subCatId, subHref, img );
				Log.d(Const.APP_TAG, subCat.getTitle() + " " + subCat.getId() + " " + subCat.getHref() );
				subCategories.add( subCat );
			}
		}
		catch( JSONException e){
			e.printStackTrace();
		}
		Log.d(Const.APP_TAG, "subcat size: " + subCategories.size() );
		return subCategories;
	}
}