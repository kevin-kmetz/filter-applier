package FilterApplier;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ChunkAveragerFilter extends ImageFilter {

	int chunkWidth;
	int chunkHeight;

	public ChunkAveragerFilter() {

		this(2, 2);

	}

	public ChunkAveragerFilter(int chunkWidth, int chunkHeight) {

		this.chunkWidth = chunkWidth;
		this.chunkHeight = chunkHeight;

	}

	public BufferedImage apply(BufferedImage sourceImage) {

		BufferedImage outputImage = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), sourceImage.getType());

		// Calculate the number of normally-sized chunks (abnormally-sized chunks are handled later).
		int numberOfHorizontalChunks = sourceImage.getWidth() / chunkWidth;
		int numberOfVerticalChunks = sourceImage.getHeight() / chunkHeight;

		for (int y = 0; y < numberOfVerticalChunks; y++) {

			for (int x = 0; x < numberOfHorizontalChunks; x++) {

				colorChunk(sourceImage, outputImage, x * chunkWidth, y * chunkHeight, chunkWidth, chunkHeight);

			}

		}

		// Calculate if there are leftover abnormal chunks to see if abnormally-sized edge chunks to need to be processed.
		int leftOverColumnWidth = sourceImage.getWidth() % chunkWidth;
		int leftOverRowHeight = sourceImage.getHeight() % chunkHeight;

		System.out.println("LeftOverColumnWidth: " + leftOverColumnWidth);
		System.out.println("LeftOverRowHeight: " + leftOverRowHeight);

		// Process the abnormal chunk column on the right edge of the image, if one exists.
		if (leftOverColumnWidth != 0) {

			for (int y = 0; y < numberOfVerticalChunks; y++) {

				colorChunk(sourceImage, outputImage, numberOfHorizontalChunks * chunkWidth, y * chunkHeight, leftOverColumnWidth, chunkHeight);

			}

		}

		// Process the abnormal chunk row on the bottom edge of the image, if one exists.
		if (leftOverRowHeight != 0) {
			
			for (int x = 0; x < numberOfHorizontalChunks; x++) {

				colorChunk(sourceImage, outputImage, x * chunkWidth, numberOfVerticalChunks * chunkHeight, chunkWidth, leftOverRowHeight);

			}


		}

		// Process the abnormal chunk at the bottom-right corner of the image, if one exists.
		if (leftOverColumnWidth != 0 && leftOverRowHeight != 0) {

			colorChunk(sourceImage, outputImage, numberOfHorizontalChunks * chunkWidth, numberOfVerticalChunks * chunkHeight, leftOverColumnWidth, leftOverRowHeight);

		}

		return outputImage;

	}

	public BufferedImage apply(String sourceImageFileName) {

		// This is a temporarily line to return a junk image for testing.
		BufferedImage outputImage = new BufferedImage(600, 400, 1);

		return outputImage;

	}

	private void colorChunk(BufferedImage sourceImage, BufferedImage outputImage, int chunkXPosition, int chunkYPosition, int chunkWidth, int chunkHeight) {

		int redTotal = 0;
		int greenTotal = 0;
		int blueTotal = 0;

		int totalValues = chunkWidth * chunkHeight;

		// Get the source pixels that need shuffling and store them into a static array.
		for (int y = chunkYPosition, index = 0; y < chunkYPosition + chunkHeight; y++) {

			for (int x = chunkXPosition; x < chunkXPosition + chunkWidth; x++, index++) {

				Color tempColor = new Color(sourceImage.getRGB(x, y));

				redTotal += tempColor.getRed();
				greenTotal += tempColor.getGreen();
				blueTotal += tempColor.getBlue();

			}

		}

		int outputRed = redTotal / totalValues;
		int outputGreen = greenTotal / totalValues;
		int outputBlue = blueTotal / totalValues;

		Color outputColor = new Color(outputRed, outputGreen, outputBlue);
		int outputRGBValue = outputColor.getRGB();

		// Place the reordered colors back into the corresponding image chunk.
		for (int y = chunkYPosition, index = 0; y < chunkYPosition + chunkHeight; y++) {

			for (int x = chunkXPosition; x < chunkXPosition + chunkWidth; x++, index++) {

				outputImage.setRGB(x, y, outputRGBValue);

			}

		}

	}

}

