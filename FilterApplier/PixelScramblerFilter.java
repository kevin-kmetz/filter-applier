package FilterApplier;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.Random;

public class PixelScramblerFilter extends ImageFilter {

	public BufferedImage apply (BufferedImage sourceImage) {

		BufferedImage outputImage = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), sourceImage.getType());

		// ArrayList<Integer> sourcePixels = new ArrayList<Integer>();
		// ArrayList<Integer> targetPixels = new ArrayList<Integer>();

		int numberOfPixels = sourceImage.getWidth() * sourceImage.getHeight();
		int[] sourcePixels = new int[numberOfPixels];
		// int[] shuffledPixels = new int[numberOfPixels];

		for (int y = 0, index = 0; y < sourceImage.getHeight(); y++) {

			for (int x = 0; x < sourceImage.getWidth(); x++) {

				// sourcePixels.add(sourceImage.getRGB(x, y));
				sourcePixels[index] = sourceImage.getRGB(x, y);
				index++;

			}

		}

		Random random = new Random();
		// int index;

		/*while (sourcePixels.size() > 0) {

			index = random.nextInt(sourcePixels.size());
			targetPixels.add(sourcePixels.remove(index));

		}*/

		/*for (int y = 0, index = 0; y < outputImage.getHeight(); y++) {

			for (int x = 0; x < outputImage.getWidth(); x++) {

				index = random.nextInt(sourcePixels.size());

				outputImage.setRGB(x, y, sourcePixels.remove(index));

			}

		}*/

		for (int y = 0, upperLimit = numberOfPixels, selectedIndex = 0, tempColor = 0; y < outputImage.getHeight(); y++) {

			for (int x = 0; x < outputImage.getWidth(); x++) {

				selectedIndex = random.nextInt(upperLimit);

				outputImage.setRGB(x, y, sourcePixels[selectedIndex]);

				tempColor = sourcePixels[upperLimit - 1];
				sourcePixels[upperLimit - 1] = sourcePixels[selectedIndex];
				sourcePixels[selectedIndex] = tempColor;

				upperLimit--;

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