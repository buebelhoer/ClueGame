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
	
	Board board;
	ClueGame game;
	
	JTextField roomField;
	JComboBox<Card> personBox;
	JComboBox<Card> weaponBox;
	JPanel panel;
	
	
	public SuggestionDialog(ClueGame game, Card room, ArrayList<Card> personCards, ArrayList<Card> weaponCards) {
		super();
		
		this.game = game;
		board = game.getBoard();
		
		panel = initBox();
		
		personBox = initPersonBox(personCards);
		
		weaponBox = initWeaponBox(weaponCards);
		
		roomCard = room;
		
		initRoomField(room);
		
		createSaveButton();
		
		createCancelButton();
	}


	/**
	 * @param room
	 */
	private void initRoomField(Card room) {
		JTextField roomLabel = new JTextField("Room");
		roomLabel.setEditable(false);
		roomLabel.setSize(100, 20);
		panel.add(roomLabel);
		
		roomField = new JTextField(room.toString());
		roomField.setEditable(false);
		roomField.setSize(100, 20);
		panel.add(roomField);
	}


	public SuggestionDialog() {
		super();
	}


	/**
	 * @param panel
	 */
	protected void createCancelButton() {
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


	/**
	 * @param panel
	 * @param roomBox
	 * @param personBox
	 * @param weaponBox
	 */
	protected void createSaveButton() {
		//creates save button
		JButton saveButton = new JButton("Save");
		saveButton.setVisible(true);
		
		ActionListener saveListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				

				personCard = (Card)personBox.getSelectedItem();
				weaponCard = (Card)weaponBox.getSelectedItem();
				
				board.checkSuggestion(new Solution(personCard, roomCard, weaponCard));
				
				dispose();
				
				
			}
		};
		
		saveButton.addActionListener(saveListener);
		panel.add(saveButton);
	}


	/**
	 * @return
	 */
	protected JPanel initBox() {
		//inits the dialoge box
		setVisible(true);
		setSize(400,400);
		
		//creates the panel and adds it to the dialog box
		JPanel panel = new JPanel();
		add(panel);
		
		panel.setLayout(new GridLayout(4, 2));
		return panel;
	}


	/**
	 * @param weaponCards
	 * @param panel
	 * @return
	 */
	protected JComboBox<Card> initWeaponBox(ArrayList<Card> weaponCards) {
		JTextField weapon = new JTextField("Weapon");
		weapon.setEditable(false);
		panel.add(weapon);
		
		JComboBox<Card> weaponBox = new JComboBox<>();
		for (Card c : weaponCards) {
			weaponBox.addItem(c);
		}
		panel.add(weaponBox);
		return weaponBox;
	}


	/**
	 * @param personCards
	 * @param panel
	 * @return
	 */
	protected JComboBox<Card> initPersonBox(ArrayList<Card> personCards) {
		JTextField person = new JTextField("Person");
		person.setEditable(false);
		panel.add(person);
		
		JComboBox<Card> personBox = new JComboBox<>();
		for (Card c : personCards) {
			personBox.addItem(c);
		}
		panel.add(personBox);
		return personBox;
	}

}
