package clueGame;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.WindowEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

	//debug flag
	public static Boolean DEBUG = false;
	
	//optional flag for computer only simulation
	public  static Boolean COMPUTERS_ONLY = false;
	// how many turns the turns per game should simulate
	public  static int MAX_TURNS = 10000;
	//how many turns the games should simulate
	private static int NUMBER_OF_GAMES = 1;
	private static int gameNumber;
	
	private static Boolean SUPERSPEED = false;
	
	public  static boolean LOG_ROOMS = false;
	private static String LOG_FILE = "roomlogs.csv";
	private static FileWriter fileWriter;
	private static PrintWriter printWriter;
	
	private boolean suggestionDialogIsOpen = false;	

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
		
		if (COMPUTERS_ONLY) {

			JOptionPane.showMessageDialog(this, "You are Specting Computer Players for " + NUMBER_OF_GAMES + " Games!");

			controlPanel.disableButtons();
			board.disableMouseInput();
			moveComputerPlayer();
			
			while (true) {
				if (!SUPERSPEED) {
					try {
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
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
		
		if (p.getTeleported()) {
			if (p instanceof HumanPlayer) {
				SuggestionDialog suggestionDialog = new SuggestionDialog(this, board.getCardMap().get(board.getCell(board.getCurrentPlayer().getLocation()).getRoom().getName()), board.getPersonCards(), board.getWeaponCards());
			} else {
				Solution attempedSolution = ((ComputerPlayer)board.getCurrentPlayer()).createSuggestion(board.getCardMap().get(board.getCell(board.getCurrentPlayer().getLocation()).getRoom().getName()));
//				System.out.println(attempedSolution);
				Object tuple[] = board.checkSuggestion(attempedSolution);
				board.setHasSuggested(true);
				
				Card disprovedCard = (Card) tuple[0];
				Player disprovingPlayer = (Player) tuple[1];
				if (disprovedCard != null) {
					board.getCurrentPlayer().getSeenCards().add(disprovedCard);
					ArrayList<Card> cardList = board.getCurrentPlayer().getRevealedCards().get(disprovingPlayer);
					if (cardList == null) {
						cardList = new ArrayList<Card>();
					}
					cardList.add(disprovedCard);
					
					
				} 
				
				findPlayerFromCard(attempedSolution.getPerson()).setLocation(board.getCell(board.getCurrentPlayer().getLocation()));
				findPlayerFromCard(attempedSolution.getPerson()).setTeleported(true);
				
			}
			p.setTeleported(false);
		}

		board.repaint();
	}
	
	

	public KnownCardsPanel getCardsPanel() {
		return cardsPanel;
	}



	public GameControlPanel getControlPanel() {
		return controlPanel;
	}



	public void nextTurn() {
		if(!suggestionDialogIsOpen && board.hasMoved() && !board.hasSuggested() && board.getCell(board.getCurrentPlayer().getLocation()).isRoom() && board.getCurrentPlayer() instanceof HumanPlayer) {
			suggestionDialogIsOpen = true;
			SuggestionDialog suggestionDialog = new SuggestionDialog(this, board.getCardMap().get(board.getCell(board.getCurrentPlayer().getLocation()).getRoom().getName()), board.getPersonCards(), board.getWeaponCards());
			return;
			
		}

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
		
		if (COMPUTERS_ONLY) {
			cardsPanel = new KnownCardsPanel(board.getCurrentPlayer().getHand(), board.getCurrentPlayer().getRevealedCards());
		}
		//if computer player, move
		if (board.getCurrentPlayer() instanceof ComputerPlayer) {
			
			
			
			if (((ComputerPlayer)board.getCurrentPlayer()).accusationReady()) {
				Solution accusation = ((ComputerPlayer)board.getCurrentPlayer()).generateAccusation();
				checkAccusation(accusation);
			}
			
			moveComputerPlayer();
			
			if (board.getCell(board.getCurrentPlayer().getLocation()).isRoom()) {
				
//				System.out.println("Next PLayer: " + board.getPlayerList().get((board.getPlayerList().indexOf(board.getCurrentPlayer()) + 1) % board.getPlayerCount()).getHand());
				
				Solution attempedSolution = ((ComputerPlayer)board.getCurrentPlayer()).createSuggestion(board.getCardMap().get(board.getCell(board.getCurrentPlayer().getLocation()).getRoom().getName()));
//				System.out.println(attempedSolution);
				Object tuple[] = board.checkSuggestion(attempedSolution);
				board.setHasSuggested(true);
				
				Card disprovedCard = (Card) tuple[0];
				Player disprovingPlayer = (Player) tuple[1];
				if (disprovedCard != null) {
					board.getCurrentPlayer().getSeenCards().add(disprovedCard);
					ArrayList<Card> cardList = board.getCurrentPlayer().getRevealedCards().get(disprovingPlayer);
					if (cardList == null) {
						cardList = new ArrayList<Card>();
					}
					cardList.add(disprovedCard);
					
				} 
				
				findPlayerFromCard(attempedSolution.getPerson()).setLocation(board.getCell(board.getCurrentPlayer().getLocation()));
				findPlayerFromCard(attempedSolution.getPerson()).setTeleported(true);
				
				
				if (!SUPERSPEED) {
					JOptionPane.showMessageDialog(this, board.getCurrentPlayer().getName() + " made the suggestion: " + attempedSolution.getPerson() + " in " + attempedSolution.getRoom() + " with " + attempedSolution.getWeapon() + ". It was disproved by " + ((Player)tuple[1]).getName());
				}
			}

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
		SuggestionDialog accusationDialog = new AccusationDialog(this, board.getRoomCards(), board.getPersonCards(), board.getWeaponCards());
	}

	public Board getBoard() {
		return board;
	}
	
	public void checkAccusation(Solution solution) {
		if(board.checkAccusation(solution)) {
			JOptionPane.showMessageDialog(this, board.getCurrentPlayer().getName() + " has won! The solution was " + solution.getPerson() + " in the " + solution.getRoom() + " with the " + solution.getWeapon() );
			
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		} else {
			if (board.getCurrentPlayer() instanceof HumanPlayer ) {
				board.setHasMoved(true);
				board.getTargets().clear();
				board.repaint();
			}
			board.getCurrentPlayer().eliminate();
			JOptionPane.showMessageDialog(this, board.getCurrentPlayer().getName() + " accused incorrectly and has been eliminated! Their guess was " + solution.getPerson() + " in the " + solution.getRoom() + " with the " + solution.getWeapon() );
		}
	}
	
	void setSuggestionDialogIsOpen(boolean flag) {
		suggestionDialogIsOpen = flag;
	}

	public static void main(String[] args) {
		
		for (int i = 0; i < args.length; i++) {
			switch(args[i]) {
			case "DEBUG":
				ClueGame.DEBUG = true;
				break;
			case "LOG":
				ClueGame.LOG_ROOMS = true;
				LOG_FILE = args[i+1];
				i++;
				break;
			case "SIMULATE":
				ClueGame.COMPUTERS_ONLY = true;
				MAX_TURNS = Integer.parseInt(args[i+1]);
				i++;
				break;
			case "SUPERSPEED":
				SUPERSPEED = true;
				break;
			}
		}
		
		if(DEBUG) {
			Solution.DEBUG = true;
		}
		
		if (LOG_ROOMS) {
			try {
				fileWriter = new FileWriter(LOG_FILE);
				printWriter = new PrintWriter(fileWriter);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			ClueGame frame = new ClueGame("Mines Mystery");  // create the frame 

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
