package se.matzlarsson.skuff;

import se.matzlarsson.skuff.database.DatabaseFactory;
import se.matzlarsson.skuff.database.DatabaseHelper;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
 
public class NewsFragment extends Fragment {
     
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
		   			dbStuff();
		   		}
		   	});
	    }
    }
    
    public void dbStuff(){
    	Log.d("SKUFF", "DbStuff here, just checking");
    	DatabaseHelper db = DatabaseHelper.getInstance(getActivity());
        Cursor c1 = db.selectQuery("SELECT _id,name FROM users", null);
    	Log.d("SKUFF", "Has "+c1.getCount()+" rows");
    	
    	db.insertQuery(DatabaseFactory.getTable("users"), new String[]{"NULL", "Matz"});
    	db.updateQuery(DatabaseFactory.getTable("users"), new int[]{1}, new String[]{"Nano"}, new int[]{1}, new String[]{"Matz"});
        
        Cursor c2 = db.selectQuery("SELECT _id,name FROM users", null);
        c2.moveToFirst();
    	Log.d("SKUFF", "Now has name "+c2.getString(1));
    }
}
