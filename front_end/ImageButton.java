package front_end;

import javax.swing.Icon;
import javax.swing.JButton;

import back_end.FileNode;

/**
 * <h1>Manage the Tags</h1> The TagManager is mainly used to manage the
 * available tags that could later be choosen by user to add to some image.
 * <p>
 * 
 * @author Jin Fang, Jingwen Xu
 * @version 1.0
 * @since 2016-11-14
 */
public class ImageButton extends JButton {

	/**
	 * A unique identifier for this serializable class.
	 */
	private static final long serialVersionUID = 1L + 3;
	/**
	 * A FileNode that the imageButton is represented for.
	 * 
	 * @see back_end.FileNode
	 */
	private FileNode fileNode;

	/**
	 * Creates a ImageButton that has image itself as icon. This button has a
	 * field of the FileNode of the same image.
	 * <p>
	 * 
	 * @param icon
	 *            the icon of the ImageButton
	 * @param fn
	 *            the FileNode that the ImageButton is represeting.
	 * @see back_end.FileNode
	 */
	public ImageButton(Icon icon, FileNode fn) {
		super(icon);
		this.fileNode = fn;
	}

	/**
	 * Creates a ImageButton that has the name text.
	 * 
	 * @param text
	 */
	public ImageButton(String text) {
		super(text);
	}

	/**
	 * Returns the FileNode of the file that this ImageButton is representing.
	 * 
	 * @return FileNode the FileNode of the file that this ImageButton is
	 *         representing
	 * @see back_end.FileNode
	 */
	public FileNode getFileNode() {
		return fileNode;
	}

	/**
	 * Set the FileNode of the file that this ImageButton is representing to a
	 * FileNode fileNode.
	 * 
	 * @return FileNode the FileNode of the file that this ImageButton is
	 *         representing
	 * @param fileNode
	 *            a new fileNode to change to
	 * @see back_end.FileNode
	 */
	public void setFileNode(FileNode fileNode) {
		this.fileNode = fileNode;
	}

}
