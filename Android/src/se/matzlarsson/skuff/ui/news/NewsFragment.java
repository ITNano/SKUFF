package se.matzlarsson.skuff.ui.news;

import se.matzlarsson.skuff.R;
import se.matzlarsson.skuff.database.DatabaseFetcher;
import se.matzlarsson.skuff.ui.FragmentDisplayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
 
public class NewsFragment extends Fragment{
    
	private FragmentDisplayer displayer = null;
	
	public NewsFragment(FragmentDisplayer displayer){
		this.displayer = displayer;
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.fragment_news, container, false);
    	
    	NewsListAdapter adapter = new NewsListAdapter(getActivity().getApplicationContext(), DatabaseFetcher.getAllNews());
    	ListView list = (ListView) view.findViewById(R.id.news_posts);
    	list.setAdapter(adapter);
    	list.setOnItemClickListener(new OnItemClickListener(){
    		@Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    			String newsID = (String)((TextView)view.findViewById(R.id.news_id)).getText();
    			showNews(newsID);
            }
    	});
    	return view;
    }
	
	public void showNews(String id){
		this.displayer.displayFragment(new NewsDisplayFragment(displayer, id));
	}
}
