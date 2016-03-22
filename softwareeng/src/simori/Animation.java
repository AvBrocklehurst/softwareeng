package simori;

import java.util.Map;

public interface Animation {
	
	public int getFrameCount();
	
	public Frame getNextFrame();
	
	public interface OnFinishListener {
		
		public void onAnimationFinished();
	}
	
	public class Frame {
		public boolean[][] ledsGreyed;
		public boolean[][] ledsIlluminated;
		public Map<FunctionButton, Boolean> btnsGreyed;
	}
}