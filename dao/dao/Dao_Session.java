package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dao_objects.DbObject;
import dao_objects.DbSession;
import dao_objects.DbUser;

public class Dao_Session extends Dao {

	@Override
	public ArrayList<DbObject> getAll(DbObject dbObject) throws SQLException {
		Statement stmt = null;
		ResultSet rset = null;
		ArrayList<DbObject> sessionList = new ArrayList<DbObject>();
		try {
			connect();
			stmt = mConnection.createStatement();
			rset = stmt.executeQuery(
					"SELECT * FROM "+DbSession.TABLE_SESSIONS
					);
			while(rset.next()) {
				DbSession s = new DbSession(rset);
				sessionList.add(s);
				mConnection.close();
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			rset.close();
			stmt.close();
			mConnection.close();
			return null;
		}
		rset.close();
		stmt.close();
		return sessionList;
	}

	@Override
	public DbObject get(String id) throws SQLException {
		Statement stmt = null;
		ResultSet rset = null;
		ArrayList<DbSession> sessionList =  new ArrayList<DbSession>();
		DbSession session = null;
		try {
			connect();
			stmt = mConnection.createStatement();
			rset = stmt.executeQuery(
					"SELECT * FROM "+DbSession.TABLE_SESSIONS
					+" WHERE "
					+DbSession.ID+" = '"+id+"'"
					);
			if(rset.next())
				session = new DbSession(rset);
			mConnection.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			rset.close();
			stmt.close();
			mConnection.close();
			return null;
		}
		rset.close();
		stmt.close();
		return session;
	}

	@Override
	public DbObject add(DbObject dbObject) throws SQLException {
		DbSession session = (DbSession) dbObject;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String updated_at = dateFormat.format(date);
		PreparedStatement pstmt = null;
		try {
			connect();
			pstmt = mConnection.prepareStatement(
					"INSERT INTO "+DbSession.TABLE_SESSIONS
					+"("
					+DbSession.ID_SESSION+", "
					+DbSession.ID_USER+", "
					+DbSession.UPDATED_AT
					+") values ("
					+"'"+session.getSessionId()+"',"
					+"'"+session.getUserId()+"',"
					+"'"+updated_at +"'"
					+")"
					);
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs != null && rs.first()) {
				// on récupère l'id généré
				String generatedId = rs.getString(DbUser.ID);
				session.setId(generatedId);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			pstmt.close();
			return null;
		}
		pstmt.close();
		mConnection.close();
		return session;
	}

	@Override
	public DbObject remove(DbObject dbObject) throws SQLException {
		DbSession session = (DbSession) dbObject;
		PreparedStatement pstmt = null;
		try {
			connect();
			pstmt = mConnection.prepareStatement(
					"DELETE FROM "+DbSession.TABLE_SESSIONS
					+" WHERE "
					+DbSession.ID_SESSION+" = '"+ session.getSessionId()+"'"
					);
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			pstmt.close();
			return null;
		}
		pstmt.close();
		mConnection.close();
		return session;
	}

	@Override
	public DbObject update(DbObject dbObject) throws SQLException {
		//Not used
		return null;
	}

	@Override
	public ArrayList<DbObject> search(String sessionId) throws SQLException {
		Statement stmt = null;
		ResultSet rset = null;
		ArrayList<DbObject> sessionList =  new ArrayList<DbObject>();
		try {
			connect();
			stmt = mConnection.createStatement();
			rset = stmt.executeQuery(
					"SELECT * FROM "+DbSession.TABLE_SESSIONS
					+" WHERE "
					+DbSession.ID_SESSION+" = '"+sessionId+"'"
					);
			while(rset.next()){
				DbSession dbSession = new DbSession(rset);
				sessionList.add(dbSession);
			}
			mConnection.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			rset.close();
			stmt.close();
			mConnection.close();
			return null;
		}
		rset.close();
		stmt.close();
		return sessionList;
	}

	public ArrayList<String> getUserSessionIdList(String userId) throws SQLException {
		Statement stmt = null;
		ResultSet rset = null;
		ArrayList<String> sessionIdList =  new ArrayList<String>();
		try {
			connect();
			stmt = mConnection.createStatement();
			rset = stmt.executeQuery(
					"SELECT DISTINCT "+DbSession.ID_SESSION+" FROM "+DbSession.TABLE_SESSIONS
					+" WHERE "
					+DbSession.ID_USER+" = '"+userId+"'"
					);
			while(rset.next()){
				sessionIdList.add(rset.getString(DbSession.ID_SESSION));
			}
			mConnection.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			rset.close();
			stmt.close();
			mConnection.close();
			return null;
		}
		rset.close();
		stmt.close();
		return sessionIdList;
	}




}