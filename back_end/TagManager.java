package back_end;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


/*
 * This TagManager class is a part of the Command Design Pattern.
 * 
 * TagList acts as a command.
 * 
 * FileNode class acts as a request. It requests adding and deleting Tag objects 
 * to/from FileNode object's tags list. 
 * 
 * AddTag and DeleteTag classes are concrete command classes that implements 
 * TagList interface, which actually adds and deletes Tag objects to/from 
 * tagList in TagManager class.
 * 
 * The class TagManager is an invoker object, and it records tagList.
 * 
 * TagManager uses command pattern to identify which object will execute which 
 * command based on the type of command.
 */

/**
 * <h1>Manage the Tags</h1> The TagManager is mainly used to manage the
 * available tags that could later be choosen by user to add to some image.
 * <p>
 * 
 * @author Jin Fang, Jingwen Xu
 * @version 1.0
 * @since 2016-11-14
 */


public class TagManager implements Serializable {
	/**
	 * A unique identifier for this serializable class.
	 */
	private static final long serialVersionUID = 1L + 2;
	/**
	 * An arrayList of all tags that have been used in this application.
	 */
	public static ArrayList<Tag> tagList = new ArrayList<Tag>();

	/**
	 * Returns a string representation of tagList, which contains all the tags
	 * that has been used.
	 * 
	 * @return a string representation of tagList of this TagManaer.
	 * @see TagManager#tagList
	 */
	@Override
	public String toString() {
		return TagManager.tagList.toString();
	}

	/**
	 * Adds a new Tag t to the tagList of this TagManager only when the Tag t is
	 * not currently in the tagList of this TagManager.
	 * 
	 * @param t
	 *            the new Tag t the user want to add to the tagList of this
	 *            TagManager.
	 */
	public static void addTag(Tag t) {
		if (TagManager.tagList.contains(t) == false) {
			TagManager.tagList.add(t);
		}
	}

	/**
	 * Deletes a Tag t from the tagList of this TagManager only when the Tag is
	 * currently in the tagList of this TagManager.
	 * <p>
	 * Also deletes this Tag t from all the image files that has this Tag t in
	 * its tags.
	 * 
	 * @param t
	 *            a Tag t the user want to delete from the tagList of this
	 *            TagManager.
	 */
	public static void deleteTag(Tag t) {
		if (TagManager.tagList.contains(t)) {
			TagManager.tagList.remove(t);
			try {
				FileManager.deleteTagFromAllImage(t);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Returns a Tag that has been used most times. If there are more than one
	 * tag that has the highest number of used times, then return one of these
	 * tags.
	 * 
	 * @return a Tag that has the highest number of usedTimes.
	 */
	public static Tag findMostUsedTag() {
		Tag result = TagManager.tagList.get(0);
		for (Tag temp : TagManager.tagList) {
			if (temp.getUsedTimes() > result.getUsedTimes()) {
				result = temp;
			}
		}
		return result;
	}

	/**
	 * Serializes all Tag objects in the tagList of this TagManager and writes
	 * it into a ser file.
	 * 
	 * @throws FileNotFoundException
	 *             If the system could not find a file with the given path
	 * @throws IOException
	 *             If an input or output exception occurred
	 */
	public static void Ser() throws FileNotFoundException, IOException {
		// check if .ser file already exist
		ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream("TagManager.ser"));
		oos1.writeObject(TagManager.tagList);
		oos1.close();
	}

	/**
	 * Deserializes all Tag objects from given ser file and adds all the Tag
	 * objects into the tagList of this TagManager.
	 * 
	 * @throws FileNotFoundException
	 *             If the system could not find a file with the given path
	 * @throws IOException
	 *             If an input or output exception occurred
	 * @throws ClassNotFoundException
	 *             If the class is not found when the application is trying to
	 *             load in a class
	 */
	@SuppressWarnings("unchecked")
	public static void Deser() throws IOException {

		try {
			ObjectInputStream ois1 = new ObjectInputStream(new FileInputStream("TagManager.ser"));
			TagManager.tagList = (ArrayList<Tag>) ois1.readObject();
			ois1.close();
		} catch (IOException | ClassNotFoundException e4) {
		}
	}

	/**
	 * Returns the Tag at the index i in the tagList of this TagManager.
	 * 
	 * @param i
	 *            the index of the tag that the user is trying to retreive
	 * @return a Tag at the index i in the tagList
	 * @see back_end.TagManager#tagList
	 */
	public static Tag getTagAtIndex(int i) {
		if (i < TagManager.tagList.size()){
			return TagManager.tagList.get(i);
		}else{
			return (Tag) null;
		}
	}

	/**
	 * Finds and returns a Tag named name in the tagList of this TagManager,
	 * only if the Tag exists in the tagList.
	 * 
	 * @param name
	 *            the name of the Tag that the user wants to find in the tagList
	 * @return a Tag named name in the tagList of this TagManager
	 */
	public static Tag getTag(String name) {
		for (Tag temp : TagManager.tagList) {
			if (temp.getTagName().equals(name)) {
				return temp;
			} 
		}
		return (Tag) null; //casts null as Tag to avoid warning
	}
	
}
