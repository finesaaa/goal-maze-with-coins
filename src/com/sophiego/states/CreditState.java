package com.sophiego.states;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import com.sophiego.gfx.Assets;
import com.sophiego.main.Window;
import com.sophiego.ui.Button;
import com.sophiego.ui.Click;

public class CreditState extends State{
	
	private Button back;
	private FontMetrics fm;
	private String text;

	public CreditState(Window window) {
		super(window);
		
		back = new Button("Back To Menu", Window.WIDTH/2, Window.HEIGHT - 155, new Click() {
			
			@Override
			public void onClick() {
				State.currentState = window.getMenuState();
			}
			
		}, Assets.font30, new Color(0xFFDA77));
	}

	@Override
	public void update() {
		back.update();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.backBG, 0, 0, null);
		g.drawImage(Assets.creditBG, 20, 15, null);
		back.render(g);
	}

}
