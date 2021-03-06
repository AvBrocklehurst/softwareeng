package simori;

/**
 * Describes the ON, OK, L and R 'function'
 * buttons on the edges of the Simori-ON device.
 * @author Matt
 * @version 2.2.1
 */
public enum FunctionButton {
	L1 ("L1", "Change Voice"),
	L2 ("L2", "Change Velocity"),
	L3 ("L3", "Change Loop Speed"),
	L4 ("L4", "Change Loop Point"),
	R1 ("R1", "Change Layer"),
	R2 ("R2", "Save Configuration"),
	R3 ("R3", "Load Configuration"),
	R4 ("R4", "Master / Slave"),
	ON ("ON", "Toggle Power"),
	OK ("OK", "Confirm Changes");
	
	private String buttonName;
	private String toolTip;

	private FunctionButton(String name, String toolTip) {
		this.buttonName = name;
		this.toolTip = toolTip;
	}
	
	/** @return text to display on the button */
	public String buttonName() {
		return buttonName;
	}
	
	/** @return text to display in the button's tooltip */
	public String toolTip() {
		return toolTip;
	}
}