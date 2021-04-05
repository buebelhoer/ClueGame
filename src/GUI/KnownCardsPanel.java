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
	public KnownCardsPanel()  {
	
	}
	
	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {		
		KnownCardsPanel panel = new KnownCardsPanel();  // create the panel
		panel.setLayout(new GridLayout(3, 1));
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
	}
	
	//class that forms the panel for each of the three types of cards
	private class CardTypePanel extends JPanel {
		
		public CardPanel(String name) {
			super();
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
