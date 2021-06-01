package com.sophiego.algos.uis;

import java.util.LinkedList;
import java.util.Queue;

import com.sophiego.shopie.Player;
import com.sophiego.algos.ShortestPath;

import java.util.HashMap;

public class ShortestSteps extends ShortestPath {

	private int shortestDistance = Integer.MAX_VALUE;
	private int[] shortestRoute;

	public ShortestSteps(int[][] maze, Player player) {
		super(maze, player);
	}

	public int minMoves() {
		int superResult = super.minMoves();
		if (superResult != 0) {
			return superResult;
		}

		paths[0][coinCount - 1] = Integer.MAX_VALUE;
		paths[coinCount - 1][0] = Integer.MAX_VALUE;

		boolean[] visited = new boolean[coinCount];
		int[] route = new int[coinCount];
		route[0] = 0;
		route[coinCount - 1] = coinCount - 1; // End at Flag
		int distance = 0;
		permutePaths(0, visited, paths, route, distance, 1);
		return this.shortestDistance;
	}
	
	public int[][] getPaths(){
		return paths;
	}

	private void permutePaths(int current, boolean[] visited, int[][] paths, int[] route, int distance, int i) {
		if (i == coinCount - 1) {
			distance += paths[current][coinCount - 1];
			if (distance < this.shortestDistance) {
				this.shortestDistance = distance;
				setShortestRoute(route.clone());
			}
		}

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
