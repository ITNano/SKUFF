package se.matzlarsson.skuff.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.util.Log;

public class UploadSyncer{

	private final static String SERVER_RESPONSE_URL = "http://skuff.host-ed.me/joinContest.php";
	public final static String LOCAL_BACKUP_FILE = "contestdata.txt";

	private static UploadSyncer instance;
	private boolean working;
	
	private UploadSyncer(){
	}
	
	public static UploadSyncer getInstance(){
		if(instance == null){
			instance = new UploadSyncer();
		}
		
		return instance;
	}
	
	private boolean isWorking(){
		return working;
	}
	
	private void setWorking(boolean working){
		this.working = working;
	}
	
	public void perform(String path){
		if(!this.isWorking()){
			this.setWorking(true);
			String filename = path+(path.endsWith("\\")||path.endsWith("/")?"":"/")+LOCAL_BACKUP_FILE;

			File file = new File(filename);
			if(file.exists()){
				String contents = "";
				Scanner sc = null;
				try{
					sc = new Scanner(new FileInputStream(filename));
					while(sc.hasNextLine()){
						contents += sc.nextLine();
					}
					sc.close();
				}catch(IOException ioe){
					Log.e("Error", "IO Error while writing local save file");
				}catch(Exception e){
					Log.e("Error", "Error while writing local save file ["+e.getClass().getCanonicalName()+"]");
				}finally{
					if(sc != null){
						sc.close();
					}
				}

				if(sendDataToServer(contents)){
					if(!file.delete()){
						Log.e("Error", "Could not remove synced contest log file");
					}
				}
			}
			
			this.setWorking(false);
		}
	}
	

	
	public static boolean upload(Context context, String json){
		if(!Connectivity.getNetworkStatus(context) || !sendDataToServer(json)){
			saveResultAsLocalFile(context, json);
			return false;
		}
		return true;
	}
	
	private static boolean sendDataToServer(String json){
		try{
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(SERVER_RESPONSE_URL);
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
						Log.e("Error", "Response:["+body+"]");//+(data.length>1?data[1]:"-- No reponse --")+"]");
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
	
	private static void saveResultAsLocalFile(Context context, String json){
		if(context != null){
			String path = context.getFilesDir().getAbsolutePath();
			File filepath = null;
			FileOutputStream out;
			try{
				filepath = new File(path);
				if(!filepath.exists()){
					filepath.mkdir();
				}
				out = new FileOutputStream(path+(path.endsWith("\\")||path.endsWith("/")?"":"/")+LOCAL_BACKUP_FILE);
				out.write(json.getBytes());
				out.flush();
				out.close();
			}catch(IOException ioe){
				Log.e("Error", "IO Error while writing local save file");
			}catch(Exception e){
				Log.e("Error", "Error while writing local save file ["+e.getClass().getCanonicalName()+"]");
			}
		}
	}
}
