package objects;

import java.util.ArrayList;

import dao_objects.DbUser;

public class PcSession {

	private ArrayList<DbUser> mUserList;
	private String mSessionId;
	
	public PcSession(String sessionId, ArrayList<DbUser> userList){
		mSessionId = sessionId;
		mUserList = userList;
	}

	public ArrayList<DbUser> getUserList() {
		return mUserList;
	}

	public void setUserList(ArrayList<DbUser> userList) {
		mUserList = userList;
	}

	public String getSessionId() {
		return mSessionId;
	}

	public void setSessionId(String sessionId) {
		mSessionId = sessionId;
	}
}
