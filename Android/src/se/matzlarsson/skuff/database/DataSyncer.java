package se.matzlarsson.skuff.database;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import se.matzlarsson.skuff.database.data.Result;
import se.matzlarsson.skuff.database.data.ResultDeserializer;
import se.matzlarsson.skuff.ui.Refreshable;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DataSyncer extends AsyncTask<String, Void, Result>{

	private static DataSyncer instance = null;
	private static final String SERVER_URL = "http://skuff.host-ed.me/fetchdata.php";
	
	private ActionBarActivity activity = null;
	private boolean working = false;
	
	private DataSyncer(ActionBarActivity activity){
		this.activity = activity;
	}
	
	public static DataSyncer getInstance(ActionBarActivity activity){
		if(instance == null || instance.isCancelled() || !instance.isWorking()){
			instance = new DataSyncer(activity);
		}
		
		return instance;
	}
	
	@Override
	protected Result doInBackground(String... params) {
		if(!this.isWorking()){
			setWorking(true);
			try {
				//Create an HTTP client
				HttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost(SERVER_URL+getPreviousFetch());
				
				//Perform the request and check the status code
				HttpResponse response = client.execute(post);
				StatusLine statusLine = response.getStatusLine();
				if(statusLine.getStatusCode() == 200) {
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					try {
						//Read the server response and attempt to parse it as JSON
						Reader reader = new InputStreamReader(content);

						GsonBuilder gsonBuilder = new GsonBuilder();
						gsonBuilder.setDateFormat("yyyy-MM-dd hh:mm:ss");
						gsonBuilder.registerTypeAdapter(Result.class, new ResultDeserializer());
						Gson gson = gsonBuilder.create();
						Result result = gson.fromJson(reader, Result.class);
						saveToDb(result);
						content.close();
						return result;
					} catch (Exception ex) {
						Log.e("SKUFF", "Failed to parse JSON due to: " + ex);
					}
				} else {
					Log.e("SKUFF", "Server responded with status code: " + statusLine.getStatusCode());
				}
			} catch(Exception ex) {
				Log.e("SKUFF", "Failed to send HTTP POST request due to: " + ex + "\n" + java.util.Arrays.toString(ex.getStackTrace()));
			}
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Result result){
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
		Cursor c = db.selectQuery("SELECT time FROM "+DatabaseFactory.TABLE_UPDATES+" LIMIT 1", null);
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
		DatabaseHelper db = DatabaseHelper.getInstance();
		result.saveToDb(db);
		DBTable table = DatabaseFactory.getTable(DatabaseFactory.TABLE_UPDATES);
		db.truncateTable(table);
		db.insertQuery(table, new String[]{"1", DateUtil.dateToString(new Date())});
	}
	
	public void loadedData(Result result){
		Toast.makeText(this.activity, "Grabbed data ("+result.getUpdatesInfo()+")", Toast.LENGTH_SHORT).show();
	}
	
	public void failedLoadingData(){
		Toast.makeText(this.activity, "Failed to sync data", Toast.LENGTH_SHORT).show();
	}

}
