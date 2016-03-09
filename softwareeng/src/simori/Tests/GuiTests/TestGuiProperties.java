package simori.Tests.GuiTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFrame;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import simori.ResourceManager;
import simori.SwingGui.GuiProperties;

/**
 * Tests {@link GuiProperties} to 98.7% coverage and
 * indirectly tests unusual paths in {@link ResourceManager}.
 * Suffers from major problems relating to file handles and test ordering.
 * After the font has been loaded once with {@link Font#createFont(int, File)},
 * the TrueType file in the res folder inexplicably remains locked for writing.
 * This causes subsequent tests which rely on renaming or overwriting this file
 * to fail. There is no way to release the handle except by exiting the JVM, so
 * setUp and tearDown methods cannot be used to make the tests' outcomes
 * independent of their execution order. Once the font is successfully loaded
 * from the file, tests which rely on temporarily renaming the file or the res
 * folder to test exceptional behaviour will fail. Many other GUI and non-GUI
 * tests involve instantiating a SimoriJFrame, so will cause the font to be
 * loaded. Therefore, the tests in this class must be run before all others.
 * To ensure that the test which successfully loads the font runs last,
 * the method order has been fixed as ascending alphabetical name and a 'Z' has
 * been inserted into the name of the successful font and icon loading tests.
 * 
 * Update: The tests which had to be run last have been commented out,
 * because FixMethodOrder does not seem to work in the Blue Room.
 * 
 * @author Matt
 * @version 3.0.5
 */
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)	//Doesn't work in Blue Room!
public class TestGuiProperties {
	
	// protected constants borrowed from GuiProperties
	private static final String FONT = MockProperties.getFontName();
	private static final String ICON = MockProperties.getIconName();
	private static final Font BACKUP = MockProperties.getBackupFont();
	
	// Example strings and dimensions for testing font sizing
	private static final int DIMENSION = 200;
	private static final String TEXT = "short";
	
	// String to temporarily rename files to when breaking
	private static final String NOT_FILE = "notFile";
	
	// Information for restoring temporarily renamed files
	private File fileName, notFileName;
	private boolean overwritten;
	
	@Before
	public void setUp() {
		GuiProperties.clearCache(); // Previously loaded font and icon
		fileName = notFileName = null;
		overwritten = false;
	}
	
	@After
	public void tearDown() {
		if (notFileName != null) fixFile(); // Undo renaming of file / folder
	}
	
	/**
	 * Asserts that {@link GuiProperties#ratioOf} returns a new
	 * {@link Dimension} instead of modifying the original.
	 */
	@Test
	public void testRatioReferences() {
		Dimension a = new Dimension(100, 100);
		Dimension b = GuiProperties.ratioOf(0.1f, 0.1f, a);
		assertFalse(a == b);
	}
	
	/**
	 * Asserts that {@link GuiProperties#ratioOf} returns an identical
	 * {@link Dimension} when asked to resize to 100% in both dimensions.
	 */
	@Test
	public void testRatioOfOne() {
		Dimension a = new Dimension(100, 100);
		Dimension b = GuiProperties.ratioOf(1f, 1f, a);
		assertTrue(a.equals(b));
	}
	
	/** Tests resizing a dimension to be 100 times smaller */
	@Test
	public void testRatioOfHundredth() {
		Dimension a = new Dimension(100, 100);
		Dimension b = GuiProperties.ratioOf(0.01f, 0.01f, a);
		assertEquals(a.width / 100, b.width);
	}
	
	/** Tests resizing a dimension to be twice as large */
	@Test
	public void testRatioOfTwo() {
		Dimension a = new Dimension(100, 100);
		Dimension b = GuiProperties.ratioOf(2f, 2f, a);
		assertEquals(a.height * 2, b.height);
	}

	/**
	 * Tests that {@link GuiProperties#sizeFontTo} produces a smaller
	 * font size when fitting a longer string in an area of the same size.
	 */
	@Test
	public void testSizeFontString() {
		
		JFrame frame = new JFrame("Testing!");
		frame.setVisible(true); // Must be visible or getGraphics returns null
		Graphics g = frame.getGraphics();
		g.setFont(new Font(Font.SERIF, Font.PLAIN, 1)); // Start at min size
		GuiProperties.sizeFontTo(TEXT, DIMENSION, DIMENSION, g);
		int sizeShorter = g.getFont().getSize(); // Original string font size
		g.setFont(new Font(Font.SERIF, Font.PLAIN, 1)); // Reset to min size
		GuiProperties.sizeFontTo(TEXT + TEXT + TEXT, DIMENSION, DIMENSION, g);
		int sizeLonger = g.getFont().getSize(); // Second string font size
		frame.setVisible(false);
		assertTrue(sizeShorter > sizeLonger);
	}
	
	/**
	 * Tests that {@link GuiProperties#sizeFontTo} produces a smaller font
	 * size when fitting a string of the same length into a smaller area.
	 */
	@Test
	public void testSizeFontSpace() {
		JFrame frame = new JFrame("Testing!");
		frame.setVisible(true); // Must be visible or getGraphics returns null
		Graphics g = frame.getGraphics();
		g.setFont(new Font(Font.SERIF, Font.PLAIN, 1));
		GuiProperties.sizeFontTo(TEXT, DIMENSION, DIMENSION, g);
		int sizeMoreSpace = g.getFont().getSize(); // Size in original area
		g.setFont(new Font(Font.SERIF, Font.PLAIN, 1));
		GuiProperties.sizeFontTo(TEXT, DIMENSION / 2, DIMENSION / 2, g);
		int sizeLessSpace = g.getFont().getSize(); // Size in smaller area
		frame.setVisible(false);
		assertTrue(sizeMoreSpace > sizeLessSpace);
	}
	
