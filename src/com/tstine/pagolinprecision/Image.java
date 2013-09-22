package com.tstine.pangolinprecision;

import java.lang.StringBuffer;

public class Image{
	private String mSrc;
	private String mDescription;
	public Image(){
		mSrc = null;
		mDescription = null;
	}
	public Image( String src, String description ){
		mSrc = src;
		mDescription = description;
	}

	public Image( String src ){
		mSrc = src;
		mDescription = null;
	}

	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("src: ");
		sb.append( mSrc );
		sb.append(" description: ");
		sb.append( mDescription );
		return sb.toString();
	}

	public String getSrc(){ return mSrc; }
	public String getDescription(){ return mDescription; }
}