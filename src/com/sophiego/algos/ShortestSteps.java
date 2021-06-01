package com.sophiego.algos;

import java.util.LinkedList;
import java.util.Queue;

import com.sophiego.shopie.Player;

import java.util.HashMap;

public class ShortestSteps {

	private int shortestDistance = Integer.MAX_VALUE;
	private int[] shortestRoute;
	private int coinCount = 0;

	private Point src, dst, curr;
	private int[][] maze;

	public ShortestSteps(int[][] maze, Player player) {
		this.maze = maze;

		this.src = new Point(player.getStartRow(), player.getStartCol());
		this.dst = new Point(player.getEndRow(), player.getEndCol());
		this.curr = new Point(maze.length, maze[0].length);
	}

	public int minMoves() {
		if (shortestPath(this.src, this.dst) == Integer.MAX_VALUE)
			return -1;

		// Locate the Coins O(n^2)
		HashMap<Integer, Point> coins = new HashMap<Integer, Point>(8);
		coins.put(0, this.src); // Add Sophie
		this.coinCount++;
		for (int row = 0; row < this.maze.length; row++) {
			for (int col = 0; col < this.maze[0].length; col++) {
//				System.out.println(this.maze[row][col] + " " + this.maze.length + " - row :" + row + " - col :" + col);
				if (this.maze[row][col] == 3) {
					coins.put(this.coinCount, new Point(row, col));
					this.coinCount++;
				}
			}
		}
		coins.put(this.coinCount, this.dst);
		this.coinCount++; // Number of coins plus 2 (Sophie and Flag)

		if (this.coinCount == 3)
			return shortestPath(this.src, this.dst);

		// Generate Graph ~O(k(k + 1)/2) = ~O(k^2)
		int[][] paths = new int[this.coinCount][this.coinCount];
		// Sophie: Index 0, Flag: Index coinCount - 1
		for (int i = 0; i < paths.length; i++) {
			for (int j = i; j < paths.length; j++) {

				if (i == j) // Loop is Zero Distance
					paths[i][j] = 0;

				else { // Compute Shortest Path Between Coins/Sophie/Flag
					int path = shortestPath(coins.get(i), coins.get(j));
					if (path == Integer.MAX_VALUE)
						return -1;
					paths[i][j] = path;
					paths[j][i] = path;
				}
			}
		}

		// Ensure No Path Between Sophie and Flag
		paths[0][this.coinCount - 1] = Integer.MAX_VALUE; // No Sophie to Flag
		paths[this.coinCount - 1][0] = Integer.MAX_VALUE; // No Flag to Sophie

		// Compute All Permutations of Paths O(n!)
		boolean[] visited = new boolean[this.coinCount];
		int[] route = new int[this.coinCount];
		route[0] = 0; // Start at Sophie
		route[this.coinCount - 1] = this.coinCount - 1; // End at Flag
		int distance = 0;
		permutePaths(0, visited, paths, route, distance, 1);
		return this.shortestDistance;
	}

	/**
	 * shortestPath Compute shortest path between any two points in maze
	 * 
	 * @return distance
	 */
	private int shortestPath(Point start, Point end) {
		int[][] cost = new int[this.maze.length][this.maze[0].length];

		for (int row = 0; row < this.maze.length; row++)
			for (int col = 0; col < this.maze[0].length; col++)
				cost[row][col] = Integer.MAX_VALUE;

		cost[start.row][start.col] = 0;

		// BFS Shortest Path ~O(4n^2)
		Queue<Point> positionQueue = new LinkedList<Point>();
		positionQueue.add(start);
		while (!positionQueue.isEmpty()) {
			Point position = positionQueue.remove();
			step(position, end, cost, positionQueue, 1, 0); // Step Right
			step(position, end, cost, positionQueue, -1, 0); // Step Left
			step(position, end, cost, positionQueue, 0, 1); // Step Down
			step(position, end, cost, positionQueue, 0, -1); // Step Up
		}

		return cost[end.row][end.col];
	}

	/**
	 * step If step is possible and cheapest, updates cost for step. If goal is not
	 * reached, adds step to positionQueue. @ sr distance to step along row from
	 * position @ sc distance to step along column from position
	 */
	private void step(Point position, Point end, int[][] cost, Queue<Point> positionQueue, int sr, int sc) {
		int row = position.row;
		int col = position.col;
		int stepRow = row + sr;
		int stepCol = col + sc;
		int endRow = end.row;
		int endCol = end.col;

		if (stepCol >= 0 && stepCol < this.maze[0].length && stepRow >= 0 && stepRow < this.maze.length
				&& this.maze[stepRow][stepCol] != 1 && cost[row][col] + 1 < cost[stepRow][stepCol]) {

			cost[stepRow][stepCol] = cost[row][col] + 1; // Update Cost

			if (stepRow == endRow && stepCol == endCol) // Reached Goal
				return;

			positionQueue.add(new Point(stepRow, stepCol));
		}
	}

	/**
	 * permutePaths DFS permutation O(n!) Generates all possible paths and updates
	 * shortestRoute and shortestDistance
	 */
	private void permutePaths(int current, boolean[] visited, int[][] paths, int[] route, int distance, int i) {
		if (i == coinCount - 1) { // Only Peach Left
			distance += paths[current][this.coinCount - 1]; // Add distance to Peach from last coin
			if (distance < this.shortestDistance) {
				this.shortestDistance = distance;
				setShortestRoute(route.clone());
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
		return this.shortestRoute;
	}

	public void setShortestRoute(int[] shortestRoute) {
		this.shortestRoute = shortestRoute;
	}
}
