package se.matzlarsson.skuff.ui.contest;

public class OptionAnswer extends Answer{

	private int optionID;
	
	public OptionAnswer(int questionID, int time){
		this(questionID, -1, time);
	}
	
	public OptionAnswer(int questionID, int optionID, int time){
		super(questionID, time);
		this.optionID = optionID;
	}

	public int getOptionID() {
		return optionID;
	}

	public void setOptionID(int optionID) {
		this.optionID = optionID;
	}
	
	public String getAnswer(){
		return optionID+"";
	}
	
}
