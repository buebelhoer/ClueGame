package clueGame;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AccusationDialog extends SuggestionDialog {

	JComboBox<Card> roomBox;
	public AccusationDialog(ClueGame game, ArrayList<Card> roomCards, ArrayList<Card> personCards,
			ArrayList<Card> weaponCards) {
		super();

		this.game = game;
		board = game.getBoard();
		
		panel = initBox();
		
		roomBox = initRoomBox(roomCards);
		
		personBox = initPersonBox(personCards);
		
		weaponBox = initWeaponBox(weaponCards);
		
		
		createSaveButton();
		
		createCancelButton();

	}

	
	

/**
	 * @param roomCards
	 * @param panel
	 * @return
	 */
	protected JComboBox<Card> initRoomBox(ArrayList<Card> roomCards) {
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
		return roomBox;
	}




@Override
protected void createSaveButton() {
	//creates save button
	JButton saveButton = new JButton("Save");
	saveButton.setVisible(true);
	
	ActionListener saveListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			roomCard = (Card)roomBox.getSelectedItem();
			personCard = (Card)personBox.getSelectedItem();
			weaponCard = (Card)weaponBox.getSelectedItem();
			
			game.checkAccusation(new Solution(personCard, roomCard, weaponCard));
			
			dispose();
			
			
		}
	};
	
	saveButton.addActionListener(saveListener);
	panel.add(saveButton);
	
}
	
	
}

