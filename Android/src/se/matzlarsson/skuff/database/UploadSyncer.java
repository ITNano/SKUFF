package se.matzlarsson.skuff.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

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

				if(IOUtil.sendDataToServer(SERVER_RESPONSE_URL, contents)){
					if(!file.delete()){
						Log.e("Error", "Could not remove synced contest log file");
					}
				}
			}
			
			this.setWorking(false);
		}
	}
	

	
	public static boolean upload(Context context, String json){
		if(!Connectivity.getNetworkStatus(context) || !IOUtil.sendDataToServer(SERVER_RESPONSE_URL, json)){
			saveResultAsLocalFile(context, json);
			return false;
		}
		return true;
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
