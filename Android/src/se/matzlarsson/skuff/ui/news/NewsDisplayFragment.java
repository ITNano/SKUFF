package se.matzlarsson.skuff.ui.news;

import se.matzlarsson.skuff.R;
import se.matzlarsson.skuff.database.DatabaseFetcher;
import se.matzlarsson.skuff.database.DateUtil;
import se.matzlarsson.skuff.database.data.news.News;
import se.matzlarsson.skuff.ui.FragmentDisplayer;
import se.matzlarsson.skuff.ui.StartScreen;
import se.matzlarsson.skuff.ui.TextFormatter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class NewsDisplayFragment extends Fragment {

	private String id;
	private FragmentDisplayer displayer;
	
	public NewsDisplayFragment(FragmentDisplayer displayer, String id){
		this.displayer = displayer;
		this.id = id;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.fragment_news_display, container, false);
    	
    	TextView id = (TextView) view.findViewById(R.id.news_id);
        TextView header = (TextView) view.findViewById(R.id.news_header);
        TextView content = (TextView) view.findViewById(R.id.news_content);
        TextView addInfo = (TextView) view.findViewById(R.id.news_additional_info);

        //Setup typeface
        TextFormatter.useCustomFont(getActivity(), header);
        TextFormatter.useCustomFont(getActivity(), content);
        TextFormatter.useCustomFont(getActivity(), addInfo);
        
        News news = DatabaseFetcher.getNewsById(this.id);
        if(news != null){
	        id.setText(news.getId()+"");
	        header.setText(news.getHeader());    
	        content.setText(news.getContent());
	        String news_info_pre = getResources().getString(R.string.news_info_pre);
	        String news_info_post = getResources().getString(R.string.news_info_post);
	        addInfo.setText(news_info_pre+" "+DateUtil.dateToUIString(news.getTime())+" "+news_info_post+" "+news.getUser());
        }else{
        	Toast.makeText(getActivity(), "Nyheten hittades tyvärr inte", Toast.LENGTH_SHORT).show();
        }
        
        ((Button)view.findViewById(R.id.news_back_button)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				goBack();
			}
        });
        
    	return view;
    }
	
	public void goBack(){
		displayer.displayFragment(StartScreen.FRAGMENT_NEWS);
	}
}
