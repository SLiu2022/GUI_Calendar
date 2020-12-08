import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.GregorianCalendar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Calendar class defines all the functions of a simple GUI calendar
 * This class act as the controller in the MVC model
 * @author Shaoyue Liu
 * @version 11/7/2020
 */
public class Calendar {
	private GregorianCalendar cal;
	private ArrayList<ChangeListener> listeners;
	private ArrayList<Event> events;
	private final String fileName = "events.txt";
	
	public Calendar() {
		cal = new GregorianCalendar();
		listeners = new ArrayList<ChangeListener>();
		events = new ArrayList<Event>();
	}
	
	/**
	 * Loads initial events to the calendar using serialization
	 * @throws FileNotFoundException - exception
	 * @throws IOException - exception
	 * @throws ClassNotFoundException - exception
	 */
	public void loadEvent() throws FileNotFoundException, IOException, ClassNotFoundException {
		File input = new File(fileName);
		//If events.txt exists
		if(input.exists()){
			ObjectInputStream read = new ObjectInputStream(new FileInputStream(input));
			int size = read.readInt();
			//Add all events to the calendar
			while(size > 0){
				events.add((Event)read.readObject()); 
				size--;
			}
			read.close();
		}
	}

	/**
	 * Update when user press on previous/next day/month
	 * Each button will add/subtract 1 from the current month/day
	 * Never update day and month at the same time
	 * @param day - add/subtract one to day
	 * @param month - add/subtract one to month
	 */
	public void dayOrMonthButtonUpdate(int day, int month) {
		//calculate the newly "created" month and date change 
		cal.add(java.util.Calendar.DAY_OF_MONTH, day);
		cal.add(java.util.Calendar.MONTH, month);
		//Update
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}
	
	/**
	 * Controller	
	 * Attach listener to the arraylist
	 * @param l - change listener
	 */
	public void attachListener(ChangeListener l) {
		listeners.add(l);
	}

	/**
	 * Gets the day
	 * @return day - day
	 */
	public int getDay() {
		int day = cal.get(java.util.Calendar.DAY_OF_MONTH);
		return day;
	}
	
	/**
	 * Gets the day of the week
	 * @return day - day of the week
	 */
	public int getDayOfWeek() {
		int day = cal.get(java.util.Calendar.DAY_OF_WEEK);
		return day;
	}
	
	/**
	 * Gets the month
	 * @return month - month
	 */
	public int getMonth() {
		int month = cal.get(java.util.Calendar.MONTH);
		return month;
	}
	
	/**
	 * Gets the first day of the week
	 * @return day - first day of the week
	 */
	public int firstDayOfWeek() {
		int dayOfMonth = cal.get(java.util.Calendar.DAY_OF_MONTH);
		cal.set(java.util.Calendar.DAY_OF_MONTH, 1);
		int day = cal.get(java.util.Calendar.DAY_OF_WEEK);
		cal.set(java.util.Calendar.DAY_OF_MONTH, dayOfMonth);
		return day;
	}
	
	/**
	 * Gets the lay day of the month
	 * @return day - last day of the month
	 */
	public int getLastDayOfMonth() {
		int day = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
		return day;
	}
	
	/**
	 * Gets the year
	 * @return year - year
	 */
	public int getYear() {
		int year = cal.get(java.util.Calendar.YEAR);
		return year;
	}
	
	/**
	 * When user click on a new date
	 * Sets the current date to the new date and update the view
	 * @param date - update to this date
	 */
	public void updateDate(String date) {
		if(date != null) {
			//Sets the new date
			cal.set(java.util.Calendar.DAY_OF_MONTH, Integer.parseInt(date));
			//Update view
			for (ChangeListener l : listeners){
				l.stateChanged(new ChangeEvent(this));
			}
		}
	}
	
	/**
	 * This method returns a string of all existing events to the corresponding date 
	 * @return eventString - list of events
	 */
	public String getEventString() {
		String dateString = (getMonth()+1) + "/" + getDay() + "/" + getYear();
		String eventString = "";
		for(int i = 0; i < events.size(); i++) {
			if(events.get(i).getDateString().equalsIgnoreCase(dateString)) {
				eventString = eventString + events.get(i).toString() + '\n';
			}
		}
		return eventString;
	}
	
	/**
	 * Determine if the give date contain any events
	 * @param date - given date
	 * @return contain - true or false
	 */
	public boolean dateHasEvents(String date) {
		//return true of the given date contains any events
		boolean contain = false;
		for(int i = 0; i < events.size(); i++) {
			if(events.get(i).getDateString().equalsIgnoreCase(date)) {
				contain = true;
			}
		}
		return contain;
	}
	
	/**
	 * Add new event to the event list
	 * @param title - event title
	 * @param date - event date
	 * @param startT - start time
	 * @param endT - end time
	 * @return null - prevent compilation error
	 * @throws ParseException - exception for the event class
	 */
	public String addEvent(String title, String date, String startT, String endT) throws ParseException {
		//Check if end time is before start time
		if(startT.compareToIgnoreCase(endT) > 0)
			return "Starting time: " + startT + " > Ending time: " + endT;
		//Check for time conflict
		for(int i = 0; i < events.size(); i++) {
			if(events.get(i).getDateString().compareToIgnoreCase(date) == 0) {
				if(events.get(i).getEndTime().compareToIgnoreCase(startT) > 0 
						&& events.get(i).getStartTime().compareToIgnoreCase(endT) < 0) {
					return "Time conflict " + events.get(i).toString();
				}
			}
		}
		
		//Add event to event list
		this.events.add(new Event(title, date, startT, endT));
		//Reorder events by time
		events.sort(new Comparator<Event>() {
			public int compare(Event a, Event b) {
				if(a.getDate().compareTo(b.getDate()) == 0) {
					return a.getStartTime().compareToIgnoreCase(b.getStartTime());
				}
				return a.getDate().compareTo(b.getDate());
			}
		});
		//Update event view
		for (ChangeListener l : listeners) { 
			l.stateChanged(new ChangeEvent(this));
		}
		return null;
	}
	
	/**
	 * Delete a single event from the event list
	 * Only takes the starting time for simplicity
	 * All events in the same day should have different starting time
	 * Checked at addEvent()
	 * @param date - date to delete from
	 * @param startT - starting time of the event
	 */
	public void deleteEvent(String date, String startT) {
		//Cycle through the list of events
		for(int i = 0; i < events.size(); i++) {
			//If date and starting time matches
			//Delete the event
			if(events.get(i).getDateString().equalsIgnoreCase(date) 
					&& events.get(i).getStartTime().equalsIgnoreCase(startT)) {
				events.remove(i);
				//Update
				for (ChangeListener l : listeners) {
					l.stateChanged(new ChangeEvent(this));
				}
			}
		}
	}
	
	/**
	 * Save the existing events to the output file using serialization
	 * @throws FileNotFoundException - exception
	 * @throws IOException - exception
	 * @throws ClassNotFoundException - exception
	 */
	public void saveOnExit() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectOutputStream write = new ObjectOutputStream(new FileOutputStream(fileName));
		//Write the number of events to load on next use
		write.writeInt(events.size());
		//Write all events to file
		for(int i = 0; i < events.size(); i++)
			write.writeObject(events.get(i));
		write.close();
		}
}

