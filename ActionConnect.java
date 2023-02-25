
// implementation of Action class for Connect Four game
// defined by the Player moving, and the row and column of the move to make

public class ActionConnect implements Action2P {
	
	private int row;
	private int column;
	private Player player;
	private int value;
	
	public ActionConnect(int row, int column, Player player) {
		this.row = row;
		this.column = column;
		this.player = player;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getColumn() {
		return this.column;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}

}
