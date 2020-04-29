package FilterApplier;

import java.util.ArrayList;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import java.math.BigInteger;

public class BoxBlurFilter extends ImageFilter {

	int pixelRadius;

	public BoxBlurFilter() {

		this(1);

	}

	public BoxBlurFilter(int pixelRadius) {

		this.pixelRadius = pixelRadius;

	}

	public BufferedImage apply(BufferedImage sourceImage) {

		BufferedImage outputImage = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), sourceImage.getType());

		for (int y = 0; y < sourceImage.getHeight(); y++) {

			for (int x = 0; x < sourceImage.getWidth(); x++) {

				Color newPixelRGB = calculateNewPixel(x, y, sourceImage);
				outputImage.setRGB(x, y, newPixelRGB.getRGB());

			}

		}

		return outputImage;

	}

	public BufferedImage apply(String sourceImageFileName) {

		// This is a temporarily line to return a junk image for testing.
		BufferedImage outputImage = new BufferedImage(600, 400, 1);

		return outputImage;

	}

	private Color calculateNewPixel(int sourceX, int sourceY, BufferedImage sourceImage) {

		Color resultingColor;

		int originX = sourceX - pixelRadius;
		int originY = sourceY - pixelRadius;
		int sideLength = pixelRadius * 2 + 1;

		ArrayList<Color> withinBoundsValues = new ArrayList<Color>();

		for (int y = originY; y < originY + sideLength; y++) {

			for (int x = originX; x < originX + sideLength; x++) {

				if (IsWithinBounds(x, y, sourceImage)) {

					withinBoundsValues.add(new Color(sourceImage.getRGB(x, y)));

				}

			}

		}

		int numberOfValues = withinBoundsValues.size();

		int redTotal = 0;
		int greenTotal = 0;
		int blueTotal = 0;


		for (Color i : withinBoundsValues) {

			redTotal += i.getRed();
			blueTotal += i.getGreen();
			greenTotal += i.getBlue();

		}

		redTotal /= numberOfValues;
		blueTotal /= numberOfValues;
		greenTotal /= numberOfValues;

		resultingColor = new Color(redTotal, blueTotal, greenTotal);

		return resultingColor;

	}

	private boolean IsWithinBounds(int x, int y, BufferedImage image) {

		boolean isValidPixel = false;

		if (x >= 0 && y >= 0 && x < image.getWidth() && y < image.getHeight()) {
			isValidPixel = true;
		} else {
			isValidPixel = false;
		}

		return isValidPixel;

	}

}