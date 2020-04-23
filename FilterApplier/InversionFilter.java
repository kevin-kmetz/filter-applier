package FilterApplier;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

class InversionFilter extends ImageFilter {

	BufferedImage apply(BufferedImage sourceImage) {

		// This is a placeholder for future implementation of this filter.
		BufferedImage outputImage = new BufferedImage(600, 400, 1);

		return outputImage;

	}

	BufferedImage apply(String sourceImageFileName) {

		// This is a temporarily line to return a junk image for testing.
		BufferedImage outputImage = new BufferedImage(600, 400, 1);

		return outputImage;

	}

}