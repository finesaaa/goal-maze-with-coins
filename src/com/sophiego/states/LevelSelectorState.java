package com.sophiego.states;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

import javax.swing.JOptionPane;

import com.sophiego.entities.Level;
import com.sophiego.gfx.Assets;
import com.sophiego.gfx.Text;
import com.sophiego.helper.LevelLoader;
import com.sophiego.helper.LevelWriter;
import com.sophiego.main.Window;
import com.sophiego.ui.Button;
import com.sophiego.ui.Click;
import com.sophiego.ui.LevelButton;

public class LevelSelectorState extends State {

	private final int DOUBLETILESIZE = 80;
	private final int NUMLEVEL = 15;
	private Level[] levels = new Level[NUMLEVEL];
	private final int SPACE = 10;
	private Graphics gL;

	private final int xOffset = (Window.WIDTH - (DOUBLETILESIZE + SPACE * 4) * 5) / 2;
	private final int yOffset = (Window.HEIGHT - (DOUBLETILESIZE + SPACE * 2) * 3) / 2;

	private Button back, reset;
	private LevelButton[] bLevels = new LevelButton[NUMLEVEL];
	private LevelLoader loader;

	public LevelSelectorState(Window window) {
		super(window);

		loader = new LevelLoader(this);

		for (int id = 0; id < NUMLEVEL; id++) {
//			System.out.println("LEVEL " + id);
			levels[id] = loader.loadLevel("/levels/" + id + ".txt", id);
		}

		int counter = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 5; j++) {
				int spaceX = j * 45;
				int spaceY = i * 20;

				bLevels[counter] = new LevelButton(String.valueOf(counter + 1),
						xOffset + j * DOUBLETILESIZE + spaceX + DOUBLETILESIZE / 2,
						yOffset + i * DOUBLETILESIZE + spaceY + DOUBLETILESIZE / 2, new Click() {
							@Override
							public void onClick() {
								if (levels[State.currentLevel - 1].isSolved()) {
									if (State.currentLevel == NUMLEVEL)
										showCongratsState();
									else
										showResult();
								} else {
									playThisLevel(State.currentLevel - 1);
								}
							}

						}, levels[counter].isSolved(), levels[counter].isPlayed(), this);
				counter++;
			}
		}

		back = new Button("Back", Window.WIDTH / 2, Window.HEIGHT - 100, new Click() {

			@Override
			public void onClick() {
				State.currentState = window.getMenuState();
			}

		}, Assets.font26, Assets.bColor);

		reset = new Button("Reset", 120, Window.WIDTH / 2 + 100, new Click() {
			@Override
			public void onClick() {
				int isReset = JOptionPane.showConfirmDialog(null, "Do you really want to reset the game?",
						"Confirm Reset", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (isReset == JOptionPane.YES_OPTION) {
					resetLevel();
				}
			}
		}, Assets.font26, Assets.sColor);

	}

	@Override
	public void update() {
		back.update();
		reset.update();
		for (int i = 0; i < NUMLEVEL; i++) {
			bLevels[i].update(levels[i].isSolved(), levels[i].isPlayed(), i);
		}
	}

	@Override
	public void render(Graphics g) {

		this.gL = g;

		// background image
		g.drawImage(Assets.moon, -100, -50, null);
		g.drawImage(Assets.uranus, 0, Window.HEIGHT / 2 + 70, null);
		g.drawImage(Assets.jupiter, Window.WIDTH / 2 + 180, -100, null);
		g.drawImage(Assets.saturn2, Window.WIDTH / 2 + 150, Window.HEIGHT / 2, null);

		back.render(g);
		reset.render(g);

		// header level
		g.setFont(Assets.fontTitle);
		Text.drawString(g, "Level", Window.WIDTH / 2, Window.HEIGHT / 9, true, new Color(0, 45, 42));

		for (int i = 0; i < NUMLEVEL; i++) {
			bLevels[i].render(g);
		}
	}

	public int getNUMLEVEL() {
		return NUMLEVEL;
	}

	public void resetLevel() {
		for (int id = 0; id < NUMLEVEL; id++) {
			try {
				LevelWriter.writeToPosition("./res/levels/" + (id) + ".txt", "0", 0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for (int id = 0; id < NUMLEVEL; id++)
			levels[id] = loader.loadLevel("/levels/" + id + ".txt", id);

		render(this.gL);
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

	public Level[] getLevels() {
		return levels;
	}

	public void playThisLevel(int id_level) {
		((GameState) window.getGameState()).setLevel(levels[id_level]);
		State.currentState = window.getGameState();
	}

}
