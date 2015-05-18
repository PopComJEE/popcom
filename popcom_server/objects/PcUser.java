package objects;

import java.util.ArrayList;

import dao_objects.DbFriend;
import dao_objects.DbSession;
import dao_objects.DbUser;


public class PcUser {

	private DbUser mUser;
	private ArrayList<PcSession> mSessionList;
	private ArrayList<PcUser> mFriendList;

	public PcUser(DbUser db_user){
		mUser=db_user;
		mSessionList = new ArrayList<PcSession>();
		mFriendList = new ArrayList<PcUser>();
	}

	public PcUser(DbUser db_user, ArrayList<PcSession> sessionList, ArrayList<PcUser> friendList){
		mUser=db_user;
		if(sessionList==null)
			mSessionList = new ArrayList<PcSession>();
		else
			mSessionList = sessionList;
		if(friendList==null)
			mFriendList = new ArrayList<PcUser>();
		else
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

	public ArrayList<PcUser> getFriendList() {
		return mFriendList;
	}

	public void setFriendList(ArrayList<PcUser> friendList) {
		mFriendList = friendList;
	}


}
