package simori;

import java.util.Timer;
import java.util.TimerTask;

public class Clock implements Runnable {
	private long startTime;
	private long maxTime;
	private Timer timer;
	private volatile boolean running;
	private MatrixModel model;
	private Object bpmLock;
	private Object lock;
	
	Clock(long maxTime, boolean running, MatrixModel model, Object bpmLock, Object lock) {
		this.maxTime = maxTime;
		this.running = running;
		this.model = model;
		this.bpmLock = bpmLock;
		this.lock = lock;
	}
	
	@Override
	public void run() {//while running...
		while(running){
			//...update the tempo...
			short bpm = model.getBPM();
			changeTempo(bpm, maxTime);
			
			synchronized(bpmLock){
				//...everytime the BPM is changed
				while(bpm==model.getBPM() && running) {
					try{bpmLock.wait();}catch(InterruptedException e){}
				}
			}
			timer.cancel();
		}
	}

	/**
	 * @author Jurek
	 * @version 1.0.0
	 */
	private void changeTempo(short bpm, long maxTime) {
		long timePassed = System.currentTimeMillis() - startTime;
		long period = (long)((1f/(bpm/60f))*1000f)-maxTime;
		long timeLeft;
		if(period <= timePassed) timeLeft = 0;
		else timeLeft = period - timePassed;
		startTimer(timeLeft, period);
	}

	/**
	 * Starts the timer thread that keeps track of the tempo
	 * @version 1.0.1
	 * @author Jurek
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
}
