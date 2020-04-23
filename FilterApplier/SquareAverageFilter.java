package FilterApplier;

import java.util.ArrayList;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import java.math.BigInteger;

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

		// The following three ways of calculating new pixel values are commented out and kept
		// here for posterity's sake and for possible experimentation with later.
		// The uncommented way using long primitives should be the normal implementation.

		// A very flawed method of calculating each new pixel values using int primitives.
		// Results in undesired and incorrect blue artifacting.

		/*int valueTotal = 0;
		int numberOfValues = withinBoundsValues.size();

		for (int i : withinBoundsValues) {

			valueTotal += i / numberOfValues;

		}

		return valueTotal;*/

		// Another method of calculating each new pixel using BigIntegers.
		// Gets rid of the blue artifacting and eliminates the need for redundant division that
		// is needed to stay within the limits of a variable.

		/*BigInteger valueTotal = new BigInteger("0");

		//Integer tempInt = new Integer(withinBoundsValues.size());
		Integer tempInt = Integer.valueOf(withinBoundsValues.size());
		BigInteger numberOfValues = new BigInteger(tempInt.toString());

		for (int i : withinBoundsValues) {

			//Integer addInteger = new Integer(i);
			Integer addInteger = Integer.valueOf(i);
			valueTotal = valueTotal.add(new BigInteger(addInteger.toString()));

		}

		valueTotal = valueTotal.divide(numberOfValues);

		return valueTotal.intValue();*/

		// Yet another way to calculate each pixel, but using floats.

		/*Float valueTotal = new Float(0.0);
		int numberOfValues = withinBoundsValues.size();

		for (int i : withinBoundsValues) {

			valueTotal += (float)i / numberOfValues;
		}

		return valueTotal.intValue();*/

		// A method of calculating each new pixel using long primitives.
		// Most likely the most accurate method for calculating new pixel values.

		long valueTotal = 0l;
		long numberOfValues = withinBoundsValues.size();

		for (int i : withinBoundsValues) {

			valueTotal += i;

		}

		valueTotal /= numberOfValues;

		return Math.toIntExact(valueTotal);

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