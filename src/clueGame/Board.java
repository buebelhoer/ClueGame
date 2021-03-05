//Authors: Brendan Uebelhoer, Ben Morgan
package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Board {

	// number of rows in the board
	private int numRows;
	
	 // number of column in the board
	private int numCols;
	
	//stores the cells visited. used in targetfinding algorithm
	private Set<BoardCell> visited;

	//stores the board itself
	private BoardCell[][] board;
	
	//stores the movable cells
	private Set<BoardCell> targets;

	//maps the character symbol of a room to the room object itself
	private Map<Character,Room> roomMap;

	//strings that hold the filename of the config files.
	private String layoutConfigFile, setupConfigFile;

	//specific instance of tjhe board
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
		
		// allocates the board
		board = new BoardCell[numRows][numCols];
		
		//allocates each cell within the board
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				board[i][j] = new BoardCell(i, j);
			}
		}
		// allocates the map. uses HashMap as order doesnt matter 
		roomMap = new HashMap<Character, Room>();
		

		// BadCOnfigException indicates invalid format in the config files
		try {
			//loads the setup file
			loadSetupConfig();
			//loads the config file
			loadLayoutConfig();
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
		
		// allocates the visited and target sets
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		
		//generates the list of adjecencies for each cell
		generateAdjacecies();


	}

	 //Creates a map which stores what cells are adjacent to each other
	private void generateAdjacecies() {    
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				
				//logic makes sure it doesnt add a cell that is off the board
				
				if (i > 0) {
					board[i][j].addAdjacency(board[i-1][j]);
				}

				if (i < numRows - 1) {
					board[i][j].addAdjacency(board[i+1][j]);
				}

				if (j > 0) {
					board[i][j].addAdjacency(board[i][j-1]);
				}

				if (j < numCols - 1) {
					board[i][j].addAdjacency(board[i][j+1]);
				}
			}
		}
	}
	
	//calculates valid targets to move to from the given location
	public void calcTargets( BoardCell startCell, int pathlength) { //fills targets with all possible moves
		
		//Base Case
		//if cell is occupied, cannot move to it
		if (startCell.isOccupied()) { 
			return;
		}
		//Base Case
		//if cell is already visited, cannot move to it
		if (visited.contains(startCell)) { 
			return;
		}
		
		//Base Case
		// no moves left, add current cell to targets
		if(pathlength == 0) { 
			targets.add(startCell);	
		
		//Base Case
		// if given cell is a room, all moves used up, and added to targets
		} else if (startCell.isRoom()) { 
			targets.add(startCell);
		//Recursive Case
		} else {
			// add cell to visited
			visited.add(startCell);

			//recursively calls calcTargets() on every adjecent cell
			for (BoardCell c: startCell.getAdjList()) {
				calcTargets(c, pathlength - 1);
			}
			// removes cell from visited after all paths forward have been explore
			visited.remove(startCell); 
		}
	}
	
	public void loadSetupConfig() throws BadConfigFormatException {
		try {
			FileReader reader = new FileReader(setupConfigFile);
			Scanner scanner = new Scanner(reader);


			while (scanner.hasNextLine()) {
				String data = scanner.nextLine(); // first element of a line should be what type it is.
				
				if (data.charAt(0) == '/' && data.charAt(1) == '/') {
					continue;
				}
				
				int commaIndex = data.indexOf(',');
				String dataType = data.substring(0, commaIndex);
				data = data.substring(commaIndex+1);
				data = data.stripLeading();
				
				switch (dataType) {
				case "Room":
				case "Space":
				{
					commaIndex = data.indexOf(',');
					String roomName = data.substring(0,commaIndex);
					data = data.substring(commaIndex+1);
					data = data.stripLeading();
					char roomSymbol = data.charAt(0);
					roomMap.put(roomSymbol, new Room(roomName));
					break;
				}
				default:
					throw new BadConfigFormatException();
				}


			}
		}
		catch (FileNotFoundException e) {
			System.out.println(e);
		}

	}

	/*takes layout file and imports the data it holds into the proper locations
	*file should be a rectangular .csv of the board, where each element is 
	*a 1 or two letter symbol. first letter is the room in that cell and the
	*second letter any specialpart of the cell, such as secret passages, doors
	*etc. if not proper formated, will throw exception
	*/
	public void loadLayoutConfig() throws BadConfigFormatException {	
		try {
			//loads the layout file into a scanner to read
			FileReader reader = new FileReader(layoutConfigFile);
			Scanner scanner = new Scanner(reader);  
			
			//sets the delimiter pattern to be a comma for csv files
			scanner.useDelimiter(","); 

			String token;
			for (int i = 0; i < numRows; i++) {
				for (int j = 0; j < numCols; j++) {
					if (scanner.hasNext()) {
						token = scanner.next();  //find and returns the next complete token from this scanner
					} else {
						throw new BadConfigFormatException();
					}
					
					board[i][j].setRoom(roomMap.get(token.charAt(0)));

					if (token.length() > 1) {
						switch (token.charAt(1)) {
						case '<':
							board[i][j].setDoorDirection(DoorDirection.LEFT);
							break;
						case '>': 
							board[i][j].setDoorDirection(DoorDirection.RIGHT);
							break;
						case 'v': 
							board[i][j].setDoorDirection(DoorDirection.DOWN);
							break;
						case '^': 
							board[i][j].setDoorDirection(DoorDirection.UP);
							break;
						case '#':
							board[i][j].setLabel(true);
							break;
						case '*':
							board[i][j].setRoomCenter(true);
							roomMap.get(token.charAt(0)).setCenterCell(board[i][j]);
							break;
						default:
							board[i][j].setSecretPassage(token.charAt(1));
						}
					}
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println(e);
		} 
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
		layoutConfigFile = "data/" +layoutFile;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numCols;
	}


}
