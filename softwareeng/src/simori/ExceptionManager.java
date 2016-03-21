package simori;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;

import simori.SimoriGui.OnErrorDismissListener;

/**
 * TODO Description
 * @author Matt
 * @author Adam
 * @version 1.0.1
 */
public class ExceptionManager implements UncaughtExceptionHandler,
											OnErrorDismissListener {
	
	private SimoriGui gui;            // To generate error dialogs
	private AudioFeedbackSystem afs; //  To play a noise
	private List<Throwable> queue ; //   Holds errors to report later
	private boolean dialogOpen;    //    Flag to prevent simultaneous dialogs
	
	/** Sets constructed object as default uncaught exception handler */
	public ExceptionManager() {
		Thread.setDefaultUncaughtExceptionHandler(this);
		queue = new ArrayList<Throwable>();
		dialogOpen = false;
	}

	/** {@inheritDoc} */
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		try {
			tryReport(e);
		} catch (Throwable thrown) {
			/*
			 * When catching an uncaught exception results in an exception,
			 * you'd better make sure that one gets caught...
			 * or else you get stuck in an infinite loop!
			 */
			thrown.printStackTrace(); // Maybe email the developers?
		}
	}
	
	/**
	 * To be called when the Simori-ON is fully constructed,
	 * and a GUI and Audio Feedback System now exist
	 * to be used in reporting errors.
	 * Reports any errors which have been displayed so far.
	 * @param gui The GUI, ready for {@link SimoriGui#reportError}
	 * @param afs Sound effect system, for {@link AudioFeedbackSystem#play}
	 */
	public void simoriReady(SimoriGui gui, AudioFeedbackSystem afs) {
		this.gui = gui;
		this.afs = afs;
		reportQueuedErrors();
	}
	
	/** 
	 * <p>{@inheritDoc}</p>
	 * Displays any new errors which occurred whilst the
	 * user was reading the dialog for the previous error.
	 */
	@Override
	public void onErrorDismiss() {
		dialogOpen = false;
		reportQueuedErrors();
	}
	
	/**
	 * Attempts to show a dialog reporting the given error(s).
	 * If the Simori-ON is not yet fully constructed or a dialog
	 * displaying another error is already open, the error(s) are
	 * enqueued to display later.
	 * @param errors Any number of errors to report in a dialog
	 */
	private void tryReport(Throwable... errors) {
		if (gui == null || dialogOpen) {
			for (Throwable t : errors) queue.add(t);
		} else {
			openDialog(errors);
		}
	}
	
	/**
	 * Empties the queue of errors to be reported and attempts to report them.
	 * If they cannot be reported, {@link #tryReport} will re-queue them.
	 */
	private void reportQueuedErrors() {
		if (queue.size() == 0) return; // None queued to report
		Throwable[] t = queue.toArray(new Throwable[queue.size()]);
		queue = new ArrayList<Throwable>(); // Empty queue
		tryReport(t); // Either displays or adds back to queue
	}
	
	/**
	 * Uses {@link #gui} to create a dialog reporting the given error(s).
	 * @param errors The errors to report in the dialog
	 */
	private void openDialog(Throwable[] errors) {
		playExceptionNoise();
		String shortMsg = "";
		String title = "";
		String longMessage = "";
		for(Throwable error : errors){
			title = error.getClass().getSimpleName();
			shortMsg = "<html><b>" + error.getMessage() + "</b></html>";
			StringWriter sw = new StringWriter();
			error.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			longMessage += exceptionAsString;
			longMessage += " \n\n";
		}
		dialogOpen = true;
		gui.reportError(shortMsg, longMessage, title, this);
	}
	
	/** Plays a sound to accompany the error dialog */
	private void playExceptionNoise() {
		//afs.play(AudioFeedbackSystem.Sound.SAD);
		/* 
		 * Currently this excepts in a different thread if Simori is off
		 * (resulting in infinite error dialog loop).
		 * TODO Ask afs whether its receiver is open before attempting to play!
		 */
	}
}
