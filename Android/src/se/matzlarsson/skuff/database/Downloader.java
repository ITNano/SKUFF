package se.matzlarsson.skuff.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Downloader {

	
	public static boolean download(Context context, List<String> paths, String savePath){
		boolean status = true;
		boolean tmp = false;
		for(int i = 0; i<paths.size(); i++){
			tmp = download(context, paths.get(i), savePath);
			status = status && tmp;
		}
		
		return status;
	}
	
	public static boolean download(Context context, String path, String savePath){
		savePath = context.getFilesDir().getAbsolutePath()+savePath;
		Bitmap bmp = getBitmapFromHttp(path);
		return saveBitmap(bmp, savePath, getFilename(path));
	}
	
	private static Bitmap getBitmapFromHttp(String path){
		InputStream in = null;
		HttpURLConnection conn = null;
		try{
			conn = ((HttpURLConnection)(new URL(path).openConnection()));
			conn.connect();
			in = conn.getInputStream();
			Bitmap bitmap = BitmapFactory.decodeStream(in);
			return bitmap;
		}catch(MalformedURLException mue){
			Log.e("Conn ERROR", "Could not find URL");
		}catch(IOException ioe){
			Log.e("Conn ERROR", "IO Exception whilst download");
		}finally{
			if(in != null){
				try{
					in.close();
				}catch(IOException ioe){
					Log.e("Conn ERROR", "Could not close IN Connection");
				}
			}
			if(conn != null){
				conn.disconnect();
			}
		}
		
		return null;
	}
	
	private static String getFilename(String name){
		int index = Math.max(name.lastIndexOf("/"), name.lastIndexOf("\\"));
		if(index<0){
			return name;
		}else{
			return name.substring(index+1);
		}
	}
	
	private static boolean saveBitmap(Bitmap bmp, String path, String filename){
		File filepath = null;
		FileOutputStream out = null;
		try{
			filepath = new File(path);
			if(!filepath.exists()){
				filepath.mkdir();
			}
			out = new FileOutputStream(path+(path.endsWith("\\")||path.endsWith("/")?"":"/")+filename);
			return bmp.compress(CompressFormat.PNG, 90, out);
		}catch(FileNotFoundException fnfe){
			Log.e("Download ERROR", "File not found whilst saving...? \n\t["+fnfe.getMessage()+"]");
		}catch(Exception e){
			Log.e("Download ERROR", "Could not download");
		}finally{
			if(out != null){
				try{
					out.flush();
					out.close();
				}catch(Exception e){
					Log.e("Conn ERROR", "Could not close Out Connection");
				}
			}
		}
		
		return false;
	}
}
