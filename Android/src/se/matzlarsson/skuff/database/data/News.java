package se.matzlarsson.skuff.database.data;

import java.util.Date;

import se.matzlarsson.skuff.database.DatabaseFactory;
import se.matzlarsson.skuff.database.DatabaseHelper;
import se.matzlarsson.skuff.database.DateUtil;
import android.database.Cursor;

public class News {
	
	private int id;
	private int userID;
	private String header;
	private String content;
	private Date time;

	public News(){
		setId(-1);
		setUserID(-1);
		setHeader("");
		setContent("");
		setTime("1970-01-01 00:00:00");
	}
	
	public News(Cursor cursor){
		setId(cursor.getInt(cursor.getColumnIndex("_id")));
		setUserID(cursor.getInt(cursor.getColumnIndex("uid")));
		setHeader(cursor.getString(cursor.getColumnIndex("header")));
		setContent(cursor.getString(cursor.getColumnIndex("content")));
		setTime(cursor.getString(cursor.getColumnIndex("time")));
	}
	
	public News(int id, int userID, String header, String content, String timeString) {
		setId(id);
		setUserID(userID);
		setHeader(header);
		setContent(content);
		setTime(timeString);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
	
	public void saveToDb(DatabaseHelper db){
		db.insertOrUpdateQuery(DatabaseFactory.getTable(DatabaseFactory.TABLE_NEWS), new String[]{getId()+"", getUserID()+"", getHeader(), getContent(), getTimeString()});
	}

	@Override
	public String toString(){
		String s = "NEWS:[ ";
		s += "ID="+getId()+", ";
		s += "userID="+getUserID()+", ";
		s += "header='"+getHeader()+"', ";
		s += "content='"+getContent()+"', ";
		s += "time="+DateUtil.dateToString(getTime())+" ]";
		
		return s;
	}
}
