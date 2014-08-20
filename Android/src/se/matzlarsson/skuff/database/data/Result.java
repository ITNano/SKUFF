package se.matzlarsson.skuff.database.data;

import se.matzlarsson.skuff.database.DatabaseHelper;

public class Result {
	
	private News[] news;
	private User[] users;
	private Event[] events;
	private EventProperty[] eventProperties;
	private EventValue[] eventValues;
	
	public News[] getNews() {
		return news;
	}
	public void setNews(News[] news) {
		this.news = news;
	}
	public User[] getUsers() {
		return users;
	}
	public void setUsers(User[] users) {
		this.users = users;
	}
	
	public Event[] getEvents() {
		return events;
	}
	
	public void setEvents(Event[] events) {
		this.events = events;
	}
	
	public EventProperty[] getEventProperties() {
		return eventProperties;
	}
	
	public void setEventProperties(EventProperty[] eventProperties) {
		this.eventProperties = eventProperties;
	}
	
	public EventValue[] getEventValues() {
		return eventValues;
	}
	
	public void setEventValues(EventValue[] eventValues) {
		this.eventValues = eventValues;
	}
	
	public String getUpdatesInfo(){
		String updates = "";
		if(news.length>0)updates += news.length+" news";
		if(users.length>0)updates += (updates.length()>0?", ":"")+users.length+" users";
		if(events.length>0)updates += (updates.length()>0?", ":"")+events.length+" events";
		if(eventProperties.length>0)updates += (updates.length()>0?", ":"")+eventProperties.length+" event props";
		if(eventValues.length>0)updates += (updates.length()>0?", ":"")+eventValues.length+" event values";
		
		if(updates.length()<=0){
			updates = "No updates found";
		}
		
		return updates;
	}
	
	public void saveToDb(DatabaseHelper db){
		for(int i = 0; i<news.length; i++){
			news[i].saveToDb(db);
		}
		for(int i = 0; i<users.length; i++){
			users[i].saveToDb(db);
		}
		for(int i = 0; i<events.length; i++){
			events[i].saveToDb(db);
		}
		for(int i = 0; i<eventProperties.length; i++){
			eventProperties[i].saveToDb(db);
		}
		for(int i = 0; i<eventValues.length; i++){
			eventValues[i].saveToDb(db);
		}
	}
	
	@Override
	public String toString(){
		String s = "RESULT:[\n";
		for(int i = 0; i<news.length; i++){
			s += "\t"+news[i].toString()+"\n";
		}
		for(int i = 0; i<users.length; i++){
			s += "\t"+users[i].toString()+"\n";
		}
		for(int i = 0; i<events.length; i++){
			s += "\t"+events[i].toString()+"\n";
		}
		for(int i = 0; i<eventProperties.length; i++){
			s += "\t"+eventProperties[i].toString()+"\n";
		}
		for(int i = 0; i<eventValues.length; i++){
			s += "\t"+eventValues[i].toString()+"\n";
		}
		s += "]";
		return s;
	}
}
