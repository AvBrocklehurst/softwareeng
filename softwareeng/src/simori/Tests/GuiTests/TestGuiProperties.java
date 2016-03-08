package simori.Tests.GuiTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
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
import org.junit.Test;

import simori.ResourceManager;
import simori.SwingGui.GuiProperties;

public class TestGuiProperties {
	
	private static final String FONT = MockProperties.getFontName();
	private static final String ICON = MockProperties.getIconName();
	private static final Font BACKUP = MockProperties.getBackupFont();
	
	private File fileName, notFileName;
	
	@After
	public void tearDown() {
		GuiProperties.clearCache();
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
		breakFile(inRes.getParentFile(), false, true);
		Font font = GuiProperties.getFont();
		fixFile(false);
		assertEquals(BACKUP, font);
	}
	
	@Test
	public void testAaFontNotFound() throws IOException {
		breakFile(ResourceManager.getResource(FONT), false, true);
		Font font = GuiProperties.getFont();
		fixFile(false);
		assertEquals(BACKUP, font);
	}
	
	@Test
	public void testFontResFolder() {
		assertNotNull(GuiProperties.getFont());
	}
	
	@Test
	public void testFontFileBroken() throws IOException {
		breakFile(ResourceManager.getResource(FONT), true, true);
		Font font = GuiProperties.getFont();
		fixFile(true);
		assertEquals(BACKUP, font);
	}
	
	@Test
	public void testIconNoResFolder() throws IOException {
		File inRes = ResourceManager.getResource("thing");
		breakFile(inRes.getParentFile(), false, true);
		Image icon = GuiProperties.getIcon();
		fixFile(false);
		assertNull(icon);
	}
	
	@Test
	public void testIconResFolder() {
		assertNotNull(GuiProperties.getIcon());
	}
	
	@Test
	public void testIconNotFound() throws IOException {
		breakFile(ResourceManager.getResource(ICON), false, true);
		Image icon = GuiProperties.getIcon();
		fixFile(false);
		assertNull(icon);
	}
	
	@Test
	public void testFontCached() throws IOException {
		GuiProperties.clearCache();
		Font made = GuiProperties.getFont();
		breakFile(ResourceManager.getResource(FONT), false, false);
		Font cached = GuiProperties.getFont();
		fixFile(false);
		assertEquals(made, cached);
	}
	
	@Test
	public void testIconCached() throws IOException {
		GuiProperties.clearCache();
		Image made = GuiProperties.getIcon();
		breakFile(ResourceManager.getResource(ICON), false, false);
		Image cached = GuiProperties.getIcon();
		fixFile(false);
		assertEquals(made, cached);
	}
	
	@Test
	public void testClearCache() {
		//TODO check it uncaches
	}
	
	//TODO Check retrieval without making
	
	private void breakFile(File fileName, boolean overwrite, boolean uncache)
			throws IOException {
		this.fileName = fileName;
		notFileName = new File(fileName.getParentFile(), "notFile");
		boolean broke = fileName.renameTo(notFileName);
		if (!broke) fail("Could not tamper with file as writing is locked!");
		if (uncache) GuiProperties.clearCache();
		FileOutputStream out = null;
		if (overwrite) try {
			out = new FileOutputStream(fileName);
			final byte T = 84, R = 114, O = 111, L = 108;
			byte[] bytes = {T,R,O,L,L,O,L,O,L,O,L,O,L,O,L};
			out.write(bytes, 0, bytes.length);
		} finally {
			if (out != null) out.close();
		}
	}
	
	private void fixFile(boolean overwritten) {
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
