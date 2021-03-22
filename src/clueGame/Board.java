//Authors: Brendan Uebelhoer, Ben Morgan
package clueGame;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Board {

	// number of rows in the board
	private int numRows;

	// number of column in the board
	private int numCols;

	//stores the board itself
	private BoardCell[][] board;

	//stores cards changes as game progresses (i.e cards are drawn/removed from deck)
	private ArrayList<Card> gameCards;

	//stores categorized cards, will not change as game progress, may prove useful later, may not
	private ArrayList<Card> playerCards;
	private ArrayList<Card> roomCards;
	private ArrayList<Card> weaponCards;

	//stores game solution
	private Solution solution;

	//stores the cells visited. used in target finding algorithm
	private Set<BoardCell> visited;

	//stores the movable cells
	private Set<BoardCell> targets;

	//maps the character symbol of a room to the room object itself
	private Map<Character,Room> roomMap;

	//strings that hold the filename of the config files.
	private String layoutConfigFile, setupConfigFile;

	//list of players
	private ArrayList<Player> playerList;

	//number of players in the game
	private int playerCount;
	
	//random number generator used in the game
	private Random random;

	//specific instance of the board
	private static Board instance = new Board();

	// constructor is private to ensure only one can be created
	private Board() {
		super();
	}

	// this method returns the only Board
	public static Board getInstance() {
		return instance;
	}

	//initialize board
	public void initialize() {
		// BadConfigException indicates invalid format in the config files
		try {
			//loads the setup file
			loadSetupConfig();
			//loads the config file
			loadLayoutConfig();
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}

		// allocates the visited and target sets
		visited = new HashSet<>();
		targets = new HashSet<>();
		
		random = new Random(System.currentTimeMillis());

		//generates the list of adjacencies for each cell
		generateAdjacencies();
		
		generateSolution();
		
		Collections.shuffle(gameCards);
		dealCards();
	}
	
	private void dealCards() {
		int player = 0;
		for (Card c : gameCards) {
			playerList.get(player).addCard(c);
			
			player += 1;
			player = player % playerList.size();
		}
	}

	//Creates a map which stores what cells are adjacent to each other
	private void generateAdjacencies() {    
		//adds doors and secret passages to internal list inside room
		generateExits();
		for (int row = 0; row < numRows; row++) {
			for (int column = 0; column < numCols; column++) {

				//logic makes sure it doesn't add a cell that is off the board
				if (checkCell(row - 1, column)) {
					board[row][column].addAdjacency(board[row-1][column]);
				}

				if (checkCell(row + 1, column)) {
					board[row][column].addAdjacency(board[row+1][column]);
				}

				if (checkCell(row, column - 1)) {
					board[row][column].addAdjacency(board[row][column-1]);
				}

				if (checkCell(row, column + 1)) {
					board[row][column].addAdjacency(board[row][column+1]);
				}

				if (board[row][column].isDoorway()) {
					board[row][column].addAdjacency(getDoorDest(row, column));
				}
				if (board[row][column].isSecretPassage()) {
					board[row][column].addAdjacency(getSecretPassageDest(row, column));
				}
				if (board[row][column].isRoomCenter()) {
					//adds each doorway cell to the adj
					for (BoardCell c :board[row][column].getRoom().getExits()) {
						board[row][column].addAdjacency(c);
					}
				}
			}
		}

	}

	//fills the set of doorways to each room
	private void generateExits() {
		for (int row = 0; row < numRows; row++) {
			for (int column = 0; column < numCols; column++) {
				if (board[row][column].isDoorway()) {//checks if the cell is a doorway
					try {
						getDoorDest(row, column).getRoom().addExit(board[row][column]); //adds cell to rooms exits
					} catch (NullPointerException e) {
						System.out.println(e);
					}
				}
				if (board[row][column].isSecretPassage()) { //checks if a cell is a secret passage
					board[row][column].getRoom().addExit(getSecretPassageDest(row, column)); //adds cell to rooms exits
				}
			}
		}
	}

	//returns the center cell of the room that the secret passage links to
	private BoardCell getSecretPassageDest(int row, int column) {
		BoardCell cell = board[row][column];
		Room passageRoom = roomMap.get(cell.getSecretPassage());
		return passageRoom.getCenterCell();
	}

	//returns the center cell of the room that the door leads to
	//calling this on a cell that is not a door will return null
	private BoardCell getDoorDest(int row, int column) {
		//default initializes to UP direction to be overwritten later if needed
		BoardCell cell = board[row-1][column];

		//gets cell in door direction to get room from
		switch(board[row][column].getDoorDirection()) {
		case UP:
			//this is the default case, so nothing is done
			break;
		case DOWN:
			cell = board[row+1][column];
			break;
		case LEFT:
			cell = board[row][column-1];
			break;
		case RIGHT:
			cell = board[row][column+1];
			break;
		case NONE:
			return null;
		}

		//returns destination cell
		return cell.getRoom().getCenterCell();
	}

	//checks if a cell is a valid cell to add to the adjacency list
	private boolean checkCell(int row, int column) {
		//if the row is within the board
		if(row < 0 || row >= numRows) {
			return false;
		}
		//if the col is within the board
		if (column < 0 || column >= numCols) {
			return false;
		}
		//if the cell is an unused cell
		if (checkUnused(row,column)) {
			return false;
		}
		//if the cell is a room
		if (board[row][column].isRoom()) {
			return false;
		}
		//if none of the previous conditions are true, the cell is assumed to be valid
		return true;
	}

	//returns true if the cell at the cords is an unused cell
	private boolean checkUnused(int row, int column) {
		return board[row][column].getRoom() == roomMap.get('X');
	}

	//calculates valid targets to move to from the given location
	public void calcTargets( BoardCell startCell, int pathlength) { //fills targets with all possible moves
		targets.clear();
		//first run done here to avoid issues when start cell is a room
		visited.add(startCell);
		for (BoardCell c: startCell.getAdjList()) {
			calcTargetsRecursive(c, pathlength - 1);
		}
		visited.remove(startCell); 
	}


	/**
	 * @param currentCell the cell that the path is generated from
	 * @param pathlength the number of moves remaining to the path
	 */
	private void calcTargetsRecursive(BoardCell currentCell, int pathlength) {

		//Base Case
		//if cell is occupied, cannot move to it
		if (currentCell.isOccupied() && !currentCell.isRoomCenter()) { 
			return;
		}
		//Base Case
		//if cell is already visited, cannot move to it
		if (visited.contains(currentCell)) { 
			return;
		}
		//Base Case
		// no moves left, add current cell to targets
		if(pathlength == 0) { 
			targets.add(currentCell);	

			//Base Case
			// if given cell is a room, all moves used up, and added to targets
		} else if (currentCell.isRoom()) { 
			targets.add(currentCell);
			//Recursive Case
		} else {
			// add cell to visited
			visited.add(currentCell);

			//recursively calls calcTargets() on every adjacent cell
			for (BoardCell c: currentCell.getAdjList()) {
				calcTargetsRecursive(c, pathlength - 1);
			}
			// removes cell from visited after all paths forward have been explore
			visited.remove(currentCell); 
		}
	}
	/*
	 * loads in each card for the game.
	 * cards are formatted with the type first, then the name, then the symbol
	 * should be a stardard .txt file
	 * comments should being with "//"
	 */
	public void loadSetupConfig() throws BadConfigFormatException {
		// allocates the map. uses HashMap as order doesn't matter 
		roomMap = new HashMap<>();
		playerList = new ArrayList<>();
		gameCards = new ArrayList<>();
		playerCards = new ArrayList<>();
		weaponCards = new ArrayList<>();
		roomCards = new ArrayList<>();

		playerCount = 0;
		try {
			//creates a scanner to read from the config file
			FileReader reader = new FileReader(setupConfigFile);
			Scanner scanner = new Scanner(reader);


			while (scanner.hasNextLine()) {
				String data = scanner.nextLine(); // first element of a line should be what type it is.

				//Checks if line is empty
				if(data.isBlank()) continue;


				//checks if line is a comment
				if (data.charAt(0) == '/' && data.charAt(1) == '/') {
					continue;
				}

				//parses out card type
				int commaIndex = data.indexOf(',');
				String dataType = data.substring(0, commaIndex);
				data = data.substring(commaIndex+1);
				data = data.stripLeading();

				switch (dataType) {
				case "Room": //rooms and spaces are treated the same

					addRoom(data);
					break;
				case "Space":
					addSpace(data);
					break;
				case "Weapon":
					addWeapon(data);
					break;
				case "Player":
					addPlayer(data);
					break;
				default:
					//invalid card type
					throw new BadConfigFormatException("Invalid card type: " + dataType);
				}


			}
		}
		catch (FileNotFoundException e) {
			//lol rip
			System.out.println(e);
		}

	}

	//helper function for when load setup determines it is adding a room card
	private void addSpace(String data) {
		int commaIndex;
		//parses out space name
		commaIndex = data.indexOf(',');
		String roomName = data.substring(0,commaIndex);
		//parses out room symbol
		data = data.substring(commaIndex+1);
		data = data.stripLeading();
		char roomSymbol = data.charAt(0);
		//adds room to map of all rooms
		roomMap.put(roomSymbol, new Room(roomName));
	}

	//helper function for when load setup determines it is adding a room card
	private void addRoom(String data) {
		int commaIndex;
		//parses out room name
		commaIndex = data.indexOf(',');
		String roomName = data.substring(0,commaIndex);
		//parses out room symbol
		data = data.substring(commaIndex+1);
		data = data.stripLeading();
		char roomSymbol = data.charAt(0);
		//adds room to map of all rooms
		roomMap.put(roomSymbol, new Room(roomName));
		Card card = new Card(roomName, CardType.ROOM);
		gameCards.add(card);
		roomCards.add(card);
	}

	//helper function for when load setup determines it is adding a weapon card
	private void addWeapon(String data) {
		int commaIndex;
		Card card = new Card(data, CardType.WEAPON);
		gameCards.add(card);
		weaponCards.add(card);
	}

	//helper function for when load setup determines it is adding a player card
	private void addPlayer(String data) throws BadConfigFormatException {
		int commaIndex;
		//parses out player name
		commaIndex = data.indexOf(',');
		String playerName = data.substring(0,commaIndex);
		data = data.substring(commaIndex+1);
		data = data.stripLeading();

		//parses out player type
		commaIndex = data.indexOf(',');
		String playerType = data.substring(0,commaIndex);
		data = data.substring(commaIndex+1);
		data = data.stripLeading();

		//parses out player color
		String playerColor = data;

		// converts the string representing a color into a instance of Color
		Color color;
		switch (playerColor)
		{
		case "black":
			color = Color.black;
			break;
		case "blue":
			color = Color.blue;
			break;
		case "cyan":
			color = Color.cyan;
			break;
		case "darkGray":
			color = Color.darkGray;
			break;
		case "gray":
			color = Color.gray;
			break;
		case "green":
			color = Color.green;
			break;
		case "lightGray":
			color = Color.lightGray;
			break;
		case "magenta":
			color = Color.magenta;
			break;
		case "orange":
			color = Color.orange;
			break;
		case "pink":
			color = Color.pink;
			break;
		case "red":
			color = Color.red;
			break;
		case "white":
			color = Color.white;
			break;
		case "yellow":
			color = Color.yellow;
			break;
		default:
			throw new BadConfigFormatException("invlaid color: " + playerColor);

		}

		Player player;

		if (playerType.equals("Human")) {
			player = new HumanPlayer(playerName, color);
		} else {
			player = new ComputerPlayer(playerName, color);
		}
		playerList.add(player);

		Card card = new Card(playerName, CardType.PERSON);

		gameCards.add(card);
		playerCards.add(card);

		playerCount++;

	}

	/*takes layout file and imports the data it holds into the proper locations
	 *file should be a rectangular .csv of the board, where each element is 
	 *a 1 or two letter symbol. first letter is the room in that cell and the
	 *second letter any special part of the cell, such as secret passages, doors
	 *etc. if not proper formated, will throw exception
	 */
	public void loadLayoutConfig() throws BadConfigFormatException {	
		countRowsCols();
		generateBoard();

		try {
			//loads the layout file into a scanner to read
			FileReader reader = new FileReader(layoutConfigFile);
			Scanner scanner = new Scanner(reader);  

			String[] tokens;
			String token;
			for (int row = 0; row < numRows; row++) {
				tokens = scanner.nextLine().split(",");				

				//if there is a different number of elemenets than expected, throw an exception
				if (tokens.length != numCols) {
					throw new BadConfigFormatException("Invalid number of elements in a row. Expected: " + numCols + " Actual: " + tokens.length);
				}

				//splits the line into each individual value
				for (int column = 0; column < numCols; column++) {	

					token = tokens[column];

					if (roomMap.get(token.charAt(0)) != null) {
						//sets the room of the cell based on the char
						board[row][column].setRoom(roomMap.get(token.charAt(0)));
					} else {
						//if the character is not associated with a room, thorw exception
						throw new BadConfigFormatException("Invalid Character in layout: " + token.charAt(0));
					}
					//checks for doorway, label cell, center cell, secret passage
					parseSecondCharacter(token, row, column);
				}
			}


		} catch (FileNotFoundException e) {
			System.out.println(e);
		} 
	}

	//checks for doorway, label cell, center cell, secret passage
	/**
	 * @param token String of text, where first char is room symbol and second char is special symbol
	 * @param row Row of the cell corresponding to the token
	 * @param column Col of the cell corresponding to the token
	 * @throws BadConfigFormatException if the second character is not valid, or does not exist
	 */
	private void parseSecondCharacter(String token, int row, int column) throws BadConfigFormatException {
		if (token.length() > 1) { //checks if there is a second char
			switch (token.charAt(1)) { //acts according the what the char is
			case '<':
				board[row][column].setDoorDirection(DoorDirection.LEFT);
				break;
			case '>': 
				board[row][column].setDoorDirection(DoorDirection.RIGHT);
				break;
			case 'v': 
				board[row][column].setDoorDirection(DoorDirection.DOWN);
				break;
			case '^': 
				board[row][column].setDoorDirection(DoorDirection.UP);
				break;
			case '#': //label cell
				board[row][column].setLabel(true);
				roomMap.get(token.charAt(0)).setLabelCell(board[row][column]);
				break;
			case '*': //center cell
				board[row][column].setRoomCenter(true);
				roomMap.get(token.charAt(0)).setCenterCell(board[row][column]);
				break;
			default: //if not other, treat as secret passage
				if (roomMap.get(token.charAt(1)) != null) {
					board[row][column].setSecretPassage(token.charAt(1));
				} else {
					throw new BadConfigFormatException("Invalid second character: " + token.charAt(1) + " at " + row + " " + column);
				}
			}
		}
	}

	private void generateBoard() {
		// allocates the board
		board = new BoardCell[numRows][numCols];

		//allocates each cell within the board
		for (int row = 0; row < numRows; row++) {
			for (int column = 0; column < numCols; column++) {
				board[row][column] = new BoardCell(row, column);
			}
		}
	}

	private void countRowsCols() {
		// quickly scans the file in order 
		try {
			FileReader reader = new FileReader(layoutConfigFile);
			Scanner scanner = new Scanner(reader);

			//gets the first line of a file, then puts it into a string
			//array based on values separated by commas
			String[] tokens = scanner.nextLine().split(",");
			//sets the size of the board
			numCols = tokens.length;

			//counts the number of line in the layout file, and uses it to
			//set the number of rows in the board
			numRows = 1;
			while (scanner.hasNextLine()) {
				scanner.nextLine();
				numRows++;
			}

		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
	}
	
	
	private void generateSolution() {
		int roomIndex = random.nextInt();
		int weaponIndex = random.nextInt();
		int playerIndex = random.nextInt();
		
		solution = new Solution(playerCards.get(playerIndex), roomCards.get(roomIndex), weaponCards.get(weaponIndex));
		
		gameCards.remove(playerCards.get(playerIndex));
		gameCards.remove(roomCards.get(roomIndex));
		gameCards.remove(weaponCards.get(weaponIndex));
	}

	/*
	 * ALL CODE BENEATH THIS POINT SHOULD BE GETTER/SETTERS
	 */


	public Set<BoardCell> getTargets() {
		return targets;
	}

	public BoardCell getCell( int row, int col ) {
		return board[row][col];
	}

	public Room getRoom(BoardCell cell) {
		return cell.getRoom();
	}

	public Room getRoom(char c) {
		return roomMap.get(c);
	}

	public void setConfigFiles(String layoutFile, String setupFile) {
		setupConfigFile = "data/" + setupFile;
		layoutConfigFile = "data/" + layoutFile;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numCols;
	}

	public Set<BoardCell> getAdjList(int i, int j) {
		return board[i][j].getAdjList(); 
	}

	public ArrayList<Player> getPlayerList() {
		return playerList;
	}

	public int getPlayerCount() {
		return playerCount;
	}

	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}

	public ArrayList<Card> getGameCards() {
		return gameCards;
	}

	public void setGameCards(ArrayList<Card> gameCards) {
		this.gameCards = gameCards;
	}

	public ArrayList<Card> getPlayerCards() {
		return playerCards;
	}

	public ArrayList<Card> getRoomCards() {
		return roomCards;
	}

	public ArrayList<Card> getWeaponCards() {
		return weaponCards;
	}

	public Solution getSolution() {
		return solution;
	}
}
