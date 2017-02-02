import java.awt.Point;
import java.util.Arrays;

public class Node {

	private char[][] state;
	private Node parent;
	private int action;
	// action represents the action that the parent took to achieve this state
	private int depth;
	private int cost;
	private char[][] solutionState;

	public Node() {
		char[][] startState = { { ' ', ' ', ' ', ' ' },
								{ ' ', ' ', ' ', ' ' }, 
								{ ' ', ' ', ' ', ' ' },
								{ 'A', 'B', 'C', 'O' } };
		char[][] solution = { { ' ', ' ', ' ', ' ' }, 
							  { ' ', 'A', ' ', ' ' },
						      { ' ', 'B', ' ', ' ' }, 
						      { ' ', 'C', ' ', 'O' } };
		solutionState = solution;
		this.state = startState;
		this.parent = null;
		//root node has no parent
		this.action = 0;
		this.depth = 0;
	}

	public Node(char[][] ts) {
		this.state = ts;
		this.parent = null;
		this.action = 0;
		this.depth = 0;
	}

	public void setState(char[][] state) {
		this.state = state;
	}

	public void setParent(Node g) {
		this.parent = g;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	public void setSolutionState(char[][] solutionState) {
		this.solutionState = solutionState;
	}

	public void setCost() {
		//cost is depth so far combined with heuristics estimation to goal
		this.cost = this.depth + costToSolution(this.state);
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public char[][] getState() {
		return this.state;
	}

	public Node getParent() {
		return this.parent;
	}

	public int getAction() {
		return this.action;
	}

	public int getDepth() {
		return this.depth;
	}

	public int getCost() {
		return this.cost;
	}
	
	public char[][] getSolutionState() {
		return this.solutionState;
	}

	public boolean isSolution() {
		//to test for a solution state the array of tiles must be the same
		if (Arrays.deepEquals(state, solutionState)) {
			return true;
		}
		return false;
	}

	public void displayGrid() {
		//iterate through the 2d array printing the contents of each tile
		for (int x = 0; x < state.length; x++) {
			for (int y = 0; y < state.length; y++) {
				System.out.print("| " + state[x][y] + ' ');
			}
			System.out.println("|");
		}
		System.out.println();
	}

	public Node move(int direction) {
		char[][] currentState = this.getState();
		int N = currentState.length;
		//create a new 2d array for state as arrays are objects
		char[][] newState = new char[N][N];
		Point agentLocation = new Point();
		for (int j = 0; j < newState.length; j++) {
			for (int i = 0; i < newState.length; i++) {
				newState[j][i] = currentState[j][i];
				//test if agent to record its location
				if (currentState[j][i] =='O') {
					agentLocation.setLocation(i, j);
				}
			}
		}
		switch (direction) {
		//up
		case 1:
			if (agentLocation.y > 0 && newState[agentLocation.y - 1][agentLocation.x] != 'X') {
				newState[agentLocation.y][agentLocation.x] = currentState[agentLocation.y - 1][agentLocation.x];
				newState[agentLocation.y - 1][agentLocation.x] = 'O';
				break;
			} else {
				return null;
			}
		//down
		case 2:
			if (agentLocation.y < N - 1 && newState[agentLocation.y + 1][agentLocation.x] != 'X') {
				newState[agentLocation.y][agentLocation.x] = currentState[agentLocation.y + 1][agentLocation.x];
				newState[agentLocation.y + 1][agentLocation.x] = 'O';
				break;
			} else {
				return null;
			}
		//left
		case 3:
			if (agentLocation.x > 0 && newState[agentLocation.y][agentLocation.x - 1] != 'X') {
				newState[agentLocation.y][agentLocation.x] = currentState[agentLocation.y][agentLocation.x - 1];
				newState[agentLocation.y][agentLocation.x - 1] = 'O';
				break;
			} else {
				return null;
			}
		//right
		case 4:
			if (agentLocation.x < N - 1 && newState[agentLocation.y][agentLocation.x + 1] != 'X') {
				newState[agentLocation.y][agentLocation.x] = currentState[agentLocation.y][agentLocation.x + 1];
				newState[agentLocation.y][agentLocation.x + 1] = 'O';
				break;
			} else {
				return null;
			}
		}
		//create node with new state
		return new Node(newState);
	}

	public Point findLocation(char[][] state, char tile) {
		// iterate through state looking for certain tile, return the position
		// of that tile
		for (int j = 0; j < state.length; j++) {
			for (int i = 0; i < state.length; i++) {
				if (state[j][i] == tile) {
					return new Point(i, j);
				}
			}
		}
		return null;
	}

	public int costToSolution(char[][] state) {
		int cost = 0;
		//iterate through 2d array
		for (int j = 0; j < state.length; j++) {
			for (int i = 0; i < state.length; i++) {
				if (state[j][i] != ' ' && state[j][i] != 'X') {
					Point location = findLocation(solutionState, state[j][i]);
					//accumulate difference in x and y of tile in both the states
					cost = cost + Math.abs(i - location.x)
							+ Math.abs(j - location.y);
				}
			}
		}
		return cost;
	}

	@Override
	public boolean equals(Object o) {
		//used to make nodes with the same state equal in HashSet
		if (Arrays.deepEquals(state, ((Node) o).getState())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		//used to make nodes with the same state equal in HashSet
		return Arrays.deepHashCode(state);
	}
}
