package simori;

public enum FunctionButton {
	L1 ("L1", "Change Voice"),
	L2 ("L2", "Change Velocity"),
	L3 ("L3", "Change Loop Speed"),
	L4 ("L4", "Change Loop Point"),
	R1 ("R1", "Change Layer"),
	R2 ("R2", "Save Configuration"),
	R3 ("R3", "Load Configuration"),
	R4 ("R4", "Master / Slave"),
	POWER ("ON", "Toggle Power"),
	OK ("OK", "Confirm Changes");
	
	private String buttonName;
	
	private FunctionButton(String name, String toolTip) {
		this.buttonName = name;
	}
	
	public String buttonName() {
		return buttonName;
	}
}