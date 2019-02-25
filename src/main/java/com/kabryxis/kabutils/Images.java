package com.kabryxis.kabutils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Images {
	
	public static BufferedImage reduce(Image image, int maxWidth, int maxHeight) {
		int iw = image.getWidth(null);
		int ih = image.getHeight(null);
		int resizeWidth, resizeHeight;
		double ratio = (double)iw / maxWidth;
		resizeHeight = (int)(ih / ratio);
		if(resizeHeight >= maxHeight) {
			ratio = (double)ih / maxHeight;
			resizeHeight = (int)(ih / ratio);
		}
		resizeWidth = (int)(iw / ratio);
		BufferedImage result = new BufferedImage(resizeWidth, resizeHeight, 2);
		Graphics2D graphics = result.createGraphics();
		graphics.drawImage(image, 0, 0, resizeWidth, resizeHeight, null);
		graphics.dispose();
		return result;
	}
	
	public static int[] getColors(Image image) {
		BufferedImage temp = new BufferedImage(image.getWidth(null), image.getHeight(null), 2);
		Graphics2D graphics = temp.createGraphics();
		graphics.drawImage(image, 0, 0, null);
		graphics.dispose();
		int[] pixels = new int[temp.getWidth() * temp.getHeight()];
		temp.getRGB(0, 0, temp.getWidth(), temp.getHeight(), pixels, 0, temp.getWidth());
		return pixels;
	}
	
	public static BufferedImage copy(Image image) {
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		BufferedImage result = new BufferedImage(width, height, 2);
		Graphics2D graphics = result.createGraphics();
		graphics.drawImage(image, 0, 0, width, height, null);
		graphics.dispose();
		return result;
	}
	
	public static BufferedImage loadFromResource(ClassLoader loader, String name) {
		try {
			return ImageIO.read(Objects.requireNonNull(loader.getResource(name)));
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static BufferedImage setColor(Image image, Color oldColor, Color newColor) {
		BufferedImage bufferedImage = image instanceof BufferedImage ? (BufferedImage)image : copy(image);
		int height = bufferedImage.getHeight();
		int[] pixels = Images.getColors(bufferedImage);
		for(int i = 0; i < pixels.length; i++) {
			Color existingColor = new Color(pixels[i], true);
			if(rgbEquals(existingColor, oldColor)) bufferedImage.setRGB(i % height, i / height,
					new Color(newColor.getRed(), newColor.getGreen(), newColor.getBlue(), existingColor.getAlpha()).getRGB());
		}
		return bufferedImage;
	}
	
	public static BufferedImage setColor(Image image, Color newColor) {
		return setColor(image, Color.WHITE, newColor);
	}
	
	public static boolean rgbEquals(Color c1, Color c2) {
		return c1.getRed() == c2.getRed() && c1.getGreen() == c2.getGreen() && c1.getBlue() == c2.getBlue();
	}
	
}
