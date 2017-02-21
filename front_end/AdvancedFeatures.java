package front_end;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import back_end.FileManager;
import back_end.FileNode;
import back_end.Tag;
import back_end.TagManager;
import photo_renamer.PhotoRenamer;

/**
 * Create and show a Advanced Features Explorer, which displays the advanced
 * features.
 * <p>
 * 
 * @author Jin Fang, Jingwen Xu
 * @version 1.0
 * @since 2016-11-14
 */
public class AdvancedFeatures {
	/**
	 * Creates and returns the window for the Advanced Feature explorer.
	 * 
	 * @param imageDisplayPanel2
	 *            The Panel that displays the tags the current selected image
	 *            has. This Panel will updates after the Tags of the image has
	 *            been changed by using some advanced features
	 * @param deleteTagPanel2
	 *            The Panel that displayes all old names that the current
	 *            selected image has. This Panel will updates after the old
	 *            names by the advanced features.
	 * @param oldNamePanel2
	 *            The Panel that displayes all old names that the current
	 *            selected image has. This Panel will updates after the old
	 *            names by the advanced features.
	 * @return the window for the Advnaced Feature explorer.
	 */

	public static JFrame buildAdvancedFeaturesWindow(JPanel imageDisplayPanel2,
			JPanel deleteTagPanel2, JPanel oldNamePanel2) {

		/* creates featureFrame and adds components to it */
		JFrame featureFrame = new JFrame();
		featureFrame.setBounds(110, 300, 550, 400);
		JComponent contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		featureFrame.setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JLabel featureLabel = new JLabel("Enjoy Advanced Features");
		featureLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(featureLabel, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(8, 1, 0, 0));

		JButton mostUsedTagB = new JButton("Get Most Frequently Used Tag");
		panel_1.add(mostUsedTagB);

		JButton mostSimilarImagesB = new JButton(
				"Get Images with Most Similar Tag");
		panel_1.add(mostSimilarImagesB);

		JButton mostTagImage = new JButton("Get Image with Most Tags");
		panel_1.add(mostTagImage);

		JButton removeAllTagsB = new JButton("Remove All Tags from All Images");
		panel_1.add(removeAllTagsB);

		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4);

		JLabel dateInputLabel = new JLabel("Type in date (yyyy-MM-dd HH:mm:ss)");
		panel_1.add(dateInputLabel);

		JTextArea dateInputText = new JTextArea();
		dateInputText.setFont(new Font("Monaco", Font.PLAIN, 13));
		panel_1.add(dateInputText);

		JButton revertToDateB = new JButton("Revert To That Date");
		panel_1.add(revertToDateB);

		JPanel outputPanel = new JPanel();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add(scrollPane);
		scrollPane.setViewportView(outputPanel);

		/* adds action listener to mostUsedTag button */
		mostUsedTagB.addActionListener(new ActionListener() {

			/**
			 * Handles the user clicking on the mostUsedTagB button. The most
			 * uesed tag found will be displayed in the outputPanel in a
			 * TextArea.
			 *
			 * @param e
			 *            the event object
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				outputPanel.removeAll();
				JTextArea output = new JTextArea();
				output.setText(TagManager.findMostUsedTag().toString());
				output.setEditable(false);
				outputPanel.add(output);
				featureLabel.setText("Most frequently used tag found.");
			}

		});

		/* adds action listener to mostTagImage button */
		mostTagImage.addActionListener(new ActionListener() {
			/**
			 * Handles the user clicking on the mostTagImage button. The images
			 * with the most tags will be displayed in the outputPanel as icon
			 * of imageLabels.
			 * 
			 * @param e
			 *            the event object
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				outputPanel.removeAll();
				FileNode mostTagFileNode = FileManager.findImageWithMostTags();
				/* creates a imageLabel for the image with most tags */
				BufferedImage img = null;
				try {
					img = ImageIO.read(new File(mostTagFileNode.getPath()));
				} catch (IOException e1) {
				}
				ImageIcon icon = new ImageIcon(img);
				JLabel imageLabel = new JLabel(null, icon, JLabel.CENTER);
				outputPanel.add(imageLabel);
				/* sets text for festureLabel */
				featureLabel.setText("Image with most tags found.");
			}

		});

		/* adds action listener to mostSimilarImages button */
		mostSimilarImagesB.addActionListener(new ActionListener() {
			/**
			 * Handles the user clicking on the mostSimilarImages button. One
			 * pair of images with the highest similiarity of their tags will be
			 * displayed as imageLabels, with icons being the images themselves.
			 * 
			 * @param e
			 *            the event object
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				outputPanel.removeAll();
				/*
				 * creates an ArrayList of FileNodes that have most similar tags
				 * using findImageWithMostSimilarTag method
				 */
				ArrayList<FileNode[]> temp = FileManager
						.findImageWithMostSimilarTag();
				ArrayList<FileNode> result = new ArrayList<FileNode>();
				for (FileNode[] fna : temp) {
					for (FileNode fn : fna) {
						if (!result.contains(fn)) {
							result.add(fn);
						}
					}
				}
				/* creates a new panel and adds all imageLabels to it */
				JPanel imageOutPutPanel = new JPanel();
				imageOutPutPanel.setLayout(new GridLayout(20, 1, 0, 0));
				for (FileNode f : result) {
					BufferedImage img = null;
					try {
						img = ImageIO.read(new File(f.getPath()));
					} catch (IOException e3) {
					}
					ImageIcon icon = new ImageIcon(img);

					JLabel imageLabel1 = new JLabel(null, icon, JLabel.CENTER);
					imageOutPutPanel.add(imageLabel1);
				}
				/* adds the new panel to the outputPanel in festureFrame */
				outputPanel.add(imageOutPutPanel);
				/* sets text for featureLabel */
				featureLabel.setText("Images with most similar tags found.");

			}

		});

