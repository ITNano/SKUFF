package se.matzlarsson.skuff.ui.contest;

import se.matzlarsson.skuff.R;
import se.matzlarsson.skuff.database.data.contest.OptionQuestion;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;

public class OptionAdapter extends BaseAdapter{

	private Context context;
	private OptionQuestion question;
	
	public OptionAdapter(Context context, OptionQuestion question){
		this.context = context;
		this.question = question;
	}
	
	@Override
	public int getCount() {
		return (question==null?0:question.getOptionCount());
	}

	@Override
	public Object getItem(int position) {
		if(position>=0 && position<question.getOptionCount()){
			return question.getOption(position);
		}else{
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.contest_option_multichoice, null);
		}
		
		RadioButton option = (RadioButton)convertView.findViewById(R.id.option_button);
		option.setText(question.getOption(position).getOption());
		
		final int optionID = question.getOption(position).getId();
		option.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				submit(optionID);
			}
		});
		
		return convertView;
	}

	public void submit(int optionID){
		OptionAnswer answer = new OptionAnswer(question.getId(), QuestionFragment.getElapsedTime());
		answer.setOptionID(optionID);

		QuestionFragment.endTimer();
		ContestFragment.nextStep(answer);
	}
}
