package FilterApplier.Tests;

import FilterApplier.PixelScramblerFilter;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

class PixelScramblerFilterTest {

	public static void main(String[] args) {

		PixelScramblerFilter filter = new PixelScramblerFilter();
		
		BufferedImage testImage;

		try {

			testImage = (ImageIO.read(new File(args[0])));

			BufferedImage resultingImage = filter.apply(testImage);

			File output = new File("psfiltered_" + args[0]);
			output.createNewFile();

			ImageIO.write(resultingImage, "png", output);

		} catch (Exception e) {

			System.out.println("Failed to load " + args[0] + "!");

		}

	}

}