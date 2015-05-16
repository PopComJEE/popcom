package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import objects.PcUser;
import dao.Dao;
import dao.Dao_User;
import dao_objects.DbObject;
import dao_objects.DbUser;

public class UserController {

	private Dao mDao;

	public UserController(){
		mDao = new Dao_User();
	}

	public boolean addUser (PcUser user){
		try {
			System.out.println(mDao.add(user.getUser()));
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean deleteUser(PcUser user){
		try {
			System.out.println(mDao.remove(user.getUser()));
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean updateUser(PcUser user){
		try {
			System.out.println(mDao.update(user.getUser()));
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public PcUser getUser(String userId){
		DbUser dbUser;
		try {
			System.out.println(dbUser = (DbUser) mDao.get(userId));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return new PcUser(dbUser);
	}

	public PcUser getUserByLogin(String login){
		DbUser dbUser;
		try {
			System.out.println(dbUser = (DbUser) ((Dao_User) mDao).getByLogin(login));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return new PcUser(dbUser);
	}

	public ArrayList<PcUser> searchUser(String query){
		ArrayList<DbObject> dbUserList;
		ArrayList<PcUser> pcUserList = new ArrayList<PcUser>();
		try {
			System.out.println(dbUserList = mDao.search(query));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		for(DbObject dbUser : dbUserList){
			pcUserList.add(new PcUser((DbUser) dbUser));
		}
		return pcUserList;
	}


}
