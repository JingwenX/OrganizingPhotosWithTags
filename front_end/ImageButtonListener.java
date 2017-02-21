package front_end;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;

import photo_renamer.PhotoRenamer;
import back_end.Tag;

/**
 * ImageButtonListener is mainly used for displaying information of the image
 * onto different panels when an image is selected by clicking its ImageButton.
 * <p>
 * 
 * @author Jin Fang, Jingwen Xu
 * @version 1.0
 * @since 2016-11-14
 */
public class ImageButtonListener implements ActionListener {
	/**
	 * The panel the button will be loaded by this button. This Panel contains
	 * the all the tags the selected image has
	 */
	private JPanel deleteTagPanel;
	/**
	 * The panel the button will be loaded by this button. This Panel contains
	 * the old names of the selected image
	 */
	private JPanel oldNamePanel;
	/** The label to display the full path to the chosen directory. */
	private JLabel directoryLabel;
	/** the ImgaeButton whose action will trigger this button listener. */
	private ImageButton bt;

	/**
	 * An action listener for ImageButton bt, display the information of
	 * selected image button bt onto the deleteTagPanel, oldNamePanel and change
	 * directoryLabel accordingly.
	 *
	 * @param deleteTagPanel
	 *            This Panel contains the all the tags the selected image has.
	 * @param oldNamePanel
	 *            The label to display the full path to the chosen directory.
	 * @param directoryLabel
	 *            the ImgaeButton whose action will trigger this button
	 *            listener.
	 * @param bt
	 *            the ImgaeButton being selected by the user.
	 */
	public ImageButtonListener(JPanel deleteTagPanel, JPanel oldNamePanel,
			JLabel directoryLabel, ImageButton bt) {
		this.deleteTagPanel = deleteTagPanel;
		this.oldNamePanel = oldNamePanel;
		this.directoryLabel = directoryLabel;
		this.bt = bt;
	}

	/**
	 * Handle the user clicking on the open button.
	 * 
	 * When a imageButton is clicked, the image that is represented by the image
	 * is selected to be current image. Then tags of the image is added to the
	 * deleteTagPanel, the old names of the image is added to the oldNamePanel.
	 * And the directoryLabel is changed to the information of the selected
	 * image.
	 *
	 * @param e
	 *            the event object
	 */
	public void actionPerformed(ActionEvent e) {

		/* sets the current working FileNode */
		PhotoRenamer.currentFileNode = bt.getFileNode();

		/*
		 * clears the deleteTagPanel and adds the TagCheckBox to deletTagePanel
		 */
		for (TagCheckBox b : PhotoRenamer.deleteTagPanelList) {
			deleteTagPanel.remove(b);
		}
		PhotoRenamer.deleteTagPanelList.clear();
		for (Tag t : bt.getFileNode().getTags()) {
			TagCheckBox tagCB = new TagCheckBox(t.getTagName(), t);
			this.deleteTagPanel.add(tagCB);
			PhotoRenamer.deleteTagPanelList.add(tagCB);
		}
		/* refreshes the deleteTagPanel */
		this.deleteTagPanel.revalidate();
		this.deleteTagPanel.repaint();

		/*
		 * clears the oldNamePanel and then adds the old names to the
		 * oldNamePanel
		 */
		for (OldNameRadioButton b : PhotoRenamer.oldNamePanelList) {
			oldNamePanel.remove(b);
		}
		PhotoRenamer.oldNamePanelList.clear();
		/* groups radio buttons so that only one of them can be selected */
		ButtonGroup buttonGroup = new ButtonGroup();
		for (String oldName : bt.getFileNode().getOldNames().keySet()) {
			OldNameRadioButton oldNameB = new OldNameRadioButton(oldName,
					oldName);
			buttonGroup.add(oldNameB);
			this.oldNamePanel.add(oldNameB);
			PhotoRenamer.oldNamePanelList.add(oldNameB);
		}
		/* refreshes the oldNamePanel */
		this.oldNamePanel.revalidate();
		this.oldNamePanel.repaint();
		/* sets text in directoryLabel */
		directoryLabel.setText("Operating on: " + bt.getFileNode().getName()
				+ " (" + bt.getFileNode().getPath() + ")");
	}

}
