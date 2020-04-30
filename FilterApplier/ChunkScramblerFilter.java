package FilterApplier;

import java.util.Random;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ChunkScramblerFilter extends ImageFilter {

	int chunkWidth;
	int chunkHeight;

	public ChunkScramblerFilter() {

		this(2, 2);

	}

	public ChunkScramblerFilter(int chunkWidth, int chunkHeight) {

		this.chunkWidth = chunkWidth;
		this.chunkHeight = chunkHeight;

	}

	public BufferedImage apply(BufferedImage sourceImage) {

		BufferedImage outputImage = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), sourceImage.getType());

		// Calculate the number of normally-sized chunks (abnormally-sized chunks are handled later).
		int numberOfHorizontalChunks = sourceImage.getWidth() / chunkWidth;
		int numberOfVerticalChunks = sourceImage.getHeight() / chunkHeight;
		int numberOfNormalChunks = numberOfHorizontalChunks * numberOfVerticalChunks;

		int[] chunkIndexes = new int[numberOfNormalChunks];

		for (int i = 0; i < chunkIndexes.length; i++) {
			chunkIndexes[i] = i;
		}

		shuffleArray(chunkIndexes);

		for (int i = 0; i < numberOfNormalChunks; i++) {

			int sourceXStart = (chunkIndexes[i] % numberOfHorizontalChunks) * chunkWidth;;
			int sourceYStart = (chunkIndexes[i] / numberOfHorizontalChunks) * chunkHeight;
			int outputXStart = (i % numberOfHorizontalChunks) * chunkWidth;
			int outputYStart = (i / numberOfHorizontalChunks) * chunkHeight;

			for (int y = 0; y < chunkHeight; y++) {

				for (int x = 0; x < chunkWidth; x++) {

					//System.out.println("Output: " + (outputXStart + x) + ", " + (outputYStart + y));
					//System.out.println("Source: " + (sourceXStart + x) + ", " + (sourceYStart + y));

					outputImage.setRGB(outputXStart + x, outputYStart + y, sourceImage.getRGB(sourceXStart + x, sourceYStart + y));

				}

			}

		}

		/*
		// Calculate if there are leftover abnormal chunks to see if abnormally-sized edge chunks to need to be processed.
		int leftOverColumnWidth = sourceImage.getWidth() % chunkWidth;
		int leftOverRowHeight = sourceImage.getHeight() % chunkHeight;

		System.out.println("LeftOverColumnWidth: " + leftOverColumnWidth);
		System.out.println("LeftOverRowHeight: " + leftOverRowHeight);

		// Process the abnormal chunk column on the right edge of the image, if one exists.
		if (leftOverColumnWidth != 0) {

			for (int y = 0; y < numberOfVerticalChunks; y++) {

				scrambleChunk(sourceImage, outputImage, numberOfHorizontalChunks * chunkWidth, y * chunkHeight, leftOverColumnWidth, chunkHeight);

			}

		}

		// Process the abnormal chunk row on the bottom edge of the image, if one exists.
		if (leftOverRowHeight != 0) {
			
			for (int x = 0; x < numberOfHorizontalChunks; x++) {

				scrambleChunk(sourceImage, outputImage, x * chunkWidth, numberOfVerticalChunks * chunkHeight, chunkWidth, leftOverRowHeight);

			}


		}

		// Process the abnormal chunk at the bottom-right corner of the image, if one exists.
		if (leftOverColumnWidth != 0 && leftOverRowHeight != 0) {

			scrambleChunk(sourceImage, outputImage, numberOfHorizontalChunks * chunkWidth, numberOfVerticalChunks * chunkHeight, leftOverColumnWidth, leftOverRowHeight);

		}
		*/

		return outputImage;

	}

	public BufferedImage apply(String sourceImageFileName) {

		// This is a temporarily line to return a junk image for testing.
		BufferedImage outputImage = new BufferedImage(600, 400, 1);

		return outputImage;

	}

	private void shuffleArray(int[] array) {

		Random random = new Random();
		int selectedIndex = 0;
		int tempInt = 0;

		for (int i = array.length; i > 0; i--) {

			selectedIndex = random.nextInt(i);
			tempInt = array[i - 1];
			array[i - 1] = array[selectedIndex];
			array[selectedIndex] = tempInt;

		}

	}

}

