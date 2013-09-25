package com.tstine.pangolinprecision;

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

import java.lang.Thread;
import java.lang.Runnable;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import android.graphics.Bitmap;

import java.lang.InterruptedException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.CancellationException;

import android.app.ProgressDialog;

public class ShowcaseActivity extends Activity {
	private final String TITLE_KEY="TITLE";
	public static ProgressDialog mProgDialog = null;
	
	@Override
	public void onCreate( Bundle ringtail ){
		super.onCreate( ringtail );
		if( mProgDialog == null ){
			mProgDialog = new ProgressDialog( this );
			mProgDialog.setMessage("Loading...");
		}
		Intent intent = getIntent();
		final Uri uri = intent.getData();
		final String title = intent.getStringExtra( TITLE_KEY );
		if( title!= null )
			setTitle( title );

		ArrayList<Gridable> gridItems = null;
		try{
			GetGridItemsTask task = new GetGridItemsTask(this);
			gridItems = task.execute(uri).get();
		}catch( InterruptedException e ){
			e.printStackTrace();
		}catch( ExecutionException e ){
			e.printStackTrace();
		}catch( CancellationException e ){
			e.printStackTrace();
		}
		setupUI( gridItems, uri );
		if(		mProgDialog.isShowing() )
			mProgDialog.dismiss();

	}
	public void setupUI( ArrayList<Gridable> gridItems, Uri uri ){
		if( gridItems == null )
			setContentView(R.layout.sorry);
		else if( gridItems.isEmpty() ){
			Intent prodIntent = new Intent( this, ProductDetailsActivity.class );
			prodIntent.setData( uri );
			startActivity(prodIntent);
			finish();
		}
		else{
			if( gridItems.get(0).hasImage() ){
				setContentView( R.layout.grid_view );
				GridView grid = (GridView) findViewById( R.id.grid );
				ArrayList<URI> uriArr = new ArrayList<URI>();
				for( Gridable gridItem : gridItems ){
					try{
						uriArr.add( new URI( gridItem.getImage().getSrc() ) );
					}catch(URISyntaxException e){
						e.printStackTrace();
					}

				}
				HashMap<String,Bitmap> imgMap = null;
				try{
					imgMap = new ImageLoader( this ).execute(
						uriArr.toArray( new URI[uriArr.size()]) ).get();
				}catch( InterruptedException e ){
					e.printStackTrace();
				}catch( ExecutionException e ){
					e.printStackTrace();
				}catch( CancellationException e ){
					e.printStackTrace();
				}

				grid.setAdapter( new GridableImageAdapter( this, gridItems, imgMap ) );
				grid.setOnItemClickListener( new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick( AdapterView<?> parent, final View view,
																		 int position, long id ){
							mProgDialog.show();
							Gridable selected = (Gridable) parent.getItemAtPosition( position ) ;
							Intent intent = getIntent();
							intent.setData( Uri.parse( Const.HOST + selected.getValue("href") ) );
							intent.putExtra( TITLE_KEY, selected.getValue("title") );
							startActivity( intent );
						}
					});
			}
			
			else{
				setContentView( R.layout.list_view );
				ListView list = (ListView) findViewById( R.id.list );
				ArrayAdapter adapter = new ArrayAdapter(
					this,android.R.layout.simple_list_item_1,gridItems );
				list.setAdapter( adapter );
				list.setOnItemClickListener( new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick( AdapterView<?> parent, final View view,
																		 int position, long id ){
							Gridable selected = (Gridable) parent.getItemAtPosition( position );
							Intent intent = getIntent();
							intent.setData( Uri.parse( Const.HOST + selected.getValue("href") ) );
							intent.putExtra( TITLE_KEY, selected.getValue("title") );
							mProgDialog.show();
							startActivity(intent );
						}
					});
			}
		}
	}

}