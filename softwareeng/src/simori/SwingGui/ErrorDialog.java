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
	private static final float BOTTOM_PROPORTION = 0.1f;
	private static final float PADDING_PROPORTION = 0.1f;
	
	private static final String IPSUM = "Watch it, Goldie. Do you mind if we park for a while? Welcome to my latest experiment. It's the one I've been waiting for all my life. Wrecked? Thank god I still got my hair. What on Earth is that thing I'm wearing? whoa, whoa Doc, stuck here, I can't be stuck here, I got a life in 1985. I got a girl. Can't be. This is nuts. Aw, c'mon. you guys look great. Mom, you look so thin. He's absolutely right, Marty. the last thing you need is headaches. A bolt of lightning, unfortunately, you never know when or where it's ever gonna strike. No no no, Doc, I just got here, okay, Jennifer's here, we're gonna take the new truck for a spin. Um, yeah well I might have sort of ran into my parents. Shut your filthy mouth, I'm not that kind of girl. Marty, you're beginning to sound just like my mother. Watch it, Goldie. My name's Lorraine, Lorraine Baines. Ahh. Watch this. Not me, the car, the car. My calculations are correct, when this baby hits eighty-eight miles per hour, your gonna see some serious shit. Watch this, watch this. Ha, what did I tell you, eighty-eight miles per hour. The temporal displacement occurred at exactly 1:20 a.m. and zero seconds. Yes, definitely, god-dammit George, swear. Okay, so now, you come up, you punch me in the stomach, I'm out for the count, right? And you and Lorraine live happily ever after. I will. Keys? This is more serious than I thought. Apparently your mother is amorously infatuated with you instead of your father. C'mon, more, dammit. Jeez. Holy shit. Let's see if you bastards can do ninety. Uh, I think so. No, why, what's a matter? What about George? Ah well, sort of. One point twenty-one gigawatts. One point twenty-one gigawatts. Great Scott. Marty, you made it. I can't play. I think it's terrible. Girls chasing boys. When I was your age I never chased a boy, or called a boy, or sat in a parked car with a boy. That's true, Marty, I think you should spend the night. I think you're our responsibility. Marty, why are you so nervous? Uh, well, I gotta go. And where's my reports? Doc, wait. No, bastards. Marty, you interacted with anybody else today, besides me? How could I have been so careless. One point twenty-one gigawatts. Tom, how am I gonna generate that kind of power, it can't be done, it can't. So tell me, future boy, who's president of the United States in 1985? George. George. Dear Doctor Brown, on the night that I go back in time, you will be shot by terrorists. Please take whatever precautions are necessary to prevent this terrible disaster. Your friend, Marty. You want it, you know you want it, and you know you want me to give it to you. Like I always told you, if you put your mind to it you could accomplish anything. What were you doing in the middle of the street, a kid your age. I hope so. Please note that Einstein's clock is in complete synchronization with my control watch. I hope you don't mind but George asked if he could take me home. Then how am I supposed to ever meet anybody. Nothing. Oh, hi , Marty. I didn't hear you come in. Fascinating device, this video unit.";
	
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
		float height = getContentPane().getHeight();
		float onePaddingProportion = PADDING_PROPORTION / 4f;
		int padding = (int) (height * onePaddingProportion);
		int topHeight = (int) (height * TOP_PROPORTION);
		int bottomHeight = (int) (height * BOTTOM_PROPORTION);
		int middleHeight = (int) height - topHeight - bottomHeight - 3 * padding;
		setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		add(Box.createVerticalStrut(padding));
		add(makeTopBit(topHeight, padding));
		add(Box.createVerticalStrut(padding));
		add(makeMiddleBit(middleHeight, padding));
		add(Box.createVerticalStrut(padding / 2));
		add(makeBottomBit(bottomHeight, padding));
		add(Box.createVerticalStrut(padding / 2));
	}
	
	private JComponent makeTopBit(int height, int padding) {
		JPanel topBit = new JPanel();
		topBit.setLayout(new BoxLayout(topBit, BoxLayout.LINE_AXIS));
		topBit.add(Box.createRigidArea(new Dimension(padding, height)));
		topBit.add(makeImageBit(height, padding));
		topBit.add(Box.createRigidArea(new Dimension(padding * 2, height)));
		topBit.add(makeLabelBit(height, padding));
		topBit.add(Box.createRigidArea(new Dimension(padding, height)));
		return topBit;
	}
	
	private JComponent makeMiddleBit(int height, int padding) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		panel.add(Box.createRigidArea(new Dimension(padding, height)));
		panel.add(makeTextAreaBit(height, padding));
		panel.add(Box.createRigidArea(new Dimension(padding, height)));
		return panel;
	}
	
	private JComponent makeBottomBit(int height, int padding) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		panel.add(Box.createRigidArea(new Dimension(padding, height)));
		panel.add(Box.createHorizontalGlue());
		panel.add(new JButton("Copy"));
		panel.add(Box.createHorizontalGlue());
		panel.add(new JButton("Okay"));
		panel.add(Box.createHorizontalGlue());
		panel.add(Box.createRigidArea(new Dimension(padding, height)));
		return panel;
	}
	
	private JComponent makeLabelBit(int height, int padding) {
		JLabel label = new JLabel("<html><p><b>Some text here!</b></p><p>In fact this could get rather long so yeah.</p></html>");
		label.setPreferredSize(new Dimension(getContentPane().getWidth() - height - 4 * padding, height));
		return label;
	}
	
	private JComponent makeImageBit(int height, int padding) {
		return new ImageComponent("Chunbori-ON.png", height, height); //or a replacement
	}
	
	private JComponent makeTextAreaBit(int height, int padding) {
		JTextArea text = new JTextArea(IPSUM);
		text.setEditable(false);
		text.setLineWrap(true);
		return new JScrollPane(text);
	}
	
	private class ImageComponent extends JComponent {
		
		Image image;
		
		public ImageComponent(String file, int width, int height) {
			File icon = ResourceManager.getResource(file);
			setSize(width, height);
			try {
				image = ImageIO.read(icon).getScaledInstance(width, height, Image.SCALE_SMOOTH);
			} catch (IOException e) {
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