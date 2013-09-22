package com.tstine.pangolinprecision;

import android.os.Bundle;
import android.app.Activity;

import android.util.Log;

import android.content.Context;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import android.view.View;
import android.view.Gravity;

import android.widget.Toast;

public class MainActivity extends Activity
{
	@Override
	public void onCreate(Bundle ringtail )
	{
		super.onCreate( ringtail );
		if(testConnection( this )){
			Intent intent = new Intent( this, ShowcaseActivity.class );
			intent.setData( Uri.parse( Const.HOST ) );
			startActivity( intent );
		}
		else{
			Toast toast = Toast.makeText( this, "Sorry, no internet connection", Toast.LENGTH_SHORT );
			toast.setGravity(Gravity.CENTER, 0, 0 );
			toast.show();
			finish();
		}
	}

	public boolean testConnection( Context ctx ){
		ConnectivityManager cm = (ConnectivityManager)
			ctx.getSystemService( Context.CONNECTIVITY_SERVICE );
		NetworkInfo net = cm.getActiveNetworkInfo();
		if( net == null )
			return false;
		String networkInfo = "Type: " + net.getTypeName() +
			"\nSubtype: " + net.getSubtypeName() +
			"\nExtra: " + net.getExtraInfo ();
		Log.d( Const.APP_TAG, networkInfo );
		boolean isConnected = net.isConnectedOrConnecting();
		return isConnected;
	}
}
