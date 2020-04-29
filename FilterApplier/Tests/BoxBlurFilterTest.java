package FilterApplier.Tests;

import FilterApplier.BoxBlurFilter;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

class BoxBlurFilterTest {

	public static void main(String[] args) {

		BoxBlurFilter filter = new BoxBlurFilter(1);
		
		BufferedImage testImage;

		try {

			testImage = (ImageIO.read(new File(args[0])));

			//BufferedImage resultingImage = filter.apply(testImage);

			BufferedImage resultingImage = testImage;

			for (int i = 100; i > 0; i--) {
				resultingImage = filter.apply(resultingImage);
			}

			File output = new File("bbfiltered_" + args[0]);
			output.createNewFile();

			ImageIO.write(resultingImage, "png", output);

		} catch (Exception e) {

			System.out.println("Failed to load " + args[0] + "!");

		}

	}

}