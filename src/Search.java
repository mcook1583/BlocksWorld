import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class Search {
	
	static int expandCount;
	
	public static Node BFS(Node root) {
		//reset expand count at beginning
		expandCount = 0;
		Queue<Node> queue = new LinkedList<Node>();
		//HashSet<Node> seen = new HashSet<Node>();
		queue.add(root);
		//when the queue isn't empty
		while (!queue.isEmpty()) {
			Node node = queue.poll();
			//check if node is a solution
			if (node.isSolution()) {
				System.out.print(expandCount);
				return node;
			}
			//seen.add(node);
			//if not a solution, expand node in a non-random order
			for (Node n : expand(node, false)) {
				//if(!seen.contains(n)){
					queue.add(n);
				//}
			}
		}
		return null;
	}
	
	public static Node DFS(Node root) {
		//reset expand count at beginning
		expandCount = 0;
		Stack<Node> stack = new Stack<Node>();
		//HashSet<Node> seen = new HashSet<Node>();
		stack.add(root);
		//while stack isn't empty
		while (!stack.isEmpty()) {
			Node node = stack.pop();
			//check if the node is a solution
			if (node.isSolution()) {
				System.out.print(expandCount);
				return node;
			}
			//seen.add(node);
			//if not a solution, expand node in a random order
			for (Node n : expand(node, true)) {
				//if(!seen.contains(n)){
					stack.add(n);
				//}
			}
		}
		return null;
	}
	
	public static Node IDS(Node root) {
		//reset expand count at beginning
		expandCount = 0;
		for (int depthLimit = 0; depthLimit < Integer.MAX_VALUE; depthLimit++) {
			Node DLSResult = DLS(root, depthLimit);
			if (DLSResult != null) {
				System.out.print(expandCount);
				return DLSResult;
			}
		}
		return null;
	}

	public static Node DLS(Node root, int limit) {
		//null represents no solution found at that depth
		return RecursiveDLS(root, limit);
	}

	public static Node RecursiveDLS(Node node, int limit) {
		if (node.isSolution()) {
			return node;
		}
		if (node.getDepth() == limit) {
			return null;
		}
		for (Node successor : expand(node, false)) {
			//call this method on each successor
			Node result = RecursiveDLS(successor, limit);
			if (result != null) {
				return result;
			}
		}
		return null;
	}
	
	public static Node AStar(Node root) {
		//reset expand count at beginning
		expandCount = 0;
		PriorityQueue<Node> pq = new PriorityQueue<Node>(new CostComparator());
		//HashSet<Node> seen = new HashSet<Node>();
		pq.add(root);
		//while priority queue is not empty
		while (!pq.isEmpty()) {
			Node node = pq.poll();
			//check if node is a solution
			if (node.isSolution()) {
				System.out.print(expandCount);
				return node;
			}
			//seen.add(node);
			//if not a solution, expand node in a non-random order
			for (Node n : expand(node, false)) {
				//if(!seen.contains(n)){
					pq.add(n);					
				//}
			}
		}
		return null;
	}
	
	public static List<Node> expand(Node node, boolean random) {
		//increase expand count
		expandCount++;
		List<Node> successors = new ArrayList<Node>();
		//order of moves to be taken
		Integer[] numberArray = {1,2,3,4};
		if(random){
			//shuffle order of moves to be taken
			List<Integer> numberList = Arrays.asList(numberArray);
			Collections.shuffle(numberList);
			numberArray = numberList.toArray(numberArray);
		}
		for (int num : numberArray) {
			//check if move is legal
			if (node.move(num) != null) {
				Node successor = node.move(num);
				successor.setParent(node);
				successor.setSolutionState(node.getSolutionState());
				successor.setAction(num);
				successor.setDepth(node.getDepth() + 1);
				successor.setCost();
				//add new node to list of successors
				successors.add(successor);
			}
		}
		return successors;
	}

}
