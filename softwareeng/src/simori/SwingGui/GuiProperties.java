package simori.SwingGui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;

import simori.ResourceManager;

/**
 * Holds constants such as colours, cursors, names and size ratios
 * used by the various GUI classes, so that its many customisable
 * aspects are configurable from one place.
 * Also provides static utility methods for obtaining the font and
 * icon, which ensure these are only loaded once.
 * Other utilities include methods for sizing text to fit a given
 * area, and applying multiple ratios to a dimension, for instance.
 * @author Matt
 * @version 3.0.0
 */
public class GuiProperties {
	
	/**
	 * The {@link SimoriJFrame} is an undecorated window,
	 * but this title is still used by the operating system.
	 */
	public static final String WINDOW_TITLE = "Simori-ON";
	
	/** Proportion of screen height/width for {@link SimoriJFrame} to fill */
	public static final float SCREEN_PROPORTION = 0.8f;
	
	/** Proportion of {@link SimoriJFrame} length to round at the corners */
	public static final float ARC_PROPORTION = 0.05f;
	
	/** Number of times longer the {@link Lcd} is in its long dimension */
	public static final float LCD_EDGE_RATIO = 5f;
	
	/**
	 * Colour to fill the window (behind the rounded rectangle outline).
	 * @see SimoriJFrame
	 */
	public static final Color WINDOW_BACKGROUND = new Color(0,0,0,0);
	
	/** Colour to fill the {@link SimoriPanel}'s rounded rectangle shape */
	public static final Color SIMORI_BACKGROUND = Color.WHITE;
	
	/** Colour to fill the square background area of the {@link LedPanel} */
	public static final Color LED_PANEL_BACKGROUND = Color.WHITE;
	
	/** Colour to draw the circular border of an {@link Led} */
	public static final Color LED_BORDER = Color.BLACK;
	
	/** Default border colour of a {@link PressableCircle} */
	public static final Color CIRCLE_BORDER = Color.BLACK;
	
	/** Border colour of the {@link #Lcd} screen */
	public static final Color LCD_BORDER = Color.BLACK;
	
	/** Colour to draw the edge of the {@link LedPanel} */
	public static final Color LED_PANEL_BORDER = Color.BLACK;
	
	/** Colour to draw the rounded rectangle border of {@link SimoriPanel} */
	public static final Color SIMORI_BORDER = Color.BLACK;
	
	/** Colour to fill a pressed {@link PressableCircle} */
	public static final Color CIRCLE_PRESSED = new Color(0xEEEEEE);
	
	/** Colour to fill a non-pressed {@link PressableCircle} */
	public static final Color CIRCLE_NOT_PRESSED = Color.WHITE;
	
	/** Colour to fill an illuminated but not pressed {@link Led} */
	public static final Color LED_COLOUR_ON = new Color(255, 176, 0);
	
	/** Colour to fill an {@link Led} which is both illuminated and pressed */
	public static final Color LED_COLOUR_ON_IN = new Color(205, 126, 0);
	
	/** Colour to fill a non-illuminated, non-pressed {@link Led} */
	public static final Color LED_COLOUR_OFF = Color.WHITE;
	
	/** Colour to fill a pressed but not illuminated {@link Led} */
	public static final Color LED_COLOUR_OFF_IN = new Color(0xEEEEEE);
	
	/** Colour to draw the text of a {@link Button} */
	public static final Color BUTTON_TEXT = Color.BLACK;
	
	/** Colour to draw the text of the {@link Lcd} */
	public static final Color LCD_TEXT = Color.BLACK;
	
	/**
	 * Standard cursor for the Simori-ON application
	 * @see LedPanel
	 */
	public static final Cursor NORMAL_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
	
	/** Cursor to indicate that a {@link PressableCircle} can be clicked */
	public static final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
	
	/** Cursor to indicate that the {@link SimoriJFrame} can be dragged */
	public static final Cursor MOVE_CURSOR = new Cursor(Cursor.MOVE_CURSOR);
	
