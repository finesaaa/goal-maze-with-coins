package com.sophiego.helper;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundLoader {
	public static synchronized void playSound(final String filename, int volume, boolean loop) {
		  new Thread(new Runnable() {
			  public void run() {
			      try {
					  Clip clip = AudioSystem.getClip();
					  AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("res/sounds/" + filename).getAbsoluteFile());
					  clip.open(inputStream);
					  clip.start();
					  if(loop) {
						  clip.loop(Clip.LOOP_CONTINUOUSLY);
					  }
					  FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
					  float range = control.getMinimum();
					  float result = range * (1 - volume / 100.0f);
					  control.setValue(result);
			      } catch (Exception e) {
			        System.err.println(e.getMessage());
			      }
			  }
		  }).start();
	}
}
