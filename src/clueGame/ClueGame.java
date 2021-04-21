package clueGame;

import java.awt.BorderLayout;
import java.awt.Point;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ClueGame extends JFrame {
	private Board board;
	private Random rng;

	//optional flag for computer only simulation
	private final static Boolean COMPUTERS_ONLY = false;
	// how many turns the turns per game should simulate
	private final static int MAX_TURNS = 10000;
	//how many turns the games should simulate
	private final static int NUMBER_OF_GAME = 1;
	private static int gameNumber;
	
	private final static boolean LOG_ROOMS = false;
	private final static String LOG_FILE = "roomlogs.csv";
	private static FileWriter fileWriter;
	private static PrintWriter printWriter;
	

	private GameControlPanel controlPanel; 
	private KnownCardsPanel cardsPanel;
	public ClueGame(String title) {
		super(title);

		rng = new Random(System.currentTimeMillis());

		JPanel mainPanel = new JPanel();  // create the panel

		//calls standard setup functions
		board = Board.getInstance();
		if (COMPUTERS_ONLY) {
			board.setConfigFiles("ClueLayout.csv", "ClueSetupComputers.txt");
		} else {
			board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		}
		
		
		
		board.initialize(rng);

		//player for known cards
		ArrayList<Card> hand;
		HashMap<Player, ArrayList<Card>> seenCards;
		if (COMPUTERS_ONLY) {
			hand = new ArrayList<>();
			seenCards = new HashMap<Player, ArrayList<Card>>();
		} else {
			hand = board.getHumanPlayer().getHand();
			seenCards = board.getHumanPlayer().getRevealedCards();
		}


		cardsPanel = new KnownCardsPanel(hand, seenCards);
		controlPanel = new GameControlPanel(this);

		setCurrentPlayer(board.getPlayerList().get(0));

		//add components to parent panel
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(board, BorderLayout.CENTER);
		mainPanel.add(cardsPanel, BorderLayout.EAST);
		mainPanel.add(controlPanel, BorderLayout.PAGE_END);

		//prepare frame
		setSize(750, 200+650);
		setContentPane(mainPanel); // put the panel in the frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		setVisible(true); // make it visible
		if (gameNumber == 1) {
		JOptionPane.showMessageDialog(this, "You are Specting Computer Players for " + NUMBER_OF_GAME + " Games!");
		}
		if (COMPUTERS_ONLY) {
			controlPanel.disableButtons();
			board.disableMouseInput();
			moveComputerPlayer();
			
			while (true) {
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				nextTurn();
				if (controlPanel.getTurnNumber() >= MAX_TURNS) {
					break;
				}
			}
		} else {
			JOptionPane.showMessageDialog(this, "You are " + board.getHumanPlayer().getName() + ". \nCan you find the solution\nbefore the Computer players?");
		}
	}
	
	

	private void setCurrentPlayer(Player p) {
		int roll = rng.nextInt(6) + 1;

		//sets lower panel info on new player
		controlPanel.setTurn(p, roll);
		controlPanel.setGuess("I have no guess!");
		controlPanel.setGuessResult( "So you have nothing?");

		//set board new player and update gui
		board.calcTargets(board.getCell(p.getRow(), p.getColumn()), roll);
		board.setCurrentPlayer(p);
		board.setHasMoved(board.getTargets().isEmpty());
		board.setHasSuggested(false);

		board.repaint();
	}
	
	

	public void nextTurn() {

		if (!board.hasMoved()) {
			JOptionPane.showMessageDialog(this, "You have not moved yet!", "Cannot move to next player", JOptionPane.ERROR_MESSAGE);
			return;
		}

		//if its the first players turn, then increment the overall turn number
		if (board.getPlayerList().indexOf(board.getCurrentPlayer()) == board.getPlayerCount() - 1) {
			controlPanel.incrementTurnNumber();
		}
		
		if (LOG_ROOMS) {
			Point p = board.getCurrentPlayer().getLocation();
			if (board.getCell(p).isRoom()) {
				printWriter.print(board.getCell(p).getRoom().getRoomNumber() + ",");
			}
		}
		
		setCurrentPlayer(board.getNextPlayer());

		//if computer player, move
		if (board.getCurrentPlayer() instanceof ComputerPlayer) {
			moveComputerPlayer();
			
			if (board.getCell(board.getCurrentPlayer().getLocation()).isRoom()) {
				Solution attempedSolution = ((ComputerPlayer)board.getCurrentPlayer()).createSuggestion(board.getCardMap().get(board.getCell(board.getCurrentPlayer().getLocation()).getRoom().getName()));
				System.out.println(attempedSolution);
				Card disprovedCard = board.checkSuggestion(attempedSolution);			
				if (disprovedCard != null) {
					board.getCurrentPlayer().getSeenCards().add(disprovedCard);
				} 
				
				Card cardToTeleport = attempedSolution.getPerson();
				
				findPlayerFromCard(cardToTeleport).setLocation(board.getCell(board.getCurrentPlayer().getLocation()));
			}
			//TODO: DIALOG HERE
		}
	}
	
	private Player findPlayerWithCard(Card card) {
		for (Player p : board.getPlayerList()) {
			if (p.getHand().contains(card)) {
				return p;
			}
		}
		
		return null;
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

	/**
	 * grabs the current player, and has then select a target and moves them to it. will error if current player is not human
	 */
	private void moveComputerPlayer() {
		ComputerPlayer player = ((ComputerPlayer)board.getCurrentPlayer());
		BoardCell target = player.selectMove(board.getTargets());

		player.setLocation(target);
		board.setHasMoved(true);
	}
	
	private void makeSuggestion(Solution solution) {
		
	}
	
	public void makeAccusation() {
		SuggestionDialog accusationDialog = new SuggestionDialog(board, board.getRoomCards(), board.getPersonCards(), board.getWeaponCards());
	}

	public static void main(String[] args) {
		if (LOG_ROOMS) {
			try {
				fileWriter = new FileWriter(LOG_FILE);
				printWriter = new PrintWriter(fileWriter);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (COMPUTERS_ONLY) {
			for(gameNumber = 1; gameNumber <= NUMBER_OF_GAME; gameNumber++) {
				ClueGame frame = new ClueGame("Mines Mystery");  // create the frame 
				frame.setVisible(false);
			}
		} else {

			ClueGame frame = new ClueGame("Mines Mystery");  // create the frame 
		}
		if (LOG_ROOMS) {
			printWriter.close();
			try {
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
