package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import objects.PcSession;
import objects.PcUser;
import dao.Dao;
import dao.Dao_Session;
import dao.Dao_User;
import dao_objects.DbObject;
import dao_objects.DbSession;
import dao_objects.DbUser;

public class SessionController {

	private Dao mDao;

	public SessionController(){
		mDao = new Dao_Session();
	}

	public boolean joinSession(PcUser user, String sessionId){
		DbSession session = new DbSession(user.getUser(), sessionId);
		try {
			System.out.println(mDao.add(session));
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean leaveSession(PcUser user, String sessionId){
		DbSession session = new DbSession(user.getUser(), sessionId);
		try {
			System.out.println(mDao.remove(session));
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public ArrayList<PcSession> getUserSessions(String userId){
		ArrayList<String> sessionIdList;
		ArrayList<PcSession> sessionList = new ArrayList<PcSession>();
		try {
			sessionIdList = (((Dao_Session) mDao).getUserSessionIdList((userId)));
			for(String sessionId : sessionIdList){
				sessionList.add(getSession(sessionId));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return sessionList;
	}

	public PcSession getSession(String sessionId){
		ArrayList<DbObject> session;
		ArrayList<DbUser> userList = new ArrayList<DbUser>();
		Dao_User daoUser = new Dao_User();
		try {
			System.out.println(session = mDao.search(sessionId));
			for(DbObject dbSession : session){
				DbUser user;
				user = (DbUser) daoUser.get(((DbSession) dbSession).getUserId());
				userList.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return new PcSession(sessionId, userList);
	}

}
