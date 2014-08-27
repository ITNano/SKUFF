package se.matzlarsson.skuff.ui.groups;

import se.matzlarsson.skuff.R;
import se.matzlarsson.skuff.database.data.StringUtil;
import se.matzlarsson.skuff.database.data.group.Group;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GroupsListAdapter extends BaseAdapter {

	private Context context;
	private Group[] groups;
	private static final int[] colors = {R.color.theme_color1, R.color.theme_color2};
	
	public GroupsListAdapter(Context context, Group[] groups){
		this.context = context;
		this.groups = groups;
	}
	
	@Override
	public int getCount() {
		return (groups==null?0:groups.length);
	}

	@Override
	public Object getItem(int position) {
		if(position>=0 && position<groups.length){
			return groups[position];
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
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.group_list_item, null);
		}		

		TextView id = (TextView)convertView.findViewById(R.id.group_id);
		TextView name = (TextView)convertView.findViewById(R.id.group_name);
		
		id.setText(groups[position].getId()+"");
		name.setText(StringUtil.swedify(groups[position].getName()));
		
		convertView.setBackgroundColor(context.getResources().getColor(colors[position%colors.length]));
        return convertView;
	}
}
