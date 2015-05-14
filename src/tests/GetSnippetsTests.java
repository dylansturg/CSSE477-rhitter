package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.rosehulman.rhitter.tasks.GetSnippetTask;

public class GetSnippetsTests {

	@Test
	public void testGetAllSnippets() {
		GetSnippetTask task = new GetSnippetTask(null, null);
		task.run();
	}

}
