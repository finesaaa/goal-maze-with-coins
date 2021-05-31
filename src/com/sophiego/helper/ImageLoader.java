package com.sophiego.helper;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.sophiego.gfx.Assets;

public class ImageLoader {

	public static BufferedImage loadImage(String path)
	{
		try {
			return ImageIO.read(Assets.class.getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
