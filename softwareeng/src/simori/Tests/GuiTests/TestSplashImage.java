package simori.Tests.GuiTests;

import static org.junit.Assert.*;

import java.awt.Dimension;

import org.junit.*;

import simori.SwingGui.GuiProperties;
import simori.SwingGui.ImageComponent;
import simori.SwingGui.SplashImage;

public class TestSplashImage {

	private ImageComponent image;
	
	@Before
	public void setUp() {
		image = new ImageComponent();
	}

	@After
	public void tearDown() {
		image = null;
	}
	
	@Test
	public void testGetXSize() {
		Dimension preferred = image.getPreferredSize();
		Dimension size = image.getSize();
		Dimension minimum = image.getMinimumSize();
		Dimension maximum = image.getMaximumSize();
		assertEquals(preferred, size);
		assertEquals(preferred, minimum);
		assertEquals(preferred, maximum);
	}
}
