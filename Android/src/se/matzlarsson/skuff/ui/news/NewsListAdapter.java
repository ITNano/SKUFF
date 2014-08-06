package se.matzlarsson.skuff.ui.news;

import java.util.List;

import se.matzlarsson.skuff.R;
import se.matzlarsson.skuff.database.DateUtil;
import se.matzlarsson.skuff.database.data.News;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
 
public class NewsListAdapter extends BaseAdapter {
     
	private final int MAX_CONTENT_LENGTH = 250;
	
    private Context context;
    private List<News> newsItems;
     
    public NewsListAdapter(Context context, List<News> newsItems){
        this.context = context;
        this.newsItems = newsItems;
    }
    
    public void add(News news){
    	if(news != null){
    		newsItems.add(news);
    	}
    }
 
    @Override
    public int getCount() {
        return newsItems.size();
    }
 
    @Override
    public Object getItem(int position) {      
        return newsItems.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.news_list_item, null);
        }
        
        TextView id = (TextView) convertView.findViewById(R.id.news_id);
        TextView header = (TextView) convertView.findViewById(R.id.news_header);
        TextView content = (TextView) convertView.findViewById(R.id.news_content);
        TextView addInfo = (TextView) convertView.findViewById(R.id.news_additional_info);
        TextView readMore = (TextView) convertView.findViewById(R.id.news_read_more);
        
        News news = newsItems.get(position);
        id.setText(news.getId()+"");
        header.setText(news.getHeader());
        
        if(news.getContent().length()<MAX_CONTENT_LENGTH){
        	content.setText(news.getContent());
        	readMore.setVisibility(View.GONE);
        }else{
        	content.setText(news.getContent().substring(0, MAX_CONTENT_LENGTH)+" ...");
        }
        
        String news_info_pre = parent.getResources().getString(R.string.news_info_pre);
        String news_info_post = parent.getResources().getString(R.string.news_info_post);
        addInfo.setText(news_info_pre+" "+DateUtil.dateToUIString(news.getTime())+" "+news_info_post+" "+news.getUser());
         
        return convertView;
    }
 
}