package back_end;

import java.io.IOException;

/*
 * This DeleteTagClass is a part of the Command Design Pattern.
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
 * A concrete command class has methods of deleteTag and
 * updateChangesToFildNodes operation.
 * 
 * @author Jin Fang, Jingwen Xu
 * @version 1.0
 * @since 2016-11-14
 */
public class DeleteTag implements TagList {
	/**
	 * Delete Tag to the TagManager
	 * 
	 * @param Tag
	 *            t the tag to be deleted to the TagManager
	 * @see Tag
	 */
	public static void deleteTag(Tag t) {
		if (TagManager.tagList.contains(t)) {
			TagManager.tagList.remove(t);
		}

	}

	/**
	 * Update the change of the Tag t in the TagManager
	 * 
	 * @param Tag
	 *            t the tag to be updated to the TagManager
	 * @see Tag
	 */
	@Override
	public void updateChangesToFileNodes(Tag t) {
		if (TagManager.tagList.contains(t)) {
			try {
				FileManager.deleteTagFromAllImage(t);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
