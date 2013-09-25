package com.tstine.pangolinprecision;

import java.util.ArrayList;

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

import android.util.Log;

import java.util.HashMap;


import java.net.URI;
import java.net.URISyntaxException;

public class GridableImageAdapter extends BaseAdapter{
	private Context mCtx;
	private ArrayList<Gridable> mGridItems;
	private HashMap<String,Bitmap> mImgMap;
	
	public GridableImageAdapter( Context ctx,
															 ArrayList<? extends Gridable> gridItems,
															 HashMap<String,Bitmap> imageMap ){
		mCtx = ctx;
		mGridItems = (ArrayList<Gridable>)gridItems;
		mImgMap = imageMap;
	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent ){
		
		LayoutInflater inflater = (LayoutInflater) mCtx
			.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		Gridable gridItem = mGridItems.get(position);
		
		if( gridItem.getLayout().equals( Const.CATEGORY_GRID_LAYOUT ) ){
			return setupCategoryGridItem( gridItem, getViewLayout( Const.CATEGORY_GRID_LAYOUT, inflater ));
		}else if (gridItem.getLayout().equals( Const.PRODUCT_GRID_LAYOUT) ){
			return setupProductGridItem( gridItem,getViewLayout( Const.PRODUCT_GRID_LAYOUT, inflater) );
		}
		else
			return null;
	}

	public View getViewLayout( String layout, LayoutInflater inflater ){
		int resId = mCtx.getResources().getIdentifier( layout,
																									"layout",
																									mCtx.getPackageName() );
		return inflater.inflate( resId, null );
	}
	
	public View setupSwatchGridItem( Gridable gridItem, View gridItemView ){
		ImageView img = (ImageView) gridItemView.findViewById( R.id.item_image );
		img.setImageBitmap( mImgMap.get( gridItem.getImage().getSrc() ) );
		return gridItemView;

	}
	
	public View setupCategoryGridItem( Gridable gridItem, View gridItemView ){
		TextView infoTV = (TextView) gridItemView.findViewById( R.id.item_text );
		infoTV.setText( gridItem.getCaption() );
		ImageView img = (ImageView) gridItemView.findViewById( R.id.item_image );
		img.setImageBitmap( mImgMap.get( gridItem.getImage().getSrc() ) );
		return gridItemView;
	}
	
	public View setupProductGridItem( Gridable gridItem, View gridItemView ){
		
		TextView titleTV = (TextView) gridItemView.findViewById( R.id.item_title );
		titleTV.setText( gridItem.getValue("title") );
		ImageView img = (ImageView) gridItemView.findViewById( R.id.item_image );
		img.setImageBitmap( mImgMap.get( gridItem.getImage().getSrc() ) );

		TextView retailTV = (TextView) gridItemView.findViewById( R.id.item_retail_price );
		retailTV.setText( gridItem.getValue("retail") );
		if( gridItem.hasValue("retail") )
			retailTV.setText( gridItem.getValue("retail") );
		else if ( gridItem.hasValue("value") )
			retailTV.setText( gridItem.getValue("value") );
		else
			retailTV.setText("0.00");
		TextView currencyTV = (TextView) gridItemView.findViewById( R.id.item_currency );
		currencyTV.setText( gridItem.getValue("currency") );

		if( gridItem.hasValue("sale" ) ){
			retailTV.setTextColor(Color.rgb(255, 20, 20 ) );
			retailTV.setPaintFlags( retailTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG );
			TextView saleTV = (TextView) gridItemView.findViewById( R.id.item_sale_price );
			saleTV.setText( gridItem.getValue("sale") );
		}
		return gridItemView;
	}

	@Override
	public Object getItem( int position ){return mGridItems.get(position);}

	@Override
	public int getCount(){return mGridItems.size();}

	@Override
	public long getItemId( int position ){return 0;}

}