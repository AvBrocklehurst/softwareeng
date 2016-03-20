package simori.SwingGui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import simori.ResourceManager;

/**
 * Component which displays the splash screen image,
 * or a dynamically-created backup that cannot be found.
 * @author Matt
 * @version 2.0.0
 */
public class SplashImage extends JComponent {
	
	private Image image;
	
	public SplashImage() {
		setOpaque(false);
		loadImage();
		sizeImage();
	}
	
	/** {@inheritDoc} */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(image.getWidth(this), image.getHeight(this));
	}
	
	/** {@inheritDoc} */
	@Override
	public Dimension getSize() {
		return getPreferredSize();
	}
	
	/** {@inheritDoc} */
	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}
	
	/** {@inheritDoc} */
	@Override
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}
	
	/** {@inheritDoc} */
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, this); // Draw image as component's background
	}
	
	/**
	 * Attempts to load {@link GuiProperties#SPLASH_IMAGE}.
	 * If the res folder cannot be located, the file does not exist
	 * within the res folder, or an error occurs reading the file,
	 * the result of {@link #makeBackupImage} is returned instead.
	 */
	private void loadImage() {
		File file = ResourceManager.getResource(GuiProperties.SPLASH_IMAGE);
		if (file == null) {
			System.err.println("Cannot find res folder!");
			image = makeBackupImage();
			return;
		}
		if (!file.exists()) {
			System.err.println("Cannot find splash screen image!");
			image = makeBackupImage();
			return;
		}
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			System.err.println("Unknown error with splash screen.");
			image = makeBackupImage();
			return;
		}
	}
	
	/**
	 * Modifies the size of the image if necessary and possible.
	 * The image's longest side is compared with the screen's shortest.
	 * If the image is less than the minimum allowed proportion, it is
	 * increased to meet the minimum. If it is greater than the maximum
	 * allowed proportion of screen size, it is reduced to meet the maximum.
	 * However, if such size corrections would scale the image to smaller
	 * than the minimum proportion it may be transformed to, or greater than
	 * the maximum, the image is treated as if it is within the range of
	 * allowable sizes, and allowed to remain in its native resolution.
	 * @see GuiProperties#SPLASH_MAX_PROPORTION
	 * @see GuiProperties#SPLASH_MIN_PROPORTION
	 * @see GuiProperties#SPLASH_MAX_RESIZE
	 * @see GuiProperties#SPLASH_MIN_RESIZE
	 */
	private void sizeImage() {
		Dimension pic =
				new Dimension(image.getWidth(this), image.getHeight(this));
		Dimension screen = getToolkit().getScreenSize();
		float ratio = 1f; // Do not scale unless it violates size limits
		float scrnShortSide = Math.min(screen.width, screen.height);
		float imgLongestSide = Math.max(pic.width, pic.height);
		float minLen = scrnShortSide * GuiProperties.SPLASH_MIN_PROPORTION;
		float maxLen = scrnShortSide * GuiProperties.SPLASH_MAX_PROPORTION;
		if (imgLongestSide < minLen) ratio = minLen / imgLongestSide;
		if (imgLongestSide > maxLen) ratio = maxLen / imgLongestSide;
		if (ratio < GuiProperties.SPLASH_MIN_RESIZE) ratio = 1f;
		if (ratio > GuiProperties.SPLASH_MAX_RESIZE) ratio = 1f;
		if (ratio != 1f) { // Resizing is necessary and possible
			Dimension newPic = GuiProperties.ratioOf(ratio, ratio, pic);
			image = image.getScaledInstance(newPic.width,
										newPic.height, Image.SCALE_FAST);
		}
	}
	
	/**
	 * Draws an image with text and a radial gradient,
	 * of the minimum allowable size for a splash screen.
	 * @return to use in place of missing splash screen image
	 */
	private Image makeBackupImage() {
		Dimension screen = getToolkit().getScreenSize();
		int w = (int) (screen.width * GuiProperties.SPLASH_MIN_PROPORTION);
		int h = (int) (screen.height * GuiProperties.SPLASH_MIN_PROPORTION);
		int type = BufferedImage.TYPE_INT_ARGB;
		BufferedImage backup = new BufferedImage(w, h, type);
		Graphics2D g = backup.createGraphics();
		drawBackground(w, h, g,
				 GuiProperties.SPLASH_BACKUP_CENTRE,
				 GuiProperties.SPLASH_BACKUP_EDGE);
		drawText(g, new Dimension(w, h),
				 GuiProperties.getFont(),
				 GuiProperties.SPLASH_BACKUP_TEXT,
				 GuiProperties.SPLASH_TEXT_PROPORTION,
				 GuiProperties.SPLASH_BACKUP);
		return backup;
	}
	
	/**
	 * Fills the specified area of a graphics context with
	 * a radial gradient from one given colour to the other.
	 * @param width Horizontal dimension of the area to fill
	 * @param height Vertical dimension of the area to fill
	 * @param g Graphics context to draw to
	 * @param start Colour at the centre of the image
	 * @param end Colour past the edge of the image
	 */
	private void drawBackground(int width, int height,
									Graphics2D g, Color start, Color end) {
		Point centre = new Point(width / 2, height / 2);
		int radius =  (Math.max(width, height));
		float[] ratios = {0f, 1f};
		Color[] colours = {start, end};
		g.setPaint(new RadialGradientPaint(centre, radius, ratios, colours));
		g.fillRect(0, 0, width, height);
	}
	
	/**
	 * Draws a String to a graphics context, centred within specified margins.
	 * @param imgSize The dimensions of the area to draw in
	 * @param text The string to draw
	 * @param ratio The proportion (1f being the same) of the area to fill
	 * @param colour The colour to draw the text
	 * @param g The graphics context to use
	 */
	private void drawText(Graphics2D g, Dimension imgSize, Font font,
								String text, float ratio, Color colour) {
		// Determine maximum font size for area within margins
		Dimension textArea = GuiProperties.ratioOf(ratio, ratio, imgSize);
		g.setFont(font);
		GuiProperties.sizeFontTo(text, textArea.width, textArea.height, g);
		
		// Use dimensions of text to determine centred position to draw it
		FontMetrics met = g.getFontMetrics(g.getFont());
		Rectangle2D stringBounds = met.getStringBounds(text, g);
		int xPos = (imgSize.width - (int) stringBounds.getWidth()) / 2;
		int yPos = (imgSize.height + (int) stringBounds.getHeight()) / 2;
		
		// Draw text at position in colour
		g.setPaint(colour);
		g.drawString(text, xPos, yPos);
	}
}
