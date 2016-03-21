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
 * Component which displays an image from the res folder,
 * or a dynamically-created backup that cannot be found.
 * Two options for sizing are available:
 * Fixed sizing resizes the image to a specified width and height.
 * Flexible sizing displays the image in its native resolution unless its
 * longest side lies outside a minimum and maximum proportion of the
 * screen's shortest side and could be resized to fit the range without
 * the scale factor falling outside an allowed range.
 * @author Matt
 * @version 3.0.0
 */
public class ImageComponent extends JComponent {
	
	private Image image;
	
	/**
	 * Displays the image from the specified resource file or
	 * a generated backup image with the specified text, resized
	 * if necessary and possible, to meet the given constraints.
	 * @param fileName Name of image resource to display
	 * @param backupText Text to draw if image is not found
	 * @param minScrProp Image must take up this proportion of screen
	 * @param maxScrProp Image may only take up this proportion of screen
	 * @param minResize Smallest factor by which size may be multiplied
	 * @param maxResize Greatest factor by which size may be multiplied
	 */
	public ImageComponent(String fileName, String backupText,
							float minScrProp, float maxScrProp,
								float minResize, float maxResize) {
		Dimension screen = getToolkit().getScreenSize();
		int backupWidth = (int) (screen.width * minScrProp);
		int backupHeight = (int) (screen.height * maxScrProp);
		setOpaque(false);
		loadImage(fileName, backupText, backupWidth, backupHeight);
		sizeImage(minScrProp, maxScrProp, minResize, maxResize);
	}
	
	/**
	 * Displays the image from the specified resource file or a generated
	 * backup image with the specified text, sized to the specified dimensions.
	 * @param fileName Name of image resource to display
	 * @param backupText Text to draw if image is not found
	 * @param width Width to resize image to
	 * @param height Height to resize image to
	 */
	public ImageComponent(String fileName, String backupText,
										int width, int height) {
		setOpaque(false);
		loadImage(fileName, backupText, width, height);
		image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
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
	 * Attempts to load {@link #image} from res/fileName.
	 * If this cannot be done, a backup image with the given text is drawn.
	 * @param fileName Name (including extension) of image to load
	 * @param backupText Text to draw as backup if image cannot be loaded
	 */
	private void loadImage(String fileName, String backupText,
								int backupWidth, int backupHeight) {
		File file = ResourceManager.getResource(fileName);
		if (file == null) {
			System.err.println("Cannot find res folder!");
			image = makeBackupImage(backupText, backupWidth, backupHeight);
			return;
		}
		if (!file.exists()) {
			System.err.println("Cannot find splash screen image!");
			image = makeBackupImage(backupText, backupWidth, backupHeight);
			return;
		}
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			System.err.println("Unknown error with splash screen.");
			image = makeBackupImage(backupText, backupWidth, backupHeight);
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
	 * @param minScrProp Image may be enlarged to this proportion of screen
	 * @param maxScrProp Image may be shrunk to this proportion of screen
	 * @param minResize Image may only be shrunk to this many times its size
	 * @param maxResize Image may only be enlarged to this many times its size
	 */
	private void sizeImage(float minScrProp, float maxScrProp,
								float minResize, float maxResize) {
		Dimension pic =
				new Dimension(image.getWidth(this), image.getHeight(this));
		Dimension screen = getToolkit().getScreenSize();
		float ratio = 1f; // Do not scale unless it violates size limits
		float scrnShortSide = Math.min(screen.width, screen.height);
		float imgLongestSide = Math.max(pic.width, pic.height);
		float minLen = scrnShortSide * minScrProp;
		float maxLen = scrnShortSide * maxScrProp;
		if (imgLongestSide < minLen) ratio = minLen / imgLongestSide;
		if (imgLongestSide > maxLen) ratio = maxLen / imgLongestSide;
		if (ratio < minResize) ratio = 1f;
		if (ratio > maxResize) ratio = 1f;
		if (ratio != 1f) { // Resizing is necessary and possible
			Dimension newPic = GuiProperties.ratioOf(ratio, ratio, pic);
			image = image.getScaledInstance(newPic.width,
										newPic.height, Image.SCALE_FAST);
		}
	}
	
	/**
	 * Creates an image of the given dimensions,
	 * featuring the given text on a radial gradient background.
	 * The colours used are defined in {@link GuiProperties}.
	 * @see GuiProperties#IMAGE_BACKUP_TEXT
	 * @see GuiProperties#IMAGE_BACKUP_TEXT_PROPORTION
	 * @see GuiProperties#IMAGE_BACKUP_CENTRE
	 * @see GuiProperties#IMAGE_BACKUP_EDGE
	 * @param text Text for the desired image
	 * @param width Width of the desired image
	 * @param height Height of the desired image
	 * @return to be used in place of image which could not be loaded
	 */
	private Image makeBackupImage(String text, int width, int height) {
		int type = BufferedImage.TYPE_INT_ARGB;
		BufferedImage backup = new BufferedImage(width, height, type);
		Graphics2D g = backup.createGraphics();
		drawBackground(width, height, g,
				 GuiProperties.IMAGE_BACKUP_CENTRE,
				 GuiProperties.IMAGE_BACKUP_EDGE);
		drawText(text, g, new Dimension(width, height),
				 GuiProperties.getFont(),
				 GuiProperties.IMAGE_BACKUP_TEXT_PROPORTION,
				 GuiProperties.IMAGE_BACKUP_TEXT);
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
	private void drawText(String text, Graphics2D g, Dimension imgSize,
									Font font, float ratio, Color colour) {
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
