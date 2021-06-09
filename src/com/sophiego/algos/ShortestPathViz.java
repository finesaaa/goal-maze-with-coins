package com.sophiego.algos;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import com.sophiego.algos.uis.Point;
import com.sophiego.shopie.Player;

public class ShortestPathViz {
	
	public int coinCount = 0;
	public Point src, dst;
	public int[][] maze;
	public int[] sequence;
	public int step = 0;

	public ShortestPathViz(int[][] maze, int[] sequence) {
		this.maze = maze;
		this.sequence = sequence;
	}

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
	
	private boolean getSteps(int x, int y, Point end, int[][] cost, int steps, int counter) {
		if (x == end.row && y == end.col && counter == steps) {
			return true;
		}
		
		boolean returnState = false;
		counter++;
		
		if (x < this.maze.length && !returnState) {
			if (cost[x + 1][y] == (counter - 1)) {
				returnState = getSteps(x + 1, y, end, cost, steps, counter);
				
			}	
		}

		if (x > 0 && !returnState) {
			if (cost[x - 1][y] == (counter - 1)) {
				returnState = getSteps(x - 1, y, end, cost, steps, counter);	
			}	
		}
		
		if (y < this.maze[0].length && !returnState) {
			if (cost[x][y + 1] == (counter - 1)) {
				returnState = getSteps(x, y + 1, end, cost, steps, counter);	
			}	
		}

		if (y > 0 && !returnState) {
			if (cost[x][y - 1] == (counter - 1)) {
				returnState = getSteps(x, y - 1, end, cost, steps, counter);	
			}	
		}
		
		return false;
	}

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
}
