package front_end;

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;

import back_end.Tag;

/**
 * The TagCheckBox to display tags.
 * 
 * @author Jin Fang, Jingwen Xu
 * @version 1.0
 * @since 2016-11-14
 */
public class TagCheckBox extends JCheckBox {
	/** the tag that this TagCheckBox is representing */
	private Tag tag;
	/**
	 * an unique identifier of this serializable class.
	 */
	private static final long serialVersionUID = 1L + 5;

	/**
	 * Creates a TagCheckBox with selection status.
	 * 
	 * @param text
	 *            the text of this TagCheckBox, with the text being the name of
	 *            the Tag tag.
	 * @param selected
	 *            the select status of this TagCheckBox
	 * @param tag
	 *            the Tag that the TagCheckBox is representing
	 */
	public TagCheckBox(String text, boolean selected, Tag tag) {
		super(text, selected);
		this.setTag(tag);
	}

	/**
	 * Creates a TagCheckBox without its selection status.
	 * 
	 * @param text
	 *            the text of this TagCheckBox, with the text being the name of
	 *            the Tag tag.
	 * @param tag
	 *            the Tag that the TagCheckBox is representing
	 */
	public TagCheckBox(String text, Tag tag) {
		super(text);
		this.setTag(tag);
	}

	/**
	 * Returns the Tag that this TagCheckBox is representing
	 * 
	 * @return Tag the Tag that this TagCheckBox is representing
	 */
	public Tag getTag() {
		return tag;
	}

	/**
	 * Set the tag that this TagCheckBox represents to be Tag tag.
	 * 
	 * @param tag
	 *            the Tag to change to
	 */
	public void setTag(Tag tag) {
		this.tag = tag;
	}

	/**
	 * Returns whether two TagCheckBox are the same.
	 * 
	 * @return boolean whether the two TagCheckBox are the same.
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof TagCheckBox)) {
			return false;
		} else {
			return this.getText() == ((AbstractButton) other).getText();
		}
	}

}
