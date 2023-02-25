
// implementation of State class for Connect Four game
// defined by a board and several variables that store the information about the moves made in the game

import java.util.ArrayList;

public class StateConnect implements State2P<StateConnect, ActionConnect> {
	
	private int columns;
	private int rows;
	private int goal;
	
	// board goes 0-max left to right, up to down
	private int[][] board;
	// array of integers for each column, each integer corresponding to the lowest slot that is availabe
	private int[] lowestSlots;
	
	// indicates whether state is terminal
	private boolean terminal;
	// indicates the color of the player who has won if terminal
	private int color = 0;
	
	// counts of moves
	private int p1Moves = 0;
	private int p2Moves = 0;
	
	// counts of contiguous pieces
	private int p1Threes = 0;
	private int p2Threes = 0;
	private int p1Twos = 0;
	private int p2Twos = 0;
	
	// utility scores for each player
	private int p1Utility = 0;
	private int p2Utility = 0;
	
	// helper variable to count the depth that h-minimax has gone
	private int depth = 0;
	
	public StateConnect(int rows, int columns, int goal) {
		this.rows = rows;
		this.columns = columns;
		this.goal = goal;
		this.board = new int[this.rows][this.columns];
		this.lowestSlots = new int[this.columns];
		for (int i = 0; i < this.columns; i++) {
			this.lowestSlots[i] = this.rows - 1;
		}
		this.terminal = this.checkTerminal();
	}
	
	// copy constructor
	public StateConnect(StateConnect another) {
		this.rows = another.rows;
		this.columns = another.columns;
		this.goal = another.goal;
		this.board = new int[this.rows][this.columns];
		for(int i = 0; i < this.board.length; i++)
		    this.board[i] = another.board[i].clone();
		this.lowestSlots = another.lowestSlots.clone();
		this.terminal = another.terminal;
		this.color = another.color;
		this.p1Moves = another.p1Moves;
		this.p2Moves = another.p2Moves;
		this.p1Threes = another.p1Threes;
		this.p2Threes = another.p2Threes;
		this.p1Twos = another.p1Twos;
		this.p2Twos = another.p2Twos;
		this.p1Utility = another.p1Utility;
		this.p2Utility = another.p2Utility;
		this.depth = another.depth;
	}

	@Override
	// iterates through columns using lowestSlots and returns list of actions
	public ArrayList<ActionConnect> possibleActions(Player player) {
		ArrayList<ActionConnect> possibleActionsList = new ArrayList<ActionConnect>();
		for (int i = 0; i < this.columns; i++) {
			if (this.board[0][i] == 0) {
				possibleActionsList.add((ActionConnect) new ActionConnect(this.lowestSlots[i], i, player));
			}
		}
		return possibleActionsList;
	}

	@Override
	// updates variables and returns new state
	public StateConnect changeState(ActionConnect action) {
		StateConnect returnState = new StateConnect(this);
		if (action.getPlayer().getId() == 1) {
			returnState.p1Moves = returnState.p1Moves + 1;
		} else {
			returnState.p2Moves = returnState.p2Moves + 1;
		}
		returnState.board[action.getRow()][action.getColumn()] = action.getPlayer().getId();
		returnState.lowestSlots[action.getColumn()] = action.getRow() - 1;
		returnState.terminal = returnState.checkTerminal();
		returnState.heuristicFunction();
		return returnState;
	}

	@Override
	public boolean isTerminal() {
		return this.terminal;
	}
	
