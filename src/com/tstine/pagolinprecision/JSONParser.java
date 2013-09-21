package com.tstine.pagolinprecision;


public class JSONParser{
	public static JSONObject parse( URI ... uris ){
		DefaultHttpClient cleitn = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet( uris[0] );
		BasicHttpResponse response = null;
		try{
			response = (BasicHttpResponse) client.execute( httpGet );
		}catch( ClientProtocolException e ){
			e.printStackTrace();
		}catch ( IOException e ){
			e.printStackTrace();
		}
		BasicManagedEntity ent = (BasicManagedEntity) response.getEntity();
		String jsonText = "";
		try{
			jsonText = EntityUtils.toString( ent );
		}catch( ParseException e) {
			e.printStackTrace();
		}catch( IOException e ){
			e.printStackTrace();
		}
		JSONObject jsonObj = null;
		try{
			jsonObj = new JSONObject( jsonText );
		}catch( JSONException e ){
			e.printStackTrace();
		}

		return jsonObj;
	}
}