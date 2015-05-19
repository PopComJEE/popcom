package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import objects.PcSession;
import objects.PcUser;
import dao.Dao;
import dao.Dao_Friend;
import dao.Dao_Session;
import dao.Dao_User;
import dao_objects.DbFriend;
import dao_objects.DbObject;
import dao_objects.DbSession;
import dao_objects.DbUser;

public class FriendController {

	private Dao mDao;

	public FriendController(){
		mDao = new Dao_Friend();
	}

	public boolean addFriend(PcUser user, String friendId){
		DbFriend friendship = new DbFriend(user.getUser(), friendId);
		try {
			mDao.add(friendship);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean removeFriend(PcUser user, String friendId){
		DbFriend friendship = new DbFriend(user.getUser(), friendId);
		try {
			mDao.remove(friendship);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public ArrayList<PcUser> getUserFriendList(String userId){
		ArrayList<DbObject> dbFriendList;
		ArrayList<PcUser> pcFriendList = new ArrayList<PcUser>();
		Dao_User daoUser = new Dao_User();
		try {
			dbFriendList = (((Dao_Friend) mDao).search((userId)));
			for(DbObject friendship : dbFriendList){
				PcUser friend = new PcUser((DbUser) daoUser.get(((DbFriend) friendship).getFriendId()));
				pcFriendList.add(friend);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return pcFriendList;
	}
}
