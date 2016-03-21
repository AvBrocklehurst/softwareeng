package simori.SwingGui;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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

import simori.SimoriGui.OnErrorDismissListener;

public class ErrorDialog extends JDialog {
	
	private static final float TOP_PROPORTION = 0.25f;
	private static final float BOTTOM_PROPORTION = 0.1f;
	private static final float PADDING_PROPORTION = 0.1f;
	
	private JLabel label;
	private JTextArea textArea;
	private OnErrorDismissListener listener;
	
	public ErrorDialog(SimoriJFrame frame) {
		setModalityType(ModalityType.APPLICATION_MODAL);
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
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				if (listener != null) listener.onErrorDismiss();
			}
		});
	}
	
	public void setOnDismissListener(OnErrorDismissListener l) {
		this.listener = l;
	}
	
	public void setShortMessage(String msg) {
		label.setText(msg);
	}
	
	public void setLongMessage(String msg) {
		textArea.setText(msg);
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
		label = new JLabel();
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
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		return new JScrollPane(textArea);
	}
}