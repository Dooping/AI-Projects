package searchalgorithm;

import java.util.*;

import searchproblem.*;

public class GraphSearch implements SearchAlgorithm {

	private SearchProblem problem;
	private Node goal;
	private Queue<Node> frontier;
	private HashMap<Node, Node> explored;
	private boolean done = false;
	
	private int generated, expansions, repeated, maxFrontier;
	private long time;
	
	public GraphSearch(SearchProblem p, Queue<Node> q) {
		explored = new HashMap<Node, Node>();
		problem = p;
		frontier = q;
		generated = 0;
		expansions = 0;
		repeated = 0;
		maxFrontier = 0;
	}
	
	public Node searchSolution() {
		if(!done){
			long startTime = System.nanoTime();	
			goal = search();
			time = System.nanoTime() - startTime;
			done = true;
			problem = null;
			frontier.clear();
		}
		return goal;
	}
	
	private Node search(){
		frontier.add(new Node(problem.getInitial()));
		maxFrontier = frontier.size();
		generated++;
		Node node;
		List<Node> children;
		while(true){
			if(frontier.isEmpty())
				return null;
			node = frontier.remove();
			
			if(explored.containsKey(node))
				repeated++;
			
			if(problem.goalTest(node.getState())){
				return node;
			}
			
			if(explored.get(node)==null){
				explored.put(node, node);
				children = node.Expand();
				generated += children.size();
				expansions++;
				frontier.addAll(children);
				maxFrontier = Math.max(maxFrontier, frontier.size());
						
			}
		}
	}

	public Map<String,Number> getMetrics() {
		Map<String,Number> metrics = new LinkedHashMap<String,Number>();
		
		metrics.put("Node Expansions",expansions);
		metrics.put("Nodes Generated",generated);
		metrics.put("Max Frontier",maxFrontier);
		metrics.put("State repetitions",repeated);		
		metrics.put("Runtime (s)", time/1E9);
		return metrics;
	}
	
}