	/**
	 * Name of the image file to use as the icon for the window.
	 * @see SimoriJFrame
	 * @see ResourceManager#getResource
	 */
	private static final String ICON_NAME = "Doctor D 128px.png";
	
	/**
	 * Name of the TrueType Font file from which to load the font
	 * used in {@link Button}s and the {@link Lcd} screen.
	 * @see ResourceManager#getResource
	 */
	private static final String FONT_NAME = "cmtt12.ttf";
	
	//Resources held on to statically instead of loading multiple times
	private static Font font;
	private static Image icon;
	
	/**
	 * Creates a Dimension based on the given dimension, but
	 * resized horizontally and vertically according to the
	 * given ratios. The original dimension is not affected.
	 * Passing 1f as both x and y would simply return a copy.
	 * @param x The ratio to apply in the horizontal direction
	 * @param y The ratio to apply in the vertical direction
	 * @param original The Dimension to apply the ratios to
	 * @return A new Dimension x times wider and y times taller
	 */
	public static Dimension ratioOf(float x, float y, Dimension original) {
		float w = (float) original.width * x;
		float h = (float) original.height * y;
		return new Dimension((int) w, (int) h);
	}
	
	/**
	 * Increases the size of a graphics context's font until
	 * it can draw the given string at the maximum possible
	 * size within the given width and height allowed.
	 * @param text   The string to scale to fit
	 * @param width  The maximum width of the string
	 * @param height The maximum height of the string
	 * @param g The graphics context with the font to scale
	 */
	public static void sizeFontTo(String text,
			int width, int height, Graphics g) {
		//Remember name, style and size, ready to construct modified font
		final String name = g.getFont().getName();
		final int style = g.getFont().getStyle();
		
		//Initialise size and measure size of text at that setting
		int size = g.getFont().getSize();
		Rectangle2D b = g.getFontMetrics().getStringBounds(text, g);
		
		//Repeatedly increase font size and re-measure until it no longer fits
		while (b.getWidth() < width && b.getHeight() < height) {
			g.setFont(new Font(name, style, size++));
			b = g.getFontMetrics().getStringBounds(text, g);
		}
		
		//Decrease size to just fit, and set as g's new font
		g.setFont(new Font(name, style, size--));
	}
	
	/**
	 * Returns the {@link Font} used by the GUI.
	 * The font is loaded on the first call, and cached
	 * for subsequent calls so that it is only loaded once.
	 * @see #FONT_NAME
	 */
	public static Font getFont() {
		if (font != null) return font;
		font = makeFont();
		return font;
	}
	
	/**
	 * Returns an {@link Image} representing the icon for the GUI's window.
	 * The icon is loaded on the first call, and cached for subsequent
	 * calls so that it is only loaded once.
	 * @see #ICON_NAME
	 */
	public static Image getIcon() {
		if (icon != null) return icon;
		icon = makeIcon();
		return icon;
	}
	
	/**
	 * Attempts to return the font from the file {@link #FONT_NAME}.
	 * If this cannot be loaded, a standard serif font is returned.
	 */
	private static Font makeFont() {
		try {
			File ttf = ResourceManager.getResource(FONT_NAME);
			if (ttf == null) {
				System.err.println("Cannot locate res folder");
				return null;
			}
			return Font.createFont(Font.TRUETYPE_FONT, ttf);
		} catch (FontFormatException | IOException e) {
			System.err.println("Could not load typeface from " + FONT_NAME);
			return new Font(Font.SERIF, Font.PLAIN, 1);
		}
	}
	
	/**
	 * Attempts to return the icon from the file {@link #ICON_NAME}.
	 * @return The icon for the GUI's window, or null
	 */
	private static Image makeIcon() {
		File icon = ResourceManager.getResource(ICON_NAME);
		if (icon == null) {
			System.err.println("Cannot locate res folder");
			return null;
		}
		if (!icon.exists())
			System.err.println("Could not loat icon " + ICON_NAME);
		return new ImageIcon(icon.getAbsolutePath()).getImage();
	}
}
