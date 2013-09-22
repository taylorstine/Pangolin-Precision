package com.tstine.pangolinprecision;

import java.util.ArrayList;
import java.lang.StringBuffer;

public class Subcategory extends Gridable{
	private String mId;
	private String mHref;

	public Subcategory( String title, String id, String href, Image img ){
		super( img, title );
		mId = id;
		mHref = href;
	}

	public String toString(){return getTitle();}
	public String getTitle(){return super.getCaption();}
	public String getId(){return mId;}
	public String getHref(){return mHref;}
}