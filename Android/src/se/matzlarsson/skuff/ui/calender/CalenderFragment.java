package se.matzlarsson.skuff.ui.calender;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import se.matzlarsson.skuff.R;
import se.matzlarsson.skuff.database.DatabaseFetcher;
import se.matzlarsson.skuff.database.data.event.Event;
import se.matzlarsson.skuff.ui.FragmentDisplayer;
import android.os.Bundle;
import android.view.View;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
 
public class CalenderFragment extends CaldroidFragment{

	private HashMap<Date, Integer> eventDates;
	
	public CalenderFragment(){
		Bundle args = new Bundle();
		args.putInt(CaldroidFragment.START_DAY_OF_WEEK, CaldroidFragment.MONDAY);
		this.setArguments(args);
		
		initEventDates();
		this.setBackgroundResourceForDates(eventDates);
		
		this.setCaldroidListener(new CaldroidListener(){

			@Override
			public void onSelectDate(Date date, View view) {
				EventViewer viewer = new EventViewer(getActivity().getApplicationContext(), date);
				if(getActivity() instanceof FragmentDisplayer){
					((FragmentDisplayer)getActivity()).displayFragment(viewer);
				}
			}
			
		});
	}
	
	public void initEventDates(){
		List<Event> events = DatabaseFetcher.getEvents();
		eventDates = new HashMap<Date, Integer>();
		for(int i = 0; i<events.size(); i++){
			eventDates.put(events.get(i).getDate(), R.color.calender_event_date);
		}
	}
}
