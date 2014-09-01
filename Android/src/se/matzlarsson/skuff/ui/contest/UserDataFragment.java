package se.matzlarsson.skuff.ui.contest;

import se.matzlarsson.skuff.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class UserDataFragment extends Fragment{

	 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	final View view = inflater.inflate(R.layout.contest_user_data, container, false);

    	Button submit = (Button)view.findViewById(R.id.contest_userdata_submit);
    	submit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				submit(view);
			}
    	});
    	
    	return view;
    }
    
    public void submit(View view){
    	EditText name = (EditText)view.findViewById(R.id.contest_userdata_name_wrapper).findViewById(R.id.contest_userdata_name_value);
    	EditText phone = (EditText)view.findViewById(R.id.contest_userdata_phone_wrapper).findViewById(R.id.contest_userdata_phone_value);
    	
    	UserData data = new UserData(name.getText().toString(), phone.getText().toString());
    	ContestFragment.endContest(data);
    }
}
