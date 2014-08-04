package se.matzlarsson.skuff.database.data;

import se.matzlarsson.skuff.database.DatabaseHelper;

public class Result {
	
	private News[] news;
	private User[] users;
	
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
	
	public void saveToDb(DatabaseHelper db){
		for(int i = 0; i<news.length; i++){
			news[i].saveToDb(db);
		}
		for(int i = 0; i<users.length; i++){
			users[i].saveToDb(db);
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
		s += "]";
		return s;
	}
}
