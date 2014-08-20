package se.matzlarsson.skuff.ui.calender;

import java.util.Date;

import se.matzlarsson.skuff.R;
import se.matzlarsson.skuff.database.DatabaseFetcher;
import se.matzlarsson.skuff.database.data.EventInfo;
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

public class EventViewer extends Fragment implements Refreshable{

	private Context context;
	private Date date;
	private EventInfo[] events;
	
	public EventViewer(Context context, Date date){
		this.context = context;
		this.date = date;
		updateData();
	}
	
	public void updateData(){
		this.events = DatabaseFetcher.getEvents(date);
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.fragment_event_viewer, container, false);
    	
    	ListView list = (ListView)view.findViewById(R.id.event_items);
    	list.setAdapter(new EventViewListAdapter(context, events));
    	
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
			((FragmentDisplayer)getActivity()).displayFragment(StartScreen.FRAGMENT_CALENDER);
		}
	}
}
