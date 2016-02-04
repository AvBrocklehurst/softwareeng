package simori;
import java.util.EventObject;

import simori.Exceptions.InvalidCoordinatesException;

public class SimoriGuiEvents {
	
	public class GridButtonEvent extends EventObject {
		
		private int x, y;
		private SimoriGui src;
		
		public GridButtonEvent(SimoriGui src, int x, int y) {
			super(src);
			this.src = src;
			this.x = x;
			this.y = y;
		}
		
		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
		
		public SimoriGui getSource() {
			return src;
		}
	}
	
	public class FunctionButtonEvent extends EventObject {
		
		private FunctionButton btn;
		private SimoriGui src;
		
		public FunctionButtonEvent(SimoriGui src, FunctionButton btn) {
			super(src);
			this.src = src;
			this.btn = btn;
		}
		
		public FunctionButton getFunctionButton() {
			return btn;
		}
		
		public SimoriGui getSource() {
			return src;
		}
	}
	
	public enum FunctionButton {
		L1, L2, L3, L4, R1, R2, R3, R4, POWER, OK
	}
	
	public interface GridButtonListener  {
		
		public void onGridButtonPress(GridButtonEvent e) throws InvalidCoordinatesException;
	}
	
	public interface FunctionButtonListener {
		
		public void onFunctionButtonPress(FunctionButtonEvent e);
	}
}