package se.matzlarsson.skuff.database;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	private static DatabaseHelper instance = null;
	
	private static String dbName = "appdata.db";
	private static int version = 1;

	private DatabaseHelper(Context context) {
		super(context, dbName, null, version);
	}
	
	public static DatabaseHelper getInstance(Context context){
		if(instance == null){
			instance = new DatabaseHelper(context);
		}
		
		return instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		DBTable[] table = DatabaseFactory.getTables();
		for(int i = 0; i<table.length; i++){
			createTable(db, table[i]);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		DBTable[] table = DatabaseFactory.getTables();
    	Log.d("SKUFF", "Removing all");
		for(int i = 0; i<table.length; i++){
			removeTable(db, table[i]);
		}
		
		onCreate(db);
	}
	
	
	public void createTable(SQLiteDatabase db, DBTable table){
		db.execSQL(table.getQuery());
	}
	
	public void removeTable(SQLiteDatabase db, DBTable table){
		db.execSQL("DROP TABLE IF EXISTS "+table.getName());
	}
	
	public void truncateTable(DBTable table){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(table.getName(), null, null);
	}
	
	public void insertQuery(DBTable table, String[] values){
		SQLiteDatabase db = this.getWritableDatabase();
		String query = "INSERT INTO "+table.getName()+"(";
		String val = "VALUES(";
		List<DBValue> dbValues = table.getValues();
		for(int i = 0; i<dbValues.size(); i++){
			query += (i>0?",":"")+(dbValues.get(i).isPrimary()?"rowid":dbValues.get(i).getName());
			val += (i>0?",":"")+dbValues.get(i).format(values[i]);
		}
		query += ") "+val+")";
		db.execSQL(query);
	}
	
	public void updateQuery(DBTable table, int[] updateIndexes, String[] values, int[] whereIndexes, String[] whereValues){
		List<DBValue> dbValues = table.getValues();
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		for(int i = 0; i<updateIndexes.length; i++){
			cv.put(dbValues.get(updateIndexes[i]).getName(), values[i]);
		}
		String where = "";
		for(int i = 0; i<whereIndexes.length; i++){
			where += (i>0?" AND ":"")+dbValues.get(whereIndexes[i]).getName()+"=?";
		}
		db.update(table.getName(), cv, where, whereValues);
	}
	
	public void deleteQuery(DBTable table, int[] whereIndexes, String[] whereValues){
		List<DBValue> dbValues = table.getValues();
		SQLiteDatabase db = this.getWritableDatabase();
		String where = "";
		for(int i = 0; i<whereIndexes.length; i++){
			where += (i>0?" AND ":"")+dbValues.get(whereIndexes[i]).getName()+"=?";
		}
		db.delete(table.getName(), where, whereValues);
		db.close();
	}
	
	public Cursor selectQuery(String query, String[] whereValues){
		SQLiteDatabase db = this.getReadableDatabase();
		return db.rawQuery(query, whereValues);
	}

}
