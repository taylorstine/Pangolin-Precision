package com.tstine.pangolinprecision;

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

public class ShowcaseActivity extends Activity {
	@Override
	public void onCreate( Bundle ringtail ){
		super.onCreate( ringtail );
		Intent intent = getIntent();
		Uri uri = intent.getData();
		ArrayList<Gridable> gridItems = JSONParser.getGridItems( uri );
		if( gridItems.isEmpty() ){
			Intent prodIntent = new Intent( this, ProductDetailsActivity.class );
			prodIntent.setData( uri );
			startActivity(prodIntent);
			finish();
		}
		else{
			if( gridItems.get(0).hasImage() ){
				setContentView( R.layout.grid_view );
				GridView grid = (GridView) findViewById( R.id.grid );
				grid.setAdapter( new ImgAdapter( this, gridItems ) );
				grid.setOnItemClickListener( new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick( AdapterView<?> parent, final View view,
																		 int position, long id ){
							Gridable selected = (Gridable) parent.getItemAtPosition( position ) ;
							Intent intent = getIntent();
							intent.setData( Uri.parse( Const.HOST + selected.getValue("href") ) );
							startActivity( intent );
						}
					});
			}
			
			else{
				setContentView( R.layout.list_view );
				ListView list = (ListView) findViewById( R.id.list );
				ArrayAdapter adapter = new ArrayAdapter( this, android.R.layout.simple_list_item_1,
																								 gridItems );
				list.setAdapter( adapter );
				list.setOnItemClickListener( new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick( AdapterView<?> parent, final View view,
																		 int position, long id ){
							Gridable selected = (Gridable) parent.getItemAtPosition( position );
							Intent intent = getIntent();
							intent.setData( Uri.parse( Const.HOST + selected.getValue("href") ) );
						startActivity(intent );
						}
					});
			}
		}
	}

}