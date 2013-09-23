package com.tstine.pangolinprecision;

import java.util.ArrayList;
import java.util.HashMap;

import java.net.URISyntaxException;
import java.net.URI;

import android.app.Activity;
import android.os.Bundle;

import android.content.Intent;

import android.util.Log;

import android.net.Uri;

import android.widget.TextView;
import android.widget.ImageView;

public class ProductDetailsActivity extends Activity{
	@Override
	public void onCreate( Bundle ringtail ){
		super.onCreate( ringtail );
		setContentView( R.layout.product_details );
		Intent intent = getIntent();
		Uri uri = intent.getData();
		ProductDetail prodDetail = JSONParser.getProductDetail( uri );

		ImageView mainImg = (ImageView) findViewById( R.id.main_image );
		HashMap<String,String> img = prodDetail.getImage(0);
		try{
			mainImg.setImageBitmap( ImageLoader.loadImage(
																new URI( (String) img.get("1x") ) ) );
				}catch( URISyntaxException e ){
			e.printStackTrace();
		}
		
		TextView titleTV = (TextView) findViewById( R.id.product_title );
		titleTV.setText( prodDetail.getValue("title") );
		TextView leadingEquityTV = (TextView) findViewById( R.id.product_leading_equity );
		leadingEquityTV.setText( prodDetail.getValue("leadingEquity" ) );
		TextView bulletsTV = (TextView) findViewById( R.id.product_bullets );
		StringBuffer bullets = new StringBuffer();
		ArrayList<String> prodBullets = prodDetail.getBullets();
		for( String bullet : prodBullets ){
			bullets.append(bullet);
			bullets.append("\n");
		}
		bulletsTV.setMinLines( prodBullets.size() );
		bulletsTV.setText( bullets.toString() );
		
		
	}
}