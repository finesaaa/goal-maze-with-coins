package com.sophiego.entities;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import com.sophiego.algos.ga.GeneticAlgorithm;
import com.sophiego.algos.uis.ShortestSteps;
import com.sophiego.gfx.Assets;
import com.sophiego.handler.KeyboardHandler;
import com.sophiego.helper.LevelWriter;
import com.sophiego.helper.SoundLoader;
import com.sophiego.main.Window;
import com.sophiego.shopie.Player;
import com.sophiego.states.CreditState;
import com.sophiego.states.LevelSelectorState;
import com.sophiego.states.State;
import com.sophiego.ui.*;

public class Level {

	public static final int TILESIZE = 36;
	
	private int[][] maze;
	private int[][] backup_maze;
	
	private int num_coin, target_num_coin, num_step, target_num_step;
	
	private int xOffset, yOffset;
	private long time, lastTime;
	
	private final int DELAY = 150;
	
	private Button restart, back;
	private CoinPanel coinPanel;
	private StepCounterPanel stepCounterPanel;
	private ShortestSteps shortestPath;
	private GeneticAlgorithm ga;
	private boolean solved, played;
	
	private int statusLevel;
	private State levelSelectorState;
	
	private int id;
	private FontMetrics fm;
	private String text;
	private Player player;
	
