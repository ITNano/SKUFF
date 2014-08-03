package se.matzlarsson.skuff.database;

public class DBValue {

	public static final String DATATYPE_NULL = "NULL";
	public static final String DATATYPE_INT = "INTEGER";
	public static final String DATATYPE_REAL = "REAL";
	public static final String DATATYPE_TEXT = "TEXT";
	public static final String DATATYPE_BLOB = "BLOB";
	
	private String name = "";
	private String datatype = "";
	private boolean primary = false;
	private boolean ai = false;
	private boolean acceptNull = true;

	public DBValue(String name, String datatype) {
		this(name, datatype, false);
	}
	public DBValue(String name, String datatype, boolean primary){
		this(name, datatype, primary, false);
	}
	public DBValue(String name, String datatype, boolean primary, boolean ai){
		this(name, datatype, primary, ai, true);
	}
	public DBValue(String name, String datatype, boolean primary, boolean ai, boolean acceptNull){
		this.name = name;
		this.datatype = datatype;
		this.primary = primary;
		this.ai = ai;
		this.acceptNull = acceptNull;
	}
	
	public void setPrimaryKey(boolean primary){
		this.primary = primary;
	}
	public void setAutoIncrement(boolean ai){
		this.ai = ai;
	}
	public void setAcceptNull(boolean acceptNull){
		this.acceptNull = acceptNull;
	}
	
	public boolean isPrimary(){
		return this.primary;
	}
	public boolean isAutoIncrement(){
		return this.ai;
	}
	public boolean isNullAccepted(){
		return this.acceptNull;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getRepresentation(){
		String repres = name+" "+datatype;
		if(this.isPrimary()){
			repres += " PRIMARY KEY";
		}
		if(this.isAutoIncrement()){
			repres += " AUTOINCREMENT";
		}
		if(!this.isNullAccepted()){
			repres += " NOT NULL";
		}
		return repres;
	}
	
	public String format(String value){
		if(this.datatype.equals(DBValue.DATATYPE_BLOB)){
			return value;
		}else if(this.datatype.equals(DBValue.DATATYPE_INT)){
			return value;
		}else if(this.datatype.equals(DBValue.DATATYPE_NULL)){
			return "NULL";
		}else if(this.datatype.equals(DBValue.DATATYPE_REAL)){
			return value;
		}else if(this.datatype.equals(DBValue.DATATYPE_TEXT)){
			return "'"+value+"'";
		}else{
			return value;
		}
	}

}
