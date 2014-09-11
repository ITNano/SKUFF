package se.matzlarsson.skuff.database;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class IOUtil {
	
	public static final String PATH_LOCAL_RESOURCES = "/resources/";

	public static Reader getReaderFromHttp(String webpage){
		try {
			//Create an HTTP client
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(webpage);
			
			//Perform the request and check the status code
			HttpResponse response = client.execute(post);
			StatusLine statusLine = response.getStatusLine();
			if(statusLine.getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				Reader reader = new InputStreamReader(content);
				return reader;
			}else {
				Log.e("SKUFF", "Server responded with status code: " + statusLine.getStatusCode());
			}
		} catch(Exception ex) {
			Log.e("SKUFF", "Failed to send HTTP POST request due to: " + ex);
		}
		return null;
	}
	
	public static Bitmap getLocalImage(Context context, String name){
		String fullPath = context.getFilesDir().getAbsolutePath()+PATH_LOCAL_RESOURCES+name+".png";
		Bitmap bmp = null;
		FileInputStream in = null;
		BufferedInputStream buf = null;
		try{
			in = new FileInputStream(fullPath);
			buf = new BufferedInputStream(in);
			bmp = BitmapFactory.decodeStream(buf);
			return bmp;
		}catch(FileNotFoundException fnfe){
			Log.e("Load Image", "FNF when loading image: "+name);
		}catch(Exception e){
			Log.e("Load Image", "Failure when loading image: "+name);
		}
		
		return bmp;
	}
	
	
	public static boolean sendDataToServer(String url, String json){
		try{
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			parameters.add(new BasicNameValuePair("device", "Android"));
			parameters.add(new BasicNameValuePair("json", json));
			post.setEntity(new UrlEncodedFormEntity(parameters, HTTP.UTF_8));
			HttpResponse response = client.execute(post);
			if(response.getStatusLine().getStatusCode()==200){
				String body = EntityUtils.toString(response.getEntity());
				String[] data = body.split("#");
				try{
					if(Integer.parseInt(data[0])==200){
						return true;
					}else{
						Log.e("Error", "Response:["+body+"]");
					}
				}catch(NumberFormatException nfe){
					Log.e("Error", "Invalid error code from response body");
				}
			}else{
				Log.e("Error", "Invalid response, received code "+response.getStatusLine().getStatusCode());
			}
		}catch(UnsupportedEncodingException uee){
			Log.e("Error", "The encoding of the contest data was corrupt");
		}catch(Exception e){
			Log.e("Error", "An error occured while uploading data.");
		}
		
		return false;
	}
}
