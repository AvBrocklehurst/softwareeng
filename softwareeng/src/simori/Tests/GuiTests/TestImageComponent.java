package simori.Tests.GuiTests;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;

import org.junit.*;

import simori.SwingGui.GuiProperties;
import simori.SwingGui.ImageComponent;
import sun.awt.image.ToolkitImage;

/**
 * 
 * @author Jurek
 *
 */
public class TestImageComponent {
	
	class MockImageComponent extends ImageComponent {

		public MockImageComponent(String fileName, String backupText, float minScrProp, float maxScrProp,
				float minResize, float maxResize) {
			super(fileName, backupText, minScrProp, maxScrProp, minResize, maxResize);
		}
		
		public MockImageComponent(String fileName, String backupText, int width, int height) {
			super(fileName, backupText, width, height);
		}

		public Image getImage() {
			return image;
		}
		
	}
	
	@Test
	public void testGetXSize() {
		MockImageComponent image = new MockImageComponent(
				GuiProperties.SPLASH_IMAGE,
				GuiProperties.SPLASH_BACKUP_TEXT,
				GuiProperties.SPLASH_MIN_PROPORTION,
				GuiProperties.SPLASH_MAX_PROPORTION,
				GuiProperties.SPLASH_MIN_RESIZE,
				GuiProperties.SPLASH_MAX_RESIZE);
		
		Dimension preferred = image.getPreferredSize();
		Dimension size = image.getSize();
		Dimension minimum = image.getMinimumSize();
		Dimension maximum = image.getMaximumSize();
		assertEquals(preferred, size);
		assertEquals(preferred, minimum);
		assertEquals(preferred, maximum);
	}
	
	@Test (expected=NullPointerException.class)
	public void testImageComponentNullBackup() {
		MockImageComponent image = new MockImageComponent(
				"test.test",
				null,
				GuiProperties.SPLASH_MIN_PROPORTION,
				GuiProperties.SPLASH_MAX_PROPORTION,
				GuiProperties.SPLASH_MIN_RESIZE,
				GuiProperties.SPLASH_MAX_RESIZE);
	}
	
	@Test
	public void testImageComponentSetSize() {
		MockImageComponent image = new MockImageComponent(
				GuiProperties.SPLASH_IMAGE,
				GuiProperties.SPLASH_BACKUP_TEXT,
				300,
				200);
		
		assertEquals(image.getImage().getWidth(null), 300);
		assertEquals(image.getImage().getHeight(null), 200);
	}
	
	@Test
	public void testImageComponentBackup() {
		MockImageComponent image = new MockImageComponent(
				"test.test",
				GuiProperties.SPLASH_BACKUP_TEXT,
				GuiProperties.SPLASH_MIN_PROPORTION,
				GuiProperties.SPLASH_MAX_PROPORTION,
				GuiProperties.SPLASH_MIN_RESIZE,
				GuiProperties.SPLASH_MAX_RESIZE);
		
		assertThat(image.getImage(), instanceOf(BufferedImage.class));
	}
}
