package com.sophiego.states;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import com.sophiego.gfx.Assets;
import com.sophiego.main.Window;
import com.sophiego.ui.Button;
import com.sophiego.ui.Click;

public class CongratsState extends State{
	
	private Button back;
	private FontMetrics fm;
	private String text;

	public CongratsState(Window window) {
		super(window);
		
		back = new Button("Return", Window.WIDTH/4 - 50, Window.HEIGHT/2 - 50, new Click() {
			
			@Override
			public void onClick() {
				((GameState)window.getGameState()).setLevel(State.currentArrLevel);
				State.currentState = window.getLevelSelectorState();
			}
			
		}, Assets.font30, new Color(0xFFDA77));
	}

	@Override
	public void update() {
		back.update();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.congratsBG, 0 , -40, null);
		back.render(g);
		
		this.text = "YOU'RE A HERO";
		
		//setting text
		g.setFont(Assets.fontOver);
		g.setColor(Assets.mColor);
		fm = g.getFontMetrics();
		g.drawString(text, 75, Window.HEIGHT/2 - 120);

	}

}
