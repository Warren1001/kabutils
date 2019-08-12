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
	 * Maintains aspect ratio, scales dimensions down equally until both dimensions
	 * are under or equal to their respective maximums.
	 *
	 * @param image The image to resize.
	 * @param maxWidth The maximum width the new image can have.
	 * @param maxHeight The maximum height the new image can have.
	 * @return The new resized image, or a copy of the image if it was already smaller than the given dimensions.
	 */
	public static BufferedImage downscale(Image image, int maxWidth, int maxHeight) {
		int iw = image.getWidth(null);
		int ih = image.getHeight(null);
		if(iw <= maxWidth && ih <= maxHeight) return Images.copy(image);
		int resizeWidth, resizeHeight;
		double ratio = (double)iw / maxWidth;
		resizeHeight = Maths.floor(ih / ratio);
		if(resizeHeight > maxHeight) {
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
	
	/**
	 * Maintains aspect ratio, scales dimensions up equally until both dimensions
	 * are above or equal to their respective minimums.
	 *
	 * @param image The image to resize.
	 * @param minWidth The minimum width the new image can have.
	 * @param minHeight The minimum height the new image can have.
	 * @return The new resized image, or a copy of the image if it was already bigger than the given dimensions.
	 */
	public static BufferedImage upscale(Image image, int minWidth, int minHeight) {
		int iw = image.getWidth(null);
		int ih = image.getHeight(null);
		if(iw >= minWidth && ih >= minHeight) return Images.copy(image);
		int resizeWidth, resizeHeight;
		double ratio = (double)minWidth / iw;
		resizeHeight = Maths.ceil(ih * ratio);
		if(resizeHeight < minHeight) {
			ratio = (double)minHeight / ih;
			resizeHeight = (int)(ih * ratio);
		}
		resizeWidth = Maths.ceil(iw * ratio);
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
