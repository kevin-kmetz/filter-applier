package FilterApplier.Tests;

import FilterApplier.ChunkPixelScramblerFilter;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

class ChunkPixelScramblerFilterTest {

	public static void main(String[] args) {

		ChunkPixelScramblerFilter filter = new ChunkPixelScramblerFilter(25, 25);
		
		BufferedImage testImage = null;

		try {

			testImage = (ImageIO.read(new File(args[0])));

			BufferedImage resultingImage = filter.apply(testImage);

			File output = new File("cpsfiltered_" + args[0]);
			output.createNewFile();

			ImageIO.write(resultingImage, "png", output);

		} catch (Exception e) {

			System.out.println("Failed to load " + args[0] + "!");

		}

	}

}