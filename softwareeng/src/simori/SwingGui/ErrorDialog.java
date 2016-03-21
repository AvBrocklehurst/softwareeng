package simori.SwingGui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import simori.ResourceManager;

public class ErrorDialog extends JDialog {
	
	private static final float TOP_PROPORTION = 0.25f;
	private static final float BOTTOM_PROPORTION = 0.15f;
	private static final float MIDDLE_PROPORTION = 1 - TOP_PROPORTION - BOTTOM_PROPORTION;
	
	private static final String ipsum = "Crop circles are Chuck Norris' way of telling the world that sometimes corn needs to lie down, When Chuck Norris sends in his taxes, he sends blank forms and includes only a picture of himself, crouched and ready to attack. Chuck Norris has not had to pay taxes, ever. The chief export of Chuck Norris is Pain Clouds are formed by the evaporation of water, what Meteorologists don't realize is it happens at the same time Chuck Norris goes swimming. Chuck Norris does not get frostbite. Chuck Norris bites frost, Chuck Norris has two speeds. Walk, and Kill The only reason people can win something is because Chuck Norris didn't take part. Chuck Norris is the reason why Waldo is hiding Chuck Norris is the reason why Waldo is hiding Chuck Norris can win a game of Connect Four in only three moves Santa delivers to Chuck Norris' house first Chuck Norris is currently suing NBC, claiming Law and Order are trademarked names for his left and right legs There is no theory of evolution. Just a list of animals Chuck Norris allows to live If you ask Chuck Norris what time it is, he always says, Two seconds 'til. After you ask, Two seconds 'til what? he roundhouse kicks you in the face. Most people have 23 pairs of chromosomes. Chuck Norris has 72... and they're all poisonous Chuck Norris can answer a 'missed call' Chuck Norris is not in this world to live up to your expectations. You are only to watch his movies and weep with fear. Chuck Norris can get to the Tootsie Roll center of a Tootsie Pop without touching it. Chuck Norris is the reason why Waldo is hiding. When the Boogeyman goes to sleep every night, he checks his closet for Chuck Norris. Remember the Soviet Union? They decided to quit after watching a DeltaForce marathon on Satellite TV CNN was originally created as the Chuck Norris Network to update Americans with on-the-spot ass kicking in real-time Contrary to popular belief, Chuck Norris, not the box jellyfish of northern Australia, is the most venomous creature on earth. Chuck Norris doesn't churn butter. He roundhouse kicks the cows and the butter comes straight out. Chuck Norris gets everything on the internet for free. His computer is too afraid to ask for his personal information, Chuck Norris drives an ice cream truck covered in human skulls. The Great Wall of China was originally created to keep Chuck Norris out. It failed miserably, There is no theory of evolution. Just a list of animals Chuck Norris allows to live The Great Wall of China was originally created to keep Chuck Norris out. It failed miserably, Remember the Soviet Union? They decided to quit after watching a DeltaForce marathon on Satellite TV Chuck Norris' hand is the only hand that can beat a Royal Flush If you ask Chuck Norris what time it is, he always says, Two seconds 'til. After you ask, Two seconds 'til what? he roundhouse kicks you in the face. Chuck Norris invented Kentucky Fried Chicken's famous secret recipe, with eleven herbs and spices. But nobody ever mentions the twelfth ingredient: Fear. Chuck Norris is currently suing NBC, claiming Law and Order are trademarked names for his left and right legs. There is no theory of evolution. Just a list of animals Chuck Norris allows to live The square root of Pain is Chuck Norris, Chuck Norris doesn't wash his clothes, he disembowels them There is no chin behind Chuck Norris' beard. There is only another fist. Someone once videotaped Chuck Norris getting pissed off. It was called Walker: Texas Chain Saw Massacre. Chuck Norris does not sleep. He waits. Chuck Norris sawThe Ring video, then watched it again the week later. When Chuck Norris does a pushup, he isn't lifting himself up, he's pushing the Earth down Chuck Norris is my Homeboy Chuck Norris uses pepper spray to spice up his steaks, Chuck Norris is my Homeboy, Chuck Norris is ten feet tall, weighs two-tons, breathes fire, and could eat a hammer and take a shotgun blast standing. Chuck Norris does not get frostbite. Chuck Norris bites frost Chuck Norris does not get frostbite. Chuck Norris bites frost." ;
	
	public ErrorDialog(SimoriJFrame frame) {
		setTitle("Error");
		setIconImage(GuiProperties.getIcon());
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		setUndecorated(false);
		float width, height;
		width = frame.getWidth() * GuiProperties.ERROR_WIDTH_PROPORTION;
		height = frame.getHeight() * GuiProperties.ERROR_HEIGHT_PROPORTION;
		getContentPane().setPreferredSize(new Dimension((int) width, (int) height));
		pack();
		addStuff();
		setLocationRelativeTo(frame);
	}
	
	private void addStuff() {
		setLayout(new BorderLayout(0,0)); //TODO add vertical padding
		add(makeTopBit(), BorderLayout.PAGE_START);
		add(makeMiddleBit(), BorderLayout.CENTER);
		add(makeBottomBit(), BorderLayout.PAGE_END);
	}
	
	private JComponent makeTopBit() {
		JPanel topBit = new JPanel();
		topBit.setLayout(new BoxLayout(topBit, BoxLayout.LINE_AXIS));
		int sectionHeight = (int) (getHeight() * TOP_PROPORTION);
		topBit.add(Box.createVerticalStrut(sectionHeight));
		File icon = ResourceManager.getResource("Chunbori-ON.png");
		if (icon != null && icon.exists()) {
			int length = (int) (sectionHeight * 0.9f);
			topBit.add(new ImageComponent(icon, length, length));
		}
		topBit.add(new JLabel("Some text here!"));
		return topBit;
	}
	
	private JComponent makeMiddleBit() {
		JTextArea text = new JTextArea(ipsum);
		text.setEditable(false);
		text.setLineWrap(true);
		return new JScrollPane(text);
	}
	
	private JComponent makeBottomBit() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		panel.add(new JButton("Copy"));
		panel.add(new JButton("Okay"));
		return panel;
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
		
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(image.getWidth(this), image.getHeight(this));
		}
	}
}