package simori.SwingGui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class GuiProperties {
	
	public static final String WINDOW_TITLE = "Simori-ON";
	
	public static final float SCREEN_PROPORTION = 0.8f;
	public static final float LCD_EDGE_RATIO = 5f;
	
	public static final Color WINDOW_BACKGROUND = new Color(0,0,0,0);
	public static final Color SIMORI_BACKGROUND = Color.WHITE;
	public static final Color LED_PANEL_BACKGROUND = Color.WHITE;
	
	public static final Color LED_BORDER = Color.BLACK;
	public static final Color CIRCLE_BORDER = Color.BLACK;
	public static final Color LCD_BORDER = Color.BLACK;
	public static final Color LED_PANEL_BORDER = Color.BLACK;
	public static final Color SIMORI_BORDER = Color.BLACK;
	
	public static final Color CIRCLE_PRESSED = new Color(0xEEEEEE);
	public static final Color CIRCLE_NOT_PRESSED = Color.WHITE;
	public static final Color LED_COLOUR_ON = new Color(255, 176, 0);
	public static final Color LED_COLOUR_ON_IN = new Color(205, 126, 0);
	public static final Color LED_COLOUR_OFF = Color.WHITE;
	public static final Color LED_COLOUR_OFF_IN = new Color(0xEEEEEE);
	
	public static final Color BUTTON_TEXT = Color.BLACK;
	public static final Color LCD_TEXT = Color.BLACK;
	
	public static final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
	public static final Cursor MOVE_CURSOR = new Cursor(Cursor.MOVE_CURSOR);
	
	private static final String ICON_NAME = "Doctor D 128px.png";
	private static final String FONT_NAME = "cmtt12.ttf";
	private static final String UP_UP = "../../";
	
	private static Font font;
	private static Image icon;
	
	public static Dimension ratioOf(float x, float y, Dimension original) {
		float w = (float) original.width * x;
		float h = (float) original.height * y;
		return new Dimension((int) w, (int) h);
	}
	
	public static void sizeFontTo(String text, int width, int height, Graphics g) {
		final String name = g.getFont().getName();
		final int style = g.getFont().getStyle();
		int size = g.getFont().getSize();
		Rectangle2D b = g.getFontMetrics().getStringBounds(text, g);
		while (b.getWidth() < width && b.getHeight() < height) {
			g.setFont(new Font(name, style, size++));
			b = g.getFontMetrics().getStringBounds(text, g);
		}
		g.setFont(g.getFont().deriveFont(--size));
	}
	
	public static Font getFont() {
		if (font != null) return font;
		font = makeFont();
		return font;
	}
	
	public static Image getIcon() {
		if (icon != null) return icon;
		icon = makeIcon();
		return icon;
	}
	
	private static Font makeFont() {
		try {
			return Font.createFont(Font.TRUETYPE_FONT, findFile(FONT_NAME));
		} catch (FontFormatException | IOException e) {
			System.err.println("Could not get font from ttf");
			return new Font(Font.SERIF, Font.PLAIN, 1);
		}
	}
	
	private static Image makeIcon() {
		return new ImageIcon(findFile(ICON_NAME).getPath()).getImage();
	}
	
	private static File findFile(String name) {
		File file = new File(name);
		if (!file.exists())
			file = new File(UP_UP + name);
		return file;
	}
}
