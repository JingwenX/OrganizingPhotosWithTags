package junit_test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import back_end.FileManager;
import back_end.FileNode;
import back_end.FileType;
import back_end.Tag;
import back_end.TagManager;

/**
 * JUnit Test Cases used for testing the methods of TagManager.
 * <p>
 * 
 * @author Jin Fang, Jingewen Xu
 * @version 1.0
 * @since 2016-11-14
 */
public class TagManagerTest {

	private Tag tag1;
	private Tag tag2;
	private Tag tag3;
	private Tag tag4;
	private Tag tag5;
	private Tag tag6;

	@SuppressWarnings("unused")
	private FileNode fn1;
	private FileNode fn2;
	private FileNode fn3;

	/**
	 * Set up the Tag and FileNodes used in all the test cases.
	 */
	@Before
	public void setUp() throws Exception {
		tag1 = new Tag("tag1");
		tag2 = new Tag("tag2");
		tag3 = new Tag("tag3");
		tag4 = new Tag("tag4");
		tag5 = new Tag("tag5");
		tag6 = new Tag("tag6");

		fn1 = new FileNode("Image1", null, FileType.IMAGE,
				"/Users/User1/Desktop/Image1");
		fn2 = new FileNode("Image", null, FileType.IMAGE,
				"/Users/User1/Desktop/Image2");
		fn3 = new FileNode("Image3", null, FileType.IMAGE,
				"/Users/User1/Desktop/Image3");

	}

	/**
	 * Test method for add Tag into TagManager. This is a test for adding a new
	 * tag into the TagManager. If the Tag is added successfully, it would
	 * appear in in the TagManager.
	 */
	@Test
	public void testAddANotExistedTagToTagManager() {
		TagManager.addTag(tag1);
		/* set up expected */
		ArrayList<Tag> expected = new ArrayList<Tag>();
		expected.add(tag1);
		/* set up actual */
		ArrayList<Tag> actual = TagManager.tagList;
		assertArrayEquals("Add a tag to tagmanager fail", expected, actual);
	}

	/**
	 * Test method for add a existing Tag. This is a test for adding an already
	 * existing tag into the TagManager. It is expected to add the tag of the
	 * same name just once.
	 */
	@Test
	public void testAddAnExistingTagToTagManager() {
		TagManager.addTag(tag1);
		TagManager.addTag(tag1);
		/* set up expected */
		ArrayList<Tag> expected = new ArrayList<Tag>();
		expected.add(tag1);
		/* set up actual */
		ArrayList<Tag> actual = TagManager.tagList;
		assertArrayEquals(
				"Add an exiting tag to tagmanager fail to detect its existance",
				expected, actual);
	}

	/**
	 * Test method for deleteTag. This is to test if deleting an existing tag
	 * from the TagManager is normal.
	 */
	@Test
	public void testDeleteAnExistingTagToTagManager() {
		TagManager.addTag(tag1);
		TagManager.addTag(tag2);
		TagManager.deleteTag(tag1);
		/* set up expected */
		ArrayList<Tag> expected = new ArrayList<Tag>();
		expected.add(tag2);
		/* set up actual */
		ArrayList<Tag> actual = TagManager.tagList;
		assertArrayEquals("Add a tag to tagmanager fail", expected, actual);
	}

	/**
	 * Test method for testing deleteTag. This is to test if deleting a tag
	 * which is not in the TagManager is normal. The TagManager is expected to
	 * does not change.
	 */
	@Test
	public void testDeleteNonExistingTagToTagManager() {
		TagManager.addTag(tag1);
		TagManager.deleteTag(tag2);
		/* set up expected */
		ArrayList<Tag> expected = new ArrayList<Tag>();
		expected.add(tag1);
		/* set up actual */
		ArrayList<Tag> actual = TagManager.tagList;
		assertArrayEquals("Add a non existing tag to tagmanager fail",
				expected, actual);
	}