	// iterates through board checking the correct quadrants for possible rows of four
	public boolean checkTerminal() {
		int count = 0;
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				int color = this.board[i][j];
				if (color == 0) {
					continue;
				}
				// top of board
				if (i + (this.goal - 1) < this.rows) {
					if (checkDown(color, 1, i, j)) {
						return true;
					}
					// right of board
					if (j + (this.goal - 1) < this.columns) {
						if (checkRight(color, 1, i, j)) {
							return true;
						}
						if (checkRightDiagonal(color, 1, i, j)) {
							return true;
						}
					}
					// left of board
					if (j - (this.goal - 2) > 0) {
						if (checkLeftDiagonal(color, 1, i, j)) {
							return true;
						}
					}
				}
				// bottom left quadrant of board
				if (j + (this.goal - 1) < this.columns) {
					if (checkRight(color, 1, i, j)) {
						return true;
					}
				}
			}
		}
		// checks if all slots filled up
		for (int i = 0; i < this.columns; i++) {
			if (this.lowestSlots[i] != -1) {
				break;
			} else {
				count++;
			}
			if (count == this.columns) {
				return true;
			}
		}
		return false;
	}
	
	// recursive function to check if there is a four in a row to the down
	public boolean checkDown(int color, int slots, int row, int column) {
		if (slots == this.goal) {
			this.color = color;
			return true;
		} else {
			if (color == this.board[row + 1][column]) {
				return checkDown(color, slots + 1, row + 1, column);
			} else {
				return false;
			}
		}
	}
	
	// recursive function to check if there is a four in a row to the right
	public boolean checkRight(int color, int slots, int row, int column) {
		if (slots == this.goal) {
			this.color = color;
			return true;
		} else {
			if (color == this.board[row][column + 1]) {
				return checkRight(color, slots + 1, row, column + 1);
			} else {
				return false;
			}
		}
	}
	
	// recursive function to check if there is a four in the row right diagonally
	public boolean checkRightDiagonal(int color, int slots, int row, int column) {
		if (slots == this.goal) {
			this.color = color;
			return true;
		} else {
			if (color == this.board[row + 1][column + 1]) {
				return checkRightDiagonal(color, slots + 1, row + 1, column + 1);
			} else {
				return false;
			}
		}
	}
	
	// recursive functionto check if there is a four in the row left diagonally
	public boolean checkLeftDiagonal(int color, int slots, int row, int column) {
		if (slots == this.goal) {
			this.color = color;
			return true;
		} else {
			if (color == this.board[row + 1][column - 1]) {
				return checkLeftDiagonal(color, slots + 1, row + 1, column - 1);
			} else {
				return false;
			}
		}
	}

	@Override
	// returns (-)1000 if terminal, or utility otherwise
	public int utility(Player player) {
		if (this.terminal) {
			if (this.color == player.getId()) {
				return 1000;
			} else if (this.color != 0) {
				return -1000;
			} else {
				return 0;
			}
		} else if (player.getId() == 1) {
			return this.p1Utility;
		} else {
			return this.p2Utility;
		}
	}

	@Override
	public int getP1Moves() {
		return this.p1Moves;
	}

	@Override
	public int getP2Moves() {
		return this.p2Moves;
	}
	
	@Override
	// formats the board
	public void printState() {
		System.out.print("   ");
		char letter = 97;
		for (int i = 0; i < this.columns; i++) {
			System.out.print(" " + letter + "  ");
			letter++;
		}
		System.out.println();
		for (int i = 0; i < this.rows; i++) {
			System.out.print((i + 1) + " |");
			for (int j = 0; j < this.columns; j++) {
				if (this.board[i][j] == 1) {
					System.out.print(" R |");
				} else if (this.board[i][j] == 2) {
					System.out.print(" Y |");
				} else {
					System.out.print("   |");
				}
			}
			System.out.println("");
		}
		System.out.println();
	}
	
	@Override
	public boolean isCutoff(int depth) {
		if (this.isTerminal()) {
			return true;
		} else if (depth >= this.depth) {
			return true;
		} else {
			return false;
		}
	}
	
	// designed for standard board size
	// based off of count of contiguous pieces and pieces in the middle
	// 'middles' and 'threes' are waited the same
	// 'twos' are weighted less
	// theoretical max score I think would be ~260
	public void heuristicFunction() {
		int[] middles = this.countMiddles();
		this.countTwosThrees();
		this.p1Utility = ((middles[0] + this.p1Threes) * 10) + this.p1Twos;
		this.p2Utility = ((middles[1] + this.p2Threes) * 10) + this.p2Twos;
	}
	
	// just counts pieces in middle column
	public int[] countMiddles() {
		int[] ret = new int[]{0, 0};
		if (this.columns % 2 == 0) {
			return ret;
		}
		for (int i = 0; i < this.rows; i++) {
			if (this.board[i][this.columns / 2] == 1) {
				ret[0] = ret[0] + 1;
			} else if (this.board[i][this.columns / 2] == 2) {
				ret[1] = ret[1] + 1;
			}
		}
		return ret;
	}
	
	public void countTwosThrees() {
		StateConnect copy = new StateConnect(this);
		copy.goal = 3;
		copy.p1Threes = 0;
		copy.p2Threes = 0;
		copy.countThrees();
		copy.goal = 2;
		copy.p1Twos = 0;
		copy.p2Twos = 0;
		copy.countTwos();
		this.p1Threes = copy.p1Threes;
		this.p2Threes = copy.p2Threes;
		this.p1Twos = copy.p1Twos - (2 * this.p1Threes);
		this.p2Twos = copy.p2Twos - (2 * this.p2Threes);
	}
	
	public void countTwos() {
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				int color = this.board[i][j];
				if (color == 0) {
					continue;
				}
				int count = 0;
				if (i + 1 < this.rows) {
					if (this.checkDown(color, 1, i, j)) {
						count++;
					}
					if (j + 1 < this.columns) {
						if (this.checkRight(color, 1, i, j)) {
							count++;
						}
						if (this.checkRightDiagonal(color, 1, i, j)) {
							count++;
						}
					}
					if (j > 0) {
						if (this.checkLeftDiagonal(color, 1, i, j)) {
							count++;
						}
					}
				}
				if (j + 1 < this.columns && i + 1 >= this.rows) {
					if (this.checkRight(color, 1, i, j)) {
						count++;
					}
				}
				switch (color) {
				case 1:
					this.p1Twos = this.p1Twos + count;
					break;
				case 2:
					this.p2Twos = this.p2Twos + count;
					break;
				}
			}
		}
	}
	
	public void countThrees() {
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				int color = this.board[i][j];
				if (color == 0) {
					continue;
				}
				int count = 0;
				if (i + 2 < this.rows) {
					if (this.checkDown(color, 1, i, j)) {
						count++;
					}
					if (j + 2 < this.columns) {
						if (this.checkRight(color, 1, i, j)) {
							count++;
						}
						if (this.checkRightDiagonal(color, 1, i, j)) {
							count++;
						}
					}
					if (j - 1 > 0) {
						if (this.checkLeftDiagonal(color, 1, i, j)) {
							count++;
						}
					}
				}
				if (j + 2 < this.columns && i + 2 >= this.rows) {
					if (this.checkRight(color, 1, i, j)) {
						count++;
					}
				}
				switch (color) {
				case 1:
					this.p1Threes = this.p1Threes + count;
					break;
				case 2:
					this.p2Threes = this.p2Threes + count;
					break;
				}
			}
		}
	}
	
	public int getTerminalColor() {
		return this.color;
	}
	
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	public int getColumns() {
		return this.columns;
	}
	
}
