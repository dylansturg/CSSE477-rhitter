package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.rosehulman.rhitter.tasks.GetAllSnippetTask;
import edu.rosehulman.rhitter.tasks.GetSpecificSnippetTask;
import edu.rosehulman.rhitter.tasks.PutSnippetTask;

public class PutSnippetsTests {

	@Test
	public void testPutSnippet() {
		PutSnippetTask task = new PutSnippetTask(null, null, "", 1, "updated text");
		task.run();
	}

}
