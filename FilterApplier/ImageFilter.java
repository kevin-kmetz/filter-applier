package FilterApplier;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

abstract class ImageFilter {
	
	abstract BufferedImage apply(BufferedImage sourceImage);

	abstract BufferedImage apply(String sourceImageFileName);

	abstract boolean apply(BufferedImage sourceImage, String ouputFileName);

	abstract boolean apply(String sourceImageFileName, String outputFileName);
}