package com.tstine.pangolinprecision;


public class ProductDetails{
	private String mTitle;
	private String mBrand;
	private String mId;
	private String mSku;
	private String mUpc;
	private String mLeadingEquity;
	private ArrayList<String> mBulltes;
	private ArrayList<ProductImage> mImages;
	private ArrayList<String> mPromos;
	
	private ArrayList<ProductDetails> mVariations;
	private String mSwatch;
	private HashMap<String,String> mOptions;
	private String mCondition;
	private HashMap<String,String> mPrice;
	private String mAvailability;
	private String mShippingDate;

	private ArrayList<Product> mUpSales;
	private ArrayList<Product> mCrossSales;
	
}