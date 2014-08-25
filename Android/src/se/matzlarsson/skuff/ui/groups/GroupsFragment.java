package se.matzlarsson.skuff.ui.groups;

import se.matzlarsson.skuff.R;
import se.matzlarsson.skuff.database.DatabaseFetcher;
import se.matzlarsson.skuff.ui.FragmentDisplayer;
import se.matzlarsson.skuff.ui.Refreshable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
 
public class GroupsFragment extends Fragment implements Refreshable {
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.fragment_groups, container, false);
    	
    	ListView list = (ListView)view.findViewById(R.id.groups_list);
    	list.setAdapter(new GroupsListAdapter(getActivity().getApplicationContext(), DatabaseFetcher.getGroups()));
    	list.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String groupID = (String)((TextView)view.findViewById(R.id.group_id)).getText();
				if(getActivity() instanceof FragmentDisplayer){
					Fragment fragment = new GroupViewer(getActivity().getApplicationContext(), groupID);
					((FragmentDisplayer)getActivity()).displayFragment(fragment);
				}
			}
    	});
    	
    	return view;
    }

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}
}