package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.rosehulman.rhitter.tasks.DeleteSnippetTask;

public class DeleteSnippetTests {

	@Test
	public void testConnectionSetup() {
		DeleteSnippetTask task = new DeleteSnippetTask(null, -1);
		
		task.run();
	}

}
