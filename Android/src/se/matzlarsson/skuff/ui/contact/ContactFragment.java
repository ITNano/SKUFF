package se.matzlarsson.skuff.ui.contact;

import se.matzlarsson.skuff.R;
import se.matzlarsson.skuff.database.DatabaseFetcher;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
 
public class ContactFragment extends Fragment{
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.fragment_contact, container, false);
    	
    	ListView list = (ListView)view.findViewById(R.id.contacts_list);
    	list.setAdapter(new ContactsListAdapter(getActivity().getApplicationContext(), this, DatabaseFetcher.getContacts()));
    
    	return view;
    }
	
	public void sendMail(String emailAddress){
		Intent mail = new Intent(Intent.ACTION_SEND);
		mail.setType("message/rfc822");
		mail.putExtra(Intent.EXTRA_EMAIL, new String[]{emailAddress});
		try{
			String header = getResources().getString(R.string.contact_mail_header);
			getActivity().startActivity(Intent.createChooser(mail, header));
		}catch(ActivityNotFoundException anfe){
			Toast.makeText(getActivity(), "Failed to find a mail client", Toast.LENGTH_SHORT).show();
		}
	}
}
