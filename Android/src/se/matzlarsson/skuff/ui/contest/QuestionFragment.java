package se.matzlarsson.skuff.ui.contest;

import se.matzlarsson.skuff.R;
import se.matzlarsson.skuff.database.data.contest.ContestQuestion;
import se.matzlarsson.skuff.database.data.contest.FreeTextQuestion;
import se.matzlarsson.skuff.database.data.contest.OptionQuestion;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
 
public class QuestionFragment extends Fragment{

	private static long startTime;
	
	private ContestQuestion question;
	private int index;
	
	public QuestionFragment(ContestQuestion question, int index){
		this.question = question;
		this.index = index;
	}
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.contest_question, container, false);

    	TextView header = (TextView)view.findViewById(R.id.contest_question_header);
    	TextView q = (TextView)view.findViewById(R.id.contest_question);
    	final ListView answers = (ListView)view.findViewById(R.id.contest_answers);
    	Button submit = (Button)view.findViewById(R.id.contest_submit_question);
    	
    	header.setText(getResources().getString(R.string.contest_question)+" "+index);
    	q.setText(question.getQuestion());
    	
    	if(question instanceof OptionQuestion){
    		answers.setAdapter(new OptionAdapter(getActivity().getApplicationContext(), (OptionQuestion)question));
    		submit.setVisibility(View.GONE);
    	}else if(question instanceof FreeTextQuestion){
    		final FreeTextAdapter adapter = new FreeTextAdapter(getActivity().getApplicationContext(), (FreeTextQuestion)question);
    		answers.setAdapter(adapter);
    		submit.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					adapter.submit(answers);
				}
    		});
    	}
    	
    	startTimer();

    	return view;
    }
	
	public static void startTimer(){
		startTime = System.currentTimeMillis();
	}
	
	protected static int getElapsedTime(){
		return (int)(System.currentTimeMillis()-startTime);
	}
}
