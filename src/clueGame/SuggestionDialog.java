package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SuggestionDialog extends JDialog {

	Card personCard;
	Card roomCard;
	Card weaponCard;
	
	public SuggestionDialog(Board board, ArrayList<Card> roomCards, ArrayList<Card> personCards, ArrayList<Card> weaponCards) {
		super();
		
		//inits the dialoge box
		setVisible(true);
		setSize(400,400);
		
		//creates the panel and adds it to the dialog box
		JPanel panel = new JPanel();
		add(panel);
		
		panel.setLayout(new GridLayout(4, 2));
		
		//creates and adds the room label
		JTextField room = new JTextField("Room");
		room.setEditable(false);
		room.setSize(100, 20);
		panel.add(room);
		
		//
		JComboBox<Card> roomBox = new JComboBox<>();
		roomBox.setSize(100, 20);
		for (Card c : roomCards) {
			roomBox.addItem(c);
		}
		panel.add(roomBox);
		
		JTextField person = new JTextField("Person");
		person.setEditable(false);
		panel.add(person);
		
		JComboBox<Card> personBox = new JComboBox<>();
		for (Card c : personCards) {
			personBox.addItem(c);
		}
		panel.add(personBox);
		
		JTextField weapon = new JTextField("Weapon");
		weapon.setEditable(false);
		panel.add(weapon);
		
		JComboBox<Card> weaponBox = new JComboBox<>();
		for (Card c : weaponCards) {
			weaponBox.addItem(c);
		}
		panel.add(weaponBox);
		
		JButton saveButton = new JButton("Save");
		saveButton.setVisible(true);
		
		ActionListener saveListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				roomCard = (Card)roomBox.getSelectedItem();
				personCard = (Card)personBox.getSelectedItem();
				weaponCard = (Card)weaponBox.getSelectedItem();
				
				dispose();
				
				
			}
		};
		
		saveButton.addActionListener(saveListener);
		panel.add(saveButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setVisible(true);
		
		ActionListener cancelListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				
				
			}
		};
		
		cancelButton.addActionListener(cancelListener);
		panel.add(cancelButton);
	}

	
	public Solution getSuggestion() {
		return new Solution(personCard, roomCard, weaponCard);
	}
}
