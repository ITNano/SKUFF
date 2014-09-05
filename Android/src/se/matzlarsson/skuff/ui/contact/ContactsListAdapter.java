package se.matzlarsson.skuff.ui.contact;

import se.matzlarsson.skuff.R;
import se.matzlarsson.skuff.database.IOUtil;
import se.matzlarsson.skuff.database.data.contact.Contact;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContactsListAdapter extends BaseAdapter{

	private Context context;
	private Contact[] contacts;
	private ContactFragment parent;
	
	public ContactsListAdapter(Context context, ContactFragment parent, Contact[] contacts){
		this.context = context;
		this.parent = parent;
		this.contacts = contacts;
	}
	
	@Override
	public int getCount() {
		return (contacts==null?0:contacts.length);
	}

	@Override
	public Object getItem(int position) {
		if(position>=0 && position<contacts.length){
			return contacts[position];
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
			convertView = inflater.inflate(R.layout.contact_item, null);
		}

		ImageView image = (ImageView)convertView.findViewById(R.id.contact_image);
		LinearLayout info = (LinearLayout)convertView.findViewById(R.id.contact_info);
		TextView name = (TextView)info.findViewById(R.id.contact_name);
		TextView phone = (TextView)info.findViewById(R.id.contact_phone);
		TextView mail = (TextView)info.findViewById(R.id.contact_mail);
		
		Bitmap bmp = IOUtil.getLocalImage(context, contacts[position].getImageName());
		if(bmp != null){
			image.setImageBitmap(bmp);
		}
		
		String contactName = contacts[position].getName();
		name.setText(contactName);
		phone.setText(contacts[position].getPhone());
		if(contacts[position].hasMail()){
			mail.setText("Maila "+contactName.split(" ")[0]);
			final int currentPos = position;
			mail.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					sendMail(contacts[currentPos].getMail());
				}
			});
		}
		
		
		return convertView;
	}
	
	public void sendMail(String emailAddress){
		this.parent.sendMail(emailAddress);
	}
}
