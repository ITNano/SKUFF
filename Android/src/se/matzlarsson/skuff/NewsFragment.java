package se.matzlarsson.skuff;

import se.matzlarsson.skuff.database.DataSyncer;
import se.matzlarsson.skuff.database.DatabaseFactory;
import se.matzlarsson.skuff.database.DatabaseHelper;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
 
public class NewsFragment extends Fragment implements Refreshable{
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.fragment_news, container, false);
    	
    	return view;
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
		   		}
		   	});
	    }
    }

	@Override
	public void refresh() {
		if(DatabaseHelper.isStarted()){
			DatabaseHelper db = DatabaseHelper.getInstance();
			Cursor c = db.selectQuery("SELECT * FROM "+DatabaseFactory.TABLE_NEWS, null);
			Toast.makeText(getActivity(), "Found "+c.getCount()+" news", Toast.LENGTH_SHORT).show();
		}
	}
}
