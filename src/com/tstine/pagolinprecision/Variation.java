package com.tstine.pangolinprecision;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Map;

import android.util.Log;

import java.lang.StringBuffer;

public class Variation extends Gridable{
	private HashMap<String,String> mInfo;
	private ArrayList<HashMap<String,String>> mImages;
	private HashMap<String,String> mOptions;

	public Variation( HashMap<String, String> info,
										HashMap<String, String> options,
										ArrayList<HashMap<String,String>> images ){
		super( new Image( info.get("swatch") ), info, Const.SWATCH_GRID_LAYOUT );
		mInfo = info;
		mOptions = options;
		mImages = images;
	}

	@Override
	public String getCaption(){
		StringBuilder sb = new StringBuilder();
		Iterator it = mOptions.entrySet().iterator();
		String prefix="";
		while( it.hasNext() ){
			Map.Entry pair = (Map.Entry)it.next();
			sb.append(prefix);
			prefix="\n";
			sb.append(pair.getKey()+ ": " + pair.getValue() +" ");
		}
		return sb.toString();
	}

	public HashMap<String,String> getImageMap( int idx ){
		if( idx >= mImages.size() )
			return Const.getDefaultImgMap();
		return mImages.get(idx);
	}
	public String toString(){
		StringBuffer sb = new StringBuffer();
		Iterator it = mInfo.entrySet().iterator();
		sb.append("Variation:\n");
		while( it.hasNext() ){
			Map.Entry pair = (Map.Entry) it.next();
			sb.append( pair.getKey() + ": " + pair.getValue() );
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
		sb.append("Options:\n");
		it = mOptions.entrySet().iterator();
		while( it.hasNext() ){
			Map.Entry pair = (Map.Entry) it.next();
			sb.append( pair.getKey() + ": " + pair.getValue() );
			sb.append("\n");
		}
		return sb.toString();
	}
}