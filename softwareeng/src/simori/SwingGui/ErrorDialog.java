package simori.SwingGui;

import java.awt.Dimension;

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

public class ErrorDialog extends JDialog {
	
	private static final float TOP_PROPORTION = 0.25f;
	private static final float BOTTOM_PROPORTION = 0.1f;
	private static final float PADDING_PROPORTION = 0.1f;
	
	private static final String IPSUM = "Its good. Doc, you don't just walk into a store and ask for plutonium. Did you rip this off? Are those my clocks I hear? Why thank you, Marty. George. Good morning, sleepyhead, Good morning, Dave, Lynda No, I refuse to except the responsibility. Right check, Doc. C'mon, more, dammit. Jeez. Holy shit. Let's see if you bastards can do ninety. Let him go, Biff, you're drunk. My equipment, that reminds me, Marty, you better not hook up to the amplifier. There's a slight possibility for overload. Thanks a lot, kid. Well, this is a radiation suit. What? Leave me alone. I had a horrible nightmare, dreamed I went back in time, it was terrible. Let's get you into a radiation suit, we must prepare to reload. No wait, Doc, the bruise, the bruise on your head, I know how that happened, you told me the whole story. you were standing on your toilet and you were hanging a clock, and you fell, and you hit your head on the sink, and that's when you came up with the idea for the flux capacitor, which makes time travel possible. Roads? Where we're going we don't need roads. Ah. Nothing. That Biff, what a character. Always trying to get away with something. Been on top of Biff ever since high school. Although, if it wasn't for him- Good, I'll see you tonight. Don't forget, now, 1:15 a.m., Twin Pines Mall. Lorraine, are you up there? I'm really gonna miss you. Doc, about the future- I said the keys are in here. Good, there's somebody I'd like you to meet. Lorraine. Well, I guess that's everything. I just wanna use the phone. whoa, this is it, this is the part coming up, Doc. Excuse me. George, help me, please. Yeah, exactly. Marty, is that you? You too. Hey man, the dance is over. Unless you know someone else who could play the guitar. Oh. What about George? Doc. Marty you gotta come back with me. Never mind that now, never mind that now. Whoa, whoa, Biff, what's that? Marty, why are you so nervous? This Saturday night, mostly clear, with some scattered clouds. Lows in the upper forties. Uh listen, do you know where Riverside Drive is? Yeah, well history is gonna change. Yeah, I'm- mayor. Now that's a good idea. I could run for mayor. I'll call you tonight. This is more serious than I thought. Apparently your mother is amorously infatuated with you instead of your father. Listen, Doc, you know there's something I haven't told you about the night we made that tape. oh yeah, all you gotta do is go over there and ask her. Uncle Jailbird Joey?";	
	
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
		return new ImageComponent(
				GuiProperties.ERROR_ICON,
				GuiProperties.ERROR_BACKUP_TEXT,
				height, height);
	}
	
	private JComponent makeTextAreaBit(int height, int padding) {
		JTextArea text = new JTextArea(IPSUM);
		text.setEditable(false);
		text.setLineWrap(true);
		return new JScrollPane(text);
	}
}