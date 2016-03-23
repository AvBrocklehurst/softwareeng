package simori.Tests;

import static org.junit.Assert.*;

import org.junit.*;

import simori.GreyCentreWipe;
import simori.SimoriGui.Animation.Frame;
import simori.SimoriGui.Animation.OnFinishListener;
import simori.AudioFeedbackSystem;
import simori.MIDISoundSystem;
import simori.MatrixModel;
import simori.Exceptions.SimoriNonFatalException;
import simori.Modes.QwertyKeyboard;
import simori.SwingGui.SimoriJFrame;

/**
 * 
 * @author Jurek
 *
 */
public class TestGreyCentreWipe {
	public Moc	s
	
	private GreyCentreWipe anim;
	
	@Test
	public void testAnimation() {
		anim = new GreyCentreWipe(true, true, true, true, 16);
		Frame frame = anim.getNextFrame();
		boolean[][] which = frame.ledsGreyed;
		for(int x=0; x<16; x++){
			for(int y=0; x<16; x++){
				assertTrue(which[x][y]);
			}
		}
		int count = 0;
		for(int column=0; column<8; column++){
			if(which[column][8]==true) count++;
		}
		assertEquals(count, 0);
	}
	
	@Test
	public void testAnimationFinishing() {
		anim = new GreyCentreWipe(false, false, false, false, 16);
		Frame frame = anim.getNextFrame();
		boolean[][] which = frame.ledsGreyed;
		for(int x=0; x<16; x++){
			for(int y=0; x<16; x++){
				assertFalse(which[x][y]);
			}
		}
		int count = 0;
		for(int column=0; column<8; column++){
			if(which[column][8]==true) count++;
		}
		assertEquals(count, 7);
	}
	
	@Test (expected=UnsupportedOperationException.class)
	public void testNextStarting() {
		anim = new GreyCentreWipe(false, false, false, false, 16);
		anim.getNextFrame();
		int size = 16;
		int symmetry = --size % 2 == 0 ? 0 : 1;
		int bl = size / 2 - anim.getPhase();
		int tr = size / 2 + anim.getPhase() + symmetry;
		for (int i = bl; i <= tr; i++) {
			assertTrue(anim.getWhich()[tr][i]);
			assertTrue(anim.getWhich()[bl][i]);
			assertTrue(anim.getWhich()[i][tr]);
			assertTrue(anim.getWhich()[i][bl]);
		}
		anim.getNextFrame();
		anim.getNextFrame();
		anim.getNextFrame();
		anim.getNextFrame();
		anim.getNextFrame();
		anim.getNextFrame();
		anim.getNextFrame();
		assertNull(anim.getNextFrame());
	}

	//@Test (expected=UnsupportedOperationException.class)
	public void testNextFinishing() {
		anim = new GreyCentreWipe(false, false, false, false, 16);
		anim.getNextFrame();
		int size = 16;
		int symmetry = --size % 2 == 0 ? 0 : 1;
		int bl = size / 2 - anim.getPhase();
		int tr = size / 2 + anim.getPhase() + symmetry;
		for (int i = bl; i <= tr; i++) {
			assertFalse(anim.getWhich()[tr][i]);
			assertFalse(anim.getWhich()[bl][i]);
			assertFalse(anim.getWhich()[i][tr]);
			assertFalse(anim.getWhich()[i][bl]);
		}
		anim.getNextFrame();
		anim.getNextFrame();
		anim.getNextFrame();
		anim.getNextFrame();
		anim.getNextFrame();
		anim.getNextFrame();
		anim.getNextFrame();
		assertNull(anim.getNextFrame());
	}
	
	//@Test (expected=UnsupportedOperationException.class)
	public void testFinishing() {
		anim = new GreyCentreWipe(true, true, true, true, 16);
		anim.finished();
	}
}
