package FilterApplier;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class CrosshairFilter extends ImageFilter {

	public BufferedImage apply(BufferedImage sourceImage) {

		BufferedImage outputImage = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), sourceImage.getType());

		int[] rowAverageColors = new int[sourceImage.getHeight()];
		int[] columnAverageColors = new int[sourceImage.getWidth()];

		long colorSum;

		// Calculate the average color for each row.
		for (int y = 0; y < sourceImage.getHeight(); y++) {

			colorSum = 0l;

			for (int x = 0; x < sourceImage.getWidth(); x++) {

				colorSum += sourceImage.getRGB(x, y);

			}

			rowAverageColors[y] = Math.toIntExact(colorSum / Long.valueOf(sourceImage.getWidth()));

		}

		// Calculate the average color for each column.
		for (int x = 0; x < sourceImage.getWidth(); x++) {

			colorSum = 0l;

			for (int y = 0; y < sourceImage.getHeight(); y++) {

				colorSum += sourceImage.getRGB(x, y);

			}

			columnAverageColors[x] = Math.toIntExact(colorSum / Long.valueOf(sourceImage.getHeight()));

		}


		// Calculate the new values for each pixel.
		for (int y = 0; y < sourceImage.getHeight(); y++) {

			for (int x = 0; x < sourceImage.getWidth(); x++) {

				long resultingColor = (long)columnAverageColors[x] + (long)rowAverageColors[y];
				resultingColor /= 2l;

				outputImage.setRGB(x, y, Math.toIntExact(resultingColor));

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