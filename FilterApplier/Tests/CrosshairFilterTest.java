package FilterApplier.Tests;

import FilterApplier.CrosshairFilter;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

class CrosshairFilterTest {

	public static void main(String[] args) {

		CrosshairFilter filter = new CrosshairFilter();
		
		BufferedImage testImage;

		try {

			testImage = (ImageIO.read(new File(args[0])));

			BufferedImage resultingImage = filter.apply(testImage);

			File output = new File("chfiltered_" + args[0]);
			output.createNewFile();

			ImageIO.write(resultingImage, "png", output);

		} catch (Exception e) {

			System.out.println("Failed to load " + args[0] + "!");

		}

	}

}