package se.matzlarsson.skuff.database.data;

public class EventInfo {

	private Event event;
	EventValue[] values;
	
	public EventInfo(Event event, EventValue[] values) {
		this.event = event;
		this.values = values;
	}

	public Event getEvent() {
		return event;
	}

	public EventValue[] getValues() {
		return values;
	}

}
