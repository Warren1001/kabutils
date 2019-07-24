package com.kabryxis.kabutils;

import com.kabryxis.kabutils.data.Maths;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Images {
	
	/**
	 * Maintains aspect ratio, scales dimensions up or down equally until both dimensions
	 * are under their respective maxes, one of which being at the specified max.
	 *
	 * @param image The image to resize.
	 * @param maxWidth The maximum width the new image can have.
	 * @param maxHeight The maximum height the new image can have.
	 * @return The new resized image.
	 */
	public static BufferedImage resize(Image image, int maxWidth, int maxHeight) {
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
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		BufferedImage bufferedImage;
		if(image instanceof BufferedImage) bufferedImage = (BufferedImage)image;
		else {
			bufferedImage = new BufferedImage(width, height, 2);
			Graphics2D graphics = bufferedImage.createGraphics();
			graphics.drawImage(image, 0, 0, width, height, null);
			graphics.dispose();
		}
		int[] pixels = new int[width * height];
		bufferedImage.getRGB(0, 0, width, height, pixels, 0, width);
		return pixels;
	}
	
	public static boolean imageColorsEqual(int[] colors1, int[] colors2, double margin) {
		double max = colors1.length;
		int pass = 0;
		for(int i = 0; i < max; i++) {
			int c1 = colors1[i];
			int c2 = colors2[i];
			if(Math.abs(c1 - c2) <= 5) { // TODO
				pass++;
				if(pass >= max * margin) return true;
			}
		}
		return false;
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
			throw new RuntimeException(e);
		}
	}
	
	public static BufferedImage setColor(Image image, Color oldColor, Color newColor) {
		BufferedImage bufferedImage = image instanceof BufferedImage ? (BufferedImage)image : copy(image);
		int width = bufferedImage.getWidth();
		int[] pixels = Images.getColors(bufferedImage);
		for(int i = 0; i < pixels.length; i++) {
			Color existingColor = new Color(pixels[i], true);
			if(rgbEquals(existingColor, oldColor)) bufferedImage.setRGB(i % width, i / width,
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
	
	public static BufferedImage shade(Image image, Color color) {
		BufferedImage bufferedImage = image instanceof BufferedImage ? (BufferedImage)image : copy(image);
		int width = bufferedImage.getWidth();
		int[] pixels = Images.getColors(bufferedImage);
		for(int i = 0; i < pixels.length; i++) {
			Color existingColor = new Color(pixels[i], true);
			bufferedImage.setRGB(i % width, i / width, new Color(Maths.floor(Maths.average(existingColor.getRed(), color.getRed())),
					Maths.floor(Maths.average(existingColor.getGreen(), color.getGreen())), Maths.floor(Maths.average(existingColor.getBlue(), color.getBlue())), existingColor.getAlpha()).getRGB());
		}
		return bufferedImage;
	}
	
	public static BufferedImage read(File file) {
		try {
			return ImageIO.read(file);
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
