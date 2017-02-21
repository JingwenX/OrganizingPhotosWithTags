package back_end;

/*
 * This TagList abstract interface is a part of the Command Design Pattern.
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
 * A interface which has method updateChangesToFileNode. This interface could be
 * implemented with methods that operates on TagManager.
 * 
 * @author Jin Fang, Jingwen Xu
 * @version 1.0
 * @since 2016-11-14
 * @see back_end.Tag
 */
public abstract interface TagList {

	public void updateChangesToFileNodes(Tag t);
}
