//Authors: Brendan Uebelhoer, Ben Morgan
package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Board {

	private int numRows; // number of rows in the board
	private int numCols; // number of column in the board
	private Set<BoardCell> visited;

	private BoardCell[][] board;//stores the board itself
	private Set<BoardCell> targets;//stores the movable cells

	private Map<Character,Room> roomMap;

	private String layoutConfigFile, setupConfigFile;

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
		try {
			FileReader reader = new FileReader(layoutConfigFile);
			Scanner scanner = new Scanner(reader);
			
			String[] tokens = scanner.nextLine().split(",");
			numCols = tokens.length;
			
			int count = 1;
			while (scanner.hasNextLine()) {
				scanner.nextLine();
				count++;
			}
			
			numRows = count;
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
		board = new BoardCell[numRows][numCols]; //This needs to happen elsewhere
		roomMap = new HashMap<Character, Room>();
		loadSetupConfig();
		loadLayoutConfig();
		

		visited = new HashSet<BoardCell>();
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				board[i][j] = new BoardCell(i,j); // fills in the cells with their cords
			}
		}

		generateAdjacecies();
		targets = new HashSet<BoardCell>();

	}

	private void generateAdjacecies() {     //adds neighboring cells to list if they are valid
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
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

	public void calcTargets( BoardCell startCell, int pathlength) { //fills targets with all possible moves
		if (startCell.isOccupied()) { //if cell is occupied, cannot move to it
			return;
		}

		if (visited.contains(startCell)) { // if cell is already visited, cannot move to it
			return;
		}

		if(pathlength == 0) { // no moves left, add current cell to targets
			targets.add(startCell);	
		} else if (startCell.isRoom()) { // if given cell is a room, all moves used up, and added to targets
			targets.add(startCell);
		} else {
			visited.add(startCell); // add cell to visited

			for (BoardCell c: startCell.getAdjList()) {
				calcTargets(c, pathlength - 1);
			}

			visited.remove(startCell); // removes cell from visited after all paths forward have been explore
		}
	}

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

	public void loadSetupConfig() {
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
					continue;
				}


			}
		}
		catch (FileNotFoundException e) {
			System.out.println(e);
		}

	}

	public void loadLayoutConfig() {	
		try {
			FileReader reader = new FileReader(layoutConfigFile);
			Scanner scanner = new Scanner(reader);
			
			String[] tokens = scanner.nextLine().split(",");
			numCols = tokens.length;
			
			int count = 1;
			while (scanner.hasNextLine()) {
				scanner.nextLine();
				count++;
			}
			
			numRows = count;
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
		
		try {
			FileReader reader = new FileReader(layoutConfigFile);
			Scanner scanner = new Scanner(reader);  
			scanner.useDelimiter(",");   //sets the delimiter pattern  

			String token;
			for (int i = 0; i < numRows; i++) {
				for (int j = 0; j < numCols; j++) {
					token = scanner.next();  //find and returns the next complete token from this scanner
					
					board[i][j] = new BoardCell(i, j);
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
							roomMap.get(token.charAt(1)).setCenterCell(board[i][j]);
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

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numCols;
	}


}
