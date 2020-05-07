package FilterApplier;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class InversionFilter extends ImageFilter {

	public BufferedImage apply(BufferedImage sourceImage) {

		BufferedImage outputImage = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), sourceImage.getType());

		for (int y = 0; y < sourceImage.getHeight(); y++) {

			for (int x = 0; x < sourceImage.getWidth(); x++) {

				// outputImage.setRGB(x, y, Integer.MAX_VALUE - sourceImage.getRGB(x, y));

				Color sourceColor = new Color(sourceImage.getRGB(x, y));
				Color outputColor = new Color(255 - sourceColor.getRed(), 255 - sourceColor.getGreen(), 255 - sourceColor.getBlue());

				outputImage.setRGB(x, y, outputColor.getRGB());

			}

		}

		return outputImage;

	}

	public BufferedImage apply(String sourceImageFileName) {

		// This is a temporarily line to return a junk image for testing.
		BufferedImage outputImage = new BufferedImage(600, 400, 1);

		return outputImage;

	}

}