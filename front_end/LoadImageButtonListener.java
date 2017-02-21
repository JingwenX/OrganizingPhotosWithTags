package front_end;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import back_end.FileManager;
import back_end.FileNode;
import back_end.FileType;

/**
 * The listener for the button to choose a directory and load the images in this
 * directory.
 * <p>
 * 
 * @author Jin Fang, Jingwen Xu
 * @version 1.0
 * @since 2016-11-14
 */
public class LoadImageButtonListener implements ActionListener {
	/** The window the button is in. */
	private JFrame directoryFrame;
	/** The label for the full path to the chosen directory. */
	private JLabel directoryLabel;
	/** The file chooser to use when the user clicks. */
	private JFileChooser fileChooser;
	/** The area to use to display the nested directory contents. */
	private JPanel imageDisplayPanel;
	/** The Panel that displays the tags the current selected image has. */
	private JPanel deleteTagPanel;
	/**
	 * The Panel that displayes all old names that the current selected image
	 * has.
	 */
	private JPanel oldNamePanel;

	/**
	 * An action listener for window dirFrame, displaying a file path on
	 * dirLabel, using fileChooser to choose a file.
	 *
	 * @param dirFrame
	 *            the main window
	 * @param dirLabel
	 *            the label for the directory path
	 * @param imageDisplayPanel
	 *            The Panel that displays the the current loaded images
	 * @param fileChooser
	 *            the file chooser to use
	 * @param addTagPanel
	 *            the addTagPanel that displays all tags in the TagManager
	 * @param deleteTagPanel
	 *            the deleteTagPanel that updates after image selection
	 * @param oldNamePanel
	 *            the oldNamePanel that updates after image selection
	 */
	public LoadImageButtonListener(JFrame dirFrame, JLabel dirLabel,
			JPanel imageDisplayPanel, JFileChooser fileChooser,
			JPanel addTagPanel, JPanel deleteTagPanel, JPanel oldNamePanel) {
		this.directoryFrame = dirFrame;
		this.directoryLabel = dirLabel;
		this.imageDisplayPanel = imageDisplayPanel;
		this.fileChooser = fileChooser;
		this.deleteTagPanel = deleteTagPanel;
		this.oldNamePanel = oldNamePanel;
	}

	/**
	 * Handle the user clicking on the LoadImageButton.
	 * <p>
	 * Clicking on this button will opens a JFileChooser for user to choose a
	 * directory to work with. Then displays all images in given directory as
	 * ImageButton on the imageDisplayPanel for user to select.
	 * 
	 * @param e
	 *            the event object
	 * @see front_end.ImageButton
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		int returnVal = fileChooser.showOpenDialog(directoryFrame
				.getContentPane());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			if (file.exists()) {
				/* sets texts for directotyLabel */
				directoryLabel.setText("Selected Directory"
						+ file.getAbsolutePath()
						+ " --EVERY IMAGE IS A BUTTON! CLICK ON ONE IMAGE TO OPERATE-- ");
				/* makes the root using the selcted file */
				FileNode root = new FileNode(file.getName(), null,
						FileType.DIRECTORY, file.getAbsolutePath());
				try {
					/* deserializes FileNodes from the configuration file */
					ArrayList<FileNode> allFile = FileManager.Deser();
					Boolean flag = false;
					FileNode temp = null;
					for (FileNode fn : allFile) {
						if (fn.getPath().equals(root.getPath())) {
							flag = true;
							temp = fn;
							break;
						}
					}
					/*
					 * if the selected directory is accessed before, adds
					 * FileNodes from the configuration file to the fileList
					 */
					if (flag) {
						FileManager.buildFileList(temp);
					} else {
						/*
						 * if the directory is not accessed before, creates new
						 * FileNodes and adds them to the fileList
						 */
						FileManager.buildFileList(file, root);
					}
				} catch (ClassNotFoundException | IOException e1) {
					e1.printStackTrace();
				}
				/* creates Image buttons based on FilesNodes in the fileList */
				for (FileNode fn : FileManager.fileList) {
					if (!fn.isDirectory()) {
						ImageIcon ii = new ImageIcon(fn.getPath());
						ImageButton bt = new ImageButton(ii, fn);
						/* adds action listeners to every Image button */
						ActionListener imageAL = new ImageButtonListener(
								deleteTagPanel, oldNamePanel, directoryLabel,
								bt);
						bt.addActionListener(imageAL);
						/*
						 * adds all Image buttons to the imageDisplayPanel and
						 * refresh the panel
						 */
						this.imageDisplayPanel.add(bt);
						this.imageDisplayPanel.revalidate();
						this.imageDisplayPanel.repaint();
					}

				}
			}
		} else {
			directoryLabel.setText("No Path Selected");
		}
	}

}
