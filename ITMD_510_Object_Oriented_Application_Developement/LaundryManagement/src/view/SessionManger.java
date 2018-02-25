package view;

import model.User;

/**
 * 
 * @author Shrija chavan
 *Session management class.
 */
public class SessionManger {
	
	private static User user;

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		SessionManger.user = user;
	}
	
	
	

}
