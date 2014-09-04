package se.matzlarsson.skuff.ui.contest;

import java.util.ArrayList;
import java.util.List;

import se.matzlarsson.skuff.database.UploadSyncer;
import se.matzlarsson.skuff.database.data.contest.Contest;
import android.content.Context;

public class ContestModel {

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
		boolean synced = UploadSyncer.upload(context, getJSON());
		this.contest = null;
		this.currentQuestion = -1;
		this.started = false;
		
		return synced;
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
}
