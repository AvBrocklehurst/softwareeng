package simori.Modes;

import simori.ModeController;
import simori.SimoriGui.FunctionButtonEvent;
import simori.SimoriGui.GridButtonEvent;
import simori.Exceptions.InvalidCoordinatesException;
import simori.Modes.NetworkMaster.ScanProgressListener;

public class MasterSlaveMode extends Mode implements ScanProgressListener {
	
	private static final String FINISH_SUCCESS = "Slave located!";
	private static final String FINISH_FAILURE = "Could not find slave!";
	private static final String OBTAINING_IP = "Obtaining IP range...";
	private static final String SCANNING_RANGE = "Scanning %s IP range...";
	
	private NetworkMaster master;
	private boolean[][] grid;
	private int row, column;
	private int rows, columns;

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
		if (lastOctet > 0) lastOctet--;
		grid[row][column] = false;
		column = lastOctet % columns;
		row    = lastOctet / rows;
		while (column >= columns) column -= columns;
		while (row >= rows) row -= rows;
		grid[row][column] = true;
		getGui().setGrid(grid);
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
		case ON :
		case OK :
			master.setIpScanListener(null);
			super.onFunctionButtonPress(e);
		default:
			break;
		}
	}

	/** {@inheritDoc} */
	@Override
	public void onGridButtonPress(GridButtonEvent e)
			throws InvalidCoordinatesException {}
}
