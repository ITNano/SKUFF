package se.matzlarsson.skuff.database.data.contest;

import se.matzlarsson.skuff.database.DatabaseFactory;
import se.matzlarsson.skuff.database.DatabaseHelper;
import se.matzlarsson.skuff.database.DateUtil;
import android.database.Cursor;

public class ContestFactory {

	public static Contest getContest(){
		DatabaseHelper db = DatabaseHelper.getInstance();
		Cursor c = db.selectQuery("SELECT _id,theme,price,ends,time FROM "+DatabaseFactory.TABLE_CONTEST+" WHERE ends>? LIMIT 1", new String[]{DateUtil.getNow()});
		if(c.getCount()>0){
			c.moveToFirst();
			Contest contest = new Contest(c);
			c = db.selectQuery("SELECT _id,contestID,question,type FROM "+DatabaseFactory.TABLE_CONTEST_QUESTION+" WHERE contestID=?", new String[]{contest.getId()+""});
			c.moveToFirst();
			ContestQuestion question = null;
			for(int i = 0; i<c.getCount(); i++){
				question = getContestQuestion(c.getInt(c.getColumnIndex("type")));
				question.initialize(c);
				if(question instanceof OptionQuestion){
					Cursor c2 = db.selectQuery("SELECT _id,questionID,option FROM "+DatabaseFactory.TABLE_CONTEST_OPTION+" WHERE questionID=?", new String[]{question.getId()+""});
					c2.moveToFirst();
					QuestionOption option = null;
					for(int j = 0; j<c2.getCount(); j++){
						option = new QuestionOption(c2);
						((OptionQuestion)question).addOption(option);
						c2.moveToNext();
					}
				}
				contest.addQuestion(question);
				c.moveToNext();
			}
		
			return contest;
		}else{
			return null;
		}
	}
	
	protected static ContestQuestion getContestQuestion(int typeIndex){
		switch(typeIndex){
			case OptionQuestion.TYPE_INDEX:return new OptionQuestion();
			case FreeTextQuestion.TYPE_INDEX:return new FreeTextQuestion();
			default:return new OptionQuestion();
		}
	}
}
