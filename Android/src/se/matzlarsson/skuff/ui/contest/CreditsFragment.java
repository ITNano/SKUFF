package se.matzlarsson.skuff.ui.contest;

import se.matzlarsson.skuff.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CreditsFragment extends Fragment {
     
	private boolean success;
	
	public CreditsFragment(boolean success){
		this.success = success;
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.contest_credits, container, false);

    	TextView header = (TextView)view.findViewById(R.id.contest_credits_header);
    	TextView info = (TextView)view.findViewById(R.id.contest_credits_info);
    	
    	if(!this.success){
    		header.setText(getResources().getString(R.string.contest_credits_header_fail));
    		info.setText(getResources().getString(R.string.contest_credits_info_fail));
    	}
    	
    	return view;
    }

}
