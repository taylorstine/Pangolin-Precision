package com.tstine.pangolinprecision;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;

import java.net.URISyntaxException;
import java.net.URI;

import android.app.Activity;
import android.os.Bundle;

import android.view.MotionEvent;
import android.view.ViewManager;

import android.content.Intent;

import android.util.Log;

import android.net.Uri;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.ImageView;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.AdapterView;

import java.lang.InterruptedException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.CancellationException;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

import org.json.JSONException;
import java.lang.Exception;

import android.view.View;

public class ProductDetailsActivity extends Activity{
	@Override
	public void onCreate( Bundle ringtail ){
		super.onCreate( ringtail );
		setContentView( R.layout.product_details );
		if(		ShowcaseActivity.mProgDialog.isShowing() )
			ShowcaseActivity.mProgDialog.dismiss();
		Intent intent = getIntent();
		Uri uri = intent.getData();
		ProductDetail prodDetail = null;
		try{
			prodDetail = JSONParser.getProductDetail( uri );
		}catch( Exception e ){
			Intent intentSorry = new Intent( this, SorryActivity.class );
			startActivity(intentSorry);
			finish();
		}
		setupUI( prodDetail );
	}
	private void setupUI( ProductDetail prodDetail ){
		GridView grid = (GridView) findViewById( R.id.swatch_grid );
		ImageView mainImg = (ImageView) findViewById( R.id.main_image );
				 
		final HashMap<String,ArrayList<String>> varOptions = prodDetail.getVariationOptions();
		ArrayList<String>options = varOptions.get( prodDetail.getActiveVariation().getImage().getSrc() );

		if( options == null || options.size() == 0 || options.get(0).equals("") ){
			Spinner spinner = (Spinner) findViewById( R.id.options_spinner );
			((ViewManager)spinner.getParent()).removeView(spinner);
			((ViewManager)grid.getParent()).removeView(grid);
			RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams( 180, 180 );
			relativeParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
			relativeParams.addRule( RelativeLayout.BELOW, R.id.product_title );
			mainImg.setLayoutParams( relativeParams );
			
		}else{
			Spinner spinner = (Spinner) findViewById( R.id.options_spinner );
		 List<String> list = options;
		 ArrayAdapter<String> adapter =
			 new ArrayAdapter<String>( this,
																 R.layout.spinner_item,
																 list );
		 adapter.setDropDownViewResource(
			 R.layout.spinner_item );
		 spinner.setAdapter( adapter );
		 spinner.setOnItemSelectedListener( new SpinnerOnItemSelectedListener() );

		 grid.setAdapter( new ImageUrlAdapter( this, varOptions.keySet().toArray(new String[varOptions.size() ]) ));
		 grid.setOnItemClickListener( new SwatchClicker( this, varOptions,
																										 prodDetail) );
		}

		HashMap<String,String> img = prodDetail.getActiveVariationImageMap(0);
		 try{
			 mainImg.setImageBitmap( new ImageLoader(this ).execute(
																 new URI( (String) img.get("1x") ) )
															 .get()
															 .get( img.get("1x") ));
		 }catch( URISyntaxException e ){
			 e.printStackTrace();
		 }catch( InterruptedException e ){
			 e.printStackTrace();
		 }catch( ExecutionException e ){
			 e.printStackTrace();
		 }catch( CancellationException e ){
			 e.printStackTrace();
		 }

		 TextView titleTV = (TextView) findViewById( R.id.product_title );
		 titleTV.setText( "\n\t"+prodDetail.getValue("title") +"\n");
		 TextView leadingEquityTV = (TextView) findViewById( R.id.product_leading_equity );
		 leadingEquityTV.setText( prodDetail.getValue("leadingEquity" ) );
		 TextView bulletsTV = (TextView) findViewById( R.id.product_bullets );
		 StringBuffer bullets = new StringBuffer();
		 ArrayList<String> prodBullets = prodDetail.getBullets();
		 for( String bullet : prodBullets ){
			 bullets.append("\t");
			 bullets.append(bullet);
			 bullets.append("\n\n");
		 }
		 bulletsTV.setMinLines( prodBullets.size() );
		 bulletsTV.setText( bullets.toString() );

		 TextView promosTV = (TextView) findViewById( R.id.promos );
		 StringBuffer promos = new StringBuffer();
		 ArrayList<String> prodPromos = prodDetail.getPromos();
		 for( String bullet : prodPromos ){
			 promos.append("\t");
			 promos.append(bullet);
			 promos.append("\n\n");
		 }
		 promosTV.setMinLines( prodPromos.size() );
		 promosTV.setText( promos.toString() );

		 
		 GridView crossSaleGrid = (GridView) findViewById( R.id.cross_sales );
		 GridView upSalesGrid = (GridView) findViewById( R.id.up_sales );

		 ArrayList<Gridable> crossSales = prodDetail.getCrossSales();
		 ArrayList<Gridable> upSales = prodDetail.getUpsales();
		 if( crossSales.size() == 0 ){
			 TextView TV = (TextView) findViewById( R.id.cross_sale_label );
			 TV.setVisibility(View.GONE );
			 crossSaleGrid.setVisibility(View.GONE);
		 }
		 if( upSales.size() == 0 ){
			 TextView TV = (TextView) findViewById( R.id.up_sale_label );
			 TV.setVisibility(View.GONE );
			 upSalesGrid.setVisibility(View.GONE);
		 }

		 TextView retailTV = (TextView) findViewById( R.id.item_retail_price );
		retailTV.setText( prodDetail.getActiveVariationValue("retail") );
		if( prodDetail.getActiveVariationValue("retail") != null )
			retailTV.setText( prodDetail.getActiveVariationValue("retail") );
		else if ( prodDetail.getActiveVariationValue("value") != null  )
			retailTV.setText( prodDetail.getActiveVariationValue("value") );
		else
			retailTV.setText("0.00");
		TextView currencyTV = (TextView) findViewById( R.id.item_currency );
		currencyTV.setText( prodDetail.getActiveVariationValue("currency") );

		if( prodDetail.getActiveVariationValue("sale") != null ){
			retailTV.setTextColor(Color.rgb(255, 20, 20 ) );
			retailTV.setPaintFlags( retailTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG );
			TextView saleTV = (TextView) findViewById( R.id.item_sale_price );
			saleTV.setText( prodDetail.getActiveVariationValue("sale") );
		}

		 
		 setupGridView( crossSaleGrid, crossSales, false );
		 setupGridView( upSalesGrid, upSales, false );
	}

