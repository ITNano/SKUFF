package se.matzlarsson.skuff.ui.contest;

public abstract class Answer {

	private int questionID;
	private int time;

	public Answer(int questionID, int time){
		setQuestionID(questionID);
		setTime(time);
	}

	public int getQuestionID() {
		return questionID;
	}

	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}
	
	public int getTime(){
		return time;
	}
	
	public void setTime(int time){
		this.time = time;
	}
	
	public abstract String getAnswer();
	
}
