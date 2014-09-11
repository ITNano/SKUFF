package se.matzlarsson.skuff.ui;

import se.matzlarsson.skuff.R;
import se.matzlarsson.skuff.database.IOUtil;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
 
public class TipFragment extends Fragment {
	
	private static final String SERVER_URL = "http://skuff.host-ed.me/sendTip.php";
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.fragment_tip, container, false);
    	
    	CheckBox anonymous = (CheckBox)view.findViewById(R.id.tip_anonymous);
    	final EditText name = (EditText)view.findViewById(R.id.tip_name);
    	Button submit = (Button)view.findViewById(R.id.tip_send_button);
    	
    	anonymous.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				name.setEnabled(!isChecked);
				closeKeyboards((View)buttonView.getParent());
			}
    	});
    	
    	submit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				submit((View)v.getParent());
			}
    	});
    	
    	return view;
    }
    
    private void closeKeyboards(View tipView){
    	EditText nameInput = ((EditText)tipView.findViewById(R.id.tip_name));
    	EditText messageInput = ((EditText)tipView.findViewById(R.id.tip_message));

    	InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    	imm.hideSoftInputFromWindow(nameInput.getWindowToken(), 0);
    	imm.hideSoftInputFromWindow(messageInput.getWindowToken(), 0);
    }
    
    private void submit(View tipView){
    	Spinner chooser = ((Spinner)tipView.findViewById(R.id.tip_subject_chooser));
    	CheckBox ch_anonymous = ((CheckBox)tipView.findViewById(R.id.tip_anonymous));
    	EditText nameInput = ((EditText)tipView.findViewById(R.id.tip_name));
    	EditText messageInput = ((EditText)tipView.findViewById(R.id.tip_message));
    	
    	String subject = (String)chooser.getSelectedItem();
    	subject = subject.replaceAll("\"", "&quote;");
    	boolean anonymous = ch_anonymous.isChecked();
    	String name = (anonymous?"":nameInput.getText().toString());
    	name = name.replaceAll("\"", "&quote;");
    	String message = messageInput.getText().toString();
    	message = message.replaceAll("\"", "&quote;");

    	closeKeyboards(tipView);
    	
    	String json = "{\"subject\":\""+subject+"\", \"name\":\""+name+"\", \"message\":\""+message+"\"}";
    	if(IOUtil.sendDataToServer(SERVER_URL, json)){
    		Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.tip_success), Toast.LENGTH_SHORT).show();
    		resetView(tipView);
    	}else{
    		Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.tip_failure), Toast.LENGTH_SHORT).show();
    	}
    }
    
    private void resetView(View tipView){
    	Spinner chooser = ((Spinner)tipView.findViewById(R.id.tip_subject_chooser));
    	CheckBox ch_anonymous = ((CheckBox)tipView.findViewById(R.id.tip_anonymous));
    	EditText nameInput = ((EditText)tipView.findViewById(R.id.tip_name));
    	EditText messageInput = ((EditText)tipView.findViewById(R.id.tip_message));
    	
    	chooser.setSelection(0);
		ch_anonymous.setSelected(false);
		nameInput.setText("");
		messageInput.setText("");
    }
}
