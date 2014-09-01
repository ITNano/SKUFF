package se.matzlarsson.skuff.ui.contest;

import java.util.ArrayList;
import java.util.List;

public class FreeTextAnswer extends Answer{

	private List<String> answers;
	
	public FreeTextAnswer(int questionID, int time){
		super(questionID, time);
		answers = new ArrayList<String>();
	}
	
	public void addAnswer(String answer){
		if(answer != null){
			answers.add(answer);
		}
	}
	
	public void removeAnswer(String answer){
		if(answer != null){
			answers.remove(answer);
		}
	}
	
	public String getAnswer(){
		String s = "";
		for(int i = 0; i<answers.size(); i++){
			s += answers.get(i)+(i+1<answers.size()?"(split)":"");
		}
		
		return s;
	}
}
