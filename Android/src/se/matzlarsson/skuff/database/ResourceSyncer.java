package se.matzlarsson.skuff.database;

import java.io.Reader;
import java.util.Date;

import se.matzlarsson.skuff.database.data.ResourceResult;
import se.matzlarsson.skuff.database.data.ResourceResultDeserializer;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ResourceSyncer{

	private static ResourceSyncer instance = null;
	private static final String SERVER_RESOURCES_URL = "http://skuff.host-ed.me/fetchresources.php";
	
	private String internalMemoryPath = "";
	private boolean working = false;
	
	private ResourceSyncer(String internalMemoryPath){
		this.internalMemoryPath = internalMemoryPath;
	}
	
	public static ResourceSyncer getInstance(String internalMemoryPath){
		if(instance == null || !instance.isWorking()){
			instance = new ResourceSyncer(internalMemoryPath);
		}
		
		return instance;
	}
	
	public void perform() {
		if(!this.isWorking()){
			setWorking(true);
			ResourceResult res = fetchResources();
			loadedData(res);
			setWorking(false);
		}else{
			failedLoadingData();
		}
	}
	
	private ResourceResult fetchResources(){
		try{
			Reader reader = IOUtil.getReaderFromHttp(SERVER_RESOURCES_URL+getPreviousFetch());
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat("yyyy-MM-dd hh:mm:ss");
			gsonBuilder.registerTypeAdapter(ResourceResult.class, new ResourceResultDeserializer());
			Gson gson = gsonBuilder.create();
			ResourceResult res = gson.fromJson(reader, ResourceResult.class);
			if(Downloader.download(internalMemoryPath, res.getPaths(), IOUtil.PATH_LOCAL_RESOURCES)){
				Log.d("SKUFF", "ResourceSyncer: Download successful (saved "+res.getCount()+" resources)");
				saveUpdateTime();
			}else{
				Log.d("SKUFF", "Download failed");
			}
			return res;
		}catch(Exception e){
			Log.e("Download", "Could not fetch resources");
		}
		return null;
	}
	
	public boolean isWorking(){
		return this.working;
	}
	
	public void setWorking(boolean working){
		this.working = working;
	}
	
	public String getPreviousFetch(){
		long timestamp = DatabaseFetcher.getPreviousFetch(DatabaseFetcher.PREVIOUS_FETCH_RESOURCE);
		if(timestamp>0){
			return "?prevFetch="+timestamp;
		}else{
			return "";
		}
	}
	
	public void saveUpdateTime(){
		DatabaseHelper db = DatabaseHelper.getInstance();
		DBTable table = DatabaseFactory.getTable(DatabaseFactory.TABLE_UPDATES);
		db.insertOrUpdateQuery(table, new String[]{DatabaseFetcher.PREVIOUS_FETCH_RESOURCE+"", DateUtil.dateToString(new Date())});
	}
	
	public void loadedData(ResourceResult result){
		Log.d("SKUFF", "ResourceSyncer: Grabbed resources ("+(result==null?"error":result.getCount()+" items)"));
	}
	
	public void failedLoadingData(){
		Log.d("SKUFF", "ResourceSyncer: Failed to sync resources");
	}

}
