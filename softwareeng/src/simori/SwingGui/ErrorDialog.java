package simori.SwingGui;

import java.awt.Dimension;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

/**
 * A JDialog which displays an error with an icon, a customisable title,
 * short summary message and long scrolling message. The options to exit
 * the system, copy the long message or dismiss the error are presented.
 * @author Matt
 * @version 1.3.0
 */
public class ErrorDialog extends JDialog {
	
	private boolean fatal;
	protected JLabel label; // Formattable area for short summary
	protected JTextArea textArea; // Scrollable area for long message
	private OnErrorDismissListener listener; // To notify on close
	
	/**
	 * Creates but does not display a dialog.
	 * Information can be entered by calling setters,
	 * and displayed with {@link #setVisible}.
	 * The options of exiting the system, copying the long message
	 * or dismissing the error are presented, unless the error is
	 * fatal, in which case it cannot be dismissed without exiting.
	 * @param frame The parent SimoriGui which created this dialog
	 * @param true if the error was fatal
	 */
	public ErrorDialog(SimoriJFrame frame, boolean fatal) {
		this.fatal = fatal;
		setUpWindow();
		sortSize(frame);
		addStuff();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				// Dialog dismissed, so notify listener
				if (listener != null) listener.onErrorDismiss();
			}
		});
	}
	
	/** @param to register for callback when dialog is dismissed */
	public void setOnDismissListener(OnErrorDismissListener l) {
		this.listener = l;
	}
	
	/** @param Optionally HTML formatted short summary message */
	public void setShortMessage(String msg) {
		label.setText(msg);
	}
	
	/** @param Message to display in scrollable text area */
	public void setLongMessage(String msg) {
		textArea.setText(msg);
	}
	
	/** Customises the properties of the dialog window */
	private void setUpWindow() {
		setTitle(GuiProperties.ERROR_DEFAULT_TITLE);
		setIconImage(GuiProperties.getIcon());
		setUndecorated(false); // Has OS skin
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL); // Covers GUI window
		setDefaultCloseOperation(fatal ?
				WindowConstants.DO_NOTHING_ON_CLOSE :  // Cannot be closed
				WindowConstants.DISPOSE_ON_CLOSE);    // "x" button closes it
	}
	
	/**
	 * Sets the size of the content pane to the correct proportion of the
	 * given SimoriJFrame's size, and aligns the windows' centres.
	 * @see GuiProperties#ERROR_WIDTH_PROPORTION
	 * @see GuiProperties#ERROR_HEIGHT_PROPORTION
	 * @param frame The parent SimoriGui which created this dialog
	 */
	private void sortSize(SimoriJFrame frame) {
		float width, height;
		width = frame.getWidth() * GuiProperties.ERROR_WIDTH_PROPORTION;
		height = frame.getHeight() * GuiProperties.ERROR_HEIGHT_PROPORTION;
		Dimension size = new Dimension((int) width, (int) height);
		getContentPane().setPreferredSize(size);
		pack(); // Expand frame to fit content pane
		setLocationRelativeTo(frame); // Size is known, so align centres
	}
	
	/**
	 * Calculates the proportion of frame height to allocate to the top
	 * (icon and summary), middle (scrollable long message) and bottom
	 * (buttons) sections as well as the vertical padding, then calls
	 * {@link #addSections} to add the components. Note: The calculated
	 * vertical padding is also used as the horizontal padding.
	 * @see GuiProperties#ERROR_PADDING_PROPORTION
	 * @see GuiProperties#ERROR_TOP_PROPORTION
	 * @see GuiProperties#ERROR_BOTTOM_PROPORTION
	 */
	private void addStuff() {
		float height = getContentPane().getHeight(); // Not including OS' bar
		float onePadProp = GuiProperties.ERROR_PADDING_PROPORTION / 4f;
		int pad = (int) (height * onePadProp); // Padding between components
		int topHght = (int) (height * GuiProperties.ERROR_TOP_PROPORTION);
		int btmHght = (int) (height * GuiProperties.ERROR_BOTTOM_PROPORTION);
		int midHght = (int) height - topHght - btmHght - 3 * pad; // The rest
		addSections(pad, topHght, btmHght, midHght);
	}
	
	/**
	 * Adds the top, middle and bottom sections of the dialog to the frame.
	 * Applies padding between each of the sections, and between the top and
	 * bottom sections and the top and bottom of the content pane.
	 * @param padding Distance apart to place components
	 * @param topHeight Height of top section (summary message)
	 * @param bottomHeight Height of bottom section (buttons)
	 * @param midHeight Height of middle section (scrollable text)
	 */
	private void addSections(int padding, int topHeight,
								int bottomHeight, int midHeight) {
		setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		add(Box.createVerticalStrut(padding)); // Between top section and edge
		add(makeTopSection(topHeight, padding));
		add(Box.createVerticalStrut(padding)); // Between top & middle sections
		add(makeMiddleSection(midHeight, padding));
		add(Box.createVerticalStrut(padding / 2)); // Between buttons & middle
		add(makeBottomSection(bottomHeight, padding));
		add(Box.createVerticalStrut(padding / 2)); // Between buttons & bottom
	}
	
	/**
	 * Creates a panel for the top section of the error dialog.
	 * This contains an icon and the summary text.
	 * @param height Exact height to make the section
	 * @param padding Horizontal space between components and edges
	 * @return The top section, ready to be added
	 */
	private JPanel makeTopSection(int height, int padding) {
		JPanel topBit = new JPanel();
		topBit.setLayout(new BoxLayout(topBit, BoxLayout.LINE_AXIS));
		topBit.add(Box.createRigidArea(new Dimension(padding, height)));
		topBit.add(makeImage(height, padding));
		topBit.add(Box.createRigidArea(new Dimension(padding * 2, height)));
		topBit.add(makeLabel(height, padding));
		topBit.add(Box.createRigidArea(new Dimension(padding, height)));
		return topBit;
	}
	
	/**
	 * Creates a panel for the middle section of the error dialog.
	 * This contains the scrollable text area.
	 * @param height Exact height to make the section
	 * @param padding Horizontal space between component and edges
	 * @return The middle section, ready to be added
	 */
	private JComponent makeMiddleSection(int height, int padding) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		panel.add(Box.createRigidArea(new Dimension(padding, height)));
		panel.add(makeTextArea(height, padding));
		panel.add(Box.createRigidArea(new Dimension(padding, height)));
		return panel;
	}
	
	/**
	 * Creates a panel for the buttons section of the error dialog.
	 * @param height Exact height to make the section
	 * @param padding Horizontal space between components and edges
	 * @return The bottom section, ready to be added
	 */
	private JComponent makeBottomSection(int height, int padding) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		panel.add(Box.createRigidArea(new Dimension(padding, height)));
		panel.add(Box.createHorizontalGlue());
		panel.add(makeExitButton());
		panel.add(Box.createHorizontalGlue());
		panel.add(makeCopyButton());
		panel.add(Box.createHorizontalGlue());
		panel.add(makeOkButton());
		panel.add(Box.createHorizontalGlue());
		panel.add(Box.createRigidArea(new Dimension(padding, height)));
		return panel;
	}
	
	/**
	 * Creates {@link #label}, taking up the remaining horizontal space.
	 * @param height Height to make label
	 * @param padding Horizontal space between components on this row
	 */
	private JComponent makeLabel(int height, int padding) {
		label = new JLabel();
		int width = getContentPane().getWidth() - height - 4 * padding;
		label.setPreferredSize(new Dimension(width, height));
		return label;
	}
	
	/**
	 * Creates a component which displays the error dialog icon,
	 * or a backup image if that cannot be loaded.
	 * @see GuiProperties#ERROR_ICON
	 * @see GuiProperties#ERROR_BACKUP_TEXT
	 * @param height Width and height to make icon
	 * @param padding Space to leave on left and right
	 */
	private ImageComponent makeImage(int height, int padding) {
		return new ImageComponent(
				GuiProperties.ERROR_ICON,
				GuiProperties.ERROR_BACKUP_TEXT,
				height, height);
	}
	
	/**
	 * Creates {@link #textArea}.
	 * @param height Height to fill
	 * @param padding Space to leave between edges
	 * @return The text area, wrapped in a scroll pane
	 */
	private JScrollPane makeTextArea(int height, int padding) {
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		return new JScrollPane(textArea);
	}
	
	/** @return button which calls System.exit(1) when pressed */
	private JButton makeExitButton() {
		JButton button = new JButton(GuiProperties.ERROR_EXIT_BUTTON);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		return button;
	}
	
	/** @return button which copies long message to clipboard when pressed */
	private JButton makeCopyButton() {
		JButton button = new JButton(GuiProperties.ERROR_COPY_BUTTON);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StringSelection str = new StringSelection(textArea.getText());
				getToolkit().getSystemClipboard().setContents(str, null);
			}
		});
		return button;
	}
	
	/** @return button which dismisses dialog when pressed */
	private JButton makeOkButton() {
		JButton button = new JButton(GuiProperties.ERROR_DISMISS_BUTTON);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ErrorDialog.this.setVisible(false);
				ErrorDialog.this.dispose();
			}
		});
		if (fatal) button.setEnabled(false);
		return button;
	}
}