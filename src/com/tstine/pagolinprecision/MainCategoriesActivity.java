package com.tstine.pangolinprecision;

import android.app.ListActivity;
import android.os.Bundle;

import java.util.ArrayList;

import java.io.IOException;

import java.net.URI;
import java.net.URISyntaxException;

import android.util.Log;

import android.content.Context;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONArray;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.view.View;

public class MainCategoriesActivity extends ListActivity
{
	@Override
	public void onCreate(Bundle ringtail )
	{
		super.onCreate( ringtail );
		if(!testConnection( this )){
			//explosion here
		}
		ArrayList<Category> cats = getCategories( Const.HOST );
		ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(this, android.R.layout.simple_list_item_1,
																																cats );
		setListAdapter( adapter );
				
	}

	@Override
	protected void onListItemClick( ListView l, View v, int position, long id ){
		Category item = (Category) getListAdapter().getItem(position );
		Intent intent = new Intent( v.getContext(), SubcategoryActivity.class );
		intent.setData( Uri.parse( Const.HOST + "/categories/" + item.getId() ) );
		startActivity( intent );
	}

	public ArrayList<Category> getCategories( String uri ){
		ArrayList<Category> categories = new ArrayList<Category>();
		JSONObject jobj = null;
		try{
			jobj = JSONParser.parse( new URI( uri ) );
		}catch( URISyntaxException e ){
			e.printStackTrace();
		}
		try{
			JSONArray catArray = (JSONArray) jobj.get( "categories");
			for( int i= 0; i < catArray.length(); i++ ){
				JSONObject catJobj = (JSONObject) catArray.get(i);
				String catName = catJobj.getString("title");
				String catId = catJobj.getString("id");
				String href = catJobj.getString("href");
				Category cat = new Category( catName, catId, href);
				categories.add( cat );
			}
		}catch( JSONException e ){
			e.printStackTrace();
		}
		return categories;
	}

	public boolean testConnection( Context ctx ){
		ConnectivityManager cm = (ConnectivityManager)
			ctx.getSystemService( Context.CONNECTIVITY_SERVICE );
		NetworkInfo net = cm.getActiveNetworkInfo();
		String networkInfo = "Type: " + net.getTypeName() +
			"\nSubtype: " + net.getSubtypeName() +
			"\nExtra: " + net.getExtraInfo ();
		Log.d( Const.APP_TAG, networkInfo );
		boolean isConnected = net.isConnectedOrConnecting();
		return isConnected;
	}
}
