package se.matzlarsson.skuff.ui.contest;

import se.matzlarsson.skuff.R;
import se.matzlarsson.skuff.database.data.contest.FreeTextQuestion;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class FreeTextAdapter extends BaseAdapter{

	private static final int TEXT_INPUTS = 3;
	
	private Context context;
	private FreeTextQuestion question;
	
	public FreeTextAdapter(Context context, FreeTextQuestion question){
		this.context = context;
		this.question = question;
	}
	
	@Override
	public int getCount() {
		return TEXT_INPUTS;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.contest_option_freetext, null);
		}
		
		return convertView;
	}
	
	public void submit(ListView parentView){
		FreeTextAnswer answer = new FreeTextAnswer(question.getId(), QuestionFragment.getElapsedTime());
		String tmp = null;
		View tmpView = null;
		for(int i = 0; i<parentView.getChildCount(); i++){
			tmpView = parentView.getChildAt(i).findViewById(R.id.freetext_field);
			if(tmpView instanceof EditText){
				tmp = ((EditText)tmpView).getText().toString();
				if(tmp!=null && tmp.length()>0){
					answer.addAnswer(tmp);
				}
			}
		}
		
		ContestFragment.nextStep(answer);
	}

}
