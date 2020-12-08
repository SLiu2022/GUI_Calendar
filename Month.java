import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This class display the right side of the GUI calendar
 * It has functional JButtons such as Create, delete, save and exit, previous/next day/month
 * And it also contain a working GUI calendar of 49 JButtons
 * @author Shaoyue Liu
 * @version 11/7/2020
 *
 */
public class Month extends JPanel implements ChangeListener, DayMonthView{
	private static final long serialVersionUID = 1L;
	private int fWidth;
	private int fHeight;
	private int day;
	private int month;
	private int today;
	private Calendar cal;
	private JFrame finalFrame;
	private JButton [] calendarButtons;
	private JTextField date;
	private MONTHS [] months;
	
	/**
	 * Initialize variables
	 * @param finalFrame - GUI calendar frame
	 * @param fWidth - width of GUI calendar frame
	 * @param fHeight - height of GUI calendar frame
	 * @param cal - calendar model
	 */
	public Month(JFrame finalFrame, int fWidth, int fHeight,  Calendar cal) {
		this.finalFrame = finalFrame;
		this.fWidth = fWidth;
		this.fHeight = fHeight;
		this.cal = cal;
		
		this.day = cal.getDay();
		this.month = cal.getMonth();
		this.today = cal.firstDayOfWeek() + day + 5;
		this.date = new JTextField();
		this.calendarButtons = new JButton[49];
		this.months = MONTHS.values();
		initialize();
	}
	
