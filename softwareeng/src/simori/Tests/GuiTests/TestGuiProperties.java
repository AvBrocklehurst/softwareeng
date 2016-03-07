package simori.Tests.GuiTests;

import static org.junit.Assert.fail;

import org.junit.Test;

import simori.SwingGui.GuiProperties;

public class TestGuiProperties {
	
	class MockGuiProperties extends GuiProperties {
		private static final String FONT_NAME = "something that doesn't exist";
	}

	@Test
	public void testRatioOf() {
		fail("Not yet implemented");
	}

	@Test
	public void testSizeFontTo() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFont() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetIcon() {
		fail("Not yet implemented");
	}
}
