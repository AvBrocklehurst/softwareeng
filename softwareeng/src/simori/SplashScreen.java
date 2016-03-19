package simori;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;

public class SplashScreen extends JFrame {
	
	private static final String SPLASH_IMAGE = "Splash Image.png";
	
	private Image img;
	private long activated;
	
	public SplashScreen() {
		activated = System.currentTimeMillis();
		img = readImage();
		if (img == null) return;
		setSize(img.getWidth(this), img.getHeight(this));
		setLocationRelativeTo(null);
		setUndecorated(true);
		setVisible(true);
	}
	
	public void swapFor(final SimoriGui gui, int after) {
		long now = System.currentTimeMillis();
		if (now - activated >= after) {
			setVisible(false);
			gui.setVisible(true);
		} else {
			Timer timer = new Timer(after, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					SplashScreen.this.setVisible(false);
					gui.setVisible(true);
				}
			});
			timer.setRepeats(false);
			timer.start();
		}
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, this);
	}
	
	private Image readImage() {
		File file = ResourceManager.getResource(SPLASH_IMAGE);
		if (file == null) {
			System.err.println("Cannot find res folder!");
			return null;
		}
		if (!file.exists()) {
			System.err.println("Cannot find splash screen image!");
			return null;
		}
		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			System.err.println("Unknown error with splash screen.");
			return null;
		}
	}
}
