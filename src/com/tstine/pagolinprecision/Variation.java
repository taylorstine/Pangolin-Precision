package com.tstine.pangolinprecision;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import java.lang.StringBuffer;

public class Variation{
	private HashMap<String,String> mInfo;
	private ArrayList<HashMap<String,String>> mImages;
	private HashMap<String,String> mOptions;

	public Variation( HashMap<String, String> info, HashMap<String, String> options,
										ArrayList<HashMap<String,String>> images ){
		mInfo = info;
		mOptions = options;
		mImages = images;
	}

	public String toString(){
		StringBuffer sb = new StringBuffer();
		Iterator it = mInfo.entrySet().iterator();
		sb.append("Variation:\n");
		while( it.hasNext() ){
			Map.Entry pair = (Map.Entry) it.next();
			sb.append( pair.getKey() + ": " + pair.getValue() );
			sb.append("\n");
			it.remove();
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
		sb.append("Options:\n");
		it = mOptions.entrySet().iterator();
		while( it.hasNext() ){
			Map.Entry pair = (Map.Entry) it.next();
			sb.append( pair.getKey() + ": " + pair.getValue() );
			sb.append("\n");
			it.remove();
		}
		return sb.toString();
	}
}