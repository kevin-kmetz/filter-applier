package FilterApplier.Tests;

import FilterApplier.ChunkScramblerFilter;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

class ChunkScramblerFilterTest {

	public static void main(String[] args) {

		ChunkScramblerFilter filter = new ChunkScramblerFilter(250, 250);
		
		BufferedImage testImage = null;

		try {

			testImage = (ImageIO.read(new File(args[0])));

			BufferedImage resultingImage = filter.apply(testImage);

			File output = new File("csfiltered_" + args[0]);
			output.createNewFile();

			ImageIO.write(resultingImage, "png", output);

		} catch (Exception e) {

			System.out.println("Failed to load " + args[0] + "!");
			System.out.print(e);
			e.printStackTrace();

		}

	}

}