package com.tstine.pangolinprecision;

import java.io.InputStream;
import java.io.IOException;

import org.apache.http.conn.BasicManagedEntity;
import org.apache.http.entity.BufferedHttpEntity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.net.URI;


public class ImageLoader{
	public static Bitmap loadImage(URI ... uris ){
		BasicManagedEntity ent = HttpGetter.get( uris[0] );
		BufferedHttpEntity bufferedEnt = null;
		try{
			bufferedEnt = new BufferedHttpEntity( ent );
		}catch( IOException e ){
			e.printStackTrace();
		}
		InputStream imgStream = null;
		try{
			imgStream = bufferedEnt.getContent();
		}catch( IOException e ){
			e.printStackTrace();
		}
		Bitmap bmp = BitmapFactory.decodeStream( imgStream );
		return bmp;
	}
}