	public Level(int[][] maze, int id_level, int player_row, int player_col, int status_level, LevelSelectorState levelSelectorState) {
		
		this.levelSelectorState = (LevelSelectorState) levelSelectorState;
		player = new Player(player_row, player_col);

		this.statusLevel = status_level;
		this.maze = maze;
		this.num_coin = 0;
		this.target_num_coin = 0;
		this.num_step = 0;
		this.id = id_level;
		
		backup_maze = new int[maze.length][maze[0].length];
		
		for (int row = 0; row < maze.length; row++) {
			for (int col = 0; col < maze[row].length; col++)
			{
				backup_maze[row][col] = maze[row][col];
				if (maze[row][col] == 3) this.target_num_coin++;
				if (maze[row][col] == 5) {
					player.setEndRow(row);
					player.setEndCol(col);
				}
			}
			
	        ga = new GeneticAlgorithm(this.maze, this.player);
			target_num_step = ga.minMoves();
			
			if(this.statusLevel == 1) {
				solved = true;
				played = true;
			} else if (this.statusLevel == 0){
				if (id_level == 0) played = true;
				if (id_level > 0) 
					if(levelSelectorState.getLevels()[id_level - 1].isSolved()) 
						played = true;
				solved = false;
			}
			
			xOffset = (Window.WIDTH - maze[0].length * TILESIZE)/2;
			yOffset = (Window.HEIGHT - maze.length * TILESIZE)/2;
			
			restart = new Button("Restart", 100, Window.HEIGHT/2, new Click(){
				@Override
				public void onClick() {
					reset();
				}}, Assets.font22, Assets.sColor);
			back = new Button("Back", Window.WIDTH - 100, Window.HEIGHT/2, new Click() {
				@Override
				public void onClick() {
					try {
						Thread.sleep(600);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					State.currentState = levelSelectorState;
				}
			}, Assets.font22, Assets.bColor);
			coinPanel = new CoinPanel("Coin: ", num_coin, target_num_coin, 80, 50, Assets.font30);
			stepCounterPanel = new StepCounterPanel("Energy: ", num_step, target_num_step, Window.WIDTH - 130, 52, Assets.font20);
			time = 0;
			lastTime = System.currentTimeMillis();
		}
	}
	
	public Level(int[][] maze, int id_level, int player_row, int player_col, int status_level, CreditState levelSelectorState) {
		
		this.levelSelectorState = (CreditState) levelSelectorState;
		player = new Player(player_row, player_col);

		this.statusLevel = status_level;
		this.maze = maze;
		this.num_coin = 0;
		this.target_num_coin = 0;
		this.num_step = 0;
		this.id = id_level;
		
		backup_maze = new int[maze.length][maze[0].length];
		
		for (int row = 0; row < maze.length; row++) {
			for (int col = 0; col < maze[row].length; col++)
			{
				backup_maze[row][col] = maze[row][col];
				if (maze[row][col] == 3) this.target_num_coin++;
				if (maze[row][col] == 5) {
					player.setEndRow(row);
					player.setEndCol(col);
				}
			}
			
	        ga = new GeneticAlgorithm(this.maze, this.player);
			target_num_step = ga.minMoves();
			
			if(this.statusLevel == 1) {
				solved = true;
				played = true;
			} else if (this.statusLevel == 0){
				if (id_level == 0) played = true;
				if (id_level > 0) 
					if(levelSelectorState.getLevels()[id_level - 1].isSolved()) 
						played = true;
				solved = false;
			}
			
			xOffset = (Window.WIDTH - maze[0].length * TILESIZE)/2;
			yOffset = (Window.HEIGHT - maze.length * TILESIZE)/2;
			
			restart = new Button("Restart", 100, Window.HEIGHT/2, new Click(){
				@Override
				public void onClick() {
					reset();
				}}, Assets.font22, Assets.sColor);
			back = new Button("Back", Window.WIDTH - 100, Window.HEIGHT/2, new Click() {
				@Override
				public void onClick() {
					try {
						Thread.sleep(600);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					State.currentState = levelSelectorState;
				}
			}, Assets.font22, Assets.bColor);
			coinPanel = new CoinPanel("Coin: ", num_coin, target_num_coin, 80, 50, Assets.font30);
			stepCounterPanel = new StepCounterPanel("Energy: ", num_step, target_num_step, Window.WIDTH - 130, 52, Assets.font20);
			time = 0;
			lastTime = System.currentTimeMillis();
		}
	}
	

	private void reset() {
		for(int row = 0; row < maze.length; row++)
			for(int col = 0; col < maze[row].length; col++)
				maze[row][col] = backup_maze[row][col];
		
		num_step = 0;
		num_coin = 0;
		stepCounterPanel.update(num_step);
		coinPanel.update(num_coin);
		player.resetPosition();
		player.resetTexture();
	}
	
	public void update() {
		time += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		
		if (KeyboardHandler.isPressed && time > DELAY) {
			if(KeyboardHandler.UP) {
				move(-1, 0);
				player.goUp();
			}
			if(KeyboardHandler.LEFT) {
				move(0, -1);
				player.goLeft();
			}
			if(KeyboardHandler.DOWN) {
				move(1, 0);
				player.goDown();
			}
			if(KeyboardHandler.RIGHT) {
				move(0, 1);
				player.goRight();
			}
		}
		
		restart.update();
		back.update();
		
		for(int row = 0; row < maze.length; row++)
			for(int col = 0; col < maze[row].length; col++)
			{
				if (num_step == target_num_step && target_num_step != -1) {
					player.dead();
				}
				if(num_step > target_num_step && target_num_step != -1) {
					SoundLoader.playSound("game_over_sound.wav", 100, false);
					try {
						Thread.sleep(400);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					((CreditState) levelSelectorState).showGameOver();
					reset();
				}
				if(maze[row][col] == 3 || maze[row][col] == 5) return;
			}
	
		((CreditState) levelSelectorState).getLevels()[id].setPlayed(true);
		((CreditState) levelSelectorState).getLevels()[id].setSolved(true);
		if (((CreditState) levelSelectorState).getLevels()[id].isSolved()) {
			State.currentLevel = id + 1;
			try {
				LevelWriter.writeToPosition("./res/levels/"+(State.currentLevel - 1) + ".txt", "1", 0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (id == (((CreditState) levelSelectorState).getNUMLEVEL() - 1)) {
				SoundLoader.playSound("game_complete_sound.wav", 100, false);
				((CreditState) levelSelectorState).showCongratsState();
			} else {
				SoundLoader.playSound("level_complete_sound.wav", 100, false);
				((CreditState) levelSelectorState).getLevels()[State.currentLevel].setPlayed(true);
				((CreditState) levelSelectorState).showResult();
			}
		}
	}

	private void move(int row, int col) {

		if(maze[player.getRow() + row][player.getCol() + col] != 1) {
			num_step++;
			stepCounterPanel.update(num_step);
			if(maze[player.getRow() + row ][player.getCol() + col] == 3)  {
				SoundLoader.playSound("coin_picked_sound.wav", 100, false);
				maze[player.getRow() + row][player.getCol() + col] = 4; //ganti brick
				this.num_coin++;
				coinPanel.update(num_coin);
			}
			if(maze[player.getRow() + row ][player.getCol() + col] == 5 && num_coin == target_num_coin)  {
				maze[player.getRow() + row][player.getCol() + col] = 4; //ganti brick
			}
			
			player.updatePosition(row, col);
		}
		time = 0;
	}
	
	public void render(Graphics g) {
		restart.render(g);
		back.render(g);
		coinPanel.render(g);
		stepCounterPanel.render(g);

		this.text = "Level " + (this.id + 1);
		g.setFont(Assets.fontLevel);
		g.setColor(Assets.mColor);
		fm = g.getFontMetrics();
		g.drawString(text, Window.WIDTH/2 - fm.stringWidth(text)/2, 100);
		
		for (int row = 0; row < maze.length; row++) {
			for (int col = 0;  col < maze[row].length; col++) {
				drawMapAsset(g, Assets.floor, col, row);
				if(maze[row][col] == 1) drawMapAsset(g, Assets.wall, col, row);
				if(maze[row][col] == 3) drawMapAsset(g, Assets.coin, col, row);
				if(maze[row][col] == 4) drawMapAsset(g, Assets.boxOn, col, row);
				if(maze[row][col] == 5) drawMapAsset(g, Assets.flag, col, row);
			}
		}
	
		g.drawImage(player.getTexture(), xOffset + player.getCol()*TILESIZE, yOffset + player.getRow()*TILESIZE, null);
	}
	
	public void drawMapAsset(Graphics g, Image img, int col, int row) {
		g.drawImage(img, xOffset + col*TILESIZE, yOffset + row*TILESIZE, null);
	}

	public int getId() {
		return id;
	}

	public void tryAgainLevel(int id_level) {
		reset();
		((CreditState) levelSelectorState).playThisLevel(id_level);
	}
	
	public boolean isPlayed() {return played;};
	public void setPlayed(boolean bool) {played = bool;};
	public boolean isSolved() {return solved;};
	public void setSolved(boolean bool) {solved = bool;}; 

}
