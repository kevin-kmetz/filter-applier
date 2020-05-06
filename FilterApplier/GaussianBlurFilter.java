package FilterApplier;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class GaussianBlurFilter extends ImageFilter {

	public BufferedImage apply(BufferedImage sourceImage) {

		BufferedImage outputImage = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), sourceImage.getType());

		for (int y = 0; y < sourceImage.getHeight(); y++) {

			for (int x = 0; x < sourceImage.getWidth(); x++) {

				outputImage.setRGB(x, y, calculateBlurredPixel(x, y, sourceImage));

			}

		}

		return outputImage;

	}

	public BufferedImage apply(String sourceImageFileName) {

		// This is a temporarily line to return a junk image for testing.
		BufferedImage outputImage = new BufferedImage(600, 400, 1);

		return outputImage;

	}

	private boolean isWithinBounds(int x, int y, BufferedImage image) {

		boolean isValidCoordinate = false;

		if (x < image.getWidth() && x >= 0 && y < image.getHeight() && y >= 0) {

			isValidCoordinate = true;

		}

		return isValidCoordinate;

	}

	private int calculateBlurredPixel(int x, int y, BufferedImage image) {

		int redTotal = 0;
		int greenTotal = 0;
		int blueTotal = 0;
		int normalizationTotal = 0;
		int normalizationValue;
		
		for (int yOffset = -1; yOffset < 2; yOffset++) {

			for (int xOffset = -1; xOffset < 2; xOffset++) {

				if (isWithinBounds(x + xOffset, y + yOffset, image)) {

					Color tempColor = new Color(image.getRGB(x + xOffset, y + yOffset));

					normalizationValue = getNormalizationValue(xOffset, yOffset);
					normalizationTotal += normalizationValue;

					redTotal +=  tempColor.getRed() * normalizationValue;
					greenTotal += tempColor.getGreen() * normalizationValue;
					blueTotal += tempColor.getBlue() * normalizationValue;

				}

			}

		}

		redTotal /= normalizationTotal;
		greenTotal /= normalizationTotal;
		blueTotal /= normalizationTotal;

		Color calculatedColor = new Color(redTotal, greenTotal, blueTotal);

		return calculatedColor.getRGB();

	}

	private int getNormalizationValue(int xOffset, int yOffset) {

		int distance = 2 * (Math.abs(xOffset) + Math.abs(yOffset));
		int normalizationValue;

		if (distance != 0) {

			normalizationValue = 4 / distance;

		} else {

			normalizationValue = 4;

		}

		return distance;

	}


}