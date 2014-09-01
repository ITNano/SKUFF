package se.matzlarsson.skuff.ui.contest;

import se.matzlarsson.skuff.R;
import se.matzlarsson.skuff.database.DatabaseFetcher;
import se.matzlarsson.skuff.ui.Refreshable;
import se.matzlarsson.skuff.ui.StartScreen;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
 
public class ContestFragment extends Fragment implements Refreshable {
	
	private static ContestModel model;
	private static StartScreen app;
	
	public ContestFragment(){
		model = new ContestModel(DatabaseFetcher.getContest());
	}
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	app = (StartScreen)getActivity();
    	if(!model.hasContest()){
    		model.setContest(DatabaseFetcher.getContest());
    	}
    	
    	View view = inflater.inflate(R.layout.fragment_contest, container, false);

    	TextView theme = (TextView)view.findViewById(R.id.contest_theme_wrapper).findViewById(R.id.contest_theme_value);
    	TextView price = (TextView)view.findViewById(R.id.contest_price_wrapper).findViewById(R.id.contest_price_value);
    	TextView ending = (TextView)view.findViewById(R.id.contest_ending_wrapper).findViewById(R.id.contest_ending_value);
    	Button startContest = (Button)view.findViewById(R.id.contest_start_button);
    	TextView error404 = (TextView)view.findViewById(R.id.contest_error404);
    	
    	if(model.hasContest()){
	    	theme.setText(model.getContest().getTheme());
	    	price.setText(model.getContest().getPrice());
	    	ending.setText(model.getContest().getEndDateString().substring(0, 16));		//No seconds
	    	startContest.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					startContest();
				}
	    	});
	    	error404.setVisibility(View.GONE);
    	}else{
    		LinearLayout root = (LinearLayout)view;
    		for(int i = 0; i<root.getChildCount(); i++){
    			root.getChildAt(i).setVisibility(View.GONE);
    		}
    		error404.setVisibility(View.VISIBLE);
    	}
    	
    	return view;
    }

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}
	
	public void startContest(){
		app.setMenuDisabled(true);
		model.startContest();
		nextStep(null);
	}
	
	public static void nextStep(Answer answer){
		model.addAnswer(answer);
		
		Fragment fragment = model.next();
		if(fragment == null){
			fragment = new UserDataFragment();
		}
		app.displayFragment(fragment);
	}
	
	public static void endContest(UserData data){
		model.setUserdata(data);
		app.setMenuDisabled(false);
		boolean success = model.endContest(app.getApplicationContext());
		app.displayFragment(new CreditsFragment(success));
	}
}
