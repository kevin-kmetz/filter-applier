package FilterApplier.Tests;

import FilterApplier.SquareAverageFilter;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

class SquareAverageFilterTest {

	public static void main(String[] args) {

		SquareAverageFilter filter = new SquareAverageFilter(5);
		
		BufferedImage testImage;

		try {

			testImage = (ImageIO.read(new File(args[0])));

			BufferedImage resultingImage = filter.apply(testImage);

			File output = new File("filtered_" + args[0]);
			output.createNewFile();

			ImageIO.write(resultingImage, "png", output);

		} catch (Exception e) {

			System.out.println("Failed to load " + args[0] + "!");

		}

	}

}