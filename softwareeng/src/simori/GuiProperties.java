package simori;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

public class GuiProperties {
	
	public static final String WINDOW_TITLE = "Simori-ON";
	
	public static final float LCD_EDGE_RATIO = 5f;
	
	public static final Color WINDOW_BACKGROUND = new Color(0,0,0,0);
	public static final Color SIMORI_BACKGROUND = Color.WHITE;
	public static final Color LED_PANEL_BACKGROUND = Color.WHITE;
	public static final Color LED_BORDER = Color.BLACK;
	public static final Color CIRCLE_BORDER = Color.BLACK;;
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
	
	public static final Font FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 1);
	
	public static final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
	public static final Cursor MOVE_CURSOR = new Cursor(Cursor.MOVE_CURSOR);
	
	public Font font;

}
