package se.matzlarsson.skuff.database.data.event;

import java.util.Date;

import se.matzlarsson.skuff.database.DatabaseFactory;
import se.matzlarsson.skuff.database.DatabaseHelper;
import se.matzlarsson.skuff.database.DateUtil;
import se.matzlarsson.skuff.database.data.Saveable;
import android.database.Cursor;

public class Event implements Saveable{

	private int id;
	private Date date;
	private String name;
	private boolean compulsory;
	private Date time;
	
	public Event() {
		setId(-1);
		setDate("1970-01-01 00:00:00");
		setName("");
		setCompulsory(false);
		setTime("1970-01-01 00:00:00");
	}
	
	public Event(Cursor cursor){
		this.setId(cursor.getInt(cursor.getColumnIndex("_id")));
		this.setDate(cursor.getString(cursor.getColumnIndex("date")));
		this.setName(cursor.getString(cursor.getColumnIndex("name")));
		this.setCompulsory(cursor.getInt(cursor.getColumnIndex("compulsory"))==1);
		this.setTime(cursor.getString(cursor.getColumnIndex("time")));
	}
	
	public Event(int id, Date date, String name, boolean compulsory, Date time){
		setId(id);
		setDate(date);
		setName(name);
		setCompulsory(compulsory);
		setTime(time);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}
	
	public String getDateString(){
		return DateUtil.dateToString(this.date);
	}
	
	public void setDate(String dateString){
		this.setDate(DateUtil.stringToDate(dateString));
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCompulsory() {
		return compulsory;
	}

	public void setCompulsory(boolean compulsory) {
		this.compulsory = compulsory;
	}

	public Date getTime() {
		return time;
	}
	
	public String getTimeString(){
		return DateUtil.dateToString(this.time);
	}

	public void setTime(String timeString) {
		setTime(DateUtil.stringToDate(timeString));
	}
	
	public void setTime(Date time){
		this.time = time;
	}

	@Override
	public void saveToDb(DatabaseHelper db){
		db.insertOrUpdateQuery(DatabaseFactory.getTable(DatabaseFactory.TABLE_EVENTS), new String[]{getId()+"", getDateString()+"", getName(), isCompulsory()?"1":"0", getTimeString()});
	}

	@Override
	public String toString(){
		String s = "EVENT:[ ";
		s += "ID="+getId()+", ";
		s += "date="+getDateString()+", ";
		s += "name='"+getName()+"', ";
		s += "compulsory='"+(isCompulsory()?"true":"false")+"', ";
		s += "time="+getTimeString()+" ]";
		
		return s;
	}
}
