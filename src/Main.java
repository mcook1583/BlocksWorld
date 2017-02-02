import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

public class Main {

	Set<Node> optimalSolutionSet;
	ArrayList<Node> optimalSolutionSequence;

	public static void main(String[] args) {
		Main m = new Main();
		Node g = new Node();
//		m.fillOpSol(g);
//		for(int i = 1; i<33 ; i++){
//			System.out.print(i+"	");
//			Search.BFS(m.fromSolution(i));
//			System.out.print("	");
//			Search.DFS(m.fromSolution(i));
//			System.out.print("	");
//			Search.IDS(m.fromSolution(i));
//			System.out.print("	");
//			Search.AStar(m.fromSolution(i));
//			System.out.println("");
//		} 
		
	}
	
	public void displaySolutionOrder(Node n){
		Stack<Node> s = new Stack<Node>();
		s.add(n);
		  while(s.peek().getParent() != null){ 
			  s.add(s.peek().getParent());
		  } 
		  while(!s.isEmpty()){ 
			  System.out.print(s.pop().getAction()); 
		  }
	}
	
	public void fillOpSol(Node g){
		Node AStarSolution = Search.AStar(g);
		optimalSolutionSet = new HashSet<Node>();
		optimalSolutionSequence = new ArrayList<Node>();
		Node currentNode = AStarSolution;
		while(currentNode.getParent() != null){
			optimalSolutionSequence.add(currentNode);
			optimalSolutionSet.add(currentNode);
			currentNode = currentNode.getParent();
		}
		optimalSolutionSequence.add(currentNode);
		optimalSolutionSet.add(currentNode);
		while(optimalSolutionSequence.size() < 16){
			Node lastMove = optimalSolutionSequence.get(optimalSolutionSequence.size()-1);
			Node randomMove = lastMove.move(new Random().nextInt(4) +1);
			if(randomMove != null && !optimalSolutionSet.contains(randomMove)){
				randomMove.setSolutionState(lastMove.getSolutionState());
				optimalSolutionSequence.add(randomMove);
				optimalSolutionSet.add(randomMove);				
			}
		}
	}
		
	public Node fromSolution(int depth){
		Node result = new Node(optimalSolutionSequence.get(depth).getState());
		result.setSolutionState(optimalSolutionSequence.get(depth).getSolutionState());
		return result;
	}

	
}