	/**
	 * Find most used tag. Returns the first tag with highest used times.
	 * Returns the first tag if none of the tags are used.
	 */
	@Test
	public void testFindMostUsedTag() {
		TagManager.addTag(tag1);
		TagManager.addTag(tag2);
		TagManager.addTag(tag3);
		TagManager.addTag(tag4);
		TagManager.addTag(tag5);
		TagManager.addTag(tag6);

		fn2.addTag(tag1, false, false);
		fn2.addTag(tag2, false, false);
		fn2.addTag(tag3, false, false);

		fn3.addTag(tag2, false, false);
		fn3.addTag(tag3, false, false);
		fn3.addTag(tag4, false, false);
		/* set up expected */
		Tag expected = tag2;
		/* set up actual */
		Tag actual = TagManager.findMostUsedTag();
		assertEquals("Find most used tag fail", expected, actual);
	}

	/**
	 * Find most used tag. Returns the first tag with highest used times.
	 * Returns the first tag if none of the tags are used.
	 * 
	 * This is to test if the function works normally when all the tags are used
	 * zero time.
	 */
	@Test
	public void testFindMostUsedTagWhenNoneUse() {

		TagManager.addTag(tag1);
		TagManager.addTag(tag2);
		TagManager.addTag(tag3);
		TagManager.addTag(tag4);
		TagManager.addTag(tag5);
		TagManager.addTag(tag6);

		/* set up expected */
		Tag expected = tag1;
		System.out.println(expected);
		/* set up actual */
		Tag actual = TagManager.findMostUsedTag();
		System.out.println(actual);

		assertEquals(
				"Find most used tag when none of the tags has been used fail",
				expected, actual);
	}

	/**
	 * Test get tag named name at index. This is to test if it returns the
	 * correct tag at given index.
	 */
	@Test
	public void testGetNameAtNormalIndex() {
		TagManager.addTag(tag1);
		TagManager.addTag(tag2);
		TagManager.addTag(tag3);
		TagManager.addTag(tag4);
		TagManager.addTag(tag5);
		TagManager.addTag(tag6);

		/* set up expected */
		Tag expected = tag1;
		/* set up actual */
		Tag actual = TagManager.getTagAtIndex(0);
		assertEquals("Find get name at normal index fail", expected, actual);
	}

	/**
	 * Test get tag named name at index. This is to test whether this method
	 * work as normal when the given index is out of bound.
	 */
	@Test
	public void testGetNameAtOutofBoundIndex() {
		TagManager.addTag(tag1);
		TagManager.addTag(tag2);
		TagManager.addTag(tag3);
		TagManager.addTag(tag4);
		TagManager.addTag(tag5);
		TagManager.addTag(tag6);

		/* set up expected */
		Tag expected = (Tag) null;
		/* set up actual */
		Tag actual = TagManager.getTagAtIndex(7);
		assertEquals("Find get name at a out-of-bound index fail", expected,
				actual);
	}

	/**
	 * This is a helper function to check if two ArrayList of Tags are equal.
	 * Return true if they are null.
	 */
	private void assertArrayEquals(String message, ArrayList<Tag> expected,
			ArrayList<Tag> actual) {
		boolean flag = true;
		if (expected.size() == actual.size()) {
			for (int i = 0; i < expected.size(); i++) {
				Tag t1 = expected.get(i);
				Tag t2 = actual.get(i);
				if (!t1.equals(t2)) {
					flag = false;
				}
			}
		} else {
			flag = false;
		}
		if (flag) {
			return;
		} else {
			fail(message);
		}

	}

	/**
	 * Teardown the objects used in the tests.
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		fn2.deleteAllTags(true, true);
		fn3.deleteAllTags(true, true);
		tag1 = null;
		tag2 = null;
		tag3 = null;
		tag4 = null;
		tag5 = null;
		tag6 = null;
		TagManager.tagList.clear();

		FileManager.fileList.clear();
		;
	}

}
