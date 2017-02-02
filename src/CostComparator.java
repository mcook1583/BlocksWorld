import java.util.Comparator;

public class CostComparator implements Comparator<Node> {

	//used in AStar part of search to order by least cost node first	
	
	@Override
	public int compare(Node n1, Node n2) {
		return n1.getCost() - n2.getCost();
	}

}
