
// implementation class for a Game of two players

import java.util.ArrayList;

public class Game2P<S extends State2P<S, A>, A extends Action2P> implements Game<S, Player, A> {
	
	private S initialState;
	
	private Player p1;
	private Player p2;
	
	public Game2P(S initialState, Player p1, Player p2) {
		this.initialState = initialState;
		this.p1 = p1;
		this.p2 = p2;
	}

	@Override
	public S getInitialState() {
		return this.initialState;
	}

	@Override
	public Player toMove(S state) {
		if (state.getP1Moves() == state.getP2Moves()) {
			return p1;
		} else {
			return p2;
		}
	}

	@Override
	public ArrayList<A> actions(S state) {
		Player movingP = toMove(state);
		return state.possibleActions(movingP);
	}

	@Override
	public S result(S state, A action) {
		return state.changeState(action);
	}

	@Override
	public boolean isTerminal(S state) {
		return state.isTerminal();
	}

	@Override
	public int utility(S state, Player player) {
		return state.utility(player);
	}
	
	@Override
	public boolean isCutoff(S state, int depth) {
		return state.isCutoff(depth);
	}

}
