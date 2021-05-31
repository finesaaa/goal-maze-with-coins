package com.sophiego.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.sophiego.gfx.Assets;
import com.sophiego.gfx.Text;
import com.sophiego.main.Window;

public class StepCounterPanel {
	
	private String text;
	private int x, y;
	private Font font;
	private int numStep;
	private int targetNumStep;

	public StepCounterPanel(String text, int numStep, int targetNumStep, int x, int y, Font font) {
		this.text = text;
		this.numStep = numStep;
		this.targetNumStep = targetNumStep;
		this.x = x;
		this.y = y;
		this.font = font;
	}

	public void update(int numStep) {
		this.numStep = numStep;
	}
	
	public void render(Graphics g){
		int width = 180;
		int height = 32;
		double step = (numStep*180)/(double) targetNumStep;
		
		//background progress
		g.setColor(Assets.lightGrayColor);
		g.fillRect(Window.WIDTH/2 + 180 - 2, 35 - 2, width + 4, height + 4);
		
		//actual progress
		g.setColor(Assets.bColor);
		g.fillRect(Window.WIDTH/2 + 180, 35, 180 - (int) step, height);
		
		//set font
		g.setFont(font);
		g.setColor(Assets.mColor);
		Text.drawString(g, (text + numStep + " / " + targetNumStep).toUpperCase(), x, y, true, Assets.mColor);
	
	}
}
