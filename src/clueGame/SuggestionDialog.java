package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
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
		
		this.board = game.getBoard();
		
		panel = initBox();
		
		initRoomField(room);
		
		personBox = initPersonBox(personCards);
		
		weaponBox = initWeaponBox(weaponCards);
		
		roomCard = room;
		
		createSaveButton();
		
		createCancelButton();
		
		setAlwaysOnTop(true);
		
		createCloseListener();
		
		createFocusListener();
		
		game.getControlPanel().disableButtons();
	}


	private void createFocusListener() {
		addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				game.setSuggestionDialogIsOpen(false);
				game.getControlPanel().enableButtons();
				dispose();
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}


	private void createCloseListener() {
		this.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				game.setSuggestionDialogIsOpen(false);
				game.getControlPanel().enableButtons();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
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
				game.setSuggestionDialogIsOpen(false);
				game.getControlPanel().enableButtons();
				
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
				
				findPlayerFromCard(personCard).setLocation(board.getCell(board.getCurrentPlayer().getLocation()));
				findPlayerFromCard(personCard).setTeleported(true);
				
				game.getControlPanel().setGuess(personCard + " in " + roomCard + " with the " + weaponCard);
				
				Object[] cardPlayer = board.checkSuggestion(new Solution(personCard, roomCard, weaponCard));
				
				Card solutionCard = (Card)cardPlayer[0];
				Player solutionPlayer = (Player)cardPlayer[1];
				
				if (solutionCard != null) {
					JOptionPane.showMessageDialog(panel, solutionPlayer.getName() +  " Showed you " + solutionCard.toString());
					game.getCardsPanel().addSeenCard(solutionCard, solutionPlayer);
					game.getControlPanel().setGuessResult(solutionPlayer.getName() + " showed you " + solutionCard);
					
				} else {
					JOptionPane.showMessageDialog(panel, "No one could disprove you!");
					game.getControlPanel().setGuessResult("No one could disprove you");
				}
				
				board.setHasSuggested(true);
				board.setHasMoved(true);
				
				board.repaint();
				
				game.getControlPanel().enableButtons();
				
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
		setSize(200,150);
		
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
	
	private Player findPlayerFromCard(Card card) {
		for (Entry<String, Card> e : board.getCardMap().entrySet()) {
			if (board.getCardMap().get(e.getKey()) == card) {
				for (Player p : board.getPlayerList()) {
					if (p.getName() == e.getKey()) {
						return p;
					}
				}
			}
		}
		
		return null;
	}

}
