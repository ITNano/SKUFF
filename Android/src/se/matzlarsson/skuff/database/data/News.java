package se.matzlarsson.skuff.database.data;

import java.util.Date;

import se.matzlarsson.skuff.database.DatabaseFactory;
import se.matzlarsson.skuff.database.DatabaseHelper;
import se.matzlarsson.skuff.database.DateUtil;
import android.database.Cursor;

public class News implements Saveable{
	
	private int id;
	private int userID;
	private String user;
	private String header;
	private String content;
	private Date time;

	public News(){
		setId(-1);
		setUserID(-1);
		setUser("");
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
		
		if(cursor.getColumnIndex("username")>=0){
			setUser(cursor.getString(cursor.getColumnIndex("username")));
		}else{
			setUser("[User"+cursor.getInt(cursor.getColumnIndex("uid"))+"]");
		}
	}
	
	public News(int id, int userID, String header, String content, String timeString) {
		setId(id);
		setUserID(userID);
		setUser("[User"+userID+"]");
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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = StringUtil.swedify(header);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = StringUtil.swedify(content);
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
