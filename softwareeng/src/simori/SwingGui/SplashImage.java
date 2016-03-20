package simori.SwingGui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import simori.ResourceManager;

public class SplashImage extends JComponent {
	
	private Image image;
	
	public SplashImage() {
		setOpaque(false);
		loadImage();
		sizeImage();
		setSize(image.getWidth(this), image.getHeight(this));
	}
	
	@Override
	public Dimension getPreferredSize() {
		return getSize();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, this);
	}
	
	private void loadImage() {
		File file = ResourceManager.getResource(GuiProperties.SPLASH_IMAGE);
		if (file == null) {
			System.err.println("Cannot find res folder!");
			image = makeBackupImage();
			return;
		}
		if (!file.exists()) {
			System.err.println("Cannot find splash screen image!");
			image = makeBackupImage();
			return;
		}
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			System.err.println("Unknown error with splash screen.");
			image = makeBackupImage();
			return;
		}
	}
	
	private void sizeImage() {
		Dimension pic =
				new Dimension(image.getWidth(this), image.getHeight(this));
		Dimension screen = getToolkit().getScreenSize();
		Float ratio = null;
		float scrnShortSide = Math.min(screen.width, screen.height);
		float imgLongestSide = Math.max(pic.width, pic.height);
		float minLen = scrnShortSide * GuiProperties.SPLASH_MIN_PROPORTION;
		float maxLen = scrnShortSide * GuiProperties.SPLASH_MAX_PROPORTION;
		if (imgLongestSide < minLen) ratio = minLen / imgLongestSide;
		if (imgLongestSide > maxLen) ratio = maxLen / imgLongestSide;
		if (ratio < GuiProperties.SPLASH_MIN_RESIZE) ratio = null;
		if (ratio > GuiProperties.SPLASH_MAX_RESIZE) ratio = null;
		if (ratio != null) {
			Dimension newPic = GuiProperties.ratioOf(ratio, ratio, pic);
			image = image.getScaledInstance(newPic.width,
										newPic.height, Image.SCALE_FAST);
		}
	}
	
	private Image makeBackupImage() {
		System.out.println("Defaulting to backup");
		return null; //TODO create a BufferedImage or whatnot
	}
}