		/* adds action listener to revertToDate button */
		revertToDateB.addActionListener(new ActionListener() {
			/**
			 * Handles the user clicking on the revertToDateB button. Performs
			 * the revertToDate to revert all changes to the date inputed by
			 * dateInputText.
			 *
			 * @param e
			 *            the event object
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				outputPanel.removeAll();
				/*
				 * clear the imageDisPlayPanel, deleteTagPanelList, and
				 * oldNamePanelList in main frame since lots of imageButton
				 * changed the client should reload images to do further actions
				 */
				imageDisplayPanel2.removeAll();
				for (TagCheckBox tcb : PhotoRenamer.deleteTagPanelList) {
					deleteTagPanel2.remove(tcb);
				}
				for (OldNameRadioButton tcb : PhotoRenamer.oldNamePanelList) {
					oldNamePanel2.remove(tcb);
				}
				/* creates a Date with specific hour, minute, and second */
				String dateString = dateInputText.getText();
				SimpleDateFormat fmt = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				try {
					Date date1 = fmt.parse(dateString);
					boolean flag = FileManager.revertToDate(date1);
					if (flag) {
						featureLabel
								.setText("Reversion completed. PLEASE RELOAD IMAGES.");
					} else {
						featureLabel
								.setText("Date input is Not Valid, please input date in correct format.");
					}
				} catch (ParseException e1) {
					e1.printStackTrace();
					featureLabel
							.setText("Date input is Not Valid, please input date in correct format.");
				}
				/* refreshes all panels to show the changes */
				outputPanel.revalidate();
				outputPanel.repaint();
				imageDisplayPanel2.revalidate();
				imageDisplayPanel2.repaint();
				deleteTagPanel2.revalidate();
				deleteTagPanel2.repaint();
				oldNamePanel2.revalidate();
				oldNamePanel2.repaint();
			}
		});

		/* adds action listener to removeAllTag button */
		removeAllTagsB.addActionListener(new ActionListener() {
			/**
			 * Handles the user clicking on the removeAllTagsB button. Remove
			 * all the tags from all images and removed the currently displayed
			 * results and images from all panels.
			 * 
			 * @param e
			 *            the event object
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				outputPanel.removeAll();
				/*
				 * clears the imageDisPlayPanel, deleteTagPanelList, and
				 * oldNamePanelList in main frame since lots of imageButton
				 * changed the client should reload images to do further actions
				 */
				imageDisplayPanel2.removeAll();
				for (TagCheckBox tcb : PhotoRenamer.deleteTagPanelList) {
					deleteTagPanel2.remove(tcb);
				}
				for (OldNameRadioButton tcb : PhotoRenamer.oldNamePanelList) {
					oldNamePanel2.remove(tcb);
				}
				/* delete all tags in the tagList from all images */
				for (Tag t : TagManager.tagList) {
					try {
						FileManager.deleteTagFromAllImage(t);
						featureLabel
								.setText("Deletion completed. PLEASE RELOAD IMAGES.");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				/* refreshes all panels to show the changes */
				outputPanel.revalidate();
				outputPanel.repaint();
				imageDisplayPanel2.revalidate();
				imageDisplayPanel2.repaint();
				deleteTagPanel2.revalidate();
				deleteTagPanel2.repaint();
				oldNamePanel2.revalidate();
				oldNamePanel2.repaint();
			}
		});

		return featureFrame;
	}

}
