package simori.Modes;

import simori.ModeController;
import simori.SimoriGui.FunctionButtonEvent;
import simori.SimoriGui.GridButtonEvent;
import simori.Exceptions.InvalidCoordinatesException;
import simori.Modes.NetworkMaster.ScanProgressListener;

/**
 * Mode which initiates a {@link NetworkMaster} scan for another Simori-ON
 * on the same network, and displays its progress by activating one LED at
 * a time, in a sequence that resembles a dot moving across a screen.
 * Messages indicating the state of the scan are displayed on the LCD screen.
 * @author Matt
 * @version 1.1.0
 */
public class MasterSlaveMode extends Mode implements ScanProgressListener {
	
	private static final String FINISH_SUCCESS = "Slave located!";
	private static final String FINISH_FAILURE = "Could not find slave!";
	private static final String OBTAINING_IP = "Obtaining IP range...";
	private static final String SCANNING_RANGE = "Scanning %s IP range...";
	
	private NetworkMaster master;
	private boolean[][] grid;
	private int row, column;	// Position of progress dot
	private int rows, columns;	// Dimensions of grid to move dot through

	public MasterSlaveMode(ModeController controller) {
		super(controller);
		rows = getGui().getGridHeight();
		columns = getGui().getGridWidth();
		grid = new boolean[rows][columns];
	}
	
	/** {@inheritDoc} */
	@Override
	public void setInitialGrid() {
		getGui().setGrid(grid);
		getGui().setText(null);
		master = getModeController().startNetworkMaster();
		master.setIpScanListener(this);
	}
	
	/** {@inheritDoc} */
	@Override
	public void onLocalIpCheck() {
		getGui().setText(OBTAINING_IP);
	}

	/** {@inheritDoc} */
	@Override
	public void onRangeChanged(String ipRange) {
		getGui().setText(String.format(SCANNING_RANGE, ipRange));
	}

	/** {@inheritDoc} */
	@Override
	public void onIpScan(int lastOctet) {
		grid[row][column] = false;		// Clear previous dot
		column = lastOctet % columns;	// Position new dot
		row    = lastOctet / rows;
		while (column >= columns) column -= columns;
		while (row >= rows) row -= rows;
		if (row % 2 != 0) column = columns - 1 - column; // Reverse direction
		grid[row][column] = true;
		getGui().setGrid(grid);			// Draw dot to indicate scan progress
	}

	/** {@inheritDoc} */
	@Override
	public void onCompletion(boolean success) {
		getGui().setText(success ? FINISH_SUCCESS : FINISH_FAILURE);
	}
	
	/** {@inheritDoc} */
	@Override
	public void onFunctionButtonPress(FunctionButtonEvent e) {
		switch (e.getFunctionButton()) {
		case ON : // ON and OK exit the mode
		case OK :
			master.setIpScanListener(null);
			super.onFunctionButtonPress(e);
		default: // Ignore all other function buttons
			break;
		}
	}

	/** {@inheritDoc} */
	@Override
	public void onGridButtonPress(GridButtonEvent e)
			throws InvalidCoordinatesException {} // No grid input in this mode
}
