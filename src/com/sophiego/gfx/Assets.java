package com.sophiego.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;

import com.sophiego.entities.Level;
import com.sophiego.helper.FontLoader;
import com.sophiego.helper.ImageLoader;
import com.sophiego.main.Window;

public class Assets {
	public static final int TILESIZE = 48;
	public static final int LOGOSIZE = 200;
	public static final Color mColor = new Color(2, 167, 159); //main
	public static final Color sColor = new Color(255, 70, 70); //secondary
	public static final Color bColor = new Color(0xFFDA77);
	public static final Color lightGrayColor = new Color(230, 230, 230);
	
	public static Image playerLeft, playerBack, playerRight, PlayerFront, playerDead;
	public static Image logo, flag, star_outline, star, mini_star, mini_star_outline, ufo;
	public static Image floor, wall, boxOn, boxOff, coin;
	public static Image moon, uranus, jupiter, saturn, saturn2;
	public static Image splashBG, baseBG, congratsBG, gameOverBG, backBG, creditBG;
	
	public static Font font20, font22, font26, font30, font36, font48, square48;
	public static Font fontTitle, fontLoading, fontLevel, fontOver;
	
	public static void init()
	{
		//player image
		playerLeft = ImageLoader.loadImage("/player/left.png").getScaledInstance(Level.TILESIZE, Level.TILESIZE, BufferedImage.SCALE_DEFAULT);
		playerBack = ImageLoader.loadImage("/player/back.png").getScaledInstance(Level.TILESIZE, Level.TILESIZE, BufferedImage.SCALE_DEFAULT);
		PlayerFront = ImageLoader.loadImage("/player/front.png").getScaledInstance(Level.TILESIZE, Level.TILESIZE, BufferedImage.SCALE_DEFAULT);
		playerRight = ImageLoader.loadImage("/player/right.png").getScaledInstance(Level.TILESIZE, Level.TILESIZE, BufferedImage.SCALE_DEFAULT);
		playerDead = ImageLoader.loadImage("/player/dead.png").getScaledInstance(Level.TILESIZE, Level.TILESIZE, BufferedImage.SCALE_DEFAULT);
		
		//background
		splashBG = ImageLoader.loadImage("/blocks/bg_splash.png").getScaledInstance(Window.WIDTH, Window.HEIGHT, BufferedImage.SCALE_DEFAULT);
		baseBG = ImageLoader.loadImage("/blocks/bg_base.png").getScaledInstance(TILESIZE, TILESIZE, BufferedImage.SCALE_DEFAULT);
		congratsBG = ImageLoader.loadImage("/blocks/bg_congrats.png").getScaledInstance(Window.WIDTH, Window.HEIGHT + 10, BufferedImage.SCALE_DEFAULT);
		gameOverBG = ImageLoader.loadImage("/blocks/bg_gameover.png").getScaledInstance(Window.WIDTH, Window.HEIGHT + 10, BufferedImage.SCALE_DEFAULT);
		backBG = ImageLoader.loadImage("/blocks/bg_back.png").getScaledInstance(Window.WIDTH, Window.HEIGHT + 10, BufferedImage.SCALE_DEFAULT);
		creditBG = ImageLoader.loadImage("/blocks/bg_credit.png").getScaledInstance(720 , 546, BufferedImage.SCALE_DEFAULT);
		
		//map
		floor = ImageLoader.loadImage("/blocks/ground.png").getScaledInstance(TILESIZE, TILESIZE, BufferedImage.SCALE_DEFAULT);
		boxOn = ImageLoader.loadImage("/blocks/ground_after.png").getScaledInstance(Level.TILESIZE, Level.TILESIZE, BufferedImage.SCALE_DEFAULT);
		wall = ImageLoader.loadImage("/blocks/ground_redbrick.png").getScaledInstance(TILESIZE, TILESIZE, BufferedImage.SCALE_DEFAULT);
		
		coin = ImageLoader.loadImage("/blocks/coin.png").getScaledInstance(Level.TILESIZE, Level.TILESIZE, BufferedImage.SCALE_DEFAULT);
		flag = ImageLoader.loadImage("/blocks/flag.png").getScaledInstance(64, 64, BufferedImage.SCALE_DEFAULT);
		
		star = ImageLoader.loadImage("/blocks/star.png").getScaledInstance(64, 64, BufferedImage.SCALE_DEFAULT);
		star_outline = ImageLoader.loadImage("/blocks/star_outline.png").getScaledInstance(64, 64, BufferedImage.SCALE_DEFAULT);
		mini_star = ImageLoader.loadImage("/blocks/star.png").getScaledInstance(14, 14, BufferedImage.SCALE_DEFAULT);
		mini_star_outline =  ImageLoader.loadImage("/blocks/star_outline.png").getScaledInstance(14, 14, BufferedImage.SCALE_DEFAULT);
		
		//menu state
		logo = ImageLoader.loadImage("/blocks/logo-hd.png").getScaledInstance(LOGOSIZE, LOGOSIZE, BufferedImage.SCALE_DEFAULT);
		ufo = ImageLoader.loadImage("/blocks/ufo.png").getScaledInstance(450, 450, BufferedImage.SCALE_DEFAULT);
		saturn = ImageLoader.loadImage("/blocks/planets/saturnus.png").getScaledInstance(360, 360, BufferedImage.SCALE_DEFAULT);
		
		//level selector state
		moon = ImageLoader.loadImage("/blocks/planets/moon.png").getScaledInstance(LOGOSIZE, LOGOSIZE, BufferedImage.SCALE_DEFAULT);
		uranus = ImageLoader.loadImage("/blocks/planets/uranus.png").getScaledInstance(LOGOSIZE, LOGOSIZE, BufferedImage.SCALE_DEFAULT);
		jupiter = ImageLoader.loadImage("/blocks/planets/jupiter.png").getScaledInstance(360, 360, BufferedImage.SCALE_DEFAULT);
		saturn2 = ImageLoader.loadImage("/blocks/planets/saturnus2.png").getScaledInstance(360, 360, BufferedImage.SCALE_DEFAULT);
		
		//font
		square48 = FontLoader.loadFont("res/fonts/square.ttf", 48);
		font48 = FontLoader.loadFont("res/fonts/Poppins-SemiBold.ttf", 48);
		font22 = FontLoader.loadFont("res/fonts/Poppins-SemiBold.ttf", 22);
		font20 = FontLoader.loadFont("res/fonts/Poppins-SemiBold.ttf", 20);
		font26 = FontLoader.loadFont("res/fonts/Poppins-SemiBold.ttf", 24);
		font30 = FontLoader.loadFont("res/fonts/Poppins-SemiBold.ttf", 30);
		font36 = FontLoader.loadFont("res/fonts/Poppins-SemiBold.ttf", 36);
		fontTitle = FontLoader.loadFont("res/fonts/PoetsenOne.ttf", 56);
		fontLoading = FontLoader.loadFont("res/fonts/square.ttf", 32);
		fontLevel = FontLoader.loadFont("res/fonts/square.ttf", 30);
		fontOver = FontLoader.loadFont("res/fonts/square.ttf", 36);
	}

	public static void setStar(Image star) {
		Assets.star = star;
	}
	
}
