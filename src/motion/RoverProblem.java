package motion;

import static java.lang.Math.*;

import searchalgorithm.Node;
import searchproblem.*;

public class RoverProblem extends InformedSearchProblem {

	

	public RoverProblem(State initial) {
		super(initial);
	}

	public RoverProblem(RoverState init, RoverState goal) {
		super (init, goal);
	}

	@Override
	public double heuristic(Node n) {
		RoverState s = (RoverState)n.getState();
		RoverState g = (RoverState)goalStates.iterator().next();
		return sqrt( pow(g.getCoordX() - s.getCoordX(), 2 )
				+ pow(g.getCoordY() - s.getCoordY(), 2)
				+ pow(g.getHeight() - s.getHeight(), 2));
	}

}
