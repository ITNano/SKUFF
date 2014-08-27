package se.matzlarsson.skuff.database;

import java.io.Reader;
import java.util.Date;

import se.matzlarsson.skuff.database.data.ResourceResult;
import se.matzlarsson.skuff.database.data.ResourceResultDeserializer;
import se.matzlarsson.skuff.ui.Refreshable;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ResourceSyncer extends AsyncTask<String, Void, ResourceResult>{

	private static ResourceSyncer instance = null;
	private static final String SERVER_RESOURCES_URL = "http://skuff.host-ed.me/fetchresources.php";
	
	private ActionBarActivity activity = null;
	private boolean working = false;
	
	private ResourceSyncer(ActionBarActivity activity){
		this.activity = activity;
	}
	
	public static ResourceSyncer getInstance(ActionBarActivity activity){
		if(instance == null || instance.isCancelled() || !instance.isWorking()){
			instance = new ResourceSyncer(activity);
		}
		
		return instance;
	}
	
	@Override
	protected ResourceResult doInBackground(String... params) {
		if(!this.isWorking()){
			setWorking(true);
			return fetchResources();
		}
		return null;
	}
	
	private ResourceResult fetchResources(){
		try{
			Reader reader = IOUtil.getReaderFromHttp(SERVER_RESOURCES_URL+getPreviousFetch());
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat("yyyy-MM-dd hh:mm:ss");
			gsonBuilder.registerTypeAdapter(ResourceResult.class, new ResourceResultDeserializer());
			Gson gson = gsonBuilder.create();
			ResourceResult res = gson.fromJson(reader, ResourceResult.class);
			if(Downloader.download(activity.getApplicationContext(), res.getPaths(), IOUtil.PATH_LOCAL_RESOURCES)){
				Log.d("SKUFF", "Download successful");
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
	
	@Override
	protected void onPostExecute(ResourceResult result){
		if(result != null){
			loadedData(result);
			if(activity != null){
				Fragment frag = activity.getSupportFragmentManager().findFragmentByTag("currentFragment");
				if(frag instanceof Refreshable){
					((Refreshable)frag).refresh();
				}
			}
		}else{
			failedLoadingData();
		}
		
		setWorking(false);
	}
	
	public boolean isWorking(){
		return this.working;
	}
	
	public void setWorking(boolean working){
		this.working = working;
	}
	
	
	public String getPreviousFetch(){
		DatabaseHelper db = DatabaseHelper.getInstance();
		Cursor c = db.selectQuery("SELECT time FROM "+DatabaseFactory.TABLE_UPDATES+" WHERE _id=? LIMIT 1", new String[]{"2"});
		if(c.getCount()>0){
			c.moveToFirst();
			String time = c.getString(c.getColumnIndex("time"));
			long timestamp = DateUtil.stringToDate(time).getTime()/1000;
			return "?prevFetch="+timestamp;
		}else{
			return "";
		}
	}
	
	public void saveUpdateTime(){
		DatabaseHelper db = DatabaseHelper.getInstance();
		DBTable table = DatabaseFactory.getTable(DatabaseFactory.TABLE_UPDATES);
		db.insertOrUpdateQuery(table, new String[]{"2", DateUtil.dateToString(new Date())});
	}
	
	public void loadedData(ResourceResult result){
		Toast.makeText(this.activity, "Grabbed resources ("+result.getCount()+" items)", Toast.LENGTH_SHORT).show();
	}
	
	public void failedLoadingData(){
		Toast.makeText(this.activity, "Failed to sync resources", Toast.LENGTH_SHORT).show();
	}

}
