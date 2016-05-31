package mno_ruili_app.net;


import java.io.IOException;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.toolbox.ImageLoader.ImageCache;

public class BitmapCache implements ImageCache {

	private LruCache<String, Bitmap> mCache;

	public BitmapCache() {
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int cacheSize = maxMemory / 6;//最大内存1/6

		mCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getRowBytes() * bitmap.getHeight();
			}
		};
	}

	@Override
	public Bitmap getBitmap(String url) 
	{
		
		/*
		String path = Tool.getFileName(url);
		
		
		path = Tool.getPath(path);
		
		Bitmap  bit = mCache.get(url);
				
		
		if(bit == null)
		{
			return  Tool.LoadBitmap(path);
		}
		else
		{
		  return bit;	
		}
		*/
		 return mCache.get(url);  
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) 
	{
		
		/*String path = Tool.getFileName(url);
		
		path = Tool.getPath(path);
		try {
						
			Tool.saveBitmap(bitmap, path,false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		mCache.put(url, bitmap);
	}

}
