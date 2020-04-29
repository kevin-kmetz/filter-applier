package FilterApplier.Tests;

import FilterApplier.ChunkAveragerFilter;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

class ChunkAveragerFilterTest {

	public static void main(String[] args) {

		ChunkAveragerFilter filter = new ChunkAveragerFilter(25, 25);
		
		BufferedImage testImage = null;

		try {

			testImage = (ImageIO.read(new File(args[0])));

			BufferedImage resultingImage = filter.apply(testImage);

			File output = new File("cafiltered_" + args[0]);
			output.createNewFile();

			ImageIO.write(resultingImage, "png", output);

		} catch (Exception e) {

			System.out.println("Failed to load " + args[0] + "!");

		}

	}

}