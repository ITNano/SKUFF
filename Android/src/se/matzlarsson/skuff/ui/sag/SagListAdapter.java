package se.matzlarsson.skuff.ui.sag;

import se.matzlarsson.skuff.R;
import se.matzlarsson.skuff.database.data.StringUtil;
import se.matzlarsson.skuff.database.data.sag.SAG;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SagListAdapter extends BaseAdapter {
	
	private Context context;
	private SAG[] sag;
	
	public SagListAdapter(Context context, SAG[] sag){
		this.context = context;
		this.sag = sag;
	}

	@Override
	public int getCount() {
		return (sag==null?0:sag.length);
	}

	@Override
	public Object getItem(int position) {
		if(position>=0 && position<sag.length){
			return sag[position];
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
			convertView = inflater.inflate(R.layout.sag_member, null);
		}
		
		ImageView image = (ImageView)convertView.findViewById(R.id.sag_member_image);
		LinearLayout info = (LinearLayout)convertView.findViewById(R.id.sag_member_info);
		TextView post = (TextView)info.findViewById(R.id.sag_member_post);
		TextView name = (TextView)info.findViewById(R.id.sag_member_name);
		TextView age = (TextView)info.findViewById(R.id.sag_member_age);
		
		Resources res = context.getResources();
		Drawable img = null;
		try{
			img = res.getDrawable(res.getIdentifier(sag[position].getImageName(), "drawable", "se.matzlarsson.skuff"));
		}catch(NotFoundException nfe){
			Log.e("Error", "Resource not found: '"+sag[position].getImageName());
		}
		if(img != null){
			image.setImageDrawable(img);
		}
		
		post.setText(StringUtil.swedify(sag[position].getPost()));
		name.setText(StringUtil.swedify(sag[position].getName()));
		age.setText(sag[position].getAge()+" "+context.getResources().getString(R.string.sag_age_unity));
		
		return convertView;
	}

}
