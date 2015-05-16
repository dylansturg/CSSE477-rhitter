package edu.rosehulman.rhitter.viewmodels;

import java.util.Date;

import edu.rosehulman.rhitter.models.Snippet;
import edu.rosehulman.rhitter.models.User;

public class SnippetViewModel {
	int id;
	String text;
	Date timestamp;
	UserViewModel publisher;

	public SnippetViewModel() {
	}

	public SnippetViewModel(Snippet snippet, User publisher) {
		this.id = snippet.getId();
		this.text = snippet.getText();
		this.timestamp = snippet.getTimestamp();

		this.publisher = new UserViewModel(publisher);

	}

	public SnippetViewModel(int id, String text, Date timestamp,
			UserViewModel publisher) {
		this.id = id;
		this.text = text;
		this.timestamp = timestamp;
		this.publisher = publisher;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp
	 *            the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the publisher
	 */
	public UserViewModel getPublisher() {
		return publisher;
	}

	/**
	 * @param publisher
	 *            the publisher to set
	 */
	public void setPublisher(UserViewModel publisher) {
		this.publisher = publisher;
	}

}
