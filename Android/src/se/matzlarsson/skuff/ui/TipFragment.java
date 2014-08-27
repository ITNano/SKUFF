package se.matzlarsson.skuff.ui;

import se.matzlarsson.skuff.R;
import se.matzlarsson.skuff.database.DataSyncer;
import se.matzlarsson.skuff.database.ResourceSyncer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
 
public class TipFragment extends Fragment {
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	return inflater.inflate(R.layout.fragment_tip, container, false);
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	
    	Button button = (Button)getActivity().findViewById(R.id.button1);
	    if(button != null){
		   	button.setOnClickListener(new OnClickListener(){
		   		@Override
		   		public void onClick(View view){
		   			DataSyncer syncer = DataSyncer.getInstance((ActionBarActivity)getActivity());
		   			syncer.execute();
		   			ResourceSyncer resSyncer = ResourceSyncer.getInstance((ActionBarActivity)getActivity());
		   			resSyncer.execute();
		   		}
		   	});
	    }
    }
}
