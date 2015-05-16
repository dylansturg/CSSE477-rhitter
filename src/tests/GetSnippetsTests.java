package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.rosehulman.rhitter.tasks.GetAllSnippetTask;
import edu.rosehulman.rhitter.tasks.GetSpecificSnippetTask;

public class GetSnippetsTests {

	@Test
	public void testGetAllSnippets() {
		GetAllSnippetTask task = new GetAllSnippetTask(null, null);
		task.run();
	}
	
	@Test
	public void testGetSpecificSnippets() {
		GetSpecificSnippetTask task = new GetSpecificSnippetTask(null, null, 1);
		task.run();
	}

}
