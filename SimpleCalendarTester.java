import java.awt.FlowLayout;
import java.io.IOException;
import java.text.ParseException;
import javax.swing.JFrame;

enum MONTHS
{
	January, February, March, April, May, June, July, August, September, October, November, December;
}


/**
 * 
 * @author Shaoyue Liu
 * @version 11/7/2020
 */
public class SimpleCalendarTester {
	private final static int F_WIDTH = 1200;
	private final static int F_HEIGHT = 500;
	
	
	public static void main(String[] args) throws ClassNotFoundException, IOException, ParseException {
		JFrame frame = new JFrame("Simple GUI Calendar");
		Calendar cal = new Calendar();
		//Load events from file
		cal.loadEvent();
		//Month and Day panel
		Month month = new Month(frame, F_WIDTH, F_HEIGHT, cal);
		Day day = new Day(F_WIDTH, F_HEIGHT, cal);
		
		//Attach listener and add to calendar frame
		cal.attachListener(month);
		cal.attachListener(day);
		frame.setLayout(new FlowLayout());
		frame.add(month);
		frame.add(day);
		
		//Initiate calendar frame
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
}
