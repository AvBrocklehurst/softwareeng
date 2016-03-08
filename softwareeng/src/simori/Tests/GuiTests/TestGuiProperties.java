package simori.Tests.GuiTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;

import org.junit.Test;

import simori.SwingGui.GuiProperties;

public class TestGuiProperties {
	
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
}
