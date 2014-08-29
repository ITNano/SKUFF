package se.matzlarsson.skuff.database.data.contest;

import se.matzlarsson.skuff.database.DatabaseFactory;
import se.matzlarsson.skuff.database.DatabaseHelper;
import se.matzlarsson.skuff.database.data.Saveable;
import android.database.Cursor;

public class QuestionOption implements Saveable{

	private int id;
	private int questionID;
	private String option;
	
	public QuestionOption(){
		setId(-1);
		setQuestionID(-1);
		setOption("");
	}
	
	public QuestionOption(Cursor c){
		setId(c.getInt(c.getColumnIndex("_id")));
		setQuestionID(c.getInt(c.getColumnIndex("questionID")));
		setOption(c.getString(c.getColumnIndex("option")));
	}
	
	public QuestionOption(int id, int questionID, String option){
		setId(id);
		setQuestionID(questionID);
		setOption(option);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuestionID() {
		return questionID;
	}

	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	@Override
	public void saveToDb(DatabaseHelper db) {
		db.insertOrUpdateQuery(DatabaseFactory.getTable(DatabaseFactory.TABLE_CONTEST_OPTION), new String[]{getId()+"", getQuestionID()+"", getOption()});
	}
	
	@Override
	public String toString(){
		String s = "OPTION: [\n";
		s += "\tID:"+getId()+"\n";
		s += "\tQuestionID:"+getQuestionID()+"\n";
		s += "\tOption:"+getOption()+" ]";
		
		return s;
	}
}
