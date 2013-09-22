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
		Gridable gridItem = mGridItems.get(position);
		
		if( gridItem.getLayout().equals( Const.CATEGORY_GRID_LAYOUT ) ){
			return setupCategoryGridItem( inflater, gridItem );
		}else{
			return setupProductGridItem( inflater, gridItem );
		}
	}
	
	public View setupCategoryGridItem( LayoutInflater inflater, Gridable gridItem ){
		View gridItemView;
		int resId = mCtx.getResources().getIdentifier( Const.CATEGORY_GRID_LAYOUT, "layout",
																									 mCtx.getPackageName() );
		gridItemView = inflater.inflate( resId, null );
		TextView infoTV = (TextView) gridItemView.findViewById( R.id.item_text );
		infoTV.setText( gridItem.getCaption() );
		ImageView img = (ImageView) gridItemView.findViewById( R.id.item_image );
		try{
			img.setImageBitmap( ImageLoader.loadImage( new URI( gridItem.getImage().getSrc() ) ));
		}catch( URISyntaxException e ){
			e.printStackTrace();
		}
		return gridItemView;
	}
	
	public View setupProductGridItem( LayoutInflater inflater, Gridable gridItem ){
		View gridItemView;
		int resId = mCtx.getResources().getIdentifier( Const.PRODUCT_GRID_LAYOUT, "layout",
																									 mCtx.getPackageName() );
		gridItemView = inflater.inflate( resId, null );
		TextView titleTV = (TextView) gridItemView.findViewById( R.id.item_title );
		titleTV.setText( gridItem.getValue("title") );
		ImageView img = (ImageView) gridItemView.findViewById( R.id.item_image );
		try{
			img.setImageBitmap( ImageLoader.loadImage( new URI( gridItem.getImage().getSrc() ) ));
		}catch( URISyntaxException e ){
			e.printStackTrace();
		}

		TextView retailTV = (TextView) gridItemView.findViewById( R.id.item_retail_price );
		retailTV.setText( gridItem.getValue("retail") );
		if( gridItem.hasValue("retail") )
			retailTV.setText( gridItem.getValue("retail") );
		else if ( gridItem.hasValue("value") )
			retailTV.setText( gridItem.getValue("value") );
		else
			retailTV.setText("0.00");
		TextView currencyTV = (TextView) gridItemView.findViewById( R.id.item_currency );
		currency.setText( gridItem.getValue("currency") );

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