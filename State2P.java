
// generic State interface specifically for games with two players

public interface State2P<S, A> extends State<S, Player, A> {
	
	public int getP1Moves();
	
	public int getP2Moves();

}
