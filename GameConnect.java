
// implementation of Game class for Connect Four game

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameConnect extends Game2P<StateConnect, ActionConnect> {
	
	private StateConnect initialState;
	
	private Player p1;
	private Player p2;
	
	private int statesVisited = 0;
	
	private double timeRed = 0;
	private double timeYellow = 0;
	
	static final Scanner input = new Scanner(System.in);
	
	public GameConnect(int rows, int columns, int goal) {
		super(new StateConnect(rows, columns, goal), new Player("Red", 1), new Player("Yellow", 2));
		this.initialState = this.getInitialState();
		this.p1 = new Player("Red", 1);
		this.p2 = new Player("Yellow", 2);
	}
	
	// prints prompt for the 2 agents for tiny board and wider board
	public void playTiny() {
		System.out.println("Choose your opponent: ");
		System.out.println("1. An agent that plays randomly");
		System.out.println("2. An agent that uses MINIMAX");
		int choice = getInputInteger(2, "Your choice? ");
		this.playGame(choice);
	}
	
	// prints prompt for the 3 agents only for standard size board
	public void playWider() {
		System.out.println("Choose your opponent: ");
		System.out.println("1. An agent that plays randomly");
		System.out.println("2. An agent that uses MINIMAX");
		System.out.println("3. An agent that uses MINIMAX with alpha-beta pruning");
		int choice = getInputInteger(3, "Your choice? ");
		this.playGame(choice);
	}
	
	// prints prompt for the 4 agents only for standard size board
	public void playStandard() {
		System.out.println("Choose your opponent: ");
		System.out.println("1. An agent that plays randomly");
		System.out.println("2. An agent that uses MINIMAX");
		System.out.println("3. An agent that uses MINIMAX with alpha-beta pruning");
		System.out.println("4. An agent that uses H-MINIMAX with alpha-beta pruning and a fixed depth cutoff");
		int choice = getInputInteger(4, "Your choice? ");
		if (choice == 1 || choice == 2 || choice == 3) {
			this.playGame(choice);
		} else {
			int depth = getInputInteger(0, "Depth limit? ");
			this.initialState.setDepth(depth);
			this.playGame(choice);
		}
	}
	
	// function to get input integer from input and making sure it is valid
	public static int getInputInteger(int max, String message) {
		while (true) {
			System.out.print(message);
			try {
				int choice = input.nextInt();
				if ((choice > 0) && (max == 0 || choice <= max)) {
					return choice;
				} else {
					System.out.println("Enter a valid integer. Try again.");
				}
			} catch (java.util.InputMismatchException e) {
				System.out.println("Enter an integer. Try again.");
				input.nextLine();
			}
		}
	}
	
	// gets player from prompt and switches to correct agent function
	public void playGame(int cpu) {
		System.out.println("Do you want to play RED (1) or YELLOW (2)?");
		int player = getInputInteger(2, "Your choice? ");
		System.out.println();
		StateConnect currentState = this.getInitialState();
		currentState.printState();
		if (player == 1) {
			currentState = userMove(currentState);
		}
		switch (cpu) {
		case 1:
			while (true) {
				if (this.isTerminal(currentState)) {
					break;
				}
				currentState = cpuMoveRandom(currentState);
				if (this.isTerminal(currentState)) {
					break;
				}
				currentState = userMove(currentState);
			}
			break;
		case 2:
			while (true) {
				if (this.isTerminal(currentState)) {
					break;
				}
				currentState = cpuMoveMinimax(currentState);
				if (this.isTerminal(currentState)) {
					break;
				}
				currentState = userMove(currentState);
			}
			break;
		case 3:
			while (true) {
				if (this.isTerminal(currentState)) {
					break;
				}
				currentState = cpuMoveMinimaxAB(currentState);
				if (this.isTerminal(currentState)) {
					break;
				}
				currentState = userMove(currentState);
			}
			break;
		case 4:
			while (true) {
				if (this.isTerminal(currentState)) {
					break;
				}
				currentState = cpuMoveHMinimax(currentState);
				if (this.isTerminal(currentState)) {
					break;
				}
				currentState = userMove(currentState);
			}
			break;
		}
		int terminalColor = currentState.getTerminalColor();
		if (terminalColor == 1) {
			System.out.println("Winner: " + p1.getName());
		} else if (terminalColor == 2) {
			System.out.println("Winner: " + p2.getName());
		} else {
			System.out.println("Draw");
		}
		System.out.println("Total time:");
		System.out.println("\t" + this.p1.getName() + ": " + this.timeRed + " secs");
		System.out.println("\t" + this.p2.getName() + ": " + this.timeYellow + " secs");
	}
	
	// random agent
	public StateConnect cpuMoveRandom(StateConnect currentState) {
		Player currentPlayer = this.toMove(currentState);
		System.out.println("Next to play: " + currentPlayer.getName());
		System.out.println();
		System.out.println("I'm thinking...");
		long startCpu = System.currentTimeMillis();
		ArrayList<ActionConnect> possibleActionsList = this.actions(currentState);
		Random rand = new Random();
		int randomInt = rand.nextInt(possibleActionsList.size());
		currentState = this.result(currentState, possibleActionsList.get(randomInt));
		char column = (char) (possibleActionsList.get(randomInt).getColumn() + 97);
		long endCpu = System.currentTimeMillis();
		this.setTime(currentPlayer.getId(), ((endCpu - startCpu) / 1000.0));
		System.out.println("\t random move: " + currentPlayer.getName() + " @" + column);
		System.out.println("Elapsed time: " + ((endCpu - startCpu) / 1000.0) + " secs");
		System.out.print(currentPlayer.getName() + " @" + column);
		System.out.println("\n");
		currentState.printState();
		return currentState;
	}
	
	// minimax agent
	public StateConnect cpuMoveMinimax(StateConnect currentState) {
		this.statesVisited = 0;
		Player currentPlayer = this.toMove(currentState);
		System.out.println("Next to play: " + currentPlayer.getName());
		System.out.println();
		System.out.println("I'm thinking...");
		long startCpu = System.currentTimeMillis();
		ActionConnect move = this.maxValue(currentState, currentPlayer);
		currentState = this.result(currentState, move);
		char column = (char) (move.getColumn() + 97);
		long endCpu = System.currentTimeMillis();
		this.setTime(currentPlayer.getId(), ((endCpu - startCpu) / 1000.0));
		System.out.println("\t visited " + this.statesVisited + " states");
		System.out.println("\t best move: " + currentPlayer.getName() + " @" + column + ", value: " + move.getValue());
		System.out.println("Elapsed time: " + ((endCpu - startCpu) / 1000.0) + " secs");
		System.out.print(currentPlayer.getName() + " @" + column);
		System.out.println("\n");
		currentState.printState();
		return currentState;
	}
	
	// maxValue function for minimax per pseudocode
	public ActionConnect maxValue(StateConnect currentState, Player currentPlayer) {
		ActionConnect move = null;
		if (this.isTerminal(currentState)) {
			move = new ActionConnect(-1, -1, null);
			move.setValue(this.utility(currentState, currentPlayer));
			this.statesVisited = this.statesVisited + 1;
			return move;
		}
		int v = -2000;
		ArrayList<ActionConnect> possibleActionsList = this.actions(currentState);
		for (int a = 0; a < possibleActionsList.size(); a++) {
			ActionConnect a2 = this.minValue(this.result(currentState, possibleActionsList.get(a)), currentPlayer);
			if (a2.getValue() > v) {
				move = possibleActionsList.get(a);
				v = a2.getValue();
				move.setValue(v);
			}
		}
		this.statesVisited = this.statesVisited + 1;
		return move;
	}
	
	// minValue function for minimax per pseudocode
	public ActionConnect minValue(StateConnect currentState, Player currentPlayer) {
		ActionConnect move = null;
		if (this.isTerminal(currentState)) {
			move = new ActionConnect(-1, -1, null);
			move.setValue(this.utility(currentState, currentPlayer));
			this.statesVisited = this.statesVisited + 1;
			return move;
		}
		int v = 2000;
		ArrayList<ActionConnect> possibleActionsList = this.actions(currentState);
		for (int a = 0; a < possibleActionsList.size(); a++) {
			ActionConnect a2 = this.maxValue(this.result(currentState, possibleActionsList.get(a)), currentPlayer);
			if (a2.getValue() < v) {
				move = possibleActionsList.get(a);
				v = a2.getValue();
				move.setValue(v);
			}
		}
		this.statesVisited = this.statesVisited + 1;
		return move;
	}
	
	// function for minimax with alpha beta pruning per pseudocode
	public StateConnect cpuMoveMinimaxAB(StateConnect currentState) {
		this.statesVisited = 0;
		Player currentPlayer = this.toMove(currentState);
		System.out.println("Next to play: " + currentPlayer.getName());
		System.out.println();
		System.out.println("I'm thinking...");
		long startCpu = System.currentTimeMillis();
		ActionConnect move = this.maxValueAB(currentState, currentPlayer, -2000, 2000);
		currentState = this.result(currentState, move);
		char column = (char) (move.getColumn() + 97);
		long endCpu = System.currentTimeMillis();
		this.setTime(currentPlayer.getId(), ((endCpu - startCpu) / 1000.0));
		System.out.println("\t visited " + this.statesVisited + " states");
		System.out.println("\t best move: " + currentPlayer.getName() + " @" + column + ", value: " + move.getValue());
		System.out.println("Elapsed time: " + ((endCpu - startCpu) / 1000.0) + " secs");
		System.out.print(currentPlayer.getName() + " @" + column);
		System.out.println("\n");
		currentState.printState();
		return currentState;
	}
	
	// maxValue function for minimax with alpha beta pruning per psuedocode
	public ActionConnect maxValueAB(StateConnect currentState, Player currentPlayer, int alpha, int beta) {
		ActionConnect move = null;
		if (this.isTerminal(currentState)) {
			move = new ActionConnect(-1, -1, null);
			move.setValue(this.utility(currentState, currentPlayer));
			this.statesVisited = this.statesVisited + 1;
			return move;
		}
		int v = -2000;
		ArrayList<ActionConnect> possibleActionsList = this.actions(currentState);
		for (int a = 0; a < possibleActionsList.size(); a++) {
			ActionConnect a2 = this.minValueAB(this.result(currentState, possibleActionsList.get(a)), currentPlayer, alpha, beta);
			if (a2.getValue() > v) {
				move = possibleActionsList.get(a);
				v = a2.getValue();
				move.setValue(v);
				alpha = Math.max(alpha, v);
			}
			if (v >= beta) {
				this.statesVisited = this.statesVisited + 1;
				return move;
			}
		}
		this.statesVisited = this.statesVisited + 1;
		return move;
	}
	
	// minValue function for minimax with alpha beta pruning per psuedocode
	public ActionConnect minValueAB(StateConnect currentState, Player currentPlayer, int alpha, int beta) {
		ActionConnect move = null;
		if (this.isTerminal(currentState)) {
			move = new ActionConnect(-1, -1, null);
			move.setValue(this.utility(currentState, currentPlayer));
			this.statesVisited = this.statesVisited + 1;
			return move;
		}
		int v = 2000;
		ArrayList<ActionConnect> possibleActionsList = this.actions(currentState);
		for (int a = 0; a < possibleActionsList.size(); a++) {
			ActionConnect a2 = this.maxValueAB(this.result(currentState, possibleActionsList.get(a)), currentPlayer, alpha, beta);
			if (a2.getValue() < v) {
				move = possibleActionsList.get(a);
				v = a2.getValue();
				move.setValue(v);
				beta = Math.min(beta, v);
			}
			if (v <= alpha) {
				this.statesVisited = this.statesVisited + 1;
				return move;
			}
		}
		this.statesVisited = this.statesVisited + 1;
		return move;
	}
	
	// function for heuristic minimax per pseudocode
	public StateConnect cpuMoveHMinimax(StateConnect currentState) {
		this.statesVisited = 0;
		Player currentPlayer = this.toMove(currentState);
		System.out.println("Next to play: " + currentPlayer.getName());
		System.out.println();
		System.out.println("I'm thinking...");
		long startCpu = System.currentTimeMillis();
		ActionConnect move = this.maxValueH(currentState, currentPlayer, -2000, 2000, 1);
		currentState = this.result(currentState, move);
		char column = (char) (move.getColumn() + 97);
		long endCpu = System.currentTimeMillis();
		this.setTime(currentPlayer.getId(), ((endCpu - startCpu) / 1000.0));
		System.out.println("\t visited " + this.statesVisited + " states");
		System.out.println("\t best move: " + currentPlayer.getName() + " @" + column + ", value: " + move.getValue());
		System.out.println("Elapsed time: " + ((endCpu - startCpu) / 1000.0) + " secs");
		System.out.print(currentPlayer.getName() + " @" + column);
		System.out.println("\n");
		currentState.printState();
		return currentState;
	}
	
	// maxValue function for heuristic minimax with alpha beta pruning per psuedocode
	public ActionConnect maxValueH(StateConnect currentState, Player currentPlayer, int alpha, int beta, int depth) {
		ActionConnect move = null;
		if (this.isCutoff(currentState, depth)) {
			move = new ActionConnect(-1, -1, null);
			move.setValue(this.utility(currentState, currentPlayer));
			this.statesVisited = this.statesVisited + 1;
			return move;
		}
		int v = -2000;
		ArrayList<ActionConnect> possibleActionsList = this.actions(currentState);
		for (int a = 0; a < possibleActionsList.size(); a++) {
			ActionConnect a2 = this.minValueH(this.result(currentState, possibleActionsList.get(a)), currentPlayer, alpha, beta, depth + 1);
			if (a2.getValue() > v) {
				move = possibleActionsList.get(a);
				v = a2.getValue();
				move.setValue(v);
				alpha = Math.max(alpha, v);
			}
			if (v >= beta) {
				this.statesVisited = this.statesVisited + 1;
				return move;
			}
		}
		this.statesVisited = this.statesVisited + 1;
		return move;
	}
	
	// minValue function for heuristic minimax with alpha beta pruning per psuedocode
	public ActionConnect minValueH(StateConnect currentState, Player currentPlayer, int alpha, int beta, int depth) {
		ActionConnect move = null;
		if (this.isCutoff(currentState, depth)) {
			move = new ActionConnect(-1, -1, null);
			move.setValue(this.utility(currentState, currentPlayer));
			this.statesVisited = this.statesVisited + 1;
			return move;
		}
		int v = 2000;
		ArrayList<ActionConnect> possibleActionsList = this.actions(currentState);
		for (int a = 0; a < possibleActionsList.size(); a++) {
			ActionConnect a2 = this.maxValueH(this.result(currentState, possibleActionsList.get(a)), currentPlayer, alpha, beta, depth + 1);
			if (a2.getValue() < v) {
				move = possibleActionsList.get(a);
				v = a2.getValue();
				move.setValue(v);
				beta = Math.min(beta, v);
			}
			if (v <= alpha) {
				this.statesVisited = this.statesVisited + 1;
				return move;
			}
		}
		this.statesVisited = this.statesVisited + 1;
		return move;
	}
	
	// function for the user move
	public StateConnect userMove(StateConnect currentState) {
		Player currentPlayer = this.toMove(currentState);
		System.out.println("Next to play: " + currentPlayer.getName());
		long startUser = System.currentTimeMillis();
		ArrayList<ActionConnect> possibleActionsList = this.actions(currentState);
		while (true) {
			char column = getColumn();
			int columnNumber = column - 97;
			boolean validAction = false;
			int actionIndex = -1;
			for (int i = 0; i < possibleActionsList.size(); i++) {
				if (columnNumber == possibleActionsList.get(i).getColumn()) {
					validAction = true;
					actionIndex = i;
					break;
				}
			}
			if (validAction) {
				currentState = this.result(currentState, possibleActionsList.get(actionIndex));
				break;
			}
		}
		long endUser = System.currentTimeMillis();
		this.setTime(currentPlayer.getId(), ((endUser - startUser) / 1000.0));
		System.out.println("Elapsed time: " + ((endUser - startUser) / 1000.0) + " secs\n");
		currentState.printState();
		return currentState;
	}
	
	// gets a letter as a column from input, checks if valid
	public char getColumn() {
		char column = '_';
		while (true) {
			System.out.print("Your move [column]? ");
			try {
				String columnInput = input.next();
				column = columnInput.charAt(0);
				if (columnInput.length() > 1) {
					column = 96;
				}
				if ((column > 96) && (column < 97 + this.initialState.getColumns())) {
					break;
				} else {
					System.out.println("Enter a valid integer. Try again.");
				}
			} catch (java.util.InputMismatchException e) {
				System.out.println("Enter an integer. Try again.");
				input.nextLine();
			}
		}
		return column;
	}
	
	public void setTime(int id, double time) {
		if (id == 1) {
			this.timeRed = this.timeRed + time;
		} else if (id == 2) {
			this.timeYellow = this.timeYellow + time;
		}
	}
	
	// main function to run the program
	public static void main(String[] args) {
		System.out.println("Connect-Four by Eric Anderson");
		System.out.println("Choose your game: ");
		System.out.println("1. Tiny 3x3x3 Connect-Three");
		System.out.println("2. Wider 5x3x3 Connect-Three");
		System.out.println("3. Standard 7x6x4 Connect-Four");
		int choice = getInputInteger(3, "Your choice? ");
		GameConnect gc = null;
		switch (choice) {
		case 1:
			gc = new GameConnect(3, 3, 3);
			gc.playTiny();
			break;
		case 2:
			gc = new GameConnect(3, 5, 3);
			gc.playWider();
			break;
		case 3:
			gc = new GameConnect(6, 7, 4);
			gc.playStandard();
			break;
		}
	}
}
