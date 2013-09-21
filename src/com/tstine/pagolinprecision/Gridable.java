package com.tstine.pangolinprecision;

public class Gridable{
	Image mImg;
	String mCaption;
	public Gridable( Image img, String caption ){
		mImg = img;
		mCaption = caption;
	}

	public Image getImage(){return mImg;}
	public String getCaption(){return mCaption;}
}