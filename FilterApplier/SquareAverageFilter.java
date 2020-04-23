package FilterApplier;

import java.util.ArrayList;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class SquareAverageFilter extends ImageFilter {

	int pixelRadius;

	public SquareAverageFilter() {

		this(1);

	}

	public SquareAverageFilter(int pixelRadius) {

		this.pixelRadius = pixelRadius;

	}

	public BufferedImage apply(BufferedImage sourceImage) {

		BufferedImage outputImage = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), sourceImage.getType());

		for (int y = 0; y < sourceImage.getHeight(); y++) {

			for (int x = 0; x < sourceImage.getWidth(); x++) {

				int newPixelRGB = calculateNewPixel(x, y, sourceImage);
				outputImage.setRGB(x, y, newPixelRGB);

			}

		}

		return outputImage;

	}

	public BufferedImage apply(String sourceImageFileName) {

		// This is a temporarily line to return a junk image for testing.
		BufferedImage outputImage = new BufferedImage(600, 400, 1);

		return outputImage;

	}

	private int calculateNewPixel(int sourceX, int sourceY, BufferedImage sourceImage) {

		int originX = sourceX - pixelRadius;
		int originY = sourceY - pixelRadius;
		int sideLength = pixelRadius * 2 + 1;

		ArrayList<Integer> withinBoundsValues = new ArrayList<Integer>();

		for (int y = originY; y < originY + sideLength; y++) {

			for (int x = originX; x < originX + sideLength; x++) {

				if (IsWithinBounds(x, y, sourceImage)) {

					withinBoundsValues.add(sourceImage.getRGB(x, y));

				}

			}

		}

		int valueTotal = 0;
		int numberOfValues = withinBoundsValues.size();

		for (int i : withinBoundsValues) {

			valueTotal += i / numberOfValues;

		}

		return valueTotal;

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