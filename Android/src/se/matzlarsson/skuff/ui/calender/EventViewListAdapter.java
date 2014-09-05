package se.matzlarsson.skuff.ui.calender;

import se.matzlarsson.skuff.R;
import se.matzlarsson.skuff.database.data.event.Event;
import se.matzlarsson.skuff.database.data.event.EventInfo;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EventViewListAdapter extends BaseAdapter{

	private Context context;
	private EventInfo[] events;
	
	public EventViewListAdapter(Context context, EventInfo[] events){
		this.context = context;
		this.events = events;
	}

	@Override
	public int getCount() {
		return (events == null?0:events.length);
	}

	@Override
	public Object getItem(int position) {
		if(position>=0 && position<events.length){
			return events[position];
		}else{
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.event_list_item, null);
        }

        TextView name = (TextView) convertView.findViewById(R.id.event_item_name);
        TextView compulsory = (TextView) convertView.findViewById(R.id.event_item_compulsory);
        LinearLayout addInfo = (LinearLayout) convertView.findViewById(R.id.event_additional_info);
        
        Event event = events[position].getEvent();
        name.setText(event.getName());
        if(event.isCompulsory()){
        	compulsory.setText(convertView.getResources().getString(R.string.compulsory));
        }else{
        	compulsory.setVisibility(View.GONE);
        	name.setPadding(name.getPaddingLeft(), name.getPaddingTop(), name.getPaddingRight(), name.getPaddingBottom()+5);
        }
        
        //Add info if not already added
        if(addInfo.getChildCount()<=0){
	        EventViewInfoListAdapter info = new EventViewInfoListAdapter(context, events[position].getValues());
	        View tmpView = null;
	        for(int i = 0; i<info.getCount(); i++){
	        	tmpView = info.getView(i, null, (ViewGroup)convertView);
	        	addInfo.addView(tmpView);
	        }
        }
        
        return convertView;
	}

}
