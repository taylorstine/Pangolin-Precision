package com.tstine.pangolinprecision;

import java.util.ArrayList;

import android.content.Context;

import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import android.util.Log;

import java.net.URI;
import java.net.URISyntaxException;

public class ImgAdapter extends BaseAdapter{
	private Context mCtx;
	private ArrayList<Gridable> mGridItems;

	public ImgAdapter( Context ctx, ArrayList<? extends Gridable> gridItems ){
		mCtx = ctx;
		mGridItems = (ArrayList<Gridable>)gridItems;
	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent ){
		LayoutInflater inflater = (LayoutInflater) mCtx
			.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		View gridItemView;
		Gridable gridItem = mGridItems.get(position);
		Log.d( Const.APP_TAG, "position: " + position + " Grid item: " + gridItem.getCaption() );
		//		if( convertView == null ){
			gridItemView = inflater.inflate( R.layout.grid_item, null );
			TextView infoTV = (TextView) gridItemView.findViewById( R.id.item_text );
			infoTV.setText( gridItem.getCaption() );
			ImageView img = (ImageView) gridItemView.findViewById( R.id.item_image );
			try{
				img.setImageBitmap( ImageLoader.loadImage( new URI( gridItem.getImage().getSrc() ) ));
			}catch( URISyntaxException e ){
				e.printStackTrace();
			 }
			//		}else{
			//gridItemView = convertView;
			//}
		return gridItemView;
	}

	@Override
	public Object getItem( int position ){return mGridItems.get(position);}

	@Override
	public int getCount(){return mGridItems.size();}

	@Override
	public long getItemId( int position ){return 0;}

}