package com.sophiego.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.sophiego.gfx.Assets;
import com.sophiego.gfx.Text;
import com.sophiego.handler.MouseHandler;
import com.sophiego.helper.SoundLoader;
import com.sophiego.states.LevelSelectorState;
import com.sophiego.states.State;

public class LevelButton {

	private final int DOUBLETILESIZE = 80;
	private String text;
	private int x, y;
	private Rectangle bounds;
	private boolean hovering, played, solved;
	private Click click;
	private LevelSelectorState levelSelectorState;
	private Font font;
	private Color color;
	private Color solvedColor = new Color(0x02A79F);
	private Color unsolvedColor = new Color(0xC4C4C4);
	
	public LevelButton(String text, int x, int y, Click click, boolean solved, boolean played, LevelSelectorState levelSelectorState) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.click = click;
		hovering = false;
		this.levelSelectorState = levelSelectorState;
		this.font = Assets.font22;
		this.played = played;
		this.solved = solved;
	}

	public void update(boolean solved, boolean played, int id) {
		this.played = played;
		this.solved = solved;
		if(bounds != null && bounds.contains(MouseHandler.x, MouseHandler.y)) {
			hovering = true;
			if(MouseHandler.left && played) {
				State.currentArrLevel = levelSelectorState.getLevels()[id];
				State.currentLevel = id + 1;
				SoundLoader.playSound("button_click_sound.wav", 100, false);
				click.onClick();
			}
		} else {
			hovering = false;
		}	
	}
	
	public void render(Graphics g){
		bounds = new Rectangle(x - DOUBLETILESIZE/2, y - DOUBLETILESIZE/2, DOUBLETILESIZE, DOUBLETILESIZE);
		g.setFont(font);
		if (this.played) 
			this.color = solvedColor;
		else
			this.color = unsolvedColor;
		if(hovering) {
			g.setColor(color.darker());
			g.fillRoundRect(x - DOUBLETILESIZE/2, y - DOUBLETILESIZE/2, DOUBLETILESIZE, DOUBLETILESIZE, 25, 25);
			Text.drawString(g, text, x, y - 6, true, Color.WHITE.darker());
		} else {
			g.setColor(color);
			g.fillRoundRect(x - DOUBLETILESIZE/2, y - DOUBLETILESIZE/2, DOUBLETILESIZE, DOUBLETILESIZE, 25, 25);
			Text.drawString(g, text, x, y - 6, true, Color.WHITE);
		}
		for (int s = 0;  s < 3; s++) {
			if(solved) 
				g.drawImage(Assets.mini_star, x + 20 * s - DOUBLETILESIZE/3, y + 10, null);
			else
				g.drawImage(Assets.mini_star_outline, x + 20 * s - DOUBLETILESIZE/3, y + 10, null);
		}
		
	}
	
	public void delayState() {
		try {
			Thread.sleep(State.DELAY);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


}
