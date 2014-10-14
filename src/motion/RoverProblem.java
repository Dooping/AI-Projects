package motion;

import static java.lang.Math.*;

import java.util.Set;

import searchalgorithm.Node;
import searchproblem.*;

public class RoverProblem extends InformedSearchProblem {

	
	public RoverProblem(State initial) {
		super(initial);
	}

	public RoverProblem(RoverState init, RoverState goal) {
		super (init, goal);
	}
	
	public RoverProblem(State initial, Set<State> goals) {
		super(initial, goals);
	}

	@Override
	public double heuristic(Node n) {
		RoverState s = (RoverState)n.getState();
		RoverState g = (RoverState)goalStates.iterator().next();
		return sqrt( ((g.getCoordX() - s.getCoordX())*(g.getCoordX() - s.getCoordX()))
				+ ((g.getCoordY() - s.getCoordY())*(g.getCoordY() - s.getCoordY()))
				+ ((g.getHeight() - s.getHeight())*(g.getHeight() - s.getHeight())));
		/*return max(max(g.getCoordX() - s.getCoordX(), g.getCoordY() - s.getCoordY()),
		g.getHeight() - s.getHeight());*/
	}

}
