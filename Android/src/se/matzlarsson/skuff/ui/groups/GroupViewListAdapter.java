package se.matzlarsson.skuff.ui.groups;

import se.matzlarsson.skuff.R;
import se.matzlarsson.skuff.database.data.group.GroupValue;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GroupViewListAdapter extends BaseAdapter{

	private Context context;
	private GroupValue[] values;
	
	public GroupViewListAdapter(Context context, GroupValue[] values){
		this.context = context;
		this.values = values;
	}

	@Override
	public int getCount() {
		return (values == null?0:values.length);
	}

	@Override
	public Object getItem(int position) {
		if(position>=0 && position<values.length){
			return values[position];
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
            convertView = mInflater.inflate(R.layout.group_info_item, null);
        }

        TextView name = (TextView) convertView.findViewById(R.id.group_item_info_name);
        TextView value = (TextView) convertView.findViewById(R.id.group_item_info_value);
        
        name.setText(values[position].getPropertyName());
        value.setText(values[position].getValue());
        
        return convertView;
	}

}
