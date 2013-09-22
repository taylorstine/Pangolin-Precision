package com.tstine.pangolinprecision;

import java.util.HashMap;
import java.util.ArrayList;
import java.lang.StringBuffer;

public class Product extends Gridable{
	private String mCondition;

	private HashMap<String,String> mBaseInfo;
	private ArrayList<ProductImage> mImages;
	private ArrayList<String> mPromos;
	private HashMap<String, String> mPrice;
	
	public Product( HashMap<String,String> baseInfo, ArrayList<ProductImage> images,
									ArrayList<String> promos, HashMap<String,String> price, String condition ){
		super( images.get(0).getThumb(), baseInfo.get("title") );
		mId = id;
		mPrice = price;
		mPromos = promos;
		mImages = images;
		mCondition = condition;
	}
	
	public String getHref(){ return mBaseInfo.get("href"); } 
	public String getTitle(){ return super.getCaption(); }

	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("title: " + getTitle() );
		/*sb.append("id: " + mId );
		sb.append("\t");
		sb.append("retail price: " + mPrice.get( Const.RETAIL_KEY ) );
		sb.append("\t");
		sb.append("sale price: " + mPrice.get( Const.SALE_KEY ) );
		sb.append("\t");
		sb.append( "value: " + mPrice.get( Const.VALUE_KEY ) );
		sb.append("\t");
		sb.append( "currency: " + mPrice.get( Const.CURRENCY_KEY ) );
		sb.append("\t");
		for( String promo : mPromos ){
			sb.append("Promo: " + promo );
			sb.append("\t");
		}
		sb.append( "condition: " + mCondition );
		sb.append("\t");
		sb.append("href: " + mHref );
		sb.append("\t");
		for( ProductImage img : mImages ){
			sb.append("thumb: " + img.getThumb().getSrc() );
			sb.append("\t");
			sb.append("1x: " + img.getX().getSrc() );
			sb.append("\t");
			sb.append("2d: " + img.getXX().getSrc() );
			sb.append("\t");
			sb.append("alt: " + img.getAlt() );
			sb.append("\t");
		}
		sb.append("\n\n");*/

		return sb.toString();
	}
}