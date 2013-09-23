package com.tstine.pangolinprecision;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

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
		mActiveVariation = variations.get(0);
		mImages = images;
	}

	public String hasValue( String key ){ return mInfo.containsKey(key);}
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
			it.remove();
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
				it.remove();
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

}