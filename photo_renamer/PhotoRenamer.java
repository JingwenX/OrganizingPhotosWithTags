package photo_renamer;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import back_end.FileManager;
import back_end.FileNode;
import back_end.Tag;
import back_end.TagManager;
import front_end.AdvancedFeaturesButtonListener;
import front_end.LoadImageButtonListener;
import front_end.ManageTagListButtonListener;
import front_end.OldNameRadioButton;
import front_end.TagCheckBox;


public class PhotoRenamer implements WindowListener{
	
	public static ArrayList<TagCheckBox> addTagPanelList = new ArrayList<TagCheckBox>();
	public static ArrayList<TagCheckBox> deleteTagPanelList = new ArrayList<TagCheckBox>();
	public static ArrayList<OldNameRadioButton> oldNamePanelList = new ArrayList<OldNameRadioButton>();
	public static FileNode currentFileNode;
	
	public static JFrame buildMainWindow() throws FileNotFoundException, ClassNotFoundException, IOException{
		
		/* builds main frame and add components to it */
		JFrame prFrame = new JFrame();
		prFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		prFrame.setBounds(100, 100, 1100, 700);
		
		WindowListener wl = new PhotoRenamer();
		prFrame.addWindowListener(wl);
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		prFrame.setContentPane(contentPane);
		
		JScrollPane imagePanelScroll = new JScrollPane();
		imagePanelScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		imagePanelScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		contentPane.add(imagePanelScroll, BorderLayout.CENTER);
		
		JPanel imagePanel = new JPanel();
		imagePanelScroll.setViewportView(imagePanel);
		imagePanel.setLayout(new BorderLayout(0, 0));
		
		JPanel functionPanel = new JPanel();
		contentPane.add(functionPanel, BorderLayout.SOUTH);
		functionPanel.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel imageDisplayPanel = new JPanel();
		imagePanel.add(imageDisplayPanel, BorderLayout.CENTER);
		imageDisplayPanel.setLayout(new GridLayout(40, 1, 5, 5));
		
		JButton af = new JButton("Advanced Features");
		functionPanel.add(af);
		
		JButton mt = new JButton("Manage Tag List");
		functionPanel.add(mt);
		
		JButton loadImageB = new JButton("LOAD IMAGE");
		imagePanelScroll.setColumnHeaderView(loadImageB);
		
		JLabel directoryLabel = new JLabel("Choose a directory to start");
		contentPane.add(directoryLabel, BorderLayout.NORTH);
		
		JPanel operationPanel = new JPanel();
		contentPane.add(operationPanel, BorderLayout.EAST);
		operationPanel.setLayout(new GridLayout(3, 1, 0, 0));
		
		JPanel deleteTagPanel = new JPanel();
		JPanel addTagPanel = new JPanel();
		JPanel oldNamePanel = new JPanel();

		JScrollPane addTagPaneScroll = new JScrollPane();
		operationPanel.add(addTagPaneScroll);
		addTagPaneScroll.setViewportView(addTagPanel);
		addTagPanel.setLayout(new GridLayout(20, 1, 0, 0));
		
		JButton addTagToImageB = new JButton("Add Tag to Image");
		addTagPanel.add(addTagToImageB, BorderLayout.NORTH);
		
		JScrollPane deleteTagPaneScroll = new JScrollPane();
		operationPanel.add(deleteTagPaneScroll);
		deleteTagPaneScroll.setViewportView(deleteTagPanel);
		deleteTagPanel.setLayout(new GridLayout(20, 1, 0, 0));
		
		JButton deleteTagFromImageB = new JButton("Delete Tag from Image");
		deleteTagPanel.add(deleteTagFromImageB, BorderLayout.NORTH);
		
		JScrollPane oldNamePaneScroll = new JScrollPane();
		operationPanel.add(oldNamePaneScroll);
		oldNamePaneScroll.setViewportView(oldNamePanel);
		
		JButton revertToOldNameB = new JButton("Revert to Old Name");
		revertToOldNameB.setToolTipText("");
		oldNamePanel.add(revertToOldNameB);
		oldNamePanel.setLayout(new GridLayout(20,1,0,0));
		
		/* creates file chooser for loading images */
		JFileChooser dirChooser = new JFileChooser();
		dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		loadImageB.setMnemonic(KeyEvent.VK_D);
		loadImageB.setActionCommand("disable");

		/* adds action listener to LoadImage button */
		ActionListener buttonListener = new LoadImageButtonListener(prFrame, directoryLabel, imageDisplayPanel, dirChooser, addTagPanel, deleteTagPanel, oldNamePanel);
		loadImageB.addActionListener(buttonListener);
		
		/* adds action listener to AdvancedFeature button */
		ActionListener advancedFeaturesBL = new AdvancedFeaturesButtonListener(imageDisplayPanel, deleteTagPanel, oldNamePanel);
		af.addActionListener(advancedFeaturesBL);
		
		/* adds action listener to ManageTagList button */
		ActionListener manageTagListBL = new ManageTagListButtonListener(addTagPanel, deleteTagPanel, oldNamePanel, directoryLabel);
		mt.addActionListener(manageTagListBL);
		
		/* adds action listener to AddTagToImage button */
		addTagToImageB.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				/*searches for checked check boxes*/
				ArrayList<TagCheckBox> temp = new ArrayList<TagCheckBox>();
				for (TagCheckBox tcb : PhotoRenamer.addTagPanelList){
					if (tcb.isSelected()){
						temp.add(tcb);
					}
				}
				
				/*check if the image already has the tag*/
				for (TagCheckBox tcb : temp){
					boolean flag = true;
					for (Tag t : PhotoRenamer.currentFileNode.getTags()){
						if (tcb.getTag().getTagName().equals(t.getTagName())){
							flag = false;
							break;
						}
					}
					/* adds tag to the image if the image does not have this tag */
					if (flag){
						PhotoRenamer.currentFileNode.addTag(tcb.getTag(), true, true);
						/*sets text in the label*/
						directoryLabel.setText("New Name: "+ PhotoRenamer.currentFileNode.getName() 
						+ " (" + PhotoRenamer.currentFileNode.getPath() + ")");
						/* updates deleteTagPanel */
						TagCheckBox tcbCopy = new TagCheckBox(tcb.getText(), tcb.getTag());
						PhotoRenamer.deleteTagPanelList.add(tcbCopy);
						deleteTagPanel.add(tcbCopy);
						/* updates oldNamePanel */
						for(OldNameRadioButton b : PhotoRenamer.oldNamePanelList){
							oldNamePanel.remove(b);
						}
						PhotoRenamer.oldNamePanelList.clear();
						ButtonGroup buttonGroup = new ButtonGroup();
						for (String oldName: PhotoRenamer.currentFileNode.getOldNames().keySet()){
							OldNameRadioButton oldNameB = new OldNameRadioButton(oldName, oldName);
							buttonGroup.add(oldNameB);
								PhotoRenamer.oldNamePanelList.add(oldNameB);
								oldNamePanel.add(oldNameB);
						}
						
					}
				}
				/* refreshes deleteTagPanel and oldNamePanel */
				deleteTagPanel.revalidate();
				deleteTagPanel.repaint();
				oldNamePanel.revalidate();
				oldNamePanel.repaint();
			}
			
		});
		
		/* adds action listener to DeleteTagFromImage button */
		deleteTagFromImageB.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				/*searches for checked check boxes*/
				ArrayList<TagCheckBox> temp = new ArrayList<TagCheckBox>();
				for (TagCheckBox tcb : PhotoRenamer.deleteTagPanelList){
					if (tcb.isSelected()){
						temp.add(tcb);

					}
				}
				
				/* deletes tags from the image */
				for (TagCheckBox tcb: temp){
					PhotoRenamer.currentFileNode.deleteTag(tcb.getTag(), true, true);
					/*sets text in the label*/
					directoryLabel.setText("New Name: "+ PhotoRenamer.currentFileNode.getName() 
					+ " (" + PhotoRenamer.currentFileNode.getPath() + ")");
					/* updates deleteTagPanel */
					deleteTagPanel.remove(tcb);
					PhotoRenamer.deleteTagPanelList.remove(tcb);
					/* updates oldNamePanel */
					for(OldNameRadioButton b : PhotoRenamer.oldNamePanelList){
						oldNamePanel.remove(b);
					}
					PhotoRenamer.oldNamePanelList.clear();
					ButtonGroup buttonGroup = new ButtonGroup();
					for (String oldName: PhotoRenamer.currentFileNode.getOldNames().keySet()){
						OldNameRadioButton oldNameB = new OldNameRadioButton(oldName, oldName);
						buttonGroup.add(oldNameB);
							PhotoRenamer.oldNamePanelList.add(oldNameB);
							oldNamePanel.add(oldNameB);
					}
				}
				/* refreshes deleteTagPanel and oldNamePanel */
				deleteTagPanel.revalidate();
				deleteTagPanel.repaint();
				oldNamePanel.revalidate();
				oldNamePanel.repaint();
			}
			
		});
		
		/* adds action listener to RevertToOldName button */
		revertToOldNameB.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				/* searches for checked check boxes */
				OldNameRadioButton selected = null;
				for (OldNameRadioButton b : PhotoRenamer.oldNamePanelList){
					if (b.isSelected()){
						selected = b;
					}
				}
				/* reverts to an old name */
				PhotoRenamer.currentFileNode.revertToOldName(selected.getOldName(), true);
				/* updates deletTagePanel */
				for(TagCheckBox b : PhotoRenamer.deleteTagPanelList){
					deleteTagPanel.remove(b);
				}
				PhotoRenamer.deleteTagPanelList.clear();
				for (Tag t: PhotoRenamer.currentFileNode.getTags()){
					TagCheckBox tagCB = new TagCheckBox(t.getTagName(), t); 
					deleteTagPanel.add(tagCB);
					PhotoRenamer.deleteTagPanelList.add(tagCB);
				}
				/* refreshes deleteTagPanel */
				deleteTagPanel.revalidate();
				deleteTagPanel.repaint();
				
				/* adds old names to  oldNamePanel */
				for(OldNameRadioButton b : PhotoRenamer.oldNamePanelList){
					oldNamePanel.remove(b);
				}
				PhotoRenamer.oldNamePanelList.clear();
				/* groups radio buttons so that only one of them can be chosen */
				ButtonGroup buttonGroup = new ButtonGroup();
				for (String oldName: PhotoRenamer.currentFileNode.getOldNames().keySet()){
					OldNameRadioButton oldNameB = new OldNameRadioButton(oldName, oldName);
					buttonGroup.add(oldNameB);
					oldNamePanel.add(oldNameB);
					PhotoRenamer.oldNamePanelList.add(oldNameB);
				}
				/* refreshes oldNamePanel*/
				oldNamePanel.revalidate();
				oldNamePanel.repaint();
				
				/*updates saddTagPanel*/
				for (TagCheckBox tcb: PhotoRenamer.addTagPanelList){
					addTagPanel.remove(tcb);
				}
				PhotoRenamer.addTagPanelList.clear();
				for (Tag t: TagManager.tagList){
					TagCheckBox tcb = new TagCheckBox(t.getTagName(), t);
					addTagPanel.add(tcb);
					PhotoRenamer.addTagPanelList.add(tcb);
				}
				/* refreshes addTagPanel*/
				addTagPanel.revalidate();
				addTagPanel.repaint();
				
				/*sets text in the label*/
				directoryLabel.setText("New Name: "+ PhotoRenamer.currentFileNode.getName() 
				+ " (" + PhotoRenamer.currentFileNode.getPath() + ")");
			}
			
		});
		
		
		/* deserialize the tagList from configuration files*/
		try {
			TagManager.Deser();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/* adds tags to addTagPanel */
		for (Tag t: TagManager.tagList){
			TagCheckBox temp = new TagCheckBox(t.getTagName(), t);
			addTagPanel.add(temp);
			PhotoRenamer.addTagPanelList.add(temp);
		}
		
		return prFrame;
	}

	public static void main (String[] args){
		try {
			PhotoRenamer.buildMainWindow().setVisible(true);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		try {
			/* writes all changes on Tags and FileNodes to configuration file */
			TagManager.Ser();
			FileManager.Ser(FileManager.fileList);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}
}
