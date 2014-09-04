package se.matzlarsson.skuff.ui.sag;

import se.matzlarsson.skuff.R;
import se.matzlarsson.skuff.database.DatabaseFetcher;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
 
public class SagFragment extends Fragment{
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.fragment_sag, container, false);
    	
    	ListView list = (ListView)view.findViewById(R.id.sag_members);
    	list.setAdapter(new SagListAdapter(getActivity().getApplicationContext(), DatabaseFetcher.getSAGMembers()));
    
    	return view;
    }
}
