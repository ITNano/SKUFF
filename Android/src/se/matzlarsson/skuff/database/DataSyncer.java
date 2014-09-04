package se.matzlarsson.skuff.database;

import java.io.Reader;
import java.util.Date;

import se.matzlarsson.skuff.database.data.Result;
import se.matzlarsson.skuff.database.data.ResultDeserializer;
import android.database.Cursor;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DataSyncer{

	private static DataSyncer instance = null;
	private static final String SERVER_URL = "http://skuff.host-ed.me/fetchdata.php";
	
	private boolean working = false;
	
	private DataSyncer(){
	}
	
	public static DataSyncer getInstance(){
		if(instance == null || !instance.isWorking()){
			instance = new DataSyncer();
		}
		
		return instance;
	}
	
	public void perform() {
		if(!this.isWorking()){
			setWorking(true);
			Result result = fetchData();
			saveToDb(result);
			loadedData(result);
			setWorking(false);
		}else{
			failedLoadingData();
		}
	}
	
	private Result fetchData(){
		try{
			Reader reader = IOUtil.getReaderFromHttp(SERVER_URL+getPreviousFetch());
			if(reader == null)Log.d("SKUFF", "Reader is null");
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat("yyyy-MM-dd hh:mm:ss");
			gsonBuilder.registerTypeAdapter(Result.class, new ResultDeserializer());
			Gson gson = gsonBuilder.create();
			Result result = gson.fromJson(reader, Result.class);
			reader.close();
			return result;
		} catch (Exception ex) {
			Log.e("SKUFF", "Failed to parse JSON due to: " + ex.getMessage());
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
		DatabaseHelper db = DatabaseHelper.getInstance();
		Cursor c = db.selectQuery("SELECT time FROM "+DatabaseFactory.TABLE_UPDATES+" WHERE _id=? LIMIT 1", new String[]{"1"});
		if(c.getCount()>0){
			c.moveToFirst();
			String time = c.getString(c.getColumnIndex("time"));
			long timestamp = DateUtil.stringToDate(time).getTime()/1000;
			return "?prevFetch="+timestamp;
		}else{
			return "";
		}
	}
	
	public void saveToDb(Result result){
		if(result != null){
			DatabaseHelper db = DatabaseHelper.getInstance();
			result.saveToDb(db);
			DBTable table = DatabaseFactory.getTable(DatabaseFactory.TABLE_UPDATES);
			db.insertOrUpdateQuery(table, new String[]{"1", DateUtil.dateToString(new Date())});
		}
	}
	
	public void loadedData(Result result){
		Log.d("SKUFF", "DataSyncer: Grabbed data ("+(result==null?"error":result.getUpdatesInfo())+")");
	}
	
	public void failedLoadingData(){
		Log.d("SKUFF", "DataSyncer: Failed to sync data");
	}

}
