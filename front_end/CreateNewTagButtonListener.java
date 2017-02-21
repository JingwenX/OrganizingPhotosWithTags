package front_end;
//integrated to ManageTagList.java

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextField;

import back_end.Tag;
import back_end.TagManager;
import photo_renamer.PhotoRenamer;
/**
 * The listener for the button which creates a new tag and adds to the TagManager.
 * <p>
 * 
 * @author Jin Fang, Jingwen Xu
 * @version 1.0
 * @since 2016-11-14
 */

public class CreateNewTagButtonListener implements ActionListener {

	/** The TextField that let user enter the name of a new Tag. Receives a non-empty text from user before user clicks. */
	private JTextField createNewTagText;
	/** The Panel that contains all Tags in the TagManager. The Tags are displayed in CheckBoxes can could be selected to delete 
	 * from the TagManager. The Panel updates and add the Tag to the Panel when user clicks.
	 */
	private JPanel allTagDisplayPanel;
	/** A Panel that contains all Tags in the TagManager. The Tags are displayed in CheckBoxes and could be selected to add to the choosen image.
	 * The Panel updates and add the Tag to the Panel when user clicks.
	 */
	private JPanel addTagPanel;

	/**
	 * An action listener for the button to create a new tag in the TagManager, 
	 *
	 * @param createNewTagText
	 *            
	 * @param allTagDisplayPanel
	 *            
	 * @param addTagPanel
	 *            
	 */
	public CreateNewTagButtonListener(JTextField createNewTagText, JPanel allTagDisplayPanel, JPanel addTagPanel) {
		this.createNewTagText = createNewTagText;
		this.allTagDisplayPanel = allTagDisplayPanel;
		this.addTagPanel = addTagPanel;

	}

	/**
	 * Handle the user clicking on the CreateNewTag Button.
	 *
	 * @param e
	 *            the event object
	 */
	public void actionPerformed(ActionEvent e) {
		String newTagText = this.createNewTagText.getText();
		/*
		 * creates a new tag if the inputs of newTagText text area is not empty
		 */
		if (newTagText != "") {
			Tag newTag = new Tag(newTagText);
			TagManager.addTag(newTag);
			TagCheckBox newTagCB = new TagCheckBox(newTag.getTagName(), newTag);
			/* adds new check box to allTagDisplayPanel */
			if (!ManageTagList.allTagsPanelList.contains(newTagCB)) {
				this.allTagDisplayPanel.add(newTagCB);
				newTagCB.setVisible(true);
				this.allTagDisplayPanel.repaint();
				this.allTagDisplayPanel.revalidate();
			}
			if (!PhotoRenamer.addTagPanelList.contains(newTagCB)) {
				this.addTagPanel.add(newTagCB);
				PhotoRenamer.addTagPanelList.add(newTagCB);
			}

		}
	}

}
