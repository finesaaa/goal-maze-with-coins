package com.sophiego.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.sophiego.gfx.Assets;
import com.sophiego.gfx.Text;

public class CoinPanel {

	private String text;
	private int x, y;
	private Font font;
	private int statusCoin, targetCoin;
	
	public CoinPanel(String text, int statusCoin, int targetCoin, int x, int y, Font font) {
		this.text = text;
		this.statusCoin = statusCoin;
		this.targetCoin = targetCoin;
		this.x = x;
		this.y = y;
		this.font = font;
	}

	public void update(int statusCoin) {
		this.statusCoin = statusCoin;
	}
	
	public void render(Graphics g){
		int radius = 8;
		int space = 8;
		g.setFont(font);
		g.setColor(Assets.mColor);
		Text.drawString(g, (text).toUpperCase() , x, y, true, Assets.mColor);
		
		//draw background coin
		for (int i = 0; i < targetCoin; i++) {
			g.setColor(Assets.lightGrayColor);
			g.fillOval(130 + i*space+radius*2*i , 50-radius ,  radius*2, radius*2);
		}
		for (int i = 0; i < statusCoin; i++) {
			g.setColor(Assets.bColor);
			g.fillOval( 130 + i*space+radius*2*i, 50-radius,  radius*2, radius*2);
		}
	}

}
