package se.matzlarsson.skuff.ui.groups;

import se.matzlarsson.skuff.R;
import se.matzlarsson.skuff.database.DatabaseFetcher;
import se.matzlarsson.skuff.database.data.StringUtil;
import se.matzlarsson.skuff.database.data.group.GroupInfo;
import se.matzlarsson.skuff.ui.FragmentDisplayer;
import se.matzlarsson.skuff.ui.Refreshable;
import se.matzlarsson.skuff.ui.StartScreen;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class GroupViewer extends Fragment implements Refreshable{

	private Context context;
	private int id;
	private GroupInfo group;
	
	public GroupViewer(Context context, String id){
		this.context = context;
		this.id = Integer.parseInt(id);
		updateData();
	}
	
	public void updateData(){
		this.group = DatabaseFetcher.getGroupInfo(this.id);
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.group_item, container, false);
    	
    	TextView name = (TextView)view.findViewById(R.id.group_item_name);
    	name.setText(StringUtil.swedify(group.getGroup().getName()));
    	ListView list = (ListView)view.findViewById(R.id.group_additional_info);
    	list.setAdapter(new GroupViewListAdapter(context, group.getValues()));
    	
    	Button back = (Button)view.findViewById(R.id.event_back_button);
    	back.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				goBack();
			}
    	});
    	
    	return view;
    }

	@Override
	public void refresh() {
		updateData();
	}
	
	public void goBack(){
		if(getActivity() instanceof FragmentDisplayer){
			((FragmentDisplayer)getActivity()).displayFragment(StartScreen.FRAGMENT_GROUPS);
		}
	}
}
