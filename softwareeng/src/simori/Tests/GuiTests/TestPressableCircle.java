package simori.Tests.GuiTests;

import static org.junit.Assert.assertEquals;

import java.awt.event.MouseEvent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.SwingGui.OnPressListenerMaker;
import simori.SwingGui.OnPressListenerMaker.OnPressListener;
import simori.SwingGui.SimoriJFrame;

public class TestPressableCircle {
	
	SimoriJFrame testgui;
	OnPressListenerMaker onpress;
	OnPressListener testlistener;
	
	@Before
	public void setUp(){
		testgui = new SimoriJFrame(null);
		onpress = new OnPressListenerMaker(testgui);
		testlistener = onpress.getListener(0, 0);
	}
	
	@After
	public void tearDown(){
		testgui = null;
		onpress = null;
	}

}
