package com.tstine.pangolinprecision;

import java.util.HashMap;

public class Const{
	public static final String APP_TAG = "PangolinPrecision";
	public static final String HOST = "https://emsapi.bbhosted.com";

	public static final String RETAIL_KEY = "retail";
	public static final String SALE_KEY = "sale";
	public static final String VALUE_KEY = "value";
	public static final String CURRENCY_KEY = "currency";

	public static final String X_PLACE_HOLDER = "http://placehold.it/300/c11b17/ffffff&text=1x";
	public static final String XX_PLACE_HOLDER = "http://placehold.it/600/c11b17/ffffff&text=2x";
	public static final String THUMB_HOLDER = "http://placehold.it/100/c11b17/ffffff&text=thumb";
	public static final String SWATCH_HOLDER = "http://placehold.it/50/c11b17/ffffff&text=swatch";

	public static final String CATEGORY_GRID_LAYOUT = "category_grid_item";
	public static final String PRODUCT_GRID_LAYOUT = "product_grid_item";
	public static final String SWATCH_GRID_LAYOUT = "swatch_grid_item";

	public static HashMap<String,String> getDefaultImgMap(){
		HashMap<String, String> imgMap = new HashMap<String,String>();
		imgMap.put("1x", X_PLACE_HOLDER );
		imgMap.put("2x", XX_PLACE_HOLDER );
		imgMap.put("thumb", THUMB_HOLDER );
		return imgMap;
	}
}