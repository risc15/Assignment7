import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.*;
import java.util.HashMap;
import acm.graphics.GCompound;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

public class BoxDiagram extends GraphicsProgram {

	/*
	 * Constants:
	 */

	private static final double BOX_WIDTH = 120;
	private static final double BOX_HEIGHT = 50;

	/*
	 * Static variables:
	 */

	// Create a HashMap that stores a string being the name of the box, and the box
	// object:

	private static HashMap<String, GCompound> labledBoxes = new HashMap<String, GCompound>();

	/*
	 * Instance Variables:
	 */

	private JButton addButton;
	private JButton removeButton;
	private JButton clearButton;
	private JTextField inputField;
	private JLabel inputLabel;
	private GPoint lastLocation;
	private GObject activeObject;

	public void init() {

		// Add the label, text field, and buttons to the southern region of the window:

		add(inputLabel = new JLabel("Name"), SOUTH);
		add(inputField = new JTextField(30), SOUTH);
		add(addButton = new JButton("Add"), SOUTH);
		add(removeButton = new JButton("Remove"), SOUTH);
		add(clearButton = new JButton("Clear"), SOUTH);

		// Add mouse and action listeners:

		addMouseListeners();
		addActionListeners();

		// Add an action listener to the text field:

		inputField.addActionListener(this);

		System.out.println("Program initialized. Hash map is empty.");

	}

	public void actionPerformed(ActionEvent e) {

		// Action for the text field when the user presses enter:

		if (e.getSource() == inputField) {
			String boxName = inputField.getText();
			addBoxToHashMap(boxName, labledBoxes);

		}

		// Actions for the buttons being pressed:

		String cmd = e.getActionCommand();
		if (cmd.equals("Add")) {

			String boxName = inputField.getText();
			addBoxToHashMap(boxName, labledBoxes);

		} else if (cmd.equals("Remove")) {

			String boxName = inputField.getText();
			removeBoxFromHashMap(boxName, labledBoxes);

		} else if (cmd.equals("Clear")) {

			removeAllFromHashMap(labledBoxes);

		}

	}

	public void mousePressed(MouseEvent e) {
		// add mouse pressed methods
		System.out.println("Mouse clicked.");
		lastLocation = new GPoint(e.getPoint());
		activeObject = getElementAt(lastLocation);
	}

	public void mouseDragged(MouseEvent e) {
		// add mouse dragged methods
		//System.out.println("Mouse dragged.");
		if (activeObject != null) {
			activeObject.move(e.getX() - lastLocation.getX(), e.getY() - lastLocation.getY());
			lastLocation = new GPoint(e.getPoint());
		}

	}

	private void printHashMapSize(HashMap<String, GCompound> theHashMap) {
		System.out.println("Size = " + theHashMap.size());
	}

	private void addBoxToHashMap(String boxName, HashMap<String, GCompound> theHashMap) {

		if (!theHashMap.containsKey(boxName)) {
			GCompound newBox = new GCompound();
			GRect newBoxRectangle = new GRect(getWidth() / 2, getHeight() / 2, BOX_WIDTH, BOX_HEIGHT);
			GLabel newBoxLabel = new GLabel(boxName, newBoxRectangle.getX(), newBoxRectangle.getY());

			newBox.add(newBoxRectangle);
			newBox.add(newBoxLabel);
			
			newBoxLabel.move(BOX_WIDTH / 4, BOX_HEIGHT / 2 + 4);

			theHashMap.put(boxName, newBox);
			System.out.println("Box: " + boxName + " added.");
			printHashMapSize(theHashMap);

			add(newBox);
		}

	}

	private void removeBoxFromHashMap(String boxName, HashMap<String, GCompound> theHashMap) {

		if (theHashMap.containsKey(boxName)) {
			theHashMap.get(boxName).removeAll();
			theHashMap.remove(boxName);
			System.out.println("Box: " + boxName + " removed.");
			printHashMapSize(theHashMap);
		}

	}

	private void removeAllFromHashMap(HashMap<String, GCompound> theHashMap) {
		for (String key : theHashMap.keySet()) {
			theHashMap.get(key).removeAll();
		}

		theHashMap.clear();
		System.out.println("HashMap cleared.");
		printHashMapSize(theHashMap);
	}

}
