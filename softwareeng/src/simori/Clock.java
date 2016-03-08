package simori;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Class implementing Runnable which keeps track of the timing. It handles when the note processor
 * should process data, and also any bpm/tempo changes.
 * @author Jurek
 * @author Adam
 * @version 1.1.0
 */
public class Clock implements Runnable {
	private long startTime;
	private long maxTime;
	private Timer timer;
	private volatile boolean running;
	private MatrixModel model;
	private Object bpmLock;
	private Object lock;
	private short bpm;
	
	Clock(long maxTime, boolean running, MatrixModel model, Object bpmLock, Object lock) {
		this.maxTime = maxTime;
		this.running = running;
		this.model = model;
		this.bpmLock = bpmLock;
		this.lock = lock;
		this.bpm = -1;
	}
	
	@Override
	public void run() {//while running...
		while(running){
			//...update the tempo...
			short bpmCheck = bpm; //store the bpm at the start of the loop.
			changeTempo(bpm, maxTime);
			
			synchronized(bpmLock){
				//...everytime the BPM is changed
				while(bpm==bpmCheck && running) {
					try{bpmLock.wait();}catch(InterruptedException e){}
				}
				timer.cancel();
				while(bpm==0){
					try{bpmLock.wait();}catch(InterruptedException e){}
				}
			}
		}
	}

	/**
	 * Changes the current bpm, by restarting the timer with the new tempo.
	 * If the amount of time passed on the current tick is greater or equal to the new
	 * period, then the timer is started immediately, otherwise the difference is calculated
	 * and used as the initial delay of the timer.
	 * @author Jurek
	 * @version 1.0.0
	 * @param bpm The new bpm
	 */
	private void changeTempo(short bpm, long maxTime) {
		long timePassed = System.currentTimeMillis() - startTime;
		long period = (long)((1f/(bpm/60f))*1000f)-maxTime;
		long timeLeft;
		if(period <= timePassed) {
			timeLeft = 0;
		} else {
			timeLeft = period - timePassed;
		}
		startTimer(timeLeft, period);
	}

	/**
	 * Starts the timer thread that keeps track of the tempo
	 * @version 1.0.1
	 * @author Jurek
	 * @param timeLeft Variable that tracks how much of an initial delay there should be before the timer starts
	 * @param period How often the timer should notify the note processor to process data
	 */
	private void startTimer(long timeLeft, long period){
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				startTime = System.currentTimeMillis();
				synchronized(lock){
					lock.notify();
				}	
			}
		}, timeLeft, period);
	}
	
	/**
	 * Sets the local running variable
	 * @author Jurek
	 * @version 1.0.0
	 * @param running
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	/**
	 * Method that gets called by parent observer when the bpm is changed.
	 * It sets the bpm for the clock to follow and forces it out of the loop
	 * so that it can update the bpm immediatly. 
	 * @author Adam
	 * @author Jurek
	 * @param newBPM  the bpm to change the clock too.
	 * @version 1.0.2
	 */
	public void updateBPM(short newBPM){
		//check if bpm within 0-160 range
		if(newBPM<0||newBPM>160) {
			System.out.println("Incorrect BPM:" + newBPM + "; acceptable 0-160");
			System.exit(1);
		}
		if(bpm==-1) new Thread(this).start();
		bpm = newBPM;
		synchronized(bpmLock){
			bpmLock.notify();
		}
	}
}
