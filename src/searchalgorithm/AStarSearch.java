package searchalgorithm;

import java.util.*;

import searchproblem.InformedSearchProblem;

public class AStarSearch implements SearchAlgorithm {
	
	private GraphSearch graph;

	public AStarSearch(final InformedSearchProblem prob) {
		Queue<Node> pqueue = new PriorityQueue<Node>(11, new Comparator<Node>() {	
			public int compare(Node o1, Node o2) {
			if( o1.getPathCost()+ prob.heuristic(o1) > o2.getPathCost() + prob.heuristic(o2) )
				return 1;
			else if ( o1.getPathCost() + prob.heuristic(o1) < o2.getPathCost() + prob.heuristic(o2) )
				return -1;
			else
				return 0;
			}});
		graph = new GraphSearch(prob,pqueue);
	}

	@Override
	public Node searchSolution() {
		return graph.searchSolution();
	}

	@Override
	public Map<String, Number> getMetrics() {
		return graph.getMetrics();
	}
	

}
