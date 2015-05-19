package objects;

import java.util.ArrayList;

import javax.json.JsonArray;
import javax.json.JsonObject;

import dao_objects.DbUser;

public class PcSession {

	private ArrayList<DbUser> mUserList;
	private String mSessionId;
	
	private JsonArray mHistory;
	private JsonObject mLastMessage;
	
	public PcSession(String sessionId, ArrayList<DbUser> userList){
		mSessionId = sessionId;
		mUserList = userList;
	}
	
	public PcSession(String sessionId, ArrayList<DbUser> userList, JsonArray history, JsonObject lastMessage){
		mSessionId = sessionId;
		mUserList = userList;
		mHistory = history;
		mLastMessage = lastMessage;
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

	public JsonArray getHistory() {
		return mHistory;
	}

	public void setHistory(JsonArray history) {
		mHistory = history;
	}

	public JsonObject getLastMessage() {
		return mLastMessage;
	}

	public void setLastMessage(JsonObject lastMessage) {
		mLastMessage = lastMessage;
	}
}
