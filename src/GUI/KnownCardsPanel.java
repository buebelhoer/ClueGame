package GUI;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;

public class KnownCardsPanel extends JPanel {
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public KnownCardsPanel(ArrayList<Card> hand, HashMap<Player, ArrayList<Card>> seenCards)  {
		setLayout(new GridLayout(3, 1));
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Known Cards"));
		
		CardPanel peoplePanel = new CardPanel("People");
		CardPanel roomsPanel = new CardPanel("Rooms");
		CardPanel weaponsPanel = new CardPanel("Weapons");
		
		populateCardPanels(hand, seenCards, peoplePanel, roomsPanel, weaponsPanel);
		
		add(peoplePanel);
		add(roomsPanel);
		add(weaponsPanel);
	}

	private void populateCardPanels(ArrayList<Card> hand, HashMap<Player, ArrayList<Card>> seenCards, CardPanel peoplePanel,
			CardPanel roomsPanel, CardPanel weaponsPanel) {
		for (Card c : hand) {
			switch (c.getCardType()) {
			case PERSON:
				peoplePanel.addHandCard(c.getCardName());
				break;
			case ROOM:
				roomsPanel.addHandCard(c.getCardName());
				break;
			case WEAPON:
				weaponsPanel.addHandCard(c.getCardName());
				break;
			}
		}
		
		for (Entry<Player, ArrayList<Card>> e : seenCards.entrySet()) {
			for (Card c : e.getValue()) {
				switch (c.getCardType()) {
				case PERSON:
					peoplePanel.addSeenCard(e.getKey().getColor(), c.getCardName());
					break;
				case ROOM:
					roomsPanel.addSeenCard(e.getKey().getColor(), c.getCardName());
					break;
				case WEAPON:
					weaponsPanel.addSeenCard(e.getKey().getColor(), c.getCardName());
					break;
				}
			}
		}
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
		public CardTypePanel(String name) {
			super();
			setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), name));			
			setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), name));
			setLayout(new GridLayout(2, 1));
			
			JPanel handPanel = new JPanel();
			handPanel.setLayout(new GridLayout(2,1));
			
			handPanel.add(new JTextField("In Hand:"));
		}
	}
	
	//class that forms a panel and shows all the cards in a particular category(Type and seen status)
	private class CardPanel extends JPanel {
		
	}
	
	
	
	
}
