package se.matzlarsson.skuff.database.data;

import se.matzlarsson.skuff.database.DatabaseHelper;
import se.matzlarsson.skuff.database.data.contact.Contact;
import se.matzlarsson.skuff.database.data.event.Event;
import se.matzlarsson.skuff.database.data.event.EventProperty;
import se.matzlarsson.skuff.database.data.event.EventValue;
import se.matzlarsson.skuff.database.data.group.Group;
import se.matzlarsson.skuff.database.data.group.GroupProperty;
import se.matzlarsson.skuff.database.data.group.GroupValue;
import se.matzlarsson.skuff.database.data.news.News;
import se.matzlarsson.skuff.database.data.sag.SAG;
import se.matzlarsson.skuff.database.data.user.User;

public class Result {
	
	private News[] news;
	private User[] users;
	private Event[] events;
	private EventProperty[] eventProperties;
	private EventValue[] eventValues;
	private Group[] groups;
	private GroupProperty[] groupProperties;
	private GroupValue[] groupValues;
	private SAG[] sag;
	private Contact[] contacts;
	
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
	
	public Group[] getGroups() {
		return groups;
	}
	
	public void setGroups(Group[] groups) {
		this.groups = groups;
	}
	
	public GroupProperty[] getGroupProperties() {
		return groupProperties;
	}
	
	public void setGroupProperties(GroupProperty[] groupProperties) {
		this.groupProperties = groupProperties;
	}
	
	public GroupValue[] getGroupValues() {
		return groupValues;
	}
	
	public void setGroupValues(GroupValue[] groupValues) {
		this.groupValues = groupValues;
	}
	
	public SAG[] getSAG() {
		return sag;
	}
	
	public void setSAG(SAG[] sag) {
		this.sag = sag;
	}
	
	public Contact[] getContacts() {
		return contacts;
	}
	
	public void setContacts(Contact[] contacts) {
		this.contacts = contacts;
	}
	
	public String getUpdatesInfo(){
		String updates = "";
		if(news.length>0)updates += news.length+" news";
		if(users.length>0)updates += (updates.length()>0?", ":"")+users.length+" users";
		if(events.length>0)updates += (updates.length()>0?", ":"")+events.length+" events";
		if(eventProperties.length>0)updates += (updates.length()>0?", ":"")+eventProperties.length+" event props";
		if(eventValues.length>0)updates += (updates.length()>0?", ":"")+eventValues.length+" event values";
		if(groups.length>0)updates += (updates.length()>0?", ":"")+groups.length+" groups";
		if(groupProperties.length>0)updates += (updates.length()>0?", ":"")+groupProperties.length+" group props";
		if(groupValues.length>0)updates += (updates.length()>0?", ":"")+groupValues.length+" group values";
		if(sag.length>0)updates += (updates.length()>0?", ":"")+sag.length+" SAG members";
		if(contacts.length>0)updates += (updates.length()>0?", ":"")+contacts.length+" contacts";
		
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
		for(int i = 0; i<groups.length; i++){
			groups[i].saveToDb(db);
		}
		for(int i = 0; i<groupProperties.length; i++){
			groupProperties[i].saveToDb(db);
		}
		for(int i = 0; i<groupValues.length; i++){
			groupValues[i].saveToDb(db);
		}
		for(int i = 0; i<sag.length; i++){
			sag[i].saveToDb(db);
		}
		for(int i = 0; i<contacts.length; i++){
			contacts[i].saveToDb(db);
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
		for(int i = 0; i<groups.length; i++){
			s += "\t"+groups[i].toString()+"\n";
		}
		for(int i = 0; i<groupProperties.length; i++){
			s += "\t"+groupProperties[i].toString()+"\n";
		}
		for(int i = 0; i<groupValues.length; i++){
			s += "\t"+groupValues[i].toString()+"\n";
		}
		for(int i = 0; i<sag.length; i++){
			s += "\t"+sag[i].toString()+"\n";
		}
		for(int i = 0; i<contacts.length; i++){
			s += "\t"+contacts[i].toString()+"\n";
		}
		s += "]";
		return s;
	}
}
