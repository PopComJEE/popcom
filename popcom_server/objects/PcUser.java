package objects;

import java.util.ArrayList;

import dao_objects.DbFriend;
import dao_objects.DbSession;
import dao_objects.DbUser;


public class PcUser {
	
	private DbUser mUser;
	private ArrayList<PcSession> mSessionList;
	private ArrayList<DbUser> mFriendList;
	
	public PcUser(DbUser db_user){
		mUser=db_user;
		mSessionList = new ArrayList<PcSession>();
		mFriendList = new ArrayList<DbUser>();
	}
	
	public PcUser(DbUser db_user, ArrayList<PcSession> sessionList, ArrayList<DbUser> friendList){
		mUser=db_user;
		mSessionList = sessionList;
		mFriendList = friendList;
	}

	public DbUser getUser() {
		return mUser;
	}

	public void setUser(DbUser user) {
		mUser = user;
	}

	public ArrayList<PcSession> getSessionList() {
		return mSessionList;
	}

	public void setSessionList(ArrayList<PcSession> sessionList) {
		mSessionList = sessionList;
	}

	public ArrayList<DbUser> getFriendList() {
		return mFriendList;
	}

	public void setFriendList(ArrayList<DbUser> friendList) {
		mFriendList = friendList;
	}
	

}
