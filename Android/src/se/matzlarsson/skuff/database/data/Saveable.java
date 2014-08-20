package se.matzlarsson.skuff.database.data;

import se.matzlarsson.skuff.database.DatabaseHelper;

public interface Saveable {

	public void saveToDb(DatabaseHelper db);
	
}
