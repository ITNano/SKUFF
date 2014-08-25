package se.matzlarsson.skuff.database.data;

import android.database.Cursor;
import se.matzlarsson.skuff.database.DatabaseFactory;
import se.matzlarsson.skuff.database.DatabaseHelper;

public class EventValue implements Saveable{

	private int id;
	private int eventID;
	private int propertyID;
	private String value;
	
	private String propertyName;
	
	public EventValue() {
		setId(-1);
		setEventID(-1);
		setPropertyID(-1);
		setValue("");
		setPropertyName("Prop "+getPropertyID());
	}
	
	public EventValue(Cursor cursor){
		setId(cursor.getInt(cursor.getColumnIndex("_id")));
		setEventID(cursor.getInt(cursor.getColumnIndex("eventID")));
		setPropertyID(cursor.getInt(cursor.getColumnIndex("propertyID")));
		setValue(cursor.getString(cursor.getColumnIndex("value")));
		
		if(cursor.getColumnIndex("propertyName")>=0){
			setPropertyName(cursor.getString(cursor.getColumnIndex("propertyName")));
		}else{
			setPropertyName("Prop "+getPropertyID());
		}
	}
	
	public EventValue(int id, int eventID, int propertyID, String value){
		setId(-1);
		setEventID(eventID);
		setPropertyID(propertyID);
		setValue(value);
		setPropertyName("Prop "+propertyID);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	public int getPropertyID() {
		return propertyID;
	}

	public void setPropertyID(int propertyID) {
		this.propertyID = propertyID;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	@Override
	public void saveToDb(DatabaseHelper db){
		db.insertOrUpdateQuery(DatabaseFactory.getTable(DatabaseFactory.TABLE_EVENT_VALUES), new String[]{getId()+"", getEventID()+"", getPropertyID()+"", getValue()});
	}

	@Override
	public String toString(){
		String s = "EVENTVALUE:[ ";
		s += "ID="+getId()+", ";
		s += "eventID="+getEventID()+", ";
		s += "propertyID="+getPropertyID()+" ("+getPropertyName()+"), ";
		s += "value="+getValue()+" ]";
		
		return s;
	}

}
