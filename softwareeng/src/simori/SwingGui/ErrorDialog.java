package simori.SwingGui;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import simori.ResourceManager;

public class ErrorDialog extends JDialog {
	
	public ErrorDialog(SimoriJFrame frame) {
		setTitle("Error");
		setIconImage(GuiProperties.getIcon());
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		setUndecorated(false);
		float width, height;
		width = frame.getWidth() * GuiProperties.ERROR_WIDTH_PROPORTION;
		height = frame.getHeight() * GuiProperties.ERROR_HEIGHT_PROPORTION;
		setSize((int) width, (int) height);
		addStuff();
		setLocationRelativeTo(frame);
	}
	
	private void addStuff() {
		setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		add(makeTopBit());
		JTextArea text = new JTextArea("loadsa text");
		text.setEditable(false);
		add(text);
		JPanel bottomBit = new JPanel();
		bottomBit.setLayout(new BoxLayout(bottomBit, BoxLayout.LINE_AXIS));
		bottomBit.add(new JButton("One"));
		bottomBit.add(new JButton("Two"));
		add(bottomBit);
	}
	
	private JPanel makeTopBit() {
		JPanel topBit = new JPanel();
		topBit.setLayout(new BoxLayout(topBit, BoxLayout.LINE_AXIS));
		File icon = ResourceManager.getResource("Chunbori-ON.png");
		if (icon != null && icon.exists()) {
			topBit.add(new ImageComponent(icon, 100, 100));
		}
		add(new JLabel("Some text here!"));
		return topBit;
	}
	
	private class ImageComponent extends JComponent {
		
		Image image;
		
		public ImageComponent(File icon, int width, int height) {
			setSize(width, height);
			try {
				image = ImageIO.read(icon).getScaledInstance(width, height, Image.SCALE_SMOOTH);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Override
		public void paintComponent(Graphics g) {
			g.drawImage(image, 0, 0, this);
		}
	}
}