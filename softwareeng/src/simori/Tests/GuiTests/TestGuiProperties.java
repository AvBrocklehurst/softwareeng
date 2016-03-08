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
 * Tests {@link GuiProperties} to 98.7% coverage.
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
 * TODO and explain the zs
 * to be loaded
 * @author Matt
 * @version 3.0.5
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestGuiProperties {
	
	// protected constants borrowed from GuiProperties
	private static final String FONT = MockProperties.getFontName();
	private static final String ICON = MockProperties.getIconName();
	private static final Font BACKUP = MockProperties.getBackupFont();
	
	// Information for temporarily 
	private File fileName, notFileName;
	private boolean overwritten;
	
	@Before
	public void setUp() {
		GuiProperties.clearCache();
		fileName = notFileName = null;
		overwritten = false;
	}
	
	@After
	public void tearDown() {
		if (notFileName != null) fixFile();
		GuiProperties.clearCache();
		fileName = notFileName = null;
	}
	
	@Test
	public void testRatioReferences() {
		Dimension a = new Dimension(100, 100);
		Dimension b = GuiProperties.ratioOf(0.1f, 0.1f, a);
		assertFalse(a == b);
	}
	
	@Test
	public void testRatioOfOne() {
		Dimension a = new Dimension(100, 100);
		Dimension b = GuiProperties.ratioOf(1f, 1f, a);
		assertTrue(a.equals(b));
	}
	
	@Test
	public void testRatioOfHundredth() {
		Dimension a = new Dimension(100, 100);
		Dimension b = GuiProperties.ratioOf(0.01f, 0.01f, a);
		assertEquals(a.width / 100, b.width);
	}
	
	@Test
	public void testRatioOfTwo() {
		Dimension a = new Dimension(100, 100);
		Dimension b = GuiProperties.ratioOf(2f, 2f, a);
		assertEquals(a.height * 2, b.height);
	}

	@Test
	public void testSizeFontString() {
		final int dimension = 200;
		final String text = "short";
		JFrame frame = new JFrame("Testing!");
		frame.setVisible(true);
		Graphics g = frame.getGraphics();
		g.setFont(new Font(Font.SERIF, Font.PLAIN, 1));
		GuiProperties.sizeFontTo(text, dimension, dimension, g);
		int sizeShorter = g.getFont().getSize();
		g.setFont(new Font(Font.SERIF, Font.PLAIN, 1));
		GuiProperties.sizeFontTo(text + text + text, dimension, dimension, g);
		int sizeLonger = g.getFont().getSize();
		frame.setVisible(false);
		assertTrue(sizeShorter > sizeLonger);
	}
	
	@Test
	public void testSizeFontSpace() {
		final int dimension = 200;
		final String text = "short";
		JFrame frame = new JFrame("Testing!");
		frame.setVisible(true);
		Graphics g = frame.getGraphics();
		g.setFont(new Font(Font.SERIF, Font.PLAIN, 1));
		GuiProperties.sizeFontTo(text, dimension, dimension, g);
		int sizeMoreSpace = g.getFont().getSize();
		g.setFont(new Font(Font.SERIF, Font.PLAIN, 1));
		GuiProperties.sizeFontTo(text, dimension / 2, dimension / 2, g);
		int sizeLessSpace = g.getFont().getSize();
		frame.setVisible(false);
		assertTrue(sizeMoreSpace > sizeLessSpace);
	}
	
	@Test
	public void testFontNoResFolder() throws IOException {
		File inRes = ResourceManager.getResource("thing");
		breakFile(inRes.getParentFile(), false);
		GuiProperties.clearCache();
		Font font = GuiProperties.getFont();
		assertEquals(BACKUP, font);
	}
	
	@Test
	public void testFontNotFound() throws IOException {
		breakFile(ResourceManager.getResource(FONT), false);
		GuiProperties.clearCache();
		Font font = GuiProperties.getFont();
		assertEquals(BACKUP, font);
	}
	
	@Test
	public void testFontFileBroken() throws IOException {
		breakFile(ResourceManager.getResource(FONT), true);
		GuiProperties.clearCache();
		Font font = GuiProperties.getFont();
		assertEquals(BACKUP, font);
	}
	
	@Test
	public void testIconNoResFolder() throws IOException {
		File inRes = ResourceManager.getResource("thing");
		breakFile(inRes.getParentFile(), false);
		GuiProperties.clearCache();
		Image icon = GuiProperties.getIcon();
		assertNull(icon);
	}
	
	@Test
	public void testIconNotFound() throws IOException {
		breakFile(ResourceManager.getResource(ICON), false);
		GuiProperties.clearCache();
		Image icon = GuiProperties.getIcon();
		assertNull(icon);
	}
	
	@Test
	public void testZ_Font() throws IOException {
		Font made = GuiProperties.getFont();
		// Cannot break file here
		Font cached = GuiProperties.getFont();
		assertEquals(made, cached);
	}

	@Test
	public void testZ_Icon() throws IOException {
		Image made = GuiProperties.getIcon();
		breakFile(ResourceManager.getResource(ICON), false);
		Image cached = GuiProperties.getIcon();
		assertEquals(made, cached);
	}
	
	private void breakFile(File fileName, boolean overwrite)
			throws IOException {
		this.fileName = fileName;
		this.overwritten = overwrite;
		notFileName = new File(fileName.getParentFile(), "notFile");
		boolean broke = fileName.renameTo(notFileName);
		if (!broke) {
			String msg = "Could not tamper with " + fileName.getName();
			fail(msg + " for testing purposes as writing is locked.");
		}
		if (overwrite) overwriteFile();
	}
	
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
	
	private void fixFile() {
		if (overwritten) fileName.delete();
		notFileName.renameTo(fileName);
	}
	
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
