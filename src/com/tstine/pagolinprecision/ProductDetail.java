package com.tstine.pangolinprecision;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import android.util.Log;

import java.lang.StringBuffer;

public class ProductDetail{
	private HashMap<String,String> mInfo;
	private ArrayList<String> mBullets;
	private ArrayList<String> mPromos;
	private ArrayList<Variation> mVariations;
	private ArrayList<Gridable> mUpsales;
	private ArrayList<Gridable> mCrossSales;
	private ArrayList<HashMap<String,String>> mImages;

	private Variation mActiveVariation;

	public ProductDetail( HashMap<String,String> info, ArrayList<String> bullets,
												ArrayList<String> promos,
												ArrayList<Variation> variations,
												ArrayList<Gridable> upsales,
												ArrayList<Gridable> crossSales,
												ArrayList<HashMap<String,String>> images){
		mInfo = info;
		mBullets = bullets;
		mPromos = promos;
		mVariations = variations;
		mUpsales = upsales;
		mCrossSales = crossSales;
		if( variations.size() > 0 )
			mActiveVariation = variations.get(0);
		else
			mActiveVariation = null;
		mImages = images;
	}

	public ArrayList<String> getPromos(){
		return mPromos;
	}

	public HashMap<String,String> getImage( int idx ){
		if( idx > mImages.size() )
			return Const.getDefaultImgMap();
		return mImages.get(idx);
	}
	public HashMap<String,String> getActiveVariationImageMap(int idx){
		if( mVariations.size() > 0 )
			return mActiveVariation.getImageMap( idx );
		else
			return mImages.get(0);
	}

	public String getActiveVariationValue( String str ){
		return mActiveVariation.getValue(str );
	}
	public boolean hasValue( String key ){ return mInfo.containsKey(key);}
	public String getValue( String key ){
		if( !hasValue(key ) )
			return "No item";
		return mInfo.get(key );
	}
	public ArrayList<String> getBullets(){return mBullets;}

	public String toString(){
		StringBuffer sb = new StringBuffer();
		Iterator it = mInfo.entrySet().iterator();
		while( it.hasNext() ){
			Map.Entry pair = (Map.Entry)it.next();
			sb.append(pair.getKey() + ": " + pair.getValue() );
			sb.append("\n");
			}
		for( String str : mBullets ){
			sb.append("bullet " + str);
			sb.append("\n");
		}
		for( String str : mPromos ){
			sb.append("promo: " + str );
			sb.append("\n");
		}
		
		sb.append("Images:\n");
		for( HashMap map : mImages ){
			it = map.entrySet().iterator();
			while( it.hasNext() ){
				Map.Entry pair = (Map.Entry) it.next();
				sb.append( pair.getKey() + ": " + pair.getValue() );
				sb.append("\n");
			}
		}

		sb.append("upsales:\n");
		for( Gridable grid : mUpsales ){
			sb.append( grid );
			sb.append("\n");
		}

		sb.append("cross sales:\n" );
		for( Gridable grid : mCrossSales ){
			sb.append( grid );
			sb.append("\n");
		}


		for(Variation vary : mVariations ){
			sb.append( vary.toString() );
			sb.append("\n");
		}

		return sb.toString();
	}

	public ArrayList<Variation> getVariations(){
		return mVariations;
	}

	public Variation getVariation(int idx){
		if( idx >= mVariations.size() )
			return null;
		return mVariations.get(idx );
	}

	public Variation setActiveVariation( int idx ){
		if( idx >= mVariations.size() )
			if( mVariations.size() > 0 )
				mActiveVariation = mVariations.get(0);
			else
				return null;
		mActiveVariation = mVariations.get(idx);
		return mActiveVariation;
	}

	public Variation setActiveVariation( Variation var ){
		if( var!= null )
			mActiveVariation = var;
		return mActiveVariation;
	}

	public Variation getActiveVariation(){
		return mActiveVariation;
	}

	public Variation getVariationBySwatch( String swatch ){
		for( Variation var : mVariations )
			if( var.getValue("swatch").equals( swatch ))
				return var;
		return null;
	}

	public HashMap<String,ArrayList<String>> getVariationOptions(){
		HashMap<String,ArrayList<String>> varOptions =
			new HashMap<String,ArrayList<String>>();
		for( Variation v : mVariations ){
			String swatch = v.getValue("swatch");
			String option = v.getCaption();
			if( varOptions.containsKey( swatch) )
				varOptions.get( swatch ).add( option );
			else{
				ArrayList<String> optArray = new ArrayList<String>();
				optArray.add( option );
				varOptions.put( swatch, optArray );
			}
		}
		return varOptions;
	}

	public ArrayList<Gridable> getCrossSales(){
		return mCrossSales;
	}
	public ArrayList<Gridable> getUpsales(){
		return mUpsales;
	}
}