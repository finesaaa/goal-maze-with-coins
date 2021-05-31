package com.sophiego.states;

import java.awt.FontMetrics;
import java.awt.Graphics;

import com.sophiego.gfx.Assets;
import com.sophiego.main.Window;
import com.sophiego.ui.Button;
import com.sophiego.ui.Click;

public class GameOverState extends State{
	
	private Button back, try_again;
	private FontMetrics fm;
	private String text;

	public GameOverState(Window window) {
		super(window);
		
		try_again = new Button("Play Again", Window.WIDTH/2 + 100, Window.HEIGHT/2 + 40, new Click() {
			
			@Override
			public void onClick() {
				((GameState)window.getGameState()).setLevel(State.currentArrLevel);
				State.currentState = window.getGameState();
			}
			
		}, Assets.font26, Assets.mColor);
		
		back = new Button("Back Home", Window.WIDTH/2 + 100, Window.HEIGHT/2 + 95, new Click() {
			
			@Override
			public void onClick() {
				((GameState)window.getGameState()).setLevel(State.currentArrLevel);
				State.currentState = window.getLevelSelectorState();
			}
			
		}, Assets.font26, Assets.bColor);
	}

	@Override
	public void update() {
		try_again.update();
		back.update();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.gameOverBG, 0 , -40, null);
		try_again.render(g);
		back.render(g);
		this.text = "SHOPIE RUN";
		
		//setting text
		g.setFont(Assets.fontOver);
		g.setColor(Assets.sColor);
		fm = g.getFontMetrics();
		g.drawString(text, Window.WIDTH/2 + fm.stringWidth(text)/2 - 145, 180);
		g.drawString("OUT OF ENERGY!", Window.WIDTH/2 + fm.stringWidth(text)/2 - 145, 240);
	}

}
