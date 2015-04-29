package popcom_server.struct;

import java.util.ArrayList;

public class PopComUser {

	private String mLogin;
	private String mPassword;
	//	private int mUserId;
	private ArrayList<PopComSession> mUserSessions;

	public PopComUser(String login, String password) {
	mLogin = login;
	mPassword = password;
}

	public ArrayList<PopComSession> getUserSessions() {
		return mUserSessions;
	}

	public void setUserSessions(ArrayList<PopComSession> userSessions) {
		mUserSessions = userSessions;
	}

	public void addUserSession(PopComSession session) {
		mUserSessions.add(session);
	}


	public String getLogin() {
		return mLogin;
	}

	public String getPassword() {
		return mPassword;
	}

	public void setLogin(String login) {
		mLogin = login;
	}

	public void setPassword(String password) {
		mPassword = password;
	}

	//	public PopComUser(String login, String password, int userId) {
	//		mLogin = login;
	//		mPassword = password;
	//	}
	//	public PopComUser(String login, String password, int userId) {
	//		mLogin = login;
	//		mPassword = password;
	//		mUserId = userId;
	//	}
	//	
	//	public int getUserId() {
	//		return mUserId;
	//	}
	//	
	//	public void setUserId(int userId) {
	//		mUserId = userId;
	//	}




}
