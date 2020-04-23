package heartlabs.marina.website.generator;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class ThumbnailGenerator {
	public static BufferedImage scale(BufferedImage source, int w, int h) {
		BufferedImage bi = getCompatibleImage(w, h);
		Graphics2D g2d = bi.createGraphics();
		double xScale = (double) w / source.getWidth();
		double yScale = (double) h / source.getHeight();
		double maxScale = Math.max(xScale, yScale);

		AffineTransform at = AffineTransform.getScaleInstance(maxScale, maxScale);

		double minDimension = Math.min(source.getWidth(), source.getHeight());
		int clippedWidth = (int) (source.getWidth() - minDimension);
		int clippedHeight = (int) (source.getHeight() - minDimension);

		at.translate(-clippedWidth / 2, -clippedHeight / 2);
		// g2d.clipRect(clippedWidth/2, clippedHeight/2, w, h);
		g2d.drawRenderedImage(source, at);
		g2d.dispose();
		return bi;
	}
	
	public static BufferedImage scale(BufferedImage source, double scale) {
		BufferedImage bi = getCompatibleImage((int)(source.getWidth()*scale), (int)(source.getHeight()*scale));
		Graphics2D g2d = bi.createGraphics();
		
		AffineTransform at = AffineTransform.getScaleInstance(scale, scale);
		
		g2d.drawRenderedImage(source, at);
		g2d.dispose();
		return bi;
	}

	private static BufferedImage getCompatibleImage(int w, int h) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		BufferedImage image = gc.createCompatibleImage(w, h);
		return image;
	}

	private static void write(BufferedImage img, File out) throws IOException {
		Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
		ImageWriter writer = (ImageWriter) writers.next();

		try (OutputStream os = new FileOutputStream(out); ImageOutputStream ios = ImageIO.createImageOutputStream(os)) {
			writer.setOutput(ios);

			ImageWriteParam param = writer.getDefaultWriteParam();

			param.setCompressionMode(ImageWriteParam.MODE_DEFAULT);
//			param.setCompressionQuality(1f); // Change the quality value you prefer
			writer.write(null, new IIOImage(img, null, null), param);

		} finally {
			writer.dispose();
		}
	}
	public static void createThumbnail(File in, File out) {
		try {
			BufferedImage img = ImageIO.read(in);
			img = scale(img, 512, 512);
			write(img, out);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	public static void scale(File in, File out, double scale) {
		try {
			BufferedImage img = ImageIO.read(in);
			img = scale(img, scale);
			write(img, out);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
