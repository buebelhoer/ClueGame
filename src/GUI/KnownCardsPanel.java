package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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
		
		CardTypePanel peoplePanel = new CardTypePanel("People");
		CardTypePanel roomsPanel = new CardTypePanel("Rooms");
		CardTypePanel weaponsPanel = new CardTypePanel("Weapons");
		
		populateCardPanels(hand, seenCards, peoplePanel, roomsPanel, weaponsPanel);
		
		add(peoplePanel);
		add(roomsPanel);
		add(weaponsPanel);
	}

	
	private void populateCardPanels(ArrayList<Card> hand, HashMap<Player, ArrayList<Card>> seenCards, CardTypePanel peoplePanel,
			CardTypePanel roomsPanel, CardTypePanel weaponsPanel) {
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
		
		ArrayList<Card> hand = new ArrayList<>();
		hand.add(new Card("handTestweapon", CardType.WEAPON));
		hand.add(new Card("handTestweapon2", CardType.WEAPON));
		
		HashMap<Player, ArrayList<Card>> seenCards = new HashMap<>();
		
		KnownCardsPanel panel = new KnownCardsPanel(hand, seenCards);  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(180,650);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
	}
	
	//class that forms the panel for each of the three types of cards
	private class CardTypePanel extends JPanel {
		
		private CardPanel handCardPanel;
		private CardPanel seenCardPanel;
		
		public CardTypePanel(String name) {
			super();
			setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), name));
			setLayout(new GridLayout(2, 1));
			
			//create a bold font
			Font font = this.getFont();
			font = font.deriveFont(Font.BOLD,font.getSize());
			
			
			//creats the panel for cards in the players hand
			JPanel handPanel = new JPanel();
			handPanel.setLayout(new GridLayout(2,1));
			
			//adds label of the hand panel
			handPanel.add(new JLabel("In Hand:"));
			
			//creates the cardpanel that stores the card in the hand
			handCardPanel = new CardPanel();
			handPanel.add(handCardPanel);
			System.out.println("hand panel added");
			
			JPanel seenPanel = new JPanel();
			seenPanel.setLayout(new GridLayout(2,1));
			
			//adds label of seen panel
			seenPanel.add(new JLabel("Seen:"));
			
			//creates the cardpanel that stores the cards that have been seen
			
			seenCardPanel = new CardPanel();
			seenPanel.add(handCardPanel);
			
			//adds the hand and seen panels to the overall panel
			add(handPanel);
			add(seenPanel);
			
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
		private JTextField noneCard;
		
		CardPanel() {
			super();
			System.out.println("card panel created");
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			noneCard = new JTextField("None");
			noneCard.setBorder(BorderFactory.createLineBorder(Color.black));
			noneCard.setBackground(Color.white);
			noneCard.setEditable(false);
			noneCard.setHorizontalAlignment(JTextField.CENTER);
			add(noneCard);
			
		}
		
		public void addCard(Color color, String cardName) {
			this.remove(noneCard);
			
			JTextField cardField = new JTextField(cardName);
			cardField.setBorder(BorderFactory.createLineBorder(Color.black));
			cardField.setBackground(color);
			cardField.setEditable(false);
			cardField.setHorizontalAlignment(JTextField.CENTER);
			add(cardField);
		}
	}
	
	
	
	
}