	/**
	 * Initialize the left side of the GUI calendar frame
	 */
	public void initialize() {
		JPanel calendarPanel = new JPanel();
		calendarPanel.setPreferredSize(new Dimension(fWidth / 2, fHeight));
		calendarPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
		calendarPanel.setLayout(new BorderLayout());
		this.add(calendarPanel);
		
		//Top panel holds all the functional buttons
		//Create, delete, save & quit, previous/next day/month
		JPanel top = new JPanel();
		top.setPreferredSize(new Dimension((int)calendarPanel.getPreferredSize().getWidth() - 10, (int)calendarPanel.getPreferredSize().getHeight() / 3));
		calendarPanel.add(top, BorderLayout.NORTH);
		//Bottom panel holds the monthly calendar
		//Contains 49 JButtons
		JPanel bottom = new JPanel();
		bottom.setPreferredSize(new Dimension((int)calendarPanel.getPreferredSize().getWidth() - 10, (int)calendarPanel.getPreferredSize().getHeight() * 3/4));
		calendarPanel.add(bottom, BorderLayout.CENTER);
		

		//Create button creates new events
		JButton create = new JButton("Create");
		create.setPreferredSize(new Dimension((int)top.getPreferredSize().getWidth() / 3 - 5, (int)top.getPreferredSize().getHeight() / 4));
		//Create button creates a new JFrame for user to enter event title and start/end time
		create.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//JFrame that holds everything
				JFrame createFrame = new JFrame("Create an event");
				createFrame.setSize(new Dimension(600, 155));
				createFrame.setLayout(new FlowLayout());
				//JTextField for user input event title
				JTextField eventTitle = new JTextField();
				eventTitle.setPreferredSize(new Dimension(560, 50));
				eventTitle.setText("<Event Title>");
				createFrame.add(eventTitle);
				//JTextField shows user selected date
				JTextField date = new JTextField();
				date.setPreferredSize(new Dimension(100, 50));
				date.setEditable(false);
				date.setHorizontalAlignment(JTextField.CENTER);
				date.setText((cal.getMonth() + 1) + "/" + cal.getDay() + "/" + cal.getYear());
				createFrame.add(date);
				//JTextField for user input start time
				JTextField startTime = new JTextField();
				startTime.setPreferredSize(new Dimension(150, 50));
				startTime.setHorizontalAlignment(JTextField.CENTER);
				startTime.setText("--:-- am/pm");
				createFrame.add(startTime);
				//JTextField for splitting the start time and end time JTextField
				JTextField to = new JTextField();
				to.setPreferredSize(new Dimension(40, 40));
				to.setHorizontalAlignment(JTextField.CENTER);
				to.setBorder(BorderFactory.createEmptyBorder());
				to.setEditable(false);
				to.setText("to");
				createFrame.add(to);
				//JTextField for user input end time
				JTextField endTime = new JTextField();
				endTime.setPreferredSize(new Dimension(150, 50));
				endTime.setHorizontalAlignment(JTextField.CENTER);
				endTime.setText("--:-- am/pm");
				createFrame.add(endTime);
				////JButton for user save event to event list
				JButton saveButton = new JButton("Save");
				saveButton.setPreferredSize(new Dimension(100, 40));
				//Save button will check for time conflict before saving the event
				saveButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							JFrame errFrame = new JFrame("Time Conflict");
							JTextField errorField = new JTextField();
							errorField.setPreferredSize(new Dimension(500, 50));
							errorField.setEditable(false);
							errFrame.add(errorField);
							errFrame.pack();
							String errorMessage;
							//If addEvent method returns null, which means failure, error frame and message will be displayed
							if((errorMessage = cal.addEvent(eventTitle.getText(), date.getText(), startTime.getText(), endTime.getText())) != null) {
								errorField.setText(errorMessage);
								errFrame.setVisible(true);
							}
							else {
								createFrame.dispose();
								errFrame.dispose();
							}
						} catch (ParseException er) {
							er.printStackTrace();
						}
					}
				});
				createFrame.add(saveButton);
				createFrame.setVisible(true);
			}	
		});
		top.add(create);
		
		//Delete button delete an event
		JButton delete = new JButton("Delete");
		delete.setPreferredSize(new Dimension((int)top.getPreferredSize().getWidth() / 3 - 5, (int)top.getPreferredSize().getHeight() / 4));
		//Delete button creates a delete frame for user to input event title and time to delete an event
		delete.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//JFrame that holds everything
				JFrame deleteFrame = new JFrame("Delete an event");
				deleteFrame.setSize(new Dimension(600, 155));
				deleteFrame.setLayout(new FlowLayout());
				//JTextField for user input event title
				JTextField eventTitle = new JTextField();
				eventTitle.setPreferredSize(new Dimension(560, 50));
				eventTitle.setText("<Event Title>");
				deleteFrame.add(eventTitle);
				//JTextField shows user selected date
				JTextField date = new JTextField();
				date.setPreferredSize(new Dimension(100, 50));
				date.setEditable(false);
				date.setHorizontalAlignment(JTextField.CENTER);
				date.setText((cal.getMonth() + 1) + "/" + cal.getDay() + "/" + cal.getYear());
				deleteFrame.add(date);
				//JTextField for user input start time
				JTextField startTime = new JTextField();
				startTime.setPreferredSize(new Dimension(150, 50));
				startTime.setHorizontalAlignment(JTextField.CENTER);
				startTime.setText("--:-- am/pm");
				deleteFrame.add(startTime);
				//JTextField for splitting the start time and end time JTextField
				JTextField to = new JTextField();
				to.setPreferredSize(new Dimension(40, 40));
				to.setHorizontalAlignment(JTextField.CENTER);
				to.setBorder(BorderFactory.createEmptyBorder());
				to.setEditable(false);
				to.setText("to");
				deleteFrame.add(to);
				//JTextField for user input end time
				JTextField endTime = new JTextField();
				endTime.setPreferredSize(new Dimension(150, 50));
				endTime.setHorizontalAlignment(JTextField.CENTER);
				endTime.setText("--:-- am/pm");
				deleteFrame.add(endTime);
				//Delete button to confirm deletion
				JButton delete2 = new JButton("Delete");
				delete2.setPreferredSize(new Dimension(100, 40));
			    delete2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//Only requires title and start time since they should be unique
						//start time should be unique or it won't pass the create event process
						cal.deleteEvent(date.getText(), startTime.getText());
						deleteFrame.dispose();		
					}
				});
				deleteFrame.setVisible(true);
				deleteFrame.add(delete2);
			}				
		});
		top.add(delete);
		
		
		//Saves all current to file and exits the program
		JButton quit = new JButton("Save & Quit");
		quit.setPreferredSize(new Dimension((int)top.getPreferredSize().getWidth() / 3 - 5, (int)top.getPreferredSize().getHeight() / 4));
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					cal.saveOnExit();
					finalFrame.dispose();
				} catch (IOException er) {
					er.printStackTrace();
				} catch (ClassNotFoundException er) {
					er.printStackTrace();
				}
			}	
		});
		top.add(quit);
		
		//Users clicks on button and calendar moves 1 day back
		JButton previousDay = new JButton("<Previous Day");
		previousDay.setPreferredSize(new Dimension((int)top.getPreferredSize().getWidth() / 2 - 5, (int)top.getPreferredSize().getHeight() / 4));
		previousDay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Highlight days that has events
				if(cal.dateHasEvents((cal.getMonth() + 1) + "/" + cal.getDay() + "/" + cal.getYear())) {
					calendarButtons[cal.firstDayOfWeek() + 5 + day].setBackground(Color.PINK);
				}
				//Reset the highlight after moving to another day
				else {
					calendarButtons[cal.firstDayOfWeek() + 5 + day].setBackground(null);
				}
				cal.dayOrMonthButtonUpdate(-1, 0);
			}	
		});
		top.add(previousDay);
		
		//Users clicks on button and calendar moves 1 day forward
		JButton nextDay = new JButton("Next Day>");
		nextDay.setPreferredSize(new Dimension((int)top.getPreferredSize().getWidth() / 2 - 5 , (int)top.getPreferredSize().getHeight() / 4));
		nextDay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Highlight days that has events
				if(cal.dateHasEvents((cal.getMonth() + 1) + "/" + cal.getDay() + "/" + cal.getYear())) {
					calendarButtons[cal.firstDayOfWeek() + 5 + day].setBackground(Color.PINK);
				}
				//Reset the highlight after moving to another day
				else {
					calendarButtons[cal.firstDayOfWeek() + 5 + day].setBackground(null);
				}
				cal.dayOrMonthButtonUpdate(1, 0);
			}	
		});
		top.add(nextDay);
		
		//Users clicks on button and calendar moves 1 month back
		JButton previousMonth = new JButton("<Previous Month");
		previousMonth.setPreferredSize(new Dimension((int)top.getPreferredSize().getWidth() / 2 - 5, (int)top.getPreferredSize().getHeight() / 4));
		previousMonth.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calendarButtons[cal.firstDayOfWeek() + 5 + day].setBackground(null);
				cal.dayOrMonthButtonUpdate(0, -1);
			}	
		});
		top.add(previousMonth);
		
		//Users clicks on button and calendar moves 1 month forward
		JButton nextMonth = new JButton("Next Month>");
		nextMonth.setPreferredSize(new Dimension((int)top.getPreferredSize().getWidth() / 2 - 5, (int)top.getPreferredSize().getHeight() / 4));
		nextMonth.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calendarButtons[cal.firstDayOfWeek() + 5 + day].setBackground(null);
				cal.dayOrMonthButtonUpdate(0, 1);
			}	
		});
		top.add(nextMonth);
		
		//Display date above the calendar
		date.setPreferredSize(new Dimension((int)bottom.getPreferredSize().getWidth()  - 50, (int)bottom.getPreferredSize().getHeight() / 11 ));
		date.setEditable(false);
		date.setHorizontalAlignment(JTextField.CENTER);
		bottom.add(date);
		
		//Define the wide and height of the 49 buttons
		final int BWIDTH = (int)bottom.getPreferredSize().getWidth() / 8;
		final int BHEIGHT = (int)bottom.getPreferredSize().getHeight() / 11;

		//When user clicks on the calendar
		ActionListener click = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Highlight days that has events
				if(cal.dateHasEvents((cal.getMonth() + 1) + "/" + cal.getDay() + "/" + cal.getYear())) {
					calendarButtons[cal.firstDayOfWeek() + 5 + day].setBackground(Color.PINK);
				}
				//Reset the highlight after moving to another day
				else {
					calendarButtons[cal.firstDayOfWeek() + 5 + day].setBackground(null);
				}
				//Gets the user clicked button
				JButton getButton = (JButton)(e.getSource());
				cal.updateDate(getButton.getText());
			}
		};
		
		String [] daysOfWeek = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};
		
		//Initialize and updates the buttons
		for(int i = 0; i < calendarButtons.length; i++) {
			//For the first 7 buttons
			//Set them to the day of week
			//Not click able
			if (i < 7) {
				calendarButtons[i] = new JButton(daysOfWeek[i]);
			}
			//For the rest of the buttons
			//Set them to days of month
			//Click able
			else {
				calendarButtons[i] = new JButton();
				calendarButtons[i].addActionListener(click);
			}
			calendarButtons[i].setPreferredSize(new Dimension(BWIDTH, BHEIGHT));
			bottom.add(calendarButtons[i]);
		}
		update();
		//Highlight selected day button
		calendarButtons[today].setBackground(Color.GREEN);
	}
	
	/**
	 * Update the calendar on user inputs
	 */
	public void update() {
		int firstDayOfWeek = cal.firstDayOfWeek() + 6;
		int lastDayOfMonth = cal.getLastDayOfMonth();
		//Update the date above calendar
		date.setText(months[cal.getMonth()].toString() + ' ' + cal.getYear());
		//Defines where the first day of calendar  will begin
		//If user selects a different month
		for(int i = 7; i < firstDayOfWeek; i++) {
			calendarButtons[i].setText(null);
		}
		for(int i = firstDayOfWeek + lastDayOfMonth; i < calendarButtons.length; i++) {
			calendarButtons[i].setText(null);
		}
		for(int i = 0; i < lastDayOfMonth; i++) {
			if(cal.dateHasEvents((cal.getMonth() + 1) + "/" + Integer.toString(i + 1) + "/" + cal.getYear())) {
				calendarButtons[cal.firstDayOfWeek() + 5 + i + 1].setBackground(Color.PINK);
			}
			else {
				calendarButtons[cal.firstDayOfWeek() + 5 + i + 1].setBackground(null);
			}
			calendarButtons[firstDayOfWeek].setText(Integer.toString(i + 1));
			firstDayOfWeek++;
		}
	}

	/**
	 * Respond to user inputs
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		if(month != cal.getMonth()) {
			month = cal.getMonth();
			cal.getYear();
			update();
		}
		day = cal.getDay();
		//Highlight selected day button
		calendarButtons[cal.firstDayOfWeek() + 5 + day].setBackground(Color.GREEN);
 	}
}
