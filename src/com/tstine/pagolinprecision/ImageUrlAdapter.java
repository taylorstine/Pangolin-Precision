package com.tstine.pangolinprecision;

import android.app.Activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;

import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import android.graphics.Paint;
import android.graphics.Color;
import android.graphics.Bitmap;

import android.content.Intent;

import java.lang.InterruptedException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.CancellationException;

import android.util.Log;

import java.net.URI;
import java.net.URISyntaxException;

public class ImageUrlAdapter extends BaseAdapter{
	private Context mCtx;
	private HashMap<String, Bitmap> mImages;
	private String[] mImageUrls;

	public ImageUrlAdapter( Context ctx, String[] imgUrls ){
		mCtx = ctx;
		mImageUrls = imgUrls;
		ArrayList<URI> uris = new ArrayList<URI>();
		for( int i=0; i < imgUrls.length; i++ ){
			try{
				if( imgUrls[i] != null )
					uris.add( new URI( imgUrls[i] ) );
			}catch(URISyntaxException e){
				e.printStackTrace();
			}
		}
		
		if( uris.isEmpty() ){
			try{
				uris.add( new URI( "http://placehold.it/300/c11b17/ffffff&text=1x"));
			}catch( URISyntaxException e){
				e.printStackTrace();
			}
		}
		try{
			mImages = new ImageLoader( (Activity)mCtx ).execute(
				uris.toArray(new URI[uris.size()]) ).get();
		}catch( InterruptedException e ){
			e.printStackTrace();
		}catch( ExecutionException e ){
			e.printStackTrace();
		}catch( CancellationException e ){
			e.printStackTrace();
		}
	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent ){
		LayoutInflater inflater = (LayoutInflater) mCtx
			.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		View gridItemView = inflater.inflate( R.layout.basic_grid_item, null );
		String imgUrl = mImageUrls[ position ];
		ImageView img = (ImageView) gridItemView.findViewById( R.id.item_image );
		img.setImageBitmap( mImages.get(imgUrl) );
		return gridItemView;
	}

	@Override
	public Object getItem( int position ){return mImageUrls[position]; }

	@Override
	public int getCount(){return mImageUrls.length; }

	@Override
	public long getItemId( int position ){return 0;}

}