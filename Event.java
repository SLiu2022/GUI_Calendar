import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class holds data for the event model
 * Event properties: title, date, start string, end string
 * @author Shaoyue Liu
 * @version 11/07/2020
 */
public class Event implements Serializable{
	private static final long serialVersionUID = 1L;
	private Date date;
	private String eventTitle;
	private String startTime;
	private String endTime;
	
	/**
	 * Initiate all the instance variables
	 * @param title - event title
	 * @param date - date of event
	 * @param startT - event start time
	 * @param endT - event end time
	 * @throws ParseException - parsing date exception
	 */
	public Event(String title, String date, String startT, String endT) throws ParseException {
		this.eventTitle = title;
		SimpleDateFormat format = new SimpleDateFormat("M/d/yyyy");
		this.date = format.parse(date);
		this.startTime = startT;
		this.endTime = endT;
	}
	
	/**
	 * Gets the date
	 * @return date - date
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * Gets date as a string
	 * @return dateString - date as a string
	 */
	public String getDateString() {
		SimpleDateFormat format = new SimpleDateFormat("M/d/yyyy");
		String dateString = format.format(date);
		return dateString;
	}
	
	/**
	 * Gets the event start time
	 * @return startTime - event start time
	 */
	public String getStartTime() {
		return startTime;
	}
	
	/**
	 * Gets the event end time
	 * @return endTime - event end time
	 */
	public String getEndTime() {
		return endTime;
	}

	
	/**
	 * Prints out the title, start time, and end time of an event
	 * with a specific format
	 */
	public String toString() {
		return eventTitle + "\n" + "    " + startTime + "  -  " + endTime;
	}
}
