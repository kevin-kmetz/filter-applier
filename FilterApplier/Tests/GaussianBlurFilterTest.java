package FilterApplier.Tests;

import FilterApplier.GaussianBlurFilter;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

class GaussianBlurFilterTest {

	public static void main(String[] args) {

		GaussianBlurFilter filter = new GaussianBlurFilter();
		
		BufferedImage testImage = null;

		try {

			testImage = (ImageIO.read(new File(args[0])));

			BufferedImage resultingImage = filter.apply(testImage);

			File output = new File("gbfiltered_" + args[0]);
			output.createNewFile();

			ImageIO.write(resultingImage, "png", output);

		} catch (Exception e) {

			System.out.println("Failed to load " + args[0] + "!");

		}

	}

}