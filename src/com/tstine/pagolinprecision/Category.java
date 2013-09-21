package com.tstine.pangolinprecision;

import java.util.ArrayList;

public class Category{
	private String mTitle;
	private String mId;
	private String mHref;

	public Category(){
		mId = null;
		mTitle = null;
	}
	public Category( String title, String id, String href ){
		mId = id;
		mTitle = title;
		mHref = href;
	}

	public String getTitle(){return mTitle;}
	public String getId(){return mId;}
	public String toString(){return mTitle;}
}