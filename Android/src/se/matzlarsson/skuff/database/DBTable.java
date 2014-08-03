package se.matzlarsson.skuff.database;

import java.util.ArrayList;
import java.util.List;

public class DBTable {
	
	private String tableName;
	private List<DBValue> values;

	public DBTable(String tableName){
		this.tableName = tableName;
		this.values = new ArrayList<DBValue>();
	}
	
	public void addValue(DBValue value){
		if(value != null){
			if(value.isPrimary() && this.getPrimaryKey() != null){
				throw new IllegalArgumentException("Duplicate Primary Keys! Value not added!");
			}else{
				values.add(value);
			}
		}
	}
	
	public String getName(){
		return this.tableName;
	}
	
	public List<DBValue> getValues(){
		return this.values;
	}
	
	public DBValue getPrimaryKey(){
		for(DBValue val : values){
			if(val.isPrimary()){
				return val;
			}
		}
		return null;
	}
	
	public String getQuery(){
		String query = "CREATE TABLE "+tableName+"(";
		for(int i = 0; i<values.size(); i++){
			query += (i>0?", ":"")+values.get(i).getRepresentation();
		}
		query += ")";
		
		return query;
	}
}
