package FilterApplier;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class GrayscaleFilter extends ImageFilter {

	public BufferedImage apply(BufferedImage sourceImage) {

		BufferedImage outputImage = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), sourceImage.getType());

		for (int y = 0; y < sourceImage.getHeight(); y++) {

			for (int x = 0; x < sourceImage.getWidth(); x++) {

				outputImage.setRGB(x, y, calculateDecoloredPixel(sourceImage.getRGB(x, y)));

			}

		}

		return outputImage;

	}

	public BufferedImage apply(String sourceImageFileName) {

		// This is a temporarily line to return a junk image for testing.
		BufferedImage outputImage = new BufferedImage(600, 400, 1);

		return outputImage;

	}

	private int calculateDecoloredPixel(int rgbValue) {

		Color originalColor = new Color(rgbValue);

		int colorValue = 0;

		colorValue += originalColor.getRed();
		colorValue += originalColor.getGreen();
		colorValue += originalColor.getBlue();

		colorValue /= 3;	// This magic number comes from the fact that color has only 3 components - red, green, and blue.

		Color grayColor = new Color(colorValue, colorValue, colorValue);

		return grayColor.getRGB();

	}

}