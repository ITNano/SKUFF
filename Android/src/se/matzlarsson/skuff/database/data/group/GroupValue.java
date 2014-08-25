package se.matzlarsson.skuff.database.data.group;

import android.database.Cursor;
import se.matzlarsson.skuff.database.DatabaseFactory;
import se.matzlarsson.skuff.database.DatabaseHelper;
import se.matzlarsson.skuff.database.data.Saveable;

public class GroupValue implements Saveable{

	private int id;
	private int groupID;
	private int propertyID;
	private String value;
	
	private String propertyName;
	
	public GroupValue() {
		setId(-1);
		setGroupID(-1);
		setPropertyID(-1);
		setValue("");
		setPropertyName("Prop "+getPropertyID());
	}
	
	public GroupValue(Cursor cursor){
		setId(cursor.getInt(cursor.getColumnIndex("_id")));
		setGroupID(cursor.getInt(cursor.getColumnIndex("groupID")));
		setPropertyID(cursor.getInt(cursor.getColumnIndex("propertyID")));
		setValue(cursor.getString(cursor.getColumnIndex("value")));
		
		if(cursor.getColumnIndex("propertyName")>=0){
			setPropertyName(cursor.getString(cursor.getColumnIndex("propertyName")));
		}else{
			setPropertyName("Prop "+getPropertyID());
		}
	}
	
	public GroupValue(int id, int groupID, int propertyID, String value){
		setId(-1);
		setGroupID(groupID);
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

	public int getGroupID() {
		return groupID;
	}

	public void setGroupID(int groupID) {
		this.groupID = groupID;
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
		db.insertOrUpdateQuery(DatabaseFactory.getTable(DatabaseFactory.TABLE_GROUP_VALUES), new String[]{getId()+"", getGroupID()+"", getPropertyID()+"", getValue()});
	}

	@Override
	public String toString(){
		String s = "GROUPVALUE:[ ";
		s += "ID="+getId()+", ";
		s += "eventID="+getGroupID()+", ";
		s += "propertyID="+getPropertyID()+" ("+getPropertyName()+"), ";
		s += "value="+getValue()+" ]";
		
		return s;
	}

}
