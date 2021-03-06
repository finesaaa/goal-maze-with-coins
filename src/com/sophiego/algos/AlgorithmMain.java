package com.sophiego.algos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import com.sophiego.states.LevelSelectorState;
import com.sophiego.algos.ga.GeneticAlgorithm;
import com.sophiego.algos.uis.ShortestSteps;
import com.sophiego.shopie.Player;

public class AlgorithmMain {
	public static void main(String[] args) {
		
		String file = getFile(1);
		String[] numbers = file.split("\\s+");
		
		int cols = parseInt(numbers[1]);
		int rows = parseInt(numbers[2]);

		int player_col = parseInt(numbers[3]);
		int player_row = parseInt(numbers[4]);
		Player player = new Player(player_row, player_col);
		
		int[][] maze = new int[rows][cols];
		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < cols; col++) {
				maze[row][col] = parseInt(numbers[(col + (row*cols)) + 5]);
				if (maze[row][col] == 5) {
					player.setEndRow(row);
					player.setEndCol(col);
				}
			}
		}
		
		ShortestSteps shortestPath = new ShortestSteps(maze, player);
		System.out.println(shortestPath.minMoves());

        GeneticAlgorithm ga = new GeneticAlgorithm(maze, player);
		System.out.println(ga.minMoves());
	}
	
	public static String getFile(int level) {
		StringBuilder builder = new StringBuilder();
		try {
			InputStream in = LevelSelectorState.class.getResourceAsStream("/levels/" + level + ".txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			String line;
			while((line = br.readLine()) != null) {
				builder.append(line + "\n");
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return builder.toString();
	}

	private static int parseInt(String num) {
		try {
			return Integer.parseInt(num);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
