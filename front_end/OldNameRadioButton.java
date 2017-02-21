package front_end;

import javax.swing.JRadioButton;
/**
 * The OldNameRadioButton to display the oldNames of an Image.
 * 
 * @author Jin Fang, Jingwen Xu
 * @version 1.0
 * @since 2016-11-14
 */
public class OldNameRadioButton extends JRadioButton {
	/**
	 * The unique identifier of this serialziable class.
	 */
	private static final long serialVersionUID = 1L + 6;
	private String oldName;

	/**
	 * Returns the oldName that this OldNameRadioButton is representing.
	 * 
	 * @return the oldName
	 * @see back_end.FileNode.oldNames
	 */
	public String getOldName() {
		return oldName;
	}

	/**
	 * Set the oldName that this OldNameRadioButton is represeting to oldName.
	 * 
	 * @param oldName
	 *            the oldName to set
	 */
	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	/**
	 * Creates an OldNameRadioButton with text as text, and its oldName being
	 * oldName.
	 * 
	 * @param text
	 */
	public OldNameRadioButton(String text, String oldName) {
		super(text);
		this.oldName = oldName;
	}

}
