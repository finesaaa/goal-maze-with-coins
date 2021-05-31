package com.sophiego.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.sophiego.entities.Level;
import com.sophiego.states.LevelSelectorState;

public class LevelLoader {

	private LevelSelectorState levelSelectorState;
	
	public LevelLoader(LevelSelectorState levelSelectorState) {
		this.levelSelectorState = levelSelectorState;
	}
	
	public Level loadLevel(String path, int curr_level) {
		String file = loadFileAsString(path);
		String[] numbers = file.split("\\s+");
		
		int status_level = parseInt(numbers[0]);
		int cols = parseInt(numbers[1]);
		int rows = parseInt(numbers[2]);
	
		int player_col = parseInt(numbers[3]);
		int player_row = parseInt(numbers[4]);
		
		int[][] maze = new int[rows][cols];
		for(int row = 0; row < rows; row++)
			for(int col = 0; col < cols; col++)
				maze[row][col] = parseInt(numbers[(col + (row*cols)) + 5]);
		
		return new Level(maze, curr_level, player_row, player_col, status_level, this.levelSelectorState);
	}

	private int parseInt(String num) {
		try {
			return Integer.parseInt(num);
		}catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}


	private String loadFileAsString(String path) {
		StringBuilder builder = new StringBuilder();
		try {
			InputStream in = LevelSelectorState.class.getResourceAsStream(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			String line;
			while((line = br.readLine()) != null) {
				builder.append(line + "\n");
			}
			br.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return builder.toString();
	}

}
