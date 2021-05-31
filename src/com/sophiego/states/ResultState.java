package com.sophiego.states;

import java.awt.FontMetrics;
import java.awt.Graphics;

import com.sophiego.gfx.Assets;
import com.sophiego.helper.SoundLoader;
import com.sophiego.main.Window;
import com.sophiego.ui.Button;
import com.sophiego.ui.Click;

public class ResultState extends State{
	
	private Button back, play_again;
	private FontMetrics fm;
	private String text;

	public ResultState(Window window) {
		super(window);
		
		back = new Button("Okay", Window.WIDTH/2 - 120, Window.HEIGHT/2 + 75, new Click() {
			
			@Override
			public void onClick() {
				State.currentState = window.getLevelSelectorState();
			}
			
		}, Assets.font26, Assets.mColor);
		
		play_again = new Button("Play Again", Window.WIDTH/2 + 100, Window.HEIGHT/2 + 75, new Click() {
			
			@Override
			public void onClick() {
				State.currentArrLevel.tryAgainLevel(State.currentArrLevel.getId());
			}
			
		}, Assets.font26, Assets.sColor);
	}

	@Override
	public void update() {
		back.update();
		play_again.update();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.backBG, 0 , 0, null);
		back.render(g);
		play_again.render(g);
		this.text = "You've passed level " + (State.currentLevel);
		
		g.setFont(Assets.fontLoading);
		g.setColor(Assets.mColor);
		fm = g.getFontMetrics();
		g.drawString(text, Window.WIDTH/2 - fm.stringWidth(text)/2, Window.HEIGHT/2 - 75 - 40);
		for (int i = 0;  i < 3; i++) 
			g.drawImage(Assets.star, Window.WIDTH/2 - 100 + i*64, Window.HEIGHT/2 - 45 - 30, null);
	}
}
