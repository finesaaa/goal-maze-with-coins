package com.sophiego.algos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.sophiego.helper.LevelLoader;
import com.sophiego.states.LevelSelectorState;
import com.sophiego.shopie.Player;

public class AlgorithmMain {
	public static void main(String[] args) {
		StringBuilder builder = new StringBuilder();
		try {
			InputStream in = LevelSelectorState.class.getResourceAsStream("/levels/3.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			String line;
			while((line = br.readLine()) != null) {
				builder.append(line + "\n");
			}
			br.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		String file = builder.toString();
		String[] numbers = file.split("\\s+");
		
		int cols = parseInt(numbers[1]);
		int rows = parseInt(numbers[2]);
	
		int player_col = parseInt(numbers[3]);
		int player_row = parseInt(numbers[4]);
		
		int[][] maze = new int[rows][cols];
		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < cols; col++) {
				maze[row][col] = parseInt(numbers[(col + (row*cols)) + 5]);
				System.out.print(maze[row][col]);
			}
			System.out.println("");
		}
		
		ShortestPath shortestPath = new ShortestPath(maze, new Player(player_row, player_col));
		System.out.println(shortestPath.minMoves());
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
