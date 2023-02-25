
// Player class
// defined by a name and an id

public class Player {
	
	private String name;
	private int id;
	private long time = 0;
	
	public Player(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getId() {
		return this.id;
	}

}
