
// generic Game interface
// S - state, P - player, A - action

import java.util.ArrayList;

public interface Game<S, P, A> {
	
	public S getInitialState();
	
	public P toMove(S state);
	
	public ArrayList<A> actions(S state);
	
	public S result(S state, A action);
	
	public boolean isTerminal(S state);
	
	public int utility(S state, P player);
	
	public boolean isCutoff(S state, int depth);
	
}
