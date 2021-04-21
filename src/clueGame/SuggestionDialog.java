package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SuggestionDialog extends JDialog {

	public SuggestionDialog(ArrayList<Card> personCards, ArrayList<Card> roomCards, ArrayList<Card> weaponCards) {
		super();
		
		//inits the dialoge box
		setVisible(true);
		setSize(400,400);
		
		//creates the panel and adds it to the dialog box
		JPanel panel = new JPanel;
		add(panel);
		
		panel.setLayout(new GridLayout(4, 2));
		
		//creates and adds the room label
		JTextField room = new JTextField("Room");
		room.setEditable(false);
		panel.add(room);
		
		//
		JComboBox<Card> roomBox = new JComboBox<>();
		for (Card c : roomCards) {
			roomBox.addItem(c);
		}
	}
	
}
