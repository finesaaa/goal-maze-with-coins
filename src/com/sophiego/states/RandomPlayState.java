package com.sophiego.states;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.sophiego.entities.Level;
import com.sophiego.gfx.Assets;
import com.sophiego.main.Window;
import com.sophiego.ui.Button;
import com.sophiego.ui.Click;

public class RandomPlayState extends State {
	private final int DOUBLETILESIZE = 80;
	private final int SPACE = 10;
	private Button back, play;
	private FontMetrics fm;
	private String text;
	private Level level;
	private Graphics gL;
	private final int NUMLEVEL = 15;
	private Level[] levels = new Level[NUMLEVEL];
	private int[][] maze = new int[ThreadLocalRandom.current().nextInt(8,12)][ThreadLocalRandom.current().nextInt(8,12)];
	
	private final int xOffset = (Window.WIDTH - (DOUBLETILESIZE + SPACE * 4)*5)/2;
	private final int yOffset = (Window.HEIGHT - (DOUBLETILESIZE + SPACE * 2)*3)/2;

	public RandomPlayState(Window window) {
		super(window);
		
		State.currentState = window.getRandomPlayState();
		
		back = new Button("Back To Menu", Window.WIDTH/2, Window.HEIGHT - 100, new Click() {
			
			@Override
			public void onClick() {
				State.currentState = window.getMenuState();
			}
			
		}, Assets.font30, new Color(0xFFDA77));
		
		play = new Button("Play", Window.WIDTH/2, Window.HEIGHT/2, new Click() {
			
			@Override
			public void onClick() {
				State.currentLevel = 1;
				playThisLevel(0);
			}
			
		}, Assets.font30, Assets.mColor);
		
		int row =  maze.length;
		int col =  maze[0].length;
		Random rn = new Random();
		
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				int[] arr = {0, 1, 3};
				int rnd = rn.nextInt(arr.length);
				this.maze[i][j] = arr[rnd];
			}
		}
		
		while(true) {
			int r = rn.nextInt(row);
			int c = rn.nextInt(col);
			if (this.maze[r][c] == 0) {
				this.maze[r][c] = 5;
				break;
			}
		}
		
		int prow, pcol;
		while(true) {
			int r = rn.nextInt(row);
			int c = rn.nextInt(col);
			if (this.maze[r][c] == 0) {
				prow = r;
				pcol = c;
				break;
			}
		}
		
		level = new Level(this.maze, 0, prow, pcol, 0, this);
		levels[0] = level;
		
	}
	
	public Level[] getLevels() {
		return levels;
	}

	@Override
	public void update() {
		back.update();
		play.update();
		
		int size = ThreadLocalRandom.current().nextInt(8,12);
		maze = new int[size][size];
				
		int row =  maze.length;
		int col =  maze[0].length;
		Random rn = new Random();
		
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				int[] arr = {0, 1, 3};
				int rnd = rn.nextInt(arr.length);
				this.maze[i][j] = arr[rnd];
			}
		}
		
		while(true) {
			int r = rn.nextInt(row);
			int c = rn.nextInt(col);
			if (this.maze[r][c] == 0) {
				this.maze[r][c] = 5;
				break;
			}
		}
		
		int prow, pcol;
		while(true) {
			int r = rn.nextInt(row);
			int c = rn.nextInt(col);
			if (this.maze[r][c] == 0) {
				prow = r;
				pcol = c;
				break;
			}
		}
		
		level = new Level(this.maze, 0, prow, pcol, 0, this);
		levels[0] = level;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.backBG, 0, 0, null);
		back.render(g);
		play.render(g);
	}
	
	public int getNUMLEVEL() {
		return NUMLEVEL;
	}
	
	public void showResult() {
		State.currentState = window.getResultState();
	}
	
	public void showGameOver() {
		State.currentState = window.getGameOverState();
	}
	
	public void showCongratsState() {
		State.currentState = window.getCongratsState();
	}
	
	public void  playThisLevel(int id_level) {
		((GameState)window.getGameState()).setLevel(levels[id_level]);
		State.currentState = window.getGameState();
	}

}
