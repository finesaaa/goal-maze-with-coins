package com.sophiego.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.sophiego.gfx.Text;
import com.sophiego.handler.MouseHandler;
import com.sophiego.helper.SoundLoader;
import com.sophiego.states.State;

public class Button {

	private String text;
	private int x, y;
	private FontMetrics fm;
	private Rectangle bounds;
	private boolean hovering;
	private Click click;
	private Font font;
	private Color color;
	
	public Button(String text, int x, int y, Click click, Font font, Color color) {
		this.x = x;
		this.y = y;
		
		this.click = click;
		hovering = false;
		
		this.text = (text).toUpperCase();
		this.font = font;
		this.color = color;
	}

	public void update() {
		if(bounds != null && bounds.contains(MouseHandler.x, MouseHandler.y)){
			hovering = true;
			if (MouseHandler.left) {
				SoundLoader.playSound("button_click_sound.wav", 100, false);
				delayState();
				click.onClick();
			}	
		} else {
			hovering = false;
		}
	}
	
	public void render(Graphics g){
		g.setFont(font);
		fm = g.getFontMetrics();
		if(hovering) {
			g.setColor(color.darker());
			g.fillRoundRect(x - fm.stringWidth(text)/2 - 25, y - fm.getHeight()/2 - 3, fm.stringWidth(text) + 50, fm.getHeight() + 6, 50, 50);
			Text.drawString(g, text, x, y, true, new Color(0xCCCCCC));
		} else {
			g.setColor(color);
			g.fillRoundRect(x - fm.stringWidth(text)/2 - 25, y - fm.getHeight()/2 - 3, fm.stringWidth(text) + 50, fm.getHeight() + 6, 50, 50);
			Text.drawString(g, text, x, y, true, Color.WHITE);
		}
		bounds = new Rectangle(x - fm.stringWidth(text)/2 - 25, y - fm.getHeight()/2 - 3, fm.stringWidth(text) + 50, fm.getHeight() + 6);
	}
	
	public void delayState() {
		try {
			Thread.sleep(State.DELAY);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
