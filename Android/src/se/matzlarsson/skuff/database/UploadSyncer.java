package se.matzlarsson.skuff.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import se.matzlarsson.skuff.ui.contest.ContestModel;
import android.os.AsyncTask;
import android.util.Log;

public class UploadSyncer extends AsyncTask<String, Void, Void>{

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
	
	@Override
	protected Void doInBackground(String... params) {
		if(!this.isWorking()){
			this.setWorking(true);
			String path = params[0];
			String filename = path+(path.endsWith("\\")||path.endsWith("/")?"":"/")+ContestModel.LOCAL_BACKUP_FILE;

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

				if(ContestModel.sendDataToServer(contents)){
					if(!file.delete()){
						Log.e("Error", "Could not remove synced contest log file");
					}
				}
			}
			
			this.setWorking(false);
		}

		return null;
	}
}
