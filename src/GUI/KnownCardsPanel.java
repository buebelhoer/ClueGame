package GUI;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import clueGame.Card;
import clueGame.ComputerPlayer;

public class KnownCardsPanel extends JPanel {
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public KnownCardsPanel(ArrayList<Card> hand, )  {
		setLayout(new GridLayout(3, 1));
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Known Cards"));
		
		CardTypePanel peoplePanel = new CardTypePanel("People");
		CardTypePanel roomsPanel = new CardTypePanel("Rooms");
		CardTypePanel weaponsPanel = new CardTypePanel("Weapons");
		
		populateCardPanels(hand)
	}
	
	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {		
		KnownCardsPanel panel = new KnownCardsPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
	}
	
	//class that forms the panel for each of the three types of cards
	private class CardTypePanel extends JPanel {
		
		CardPanel handCardPanel;
		CardPanel seenCardPanel;
		
		public CardTypePanel(String name) {
			super();
			setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), name));
			setLayout(new GridLayout(2, 1));
			
			//creats the panel for cards in the players hand
			JPanel handPanel = new JPanel();
			handPanel.setLayout(new GridLayout(2,1));
			
			//adds label of the hand panel
			handPanel.add(new JTextField("In Hand:"));
			
			//creates the cardpanel that stores the card in the hand
			handCardPanel = new CardPanel();
			handPanel.add(handCardPanel);
			
			JPanel seenPanel = new JPanel();
			seenPanel.setLayout(new GridLayout(2,1));
			
			//adds label of seen panel
			seenPanel.add(new JTextField("Seen:"));
			
			//creates the cardpanel that stores the cards that have been seen
			
			seenCardPanel = new CardPanel();
			seenPanel.add(handCardPanel);
			
			
		}
		
		public void addHandCard(String cardName) {
			handCardPanel.addCard(Color.white, cardName);
		}
		
		public void addSeenCard(Color color, String cardName) {
			seenCardPanel.addCard(color, cardName);
		}
	}
	
	//class that forms a panel and shows all the cards in a particular category(Type and seen status)
	private class CardPanel extends JPanel {
		CardPanel() {
			super();
		}
		
		public void addCard(Color color, String cardName) {
			JTextField cardField = new JTextField(cardName);
			cardField.setBorder(BorderFactory.createLineBorder(Color.black));
			cardField.setBackground(color);
			add(cardField);
		}
	}
	
	
	
	
}
