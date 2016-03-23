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
	class MockGreyCentreWipe extends GreyCentreWipe {
		public MockGreyCentreWipe(boolean ledInitGrey, boolean outwards,
				boolean btnInitGrey, boolean upwards, int size) {
			super(ledInitGrey, outwards, btnInitGrey, upwards, size);
		}
		
		public int getPhase() {
			return frameNum;
		}
	}
	
	private MockGreyCentreWipe anim;
	
	@Test
	public void testAnimationTrue() {
		anim = new MockGreyCentreWipe(true, true, true, true, 16);
		Frame frame = anim.getNextFrame();
		boolean[][] which = frame.ledsGreyed;
		for(int x=0; x<16; x++){
			for(int y=0; x<16; x++){
				assertTrue(which[x][y]);
			}
		}
		assertEquals(anim.getPhase(), 1);
	}
	
	@Test
	public void testAnimationFalse() {
		anim = new MockGreyCentreWipe(false, false, false, false, 16);
		Frame frame = anim.getNextFrame();
		boolean[][] which = frame.ledsGreyed;
		for(int x=0; x<16; x++){
			for(int y=0; x<16; x++){
				assertTrue(which[x][y]);
			}
		}
		assertEquals(anim.getPhase(), 1);
	}
	
	@Test
	public void testNextStarting() {
		anim = new MockGreyCentreWipe(false, false, false, false, 16);
		Frame frame = anim.getNextFrame();
		int size = 16;
		int symmetry = --size % 2 == 0 ? 0 : 1;
		int bl = size / 2 - anim.getPhase();
		int tr = size / 2 + anim.getPhase() + symmetry;
		for (int i = bl; i <= tr; i++) {
			assertFalse(frame.ledsGreyed[tr][i]);
			assertFalse(frame.ledsGreyed[bl][i]);
			assertFalse(frame.ledsGreyed[i][tr]);
			assertFalse(frame.ledsGreyed[i][bl]);
		}
		anim.getNextFrame();
		anim.getNextFrame();
		anim.getNextFrame();
		anim.getNextFrame();
		anim.getNextFrame();
		anim.getNextFrame();
		anim.getNextFrame();
		anim.getNextFrame();
		anim.getNextFrame();
		anim.getNextFrame();
		anim.getNextFrame();
		anim.getNextFrame();
		assertNull(anim.getNextFrame());
	}

	@Test
	public void testNextFinishing() {
		anim = new MockGreyCentreWipe(false, false, false, false, 16);
		Frame frame = anim.getNextFrame();
		int size = 16;
		int symmetry = --size % 2 == 0 ? 0 : 1;
		int bl = size / 2 - anim.getPhase();
		int tr = size / 2 + anim.getPhase() + symmetry;
		for (int i = bl; i <= tr; i++) {
			assertFalse(frame.ledsGreyed[tr][i]);
			assertFalse(frame.ledsGreyed[bl][i]);
			assertFalse(frame.ledsGreyed[i][tr]);
			assertFalse(frame.ledsGreyed[i][bl]);
		}
		anim.getNextFrame();
		anim.getNextFrame();
		anim.getNextFrame();
		anim.getNextFrame();
		anim.getNextFrame();
		anim.getNextFrame();
		anim.getNextFrame();
		anim.getNextFrame();
		anim.getNextFrame();
		anim.getNextFrame();
		anim.getNextFrame();
		anim.getNextFrame();
		assertNull(anim.getNextFrame());
	}
}
