
// generic State interface
// S - state, P - player, A - action

import java.util.ArrayList;

public interface State<S, P, A> {
	
	public ArrayList<A> possibleActions(P player);
	
	public S changeState(A action);
	
	public boolean isTerminal();
	
	public int utility(P player);
	
	public void printState();
	
	public boolean isCutoff(int depth);

}
