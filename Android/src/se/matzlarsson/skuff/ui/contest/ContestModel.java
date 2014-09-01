package se.matzlarsson.skuff.ui.contest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import se.matzlarsson.skuff.database.Connectivity;
import se.matzlarsson.skuff.database.data.contest.Contest;
import android.content.Context;
import android.util.Log;

public class ContestModel {

	private final static String SERVER_RESPONSE_URL = "http://skuff.host-ed.me/joinContest.php";
	public final static String LOCAL_BACKUP_FILE = "contestdata.txt";

	private Contest contest;
	private int currentQuestion = -1;
	private List<Answer> answers;
	private UserData userdata;
	private boolean started = false;
	
	public ContestModel(Contest contest){
		this.contest = contest;
	}

	public Contest getContest() {
		return contest;
	}

	public void setContest(Contest contest) {
		this.contest = contest;
	}
	
	public boolean hasContest(){
		return contest != null;
	}

	public UserData getUserdata() {
		return userdata;
	}

	public void setUserdata(UserData userdata) {
		this.userdata = userdata;
	}
	
	public void addAnswer(Answer answer){
		if(answer != null){
			answers.add(answer);
		}
	}
	
	public void removeAnswer(Answer answer){
		if(answer != null){
			answers.remove(answer);
		}
	}
	
	public void startContest(){
		this.currentQuestion = 0;
		answers = new ArrayList<Answer>();
		userdata = new UserData();
		started = true;
	}
	
	public boolean endContest(Context context){
		Log.d("SKUFF", "HUm"+Connectivity.getNetworkStatus(context));
		if(!Connectivity.getNetworkStatus(context) || !sendDataToServer()){
			saveResultAsLocalFile(context, getJSON());
			return false;
		}
		this.contest = null;
		this.currentQuestion = -1;
		this.started = false;
		
		return true;
	}
	
	public QuestionFragment next(){
		if(started){
			if(currentQuestion>=contest.getQuestionCount()){
				return null;
			}else{
				QuestionFragment fragment = new QuestionFragment(contest.getQuestion(currentQuestion), currentQuestion+1);
				currentQuestion++;
				return fragment;
			}
		}else{
			throw new IllegalArgumentException("The contest has not yet been started!");
		}
	}
	
	private String getJSON(){
		StringBuilder json = new StringBuilder("{");
		json.append("\"contestID\":\""+contest.getId()+"\"");
		json.append(",\"name\":\""+userdata.getName()+"\"");
		json.append(",\"phone\":\""+userdata.getPhone()+"\"");
		json.append(",\"answers\":[");
		for(int i = 0; i<answers.size(); i++){
			json.append((i>0?",":"")+"{");
			json.append("\"questionID\":\""+answers.get(i).getQuestionID()+"\"");
			json.append(",\"answer\":\""+answers.get(i).getAnswer()+"\"");
			json.append(",\"time\":\""+answers.get(i).getTime()+"\"");
			json.append("}");
		}
		json.append("]");
		json.append("}");
		
		return json.toString();
	}
	
	private boolean sendDataToServer(){
		return sendDataToServer(getJSON());
	}
	
	public static boolean sendDataToServer(String json){
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
