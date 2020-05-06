package FilterApplier;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class SobelFilter extends ImageFilter {

	boolean thresholdingEnabled = true;
	int thresholdValue = 1;

	public BufferedImage apply(BufferedImage sourceImage) {

		int width = sourceImage.getWidth();
		int height = sourceImage.getHeight();

		// Preprocess image
		System.out.println("Preprocessing the image...");
		System.out.println("Applying blur...");
		//GaussianBlurFilter gaussianBlur = new GaussianBlurFilter();
		BoxBlurFilter boxBlur = new BoxBlurFilter(10);
		System.out.println("Converting to grayscale...");
		GrayscaleFilter grayscale = new GrayscaleFilter();

		BufferedImage outputImage = boxBlur.apply(sourceImage);
		outputImage = grayscale.apply(outputImage);

		// Restrict color values to 0 - 255 for easier math calculations later.
		System.out.println("Downscaling grayscale image...");
		int[][] pixelValues = getGrayValuesOnly(outputImage);

		// get x gradient values
		System.out.println("Calculating horizontal gradients...");
		int[][] xGradients = calculateXGradients(pixelValues);

		// get y gradient values
		System.out.println("Calculating vertical gradients...");
		int[][] yGradients = calculateYGradients(pixelValues);

		// get merged gradient values
		System.out.println("Calculating merged gradients...");
		int[][] mergedGradients = calculateMergedGradients(xGradients, yGradients);

		// increase values for visibility is thresholding enabled
		if (thresholdingEnabled) {
			System.out.println("Thresholding image...");
			thresholdGradients(mergedGradients);
		}

		// convert gradients into an image
		System.out.println("Creating Sobel-filtered image...");
		outputImage = getFilteredImage(mergedGradients);

		return outputImage;

	}

	public BufferedImage apply(String sourceImageFileName) {

		// This is a temporarily line to return a junk image for testing.
		BufferedImage outputImage = new BufferedImage(600, 400, 1);

		return outputImage;

	}

	private int[][] getGrayValuesOnly(BufferedImage image) {

		int[][] values = new int[image.getHeight()][image.getWidth()];

		for (int y = 0; y < image.getHeight(); y++) {

			for (int x = 0; x < image.getWidth(); x++) {

				Color tempColor = new Color(image.getRGB(x, y));
				values[y][x] = tempColor.getRed();

			}

		}

		return values;

	}

	private int[][] calculateXGradients(int[][] values) {

		int width = values[0].length;
		int height = values.length;

		int[][] horizontalGradients = new int[height][width];

		for (int y = 0; y < height; y++) {

			for (int x = 0; x < width; x++) {

				horizontalGradients[y][x] = calculateIndividualXGradient(x, y, values);

			}

		}

		return horizontalGradients;

	}

	private int[][] calculateYGradients(int[][] values) {

		int width = values[0].length;
		int height = values.length;

		int[][] verticalGradients = new int[height][width];

		for (int y = 0; y < height; y++) {

			for (int x = 0; x < width; x++) {

				verticalGradients[y][x] = calculateIndividualYGradient(x, y, values);

			}

		}

		return verticalGradients;

	}

	private int calculateIndividualXGradient(int x, int y, int[][] values) {

		int normalizationTotal = 0;
		int gradientTotal = 0;

		// Calculate the values contributed by corner values.
		for (int yOffset = -1; yOffset <= 1; yOffset += 2) {

			for (int xOffset = -1; xOffset <= 1; xOffset += 2) {

				if (isWithinBounds(x + xOffset, y + yOffset, values)) {

					normalizationTotal += 1;
					gradientTotal += values[y + yOffset][x + xOffset] * (yOffset * (-1));		// This flips the signs to match the normal Sobel operator values.

				}

			}

		}

		// Calculate the values contributed by locations between the corners.
		for (int xOffset = -1; xOffset <= 1; xOffset += 2) {

			if (isWithinBounds(x + xOffset, y, values)) {

				normalizationTotal += 2;
				gradientTotal += 1 * (values[y][x + xOffset] * (xOffset * (-1)));
			}

		}

		// return gradientTotal / normalizationTotal;
		return gradientTotal / 8;

	}

	private int calculateIndividualYGradient(int x, int y, int[][] values) {

		int normalizationTotal = 0;
		int gradientTotal = 0;

		// Calculate the values contributed by corner values.
		for (int yOffset = -1; yOffset <= 1; yOffset += 2) {

			for (int xOffset = -1; xOffset <= 1; xOffset += 2) {

				if (isWithinBounds(x + xOffset, y + yOffset, values)) {

					normalizationTotal += 1;
					gradientTotal += values[y + yOffset][x + xOffset] * (xOffset * (-1));		// This flips the signs to match the normal Sobel operator values.

				}

			}

		}

		// Calculate the values contributed by locations between the corners.
		for (int yOffset = -1; yOffset <= 1; yOffset += 2) {

			if (isWithinBounds(x, y + yOffset, values)) {

				normalizationTotal += 2;
				gradientTotal += 1 * (values[y + yOffset][x] * (yOffset * (-1)));
			}

		}

		// return gradientTotal / normalizationTotal;
		return gradientTotal / 8;

	}

	private boolean isWithinBounds(int x, int y, int[][] array) {

		boolean isValidCoordinate = false;

		if (x < array[0].length && x >= 0 && y < array.length && y >= 0) {

			isValidCoordinate = true;

		}

		return isValidCoordinate;

	}

	private int[][] calculateMergedGradients(int[][] xGradients, int[][] yGradients) {

		int width = xGradients[0].length;		// The use of xGradients over yGradients is arbitrary.
		int height = xGradients.length;			// The two arrays should be equal in dimension, though it assumed and not checked.

		int[][] mergedGradients = new int[height][width];

		//double thresholdMultiplier = 1.41421;		// This is a manually-calculated multiplier to rescale the image back to 0-255.
		//double thresholdMultiplier = 1.00;

		for (int y = 0; y < height; y++) {

			for (int x = 0; x < width; x++) {

				int xSquared = xGradients[y][x] * xGradients[y][x];
				int ySquared = yGradients[y][x] * yGradients[y][x];

				int sum = xSquared + ySquared;
				//int gradient = (int) (thresholdMultiplier * Math.sqrt((double) sum));
				int gradient = (int) (Math.sqrt((double) sum));

				mergedGradients[y][x] = gradient;

			}

		}

		return mergedGradients;

	}

	private BufferedImage getFilteredImage(int[][] gradients) {

		int width = gradients[0].length;
		int height = gradients.length;

		BufferedImage filteredImage = new BufferedImage(width, height, 2);		// 2 is the value for TYPE_INT_ARGB;

		for (int y = 0; y < height; y++) {

			for (int x = 0; x < width; x++) {

				int rgbValue = gradients[y][x];

				if (rgbValue > 255) {
					System.out.println(rgbValue);
				}

				Color pixelColor = new Color(rgbValue, rgbValue, rgbValue);

				filteredImage.setRGB(x, y, pixelColor.getRGB());

			}

		}

		return filteredImage;

	}

	private void thresholdGradients(int[][] gradients) {

		for (int y = 0; y < gradients.length; y++) {

			for (int x = 0; x < gradients[0].length; x++) {

				if (gradients[y][x] >= thresholdValue) {

					gradients[y][x] = 255;

				}

			}

		}

	}

}