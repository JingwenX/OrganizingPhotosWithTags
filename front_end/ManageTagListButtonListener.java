package front_end;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The listener for the button to open a window that contains all posible
 * operation options for TagManager.
 * <p>
 * 
 * @author Jin Fang, Jingwen Xu
 * @version 1.0
 * @since 2016-11-14
 */
public class ManageTagListButtonListener implements ActionListener {
	/** the Panel to update after operations on the tagList */
	private JPanel addTagPanel;
	/** the Panel to update after operations on the tagList */
	private JPanel deleteTagPanel;
	/** the Panel to update after operations on the tagList */
	private JPanel oldNamePanel;
	/** the Label to change after operations */
	private JLabel directoryLabel;

	/** Creates a window to operate on the TagManger */
	public ManageTagListButtonListener(JPanel addTagPanel,
			JPanel deleteTagPanel, JPanel oldNamePanel, JLabel directoryLabel) {
		this.addTagPanel = addTagPanel;
		this.deleteTagPanel = deleteTagPanel;
		this.oldNamePanel = oldNamePanel;
		this.directoryLabel = directoryLabel;
	}

	/**
	 * Handles the user clicking on ManageTagListButton.
	 * 
	 * @param e
	 *            the event object
	 * @see back_end.TagManager
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		/* passes components from the main frame to TagManager frame */
		ManageTagList.buildTagManagerWindow(this.addTagPanel,
				this.deleteTagPanel, this.oldNamePanel, this.directoryLabel)
				.setVisible(true);
	}

}
