package se.matzlarsson.skuff.database.data.contest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import se.matzlarsson.skuff.database.DatabaseFactory;
import se.matzlarsson.skuff.database.DatabaseHelper;
import se.matzlarsson.skuff.database.DateUtil;
import se.matzlarsson.skuff.database.data.Saveable;
import android.database.Cursor;

public class Contest implements Saveable{

	private int id;
	private String theme;
	private String price;
	private Date endDate;
	private Date time;
	private List<ContestQuestion> questions;
	
	public Contest(){
		setId(-1);
		setTheme("");
		setPrice("");
		setEndDate("2014-01-01 00:00:00");
		setTime("2014-01-01 00:00:00");
		initQuestionList();
	}
	
	public Contest(Cursor c){
		setId(c.getInt(c.getColumnIndex("_id")));
		setTheme(c.getString(c.getColumnIndex("theme")));
		setPrice(c.getString(c.getColumnIndex("price")));
		setEndDate(c.getString(c.getColumnIndex("ends")));
		setTime(c.getString(c.getColumnIndex("time")));
		initQuestionList();
	}
	
	public Contest(int id, String theme, String price, Date endDate, Date time){
		setId(id);
		setTheme(theme);
		setPrice(price);
		setEndDate(endDate);
		setTime(time);
		initQuestionList();
	}
	
	public void initQuestionList(){
		questions = new ArrayList<ContestQuestion>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Date getEndDate() {
		return endDate;
	}
	
	public String getEndDateString(){
		return DateUtil.dateToString(endDate);
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public void setEndDate(String endDate){
		setEndDate(DateUtil.stringToDate(endDate));
	}

	public Date getTime() {
		return time;
	}
	
	public String getTimeString(){
		return DateUtil.dateToString(time);
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	public void setTime(String time){
		setTime(DateUtil.stringToDate(time));
	}
	
	public boolean addQuestion(ContestQuestion q){
		if(q != null){
			return questions.add(q);
		}
		return false;
	}
	
	public boolean removeQuestion(ContestQuestion q){
		if(q != null){
			return questions.remove(q);
		}
		return false;
	}
	
	public int getQuestionCount(){
		return (questions==null?0:questions.size());
	}
	
	public ContestQuestion getQuestion(int index){
		if(index>=0 && questions!=null && index<questions.size()){
			return questions.get(index);
		}
		return null;
	}
	

	@Override
	public void saveToDb(DatabaseHelper db) {
		db.insertOrUpdateQuery(DatabaseFactory.getTable(DatabaseFactory.TABLE_CONTEST), new String[]{getId()+"", getTheme(), getPrice(), getEndDateString(), getTimeString()});
	}
	
	@Override
	public String toString(){
		String s = "CONTEST: [\n";
		s += "\tID:"+getId()+"\n";
		s += "\tTheme:"+getTheme()+"\n";
		s += "\tEnds:"+getEndDateString()+"\n";
		s += "\tTime:"+getTimeString()+" ]";
		
		return s;
	}
	
}
