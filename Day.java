import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This class display the right side of the GUI calendar
 * It displays events of the selected day
 * @author Shaoyue Liu
 * @version 11/7/2020
 */
public class Day extends JPanel implements ChangeListener {
	private static final long serialVersionUID = 1L;
	private Calendar cal;
	private JTextField date;
	private JTextArea eventList;
	private int fWidth;
	private int fHeight;
	private MONTHS [] months;
	
	/**
	 * Initialize variables
	 * @param fWidth - width of the GUI calendar frame
	 * @param fHeight - width of the GUI calendar frame
	 * @param cal - calendar model
	 */
	public Day(int fWidth, int fHeight, Calendar cal) {
		this.fWidth = fWidth;
		this.fHeight = fHeight;
		this.cal = cal;
		date = new JTextField();
		eventList = new JTextArea();
		this.months = MONTHS.values();
		initialize();
	}

	/**
	 * Initialize the right side of the GUI calendar frame
	 */
	public void initialize() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(fWidth / 3, fHeight));
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
		panel.setLayout(new BorderLayout());
		date.setEditable(false);
		date.setHorizontalAlignment(JTextField.CENTER);
		eventList.setEditable(false);
		JScrollPane eventListScroll = new JScrollPane(eventList);
		panel.add(date, BorderLayout.NORTH);
		panel.add(eventListScroll, BorderLayout.CENTER);
		update(); 
		
		this.add(panel);
		
	}
	
	/**
	 * Update the date and event list when user selects a new day
	 * also update when user create/delete an event
	 */
	public void update() {
		date.setText( months[cal.getMonth()].toString() + ' ' + cal.getDay() + ", " + cal.getYear());
		eventList.setText(cal.getEventString());
	}
	
	/**
	 * Update the view of the day frame
	 * Specifically the event list
	 * @param e - change event
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		update();
	}

}
