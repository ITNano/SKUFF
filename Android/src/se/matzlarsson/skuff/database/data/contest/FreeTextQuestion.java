package se.matzlarsson.skuff.database.data.contest;

import android.database.Cursor;

public class FreeTextQuestion extends ContestQuestion{

	public static final int TYPE_INDEX = 2;
	
	public FreeTextQuestion(){
		super();
		setTypeIndex(TYPE_INDEX);
	}
	
	public FreeTextQuestion(Cursor c){
		super(c);
		setTypeIndex(TYPE_INDEX);
	}
	
	public FreeTextQuestion(int id, int contestID, String question){
		super(id, contestID, question, TYPE_INDEX);
		setTypeIndex(TYPE_INDEX);
	}
}
