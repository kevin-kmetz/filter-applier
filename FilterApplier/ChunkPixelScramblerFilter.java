package FilterApplier;

public class ChunkPixelScramblerFilter extends ImageFilter {

	int chunkWidth;
	int chunkHeight;

	public ChunkPixelScramblerFilter() {

		this(2, 2);

	}

	public ChunkPixelScramblerFilter(int chunkWidth, int chunkHeigth) {

		this.chunkWidth = chunkWidth;
		this.chunkHeight = chunkHeight;

	}

	public BufferedImage apply(BufferedImage sourceImage) {

		BufferedImage outputImage = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), sourceImage.getType());

	}

}