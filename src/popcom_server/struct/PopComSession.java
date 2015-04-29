package popcom_server.struct;

import java.util.ArrayList;

public class PopComSession {

//	private int mSessionId;
	private ArrayList<PopComUser> mUserConnectedList;
	
	public PopComSession(){
		mUserConnectedList = new ArrayList<PopComUser>();
	}
	
	public PopComSession(PopComUser user){
		mUserConnectedList = new ArrayList<PopComUser>();
		mUserConnectedList.add(user);
	}

	public PopComSession(ArrayList<PopComUser> userConnectedList) {
		mUserConnectedList = userConnectedList;
	}
	
	
	public ArrayList<PopComUser> getUserConnectedList() {
		return mUserConnectedList;
	}
	
	
	public void setUserConnectedList(ArrayList<PopComUser> userConnectedList) {
		mUserConnectedList = userConnectedList;
	}
	
	public void addUser(PopComUser popComUser){
		if(mUserConnectedList!=null){
			mUserConnectedList.add(popComUser);
		}
	}
	
	public void removeUser(PopComUser user){
		if(mUserConnectedList!=null && mUserConnectedList.contains(user)){
			mUserConnectedList.remove(user);
		}
	}
	
//	public PopComSession(int sessionId){
//		mSessionId = sessionId;
//		mUserConnectedList = new ArrayList<PopComUser>();
//	}
//	
//	public PopComSession(PopComUser user, int sessionId){
//		mSessionId = sessionId;
//		mUserConnectedList = new ArrayList<PopComUser>();
//		mUserConnectedList.add(user);
//	}
//
//	public PopComSession(ArrayList<PopComUser> userConnectedList, int sessionId) {
//		mUserConnectedList = userConnectedList;
//		mSessionId = sessionId;
//	}
//	
//	public int getSessionId() {
//		return mSessionId;
//	}
//	
//	public ArrayList<PopComUser> getUserConnectedList() {
//		return mUserConnectedList;
//	}
//	
//	public void setSessionId(int sessionId) {
//		mSessionId = sessionId;
//	}
//	
//	public void setUserConnectedList(ArrayList<PopComUser> userConnectedList) {
//		mUserConnectedList = userConnectedList;
//	}
//	
//	public void addUser(PopComUser user){
//		if(mUserConnectedList!=null){
//			mUserConnectedList.add(user);
//		}
//	}
//	
//	public void removeUser(PopComUser user){
//		if(mUserConnectedList!=null && mUserConnectedList.contains(user)){
//			mUserConnectedList.remove(user);
//		}
//	}

	
	
	
}
