package com.sophiego.states;

import java.awt.Graphics;

import com.sophiego.gfx.Assets;
import com.sophiego.gfx.Text;
import com.sophiego.helper.SoundLoader;
import com.sophiego.main.Window;

public class LoadingState extends State {

	private final String NAME = "LONELY DAY IN THE MOON . . . ";
	private String text = "";
	private int index = 0;
	private long time, lastTime;
	
	public LoadingState (Window window){
		super(window);
		SoundLoader.playSound("loading_sound.wav", 100, false);
		time = 0;
		lastTime = System.currentTimeMillis();
	}
	
	@Override
	public void update() {
		time += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		if(time > 50) {
			text = NAME.substring(0, index);
			if(index < NAME.length()){
				index++;
			} else{
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				State.currentState = window.getMenuState();
			}
			time = 0;
		}	
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.splashBG, 0, 0, null);
		g.setFont(Assets.fontLoading);
		Text.drawString(g, text, Window.WIDTH/2, Window.HEIGHT/2, true, Assets.mColor);
	}

}
