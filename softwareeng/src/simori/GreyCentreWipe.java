package simori;

import static simori.FunctionButton.L1;
import static simori.FunctionButton.L2;
import static simori.FunctionButton.L3;
import static simori.FunctionButton.L4;
import static simori.FunctionButton.OK;
import static simori.FunctionButton.R1;
import static simori.FunctionButton.R2;
import static simori.FunctionButton.R3;
import static simori.FunctionButton.R4;

import java.util.HashMap;

import simori.SimoriGui.Animation;

/**
 * Animation which greys or un-greys out the LEDs in
 * the grid in an inward or outward radial wipe pattern,
 * then greys out or un-greys out the buttons one 'row'
 * at a time, from top to bottom (reverse 'numerical' order)
 * or bottom to top ('numerical' order). Note: The ON button
 * is not animated, as it should never be greyed out.
 * @author Matt
 * @version 2.2.0
 */
public class GreyCentreWipe implements Animation {
	
	/**
	 * The 'rows' of FunctionButtons corresponding to the
	 * buttons to grey or un-grey out, in ascending numerical
	 * ('top to bottom') order.
	 */
	private static final FunctionButton[][] BUTTONS =
		{{L1, R1}, {L2, R2}, {L3, R3}, {L4, R4}, {OK}};
	
	private boolean outwards; // Whether LED change starts at centre
	private boolean upwards; // Whether button change starts at bottom
	protected int frameNum;   // Counter for frames produced
	private int size;      // Dimension of LED grid to animate
	private Frame frame;  // Frame to modify each time
	
	/**
	 * Provides the grid size, initial greyed state and direction of the wipe.
	 * @param ledInitGrey true if the LEDs should all be grey at the start
	 * @param outwards true if the wipe should start at the centre
	 * @param btnInitGrey true if the side buttons should be grey at the start
	 * @param upwards true if the buttons should change in bottom to top order
	 * @param size the dimension of the LED grid
	 */
	public GreyCentreWipe(boolean ledInitGrey, boolean outwards,
							boolean btnInitGrey, boolean upwards, int size) {
		this.outwards = outwards;
		this.upwards = upwards;
		this.size = size;
		frame = new Frame(); // The same one will be returned each time
		frame.ledsGreyed = new boolean[size][size];
		frame.btnsGreyed = new HashMap<FunctionButton, Boolean>();
		// Leave frame.ledsIlluminated null, so that illumination is ignored
		frameNum = 0;
		initialise(ledInitGrey, btnInitGrey);
	}
	
	/**
	 * Sets the buttons and grid entirely grey or non-grey,
	 * according to the starting parameters specified.
	 * @see frame
	 * @param ledInitGrey true if the entire grid should be initially grey
	 * @param btnInitGrey true if all buttons should be initially greyed
	 */
	private void initialise(boolean ledInitGrey, boolean btnInitGrey) {
		for (boolean[] row : frame.ledsGreyed) {
			for (int i = 0; i < row.length; i++)
				row[i] = ledInitGrey;
		}
		for (FunctionButton[] row : BUTTONS) {
			for (FunctionButton fb : row) {
				frame.btnsGreyed.put(fb, btnInitGrey);
			}
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public int getFrameCount() {
		return size / 2 + BUTTONS.length;
	}
	
	/** {@inheritDoc} */
	@Override
	public Frame getNextFrame() {
		// Tick further than the wipe needs
		int excess = frameNum - size / 2;
		if (excess < 0) {
			// Wipe has not reached edge / centre yet
			int phase = outwards ? frameNum : (size / 2 - frameNum - 1);
			invertSquare(frame.ledsGreyed, phase); // Apply next wipe stage
		} else if (excess < BUTTONS.length) {
			// Wipe is finished, but there are buttons still to change
			int i = upwards ? (BUTTONS.length - 1 - excess) : excess;
			for (FunctionButton fb : BUTTONS[i]) {
				boolean greyed = frame.btnsGreyed.get(fb);
				frame.btnsGreyed.put(fb, !greyed);
			}
		} else {
			// All LEDs and buttons changed, so indicate animation is over
			frame = null;
		}
		frameNum++;
		return frame;
	}
	
	/**
	 * Inverts the boolean values along the edges of
	 * a 'square' in the given multidimensional array.
	 * @param grid Square grid to draw square in
	 * @param phase Distance from centre of grid to draw
	 */
	private void invertSquare(boolean[][] grid, int phase) {
		/*
		 * Works out coordinates of square's edges.
		 * Bottom left corner is (bl, bl)
		 * Top right corner is (tr, tr)
		 * Other corners are (bl, tr) and (tr, bl)
		 */
		int size = grid.length;
		int symmetry = --size % 2 == 0 ? 0 : 1;
		int bl = size / 2 - phase;
		int tr = size / 2 + phase + symmetry;
		
		// Invert booleans along edges of square
		for (int i = bl + 1; i < tr; i++) {
			grid[tr][i] = !grid[tr][i];
			grid[bl][i] = !grid[bl][i];
			grid[i][tr] = !grid[i][tr];
			grid[i][bl] = !grid[i][bl];
		}
		
		// Corners of square must also be flipped
		grid[tr][tr] = !grid[tr][tr];
		grid[tr][bl] = !grid[tr][bl];
		grid[bl][tr] = !grid[bl][tr];
		grid[bl][bl] = !grid[bl][bl];
	}
}
