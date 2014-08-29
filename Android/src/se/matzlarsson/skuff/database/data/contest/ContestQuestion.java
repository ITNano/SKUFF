package se.matzlarsson.skuff.database.data.contest;

import se.matzlarsson.skuff.database.DatabaseFactory;
import se.matzlarsson.skuff.database.DatabaseHelper;
import se.matzlarsson.skuff.database.data.Saveable;
import android.database.Cursor;

public abstract class ContestQuestion implements Saveable{

	private int id;
	private int contestID;
	private String question;
	private int typeIndex;
	
	public ContestQuestion(){
		setId(-1);
		setContestID(-1);
		setQuestion("");
		setTypeIndex(-1);
	}
	
	public ContestQuestion(Cursor c){
		initialize(c);
	}
	
	public ContestQuestion(int id, int contestID, String question, int typeIndex){
		setId(id);
		setContestID(contestID);
		setQuestion(question);
		setTypeIndex(typeIndex);
	}
	
	public void initialize(Cursor c){
		setId(c.getInt(c.getColumnIndex("_id")));
		setContestID(c.getInt(c.getColumnIndex("contestID")));
		setQuestion(c.getString(c.getColumnIndex("question")));
		setTypeIndex(c.getInt(c.getColumnIndex("type")));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getContestID() {
		return contestID;
	}

	public void setContestID(int contestID) {
		this.contestID = contestID;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	
	public void setTypeIndex(int typeIndex){
		this.typeIndex = typeIndex;
	}
	
	public int getTypeIndex(){
		return typeIndex;
	}
	
	@Override
	public void saveToDb(DatabaseHelper db) {
		db.insertOrUpdateQuery(DatabaseFactory.getTable(DatabaseFactory.TABLE_CONTEST_QUESTION), new String[]{getId()+"", getContestID()+"", getQuestion(), getTypeIndex()+""});
	}
	
	@Override
	public String toString(){
		String s = "CONTESTQUESTION: [\n";
		s += "\tID:"+getId()+"\n";
		s += "\tContestID:"+getContestID()+"\n";
		s += "\tQuestion:"+getQuestion()+"\n";
		s += "\tTypeID:"+getTypeIndex()+" ]";
		
		return s;
	}
}
