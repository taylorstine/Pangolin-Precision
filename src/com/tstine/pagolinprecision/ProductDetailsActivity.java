package com.tstine.pangolinprecision;

import android.app.Activity;
import android.os.Bundle;

import android.content.Intent;

import android.util.Log;

import android.net.Uri;

public class ProductDetailsActivity extends Activity{
	@Override
	public void onCreate( Bundle ringtail ){
		super.onCreate( ringtail );
		setContentView( R.layout.product_details );
		Intent intent = getIntent();
		Uri uri = intent.getData();
		ProductDetail prodDetail = JSONParser.getProductDetail( uri );
		//Log.d( Const.APP_TAG, "Prod detail:\n" +prodDetail );
		TextView titleTV = (TextView) findViewById( R.id.product_title );
		titleTV.setText( prodDetail.getValue("title") );
		TextView leadingEquityTV = (TextView) findViewById( R.id.product_leading_equity );
		leadingEquityTV.setText( prodDetail.getValue("leadingEquity" ) );
		TextView bulletsTV = (TextView) findViewById( R.id.product_bullets );
		StringBuffer bullets = new StringBuffer();
		for( String bullet : prodDetail.getBullets() ){
			bullets.append(bullet);
			bullets.append("\n");
		}
		
	}
}