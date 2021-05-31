import java.util.LinkedList;
import java.util.Queue;
import java.util.HashMap;

public class MarioMaze {
	
	private int shortestDistance = Integer.MAX_VALUE;
	private int[] shortestRoute;
	private int coinCount = 0;
	
	public int shortestSteps(Point mario, Point peach, int[][] maze) {
		// Path to Peach? ~O(5n^2)
		if (shortestPath(mario, peach, maze) == Integer.MAX_VALUE) // No Path to Peach
			return -1;
		
		// Locate the Coins O(n^2)
		HashMap<Integer, Point> coins = new HashMap<Integer, Point>(12);
		coins.put(0, mario); // Add Mario
		coinCount++;
		for (int row = 0; row < maze.length; row++) {
			for (int col = 0; col < maze.length; col++) {
				if (maze[row][col] == 2) {
					coins.put(coinCount, new Point(row, col));
					coinCount++;
				}
			}
		}
		coins.put(coinCount, peach);
		coinCount++; // Number of coins plus 2 (Mario and Peach)
		
		if (coinCount == 2)
			return shortestPath(mario, peach, maze);
				
		// Generate Graph ~O(k(k + 1)/2) = ~O(k^2)
		int[][] paths = new int[coinCount][coinCount]; 
		// Mario: Index 0, Peach: Index coinCount - 1
		for (int i = 0; i < paths.length; i++) {
			for (int j = i; j < paths.length; j++) {
				
				if (i == j) // Loop is Zero Distance
					paths[i][j] = 0;
				
				else { // Compute Shortest Path Between Coins/Mario/Peach
					int path = shortestPath(coins.get(i), coins.get(j), maze);
					if (path == Integer.MAX_VALUE)
						return -1;
					paths[i][j] = path;
					paths[j][i] = path;
				}
			}
		}
		
		// Ensure No Path Between Mario and Peach
		paths[0][coinCount - 1] = Integer.MAX_VALUE; // No Mario to Peach
		paths[coinCount - 1][0] = Integer.MAX_VALUE; // No Peach to Mario
		
		// Compute All Permutations of Paths O(n!)
		boolean[] visited = new boolean[coinCount];
		int[] route = new int[coinCount];
		route[0] = 0; // Start at Mario
		route[coinCount - 1] = coinCount - 1; // End at Peach
		int distance = 0;
		permutePaths(0, visited, paths, route, distance, 1);
		return shortestDistance;
	}
	
	/**
	 * shortestPath
	 * Compute shortest path between any two points in maze
	 * @return distance
	 */
	private static int shortestPath(Point start, Point end, int[][] maze) {
		int[][] cost = new int[maze.length][maze.length];
		
		// Initialize Cost O(n^2)
		for (int row = 0; row < maze.length; row++)
			for (int col = 0; col < maze.length; col++)
				cost[row][col] = Integer.MAX_VALUE;
		
		cost[start.row][start.col] = 0;
		
		// BFS Shortest Path ~O(4n^2)
		Queue<Point> positionQueue = new LinkedList<Point>();
		positionQueue.add(start);
		while (!positionQueue.isEmpty()) {
			Point position = positionQueue.remove();
			step(position, end, maze, cost, positionQueue, 1, 0); // Step Right
			step(position, end, maze, cost, positionQueue, -1, 0); // Step Left
			step(position, end, maze, cost, positionQueue, 0, 1); // Step Down
			step(position, end, maze, cost, positionQueue, 0, -1); // Step Up
		}
		
		return cost[end.row][end.col];
	}
	
	/**
	 * step
	 * If step is possible and cheapest, updates cost for step.
	 * If goal is not reached, adds step to positionQueue.
	 * @ sr distance to step along row from position
	 * @ sc distance to step along column from position
	 */
	private static void step(Point position, Point end, int[][] maze, int [][] cost, 
						     Queue<Point> positionQueue, int sr, int sc) {
		int row = position.row;
		int col = position.col;
		int stepRow = row + sr;
		int stepCol = col + sc;
		int endRow = end.row;
		int endCol = end.col;
		
		if ( stepCol >= 0 && stepCol < maze.length && stepRow >= 0 && stepRow < maze.length &&
			 maze[stepRow][stepCol] != 1 && cost[row][col] + 1 < cost[stepRow][stepCol] ) {
			
			cost[stepRow][stepCol] = cost[row][col] + 1; // Update Cost
			
			if ( stepRow == endRow && stepCol == endCol ) // Reached Goal
				return;
			
			positionQueue.add(new Point(stepRow, stepCol));
		}
	}

	/**
	 * permutePaths
	 * DFS permutation O(n!)
	 * Generates all possible paths and updates shortestRoute and shortestDistance
	 */
	private void permutePaths(int current, boolean[] visited, int[][] paths, 
									 int[] route, int distance, int i) {
		if (i == coinCount - 1) { // Only Peach Left
			distance += paths[current][coinCount - 1]; // Add distance to Peach from last coin 
			if (distance < shortestDistance) {
				shortestDistance = distance;
				setShortestRoute( route.clone() );
			} // else path and distance are discarded
		}
		
		// Check remaining coins and take uncounted path DFS
		for (int k = 1; k < coinCount - 1; k++) {
			if (!visited[k]) {
				visited[k] = true;
				route[i] = k;
				permutePaths(k, visited, paths, route, distance + paths[current][k], i + 1);
				visited[k] = false;				
			}			
		}
	}
	
	public int[] getShortestRoute() {
		return shortestRoute;
	}

	public void setShortestRoute(int[] shortestRoute) {
		this.shortestRoute = shortestRoute;
	}
}

class Point {
	int row;
	int col;
	Point(int row, int col) {
		this.row = row;
		this.col = col;
	}
}