	public void setupGridView( GridView gridView, ArrayList<Gridable>  gridItems, final boolean scrollable){
		 ArrayList<URI> uriArr = new ArrayList<URI>();
		 for( Gridable gridItem : gridItems){
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

		 gridView.setAdapter( new GridableImageAdapter( this, gridItems, imgMap ) );
		 gridView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
				 @Override
				 public void onItemClick( AdapterView<?> parent, final View view,
																	int position, long id ){
					 Gridable selected = (Gridable) parent.getItemAtPosition( position ) ;
					 Intent intent = getIntent();
					 intent.setData( Uri.parse( Const.HOST + selected.getValue("href") ) );
					 startActivity( intent );
				 }
			 });
		 gridView.setOnTouchListener( new View.OnTouchListener() {
				 @Override
				 public boolean onTouch(View v, MotionEvent event) {
					 if(event.getAction() == MotionEvent.ACTION_MOVE){
						 return scrollable;
					 }
					 return false;
				 }					
			 });
	}

	 public void addToCartClick( View view ){
		 //String form = "id=12441070%11899507&qty=1";
		 //String form = "123456%7C7892012&qty=1";
		 //String form = "id=12441070&qty=1";
		 String form = "id=12441070%7C7892012\u0026qty=1";
		 String content_type ="application/x-www-form-urlencoded";
		 HttpGetter.post( form, content_type );
		 Toast.makeText( this, "Added to cart!", Toast.LENGTH_SHORT ).show();
	 }

	 private class SwatchClicker implements AdapterView.OnItemClickListener{
		 private Context mCtx;
		 private HashMap<String,ArrayList<String>> mVarOptions;
		 private ProductDetail mProdDetail;
		 public SwatchClicker( Context ctx,
													 HashMap<String,ArrayList<String>> varOptions,
													 ProductDetail productDetail){
			 mCtx = ctx;
			 mVarOptions = varOptions;
			 mProdDetail = productDetail;
		 }
		 

		 @Override
		 public void onItemClick( AdapterView<?> parent, final View view,
															int position, long id ){
			 String selected = (String) parent.getItemAtPosition( position ) ;
			 ArrayList<String> options = mVarOptions.get(selected);
			 Spinner spinner = (Spinner) findViewById( R.id.options_spinner );
			 ArrayAdapter<String> adapter =
				 new ArrayAdapter<String>( view.getContext(),
																	 R.layout.spinner_item, options );
			 adapter.setDropDownViewResource(
				 R.layout.spinner_item );
			 spinner.setAdapter( adapter );

			 mProdDetail.setActiveVariation(
				 mProdDetail.getVariationBySwatch( selected ) );

			 
			 TextView retailTV = (TextView) findViewById( R.id.item_retail_price );
			 retailTV.setText( mProdDetail.getActiveVariationValue("retail") );
			 if( mProdDetail.getActiveVariationValue("retail") != null )
				 retailTV.setText( mProdDetail.getActiveVariationValue("retail") );
			 else if ( mProdDetail.getActiveVariationValue("value") != null  )
				 retailTV.setText( mProdDetail.getActiveVariationValue("value") );
			 else
				 retailTV.setText("0.00");
			 TextView currencyTV = (TextView) findViewById( R.id.item_currency );
			 currencyTV.setText( mProdDetail.getActiveVariationValue("currency") );

			 if( mProdDetail.getActiveVariationValue("sale") != null ){
				 retailTV.setTextColor(Color.rgb(255, 20, 20 ) );
				 retailTV.setPaintFlags( retailTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG );
				 TextView saleTV = (TextView) findViewById( R.id.item_sale_price );
				 saleTV.setText( mProdDetail.getActiveVariationValue("sale") );
			 }
			 ImageView mainImg = (ImageView) findViewById( R.id.main_image );

			 HashMap<String,String> img = mProdDetail.getActiveVariationImageMap(0);
			 try{
				 mainImg.setImageBitmap( new ImageLoader((Activity)mCtx, false ).execute(
																	 new URI( (String) img.get("1x") ) )
																 .get()
																 .get( img.get("1x") ));
			 }catch( URISyntaxException e ){
				 e.printStackTrace();
			 }catch( InterruptedException e ){
				 e.printStackTrace();
			 }catch( ExecutionException e ){
				 e.printStackTrace();
			 }catch( CancellationException e ){
				 e.printStackTrace();
			 }

		 }
	}

	private class SpinnerOnItemSelectedListener implements AdapterView.OnItemSelectedListener{
		@Override
		public void onItemSelected( AdapterView<?> parent, View view,
																int pos, long id ){
		}
		@Override
		public void onNothingSelected( AdapterView<?> av ){
		}
	}
}