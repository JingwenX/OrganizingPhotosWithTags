package front_end;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
/**
 * The listener for the Advanced Features Button.
 * 
 * @author Jin Fang, Jingwen Xu
 * @version 1.0
 * @since 2016-11-14
 */
public class AdvancedFeaturesButtonListener implements ActionListener{
	/** The Panel that displays the the current loaded images. This Panel will updates after using some advanced features. */
	private JPanel imageDisplayPanel;
	/** The Panel that displays the tags the current selected image has. This Panel will updates after the Tags of the image has been
	/* changed by using some advanced features.*/
	private JPanel deleteTagPanel;
	/** The Panel that displayes all old names that the current selected image has. This Panel will updates after the old names by
	/* the advanced features.*/

	private JPanel oldNamePanel;
	/** The button listener for advanced features button.
	 */
	public AdvancedFeaturesButtonListener(JPanel imageDisplayPanel, JPanel deleteTagPanel, JPanel oldNamePanel) {
		this.imageDisplayPanel = imageDisplayPanel;
		this.deleteTagPanel = deleteTagPanel;
		this.oldNamePanel = oldNamePanel;
	}

	/**
	 * Returns the Panel that displays all the images of the selected directory.
	 * @return the imageDisplayPanel
	 */
	public JPanel getImageDisplayPanel() {
		return imageDisplayPanel;
	}

	/**
	 * Sets the Panel that displays all the images of the selected directory to a Panel named imageDisplayPanel.
	 * @param imageDisplayPanel 
	 * 		the imageDisplayPanel to set
	 */
	public void setImageDisplayPanel(JPanel imageDisplayPanel) {
		this.imageDisplayPanel = imageDisplayPanel;
	}

	/**
	 * Returns the Panel which contains all tags the selected image has.
	 * @return the deleteTagPanel
	 */
	public JPanel getDeleteTagPanel() {
		return deleteTagPanel;
	}

	/**
	 * Sets the Panel which contains all tags of selected image to deleteTagPanel1
	 * @param deleteTagPanel the deleteTagPanel to set
	 */
	public void setDeleteTagPanel(JPanel deleteTagPanel) {
		this.deleteTagPanel = deleteTagPanel;
	}

	/**
	 * Returns an oldNamePanel of selected image.
	 * @return the oldNamePanel
	 */
	public JPanel getOldNamePanel() {
		return oldNamePanel;
	}

	/**
	 * Sets the oldNamePanel to oldNamePanel1.
	 * @param oldNamePanel the oldNamePanel to set
	 */
	public void setOldNamePanel(JPanel oldNamePanel) {
		this.oldNamePanel = oldNamePanel;
		
	}
	/**
	 * Handles the user clicking on the advanced features Button.
	 *
	 * @param e
	 *            the event object
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		AdvancedFeatures.buildAdvancedFeaturesWindow(imageDisplayPanel, deleteTagPanel, oldNamePanel).setVisible(true);	
	}
}
