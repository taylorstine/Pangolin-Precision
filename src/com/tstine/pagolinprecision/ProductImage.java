package com.tstine.pangolinprecision;

public class ProductImage{
	private Image mThumb;
	private Image mX;
	private Image mXX;
	private String mAlt;

	public ProductImage( Image thumb, Image x, Image xx, String alt ){
		mThumb = thumb;
		mX = x;
		mXX = xx;
		mAlt = alt;
	}

	public Image getThumb(){return mThumb;}
	public Image getX(){return mX;}
	public Image getXX(){return mXX;}
	public String getAlt(){return mAlt;}
	
}