package se.matzlarsson.skuff.ui.calender;

import se.matzlarsson.skuff.R;
import se.matzlarsson.skuff.database.data.EventValue;
import se.matzlarsson.skuff.database.data.StringUtil;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EventViewInfoListAdapter extends BaseAdapter{

	private Context context;
	private EventValue[] eventValues;
	
	public EventViewInfoListAdapter(Context context, EventValue[] eventValues) {
		this.context = context;
		this.eventValues = eventValues;
	}

	@Override
	public int getCount() {
		return (eventValues==null?0:eventValues.length);
	}

	@Override
	public Object getItem(int position) {
		if(position>=0 && position<eventValues.length){
			return eventValues[position];
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
            convertView = mInflater.inflate(R.layout.event_list_info_item, null);
        }
        
        TextView header = (TextView) convertView.findViewById(R.id.event_item_info_header);
        TextView value = (TextView) convertView.findViewById(R.id.event_item_info_value);
        
        EventValue val = eventValues[position];
        header.setText(StringUtil.swedify(val.getPropertyName()));
	    value.setText(StringUtil.swedify(val.getValue()));
        
        return convertView;
	}

}
