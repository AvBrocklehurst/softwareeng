package simori.Tests;

import static org.junit.Assert.*;

import org.junit.*;

import simori.Animation;
import simori.Animation.OnFinishListener;
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

	private class MockAnimation extends Animation {
		public MockAnimation(int size, OnFinishListener listener, boolean isStartup) {
			super(size, listener, isStartup);
		}

		public int getPhase() {
			return phase;
		}
		
		public boolean[][] getWhich() {
			return which;
		}
	}
	
	private MockAnimation anim;
	
	public MockAnimation constructAnimation(boolean isStarted) {
		OnFinishListener finishFunction = new OnFinishListener() {
			@Override
			public void onAnimationFinished() throws SimoriNonFatalException {
				throw new UnsupportedOperationException();
			}
		};
		return new MockAnimation(16, finishFunction, isStarted);
	}
	
	@Test
	public void testAnimation() {
		anim = constructAnimation(true);
		boolean[][] which = anim.getWhich();
		for(int x=0; x<16; x++){
			for(int y=0; x<16; x++){
				assertTrue(which[x][y]);
			}
		}
		assertEquals(anim.getPhase(), 0);
	}
	
	@Test
	public void testAnimationFinishing() {
		anim = constructAnimation(false);
		boolean[][] which = anim.getWhich();
		for(int x=0; x<16; x++){
			for(int y=0; x<16; x++){
				assertFalse(which[x][y]);
			}
		}
		assertEquals(anim.getPhase(), 7);
	}
	
	@Test (expected=UnsupportedOperationException.class)
	public void testNextStarting() {
		anim = constructAnimation(true);
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

	@Test (expected=UnsupportedOperationException.class)
	public void testNextFinishing() {
		anim = constructAnimation(false);
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
	
	@Test (expected=UnsupportedOperationException.class)
	public void testFinishing() {
		anim = constructAnimation(true);
		anim.finished();
	}
}
