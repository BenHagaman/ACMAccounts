package vending;

import accounts.User;

/**
 * Class containing information about a running session
 *
 * Sessions are unique to users
 */
public class UserSession {

	private User user;
	private static UserSession currentSession;

    /**
     * Private constructor (only one session allowed at a time)
     *
     * Creates a session for the user
     *
     * @param user
     *          user to create the session for
     */
	private UserSession(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}

    /**
     * creates a session for the given user
     *
     * @param user
     *          user to initialize the session for
     *
     * @return
     *          true if the initialization succeeded
     *          false if there is already a running session
     */
	public static boolean startUserSession(User user) {
		if(getCurrentSession() == null) {
			currentSession = new UserSession(user);
			return true;
		}
		return false;
	}

    /**
     * returns the current running session
     *
     * @return
     *     the current session
     */
	public static UserSession getCurrentSession() {
		return currentSession;
	}

    /**
     * ends the current session and removes any identifiable information
     * from all aspects of the session
     */
	public void destroy() {
		user.destroy();
		currentSession = null;
		System.gc();
	}
	
}
