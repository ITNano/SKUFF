package se.matzlarsson.skuff.ui;

import se.matzlarsson.skuff.R;
import se.matzlarsson.skuff.R.layout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
 
public class TipFragment extends Fragment {
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	return inflater.inflate(R.layout.fragment_tip, container, false);
    }
}
