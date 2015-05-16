package edu.rosehulman.rhitter.viewmodels;

import edu.rosehulman.rhitter.models.User;

public class UserViewModel {
	int id;
	String username;

	public UserViewModel() {

	}

	public UserViewModel(User model) {
		this.id = model.getId();
		this.username = model.getUsername();
	}

	public UserViewModel(int id, String username) {
		this.id = id;
		this.username = username;
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
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

}
