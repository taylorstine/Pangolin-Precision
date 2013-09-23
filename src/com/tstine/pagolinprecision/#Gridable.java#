package com.tstine.pangolinprecision;

import java.util.HashMap;

public class Gridable{
	Image mImg;
	HashMap<String, String> mInfo;
	String mLayout;
		
	public Gridable( Image img, HashMap<String,String> info, String layout ){
		mImg = img;
		mInfo = info;
		mLayout = layout;
	}

	public Image getImage(){return mImg;}
	public String getCaption(){
		if(mInfo == null )
			return "no caption";
		return mInfo.get("title");
	}
	public String getValue( String key ){
		if( !hasValue(key) )
			return "No item";
		return mInfo.get(key);
	}
	
	public boolean hasValue( String key){ return mInfo.containsKey(key);}
	public HashMap<String,String> getInfo() {return mInfo;}
	public boolean hasImage(){ return getImage() != null; }
	public String toString(){return getCaption();}
	public String getLayout(){return mLayout;}

}