	/** Tests that the backup font is used when the res folder is not found */
	@Test
	public void testFontNoResFolder() throws IOException {
		File inRes = ResourceManager.getResource("thing");
		breakFile(inRes.getParentFile(), false); // Temporarily rename res
		GuiProperties.clearCache(); // Remove existing reference to font
		Font font = GuiProperties.getFont(); // Request font
		assertEquals(BACKUP, font);
	}
	
	/** Tests that the backup font is used when the TTF file is not found */
	@Test
	public void testFontNotFound() throws IOException {
		breakFile(ResourceManager.getResource(FONT), false);
		GuiProperties.clearCache();
		Font font = GuiProperties.getFont();
		assertEquals(BACKUP, font);
	}
	
	/** Tests that the backup font is used when the TTF file is corrupted */
	@Test
	public void testFontFileBroken() throws IOException {
		breakFile(ResourceManager.getResource(FONT), true); //Overwrite ttf
		GuiProperties.clearCache();
		Font font = GuiProperties.getFont();
		assertEquals(BACKUP, font);
	}
	
	/** Tests that the icon is null when the res folder is not found */
	@Test
	public void testIconNoResFolder() throws IOException {
		File inRes = ResourceManager.getResource("thing");
		breakFile(inRes.getParentFile(), false);
		GuiProperties.clearCache();
		Image icon = GuiProperties.getIcon();
		assertNull(icon);
	}
	
	/** Tests that the icon is null when the PNG file is not found */
	@Test
	public void testIconNotFound() throws IOException {
		breakFile(ResourceManager.getResource(ICON), false);
		GuiProperties.clearCache();
		Image icon = GuiProperties.getIcon();
		assertNull(icon);
	}
	
	/**
	 * Tests that the second call to {@link GuiProperties#getIcon}
	 * returns a cached version the same as the first, even if the
	 * PNG file is renamed in the meantime.
	 */
	//@Test	//Disabled because FixMethodOrder doesn't work
	public void testY_Icon() throws IOException {
		Image made = GuiProperties.getIcon();
		breakFile(ResourceManager.getResource(ICON), false);
		Image cached = GuiProperties.getIcon();
		assertEquals(made, cached);
	}
	
	/**
	 * Tests that the second call to {@link GuiProperties#getFont}
	 * returns a cached version the same as the first.
	 * Must be the last font-related test from this class to be run.
	 * Tests which rely on renaming the res folder or TTF file will
	 * fail if run after this test.
	 * @see TestGuiProperties
	 */
	//@Test	//Disabled because FixMethodOrder doesn't work
	public void testZ_Font() throws IOException {
		Font made = GuiProperties.getFont();
		// Calling breakFile here fails because it's locked
		Font cached = GuiProperties.getFont();
		assertEquals(made, cached);
	}

	/**
	 * Renames the given file to {@link #NOT_FILE} so that
	 * behaviour occurring when it does not exist can be tested.
	 * It can also optionally overwrite the file,
	 * to simulate it being corrupted as opposed to non-existent.
	 * This can all be undone with {@link #fixFile},
	 * which is called automatically in {@link #tearDown}.
	 * @param fileName The file to temporarily rename and/or overwrite
	 * @param overwrite true to change the contents of the given file
	 * @throws IOException If the file could not be overwritten
	 */
	private void breakFile(File fileName, boolean overwrite)
			throws IOException {
		this.fileName = fileName;
		this.overwritten = overwrite;
		
		// Create a pointer to NOT_FILE in the same directory
		notFileName = new File(fileName.getParentFile(), NOT_FILE);
		
		// Rename given file to NOT_FILE
		boolean broke = fileName.renameTo(notFileName);
		if (!broke) {
			// File cannot be written to, so fail test
			String msg = "Could not tamper with " + fileName.getName();
			fail(msg + " for testing purposes as writing is locked.");
		}
		if (overwrite) overwriteFile();
	}
	
	/**
	 * Writes some ASCII characters to {@link #fileName},
	 * causing it to exist if it did not already.
	 */
	private void overwriteFile() throws IOException {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(fileName);
			final byte T = 84, R = 114, O = 111, L = 108;
			byte[] bytes = {T,R,O,L,L,O,L,O,L,O,L,O,L,O,L};
			out.write(bytes, 0, bytes.length);
		} finally {
			if (out != null) out.close();
		}
	}
	
	/**
	 * Restores the last file broken with {@link #breakFile}
	 * by renaming it back to its original name,
	 * and deleting what it was replaced with, if anything.
	 */
	private void fixFile() {
		if (overwritten) fileName.delete();
		notFileName.renameTo(fileName);
	}
	
	/** Exposes some protected fields of GuiProperties for test purposes */
	private static class MockProperties extends GuiProperties {
		public static String getFontName() {
			return FONT_NAME;
		}
		public static String getIconName() {
			return ICON_NAME;
		}
		public static Font getBackupFont() {
			return BACKUP;
		}
	}
}