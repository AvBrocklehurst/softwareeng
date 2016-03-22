package simori;

public interface Animation { //TODO merge into SimoriGui
	
	public Frame next();
	
	public interface OnFinishListener {
		
		public void onAnimationFinished();
	}
	
	public class Frame {
		public boolean[][] ledsGreyed;
		public boolean[][] ledsIlluminated;
		public FunctionButton[] btnsGreyed;
	}
}