package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dao_objects.DbFriend;
import dao_objects.DbMessage;
import dao_objects.DbObject;
import dao_objects.DbUser;

public class Dao_Message extends Dao {


	@Override
	public ArrayList<DbObject> getAll(DbObject dbObject) throws SQLException {
		//Not used
		return null;
	}

	@Override
	public DbObject get(String id) throws SQLException {
		DbMessage friend=null;
		Statement stmt = null;
		ResultSet rset = null;
		try {
			connect();
			stmt = mConnection.createStatement();
			rset = stmt.executeQuery(
					"SELECT * FROM "+DbMessage.TABLE_MESSAGES
					+" WHERE "
					+DbMessage.ID_SESSION+" = '"+id+"'"
					);
			if(rset.next())
				System.out.println("RESULT SET 1: "+rset.getString(2));
			System.out.println("RESULT SET 2: "+rset.getString(3));
			System.out.println("RESULT SET 3: "+rset.getString(4));
				friend = new DbMessage(rset);
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
		return friend;
	}

	@Override
	public DbObject add(DbObject dbObject) throws SQLException {
		DbMessage friend = (DbMessage) dbObject;
		PreparedStatement pstmt = null;
		try {
			connect();
			pstmt = mConnection.prepareStatement(
					"INSERT INTO "+DbMessage.TABLE_MESSAGES
					+" ("
					+DbMessage.ID_SESSION+", "
					+DbMessage.HISTORY+", "
					+DbMessage.LAST_MESSAGE
					+") values ("
					+"'"+friend.getSessionId()+"',"
					+"'"+friend.getHistory()+"',"
					+"'"+friend.getLastMessage()+"'"
					+")"
					);
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs != null && rs.first()) {
				// on récupère l'id généré
				String generatedId = rs.getString(DbUser.ID);
				friend.setId(generatedId);
			}
			System.out.println("messages added !");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.out.println("messages not added !");
			pstmt.close();
			return null;
		}
		pstmt.close();
		mConnection.close();
		return friend;
	}

	@Override
	public DbObject remove(DbObject dbObject) throws SQLException {
		DbMessage friend = (DbMessage) dbObject;
		PreparedStatement pstmt = null;
		try {
			connect();
			pstmt = mConnection.prepareStatement(
					"DELETE FROM "+DbMessage.TABLE_MESSAGES
					+" WHERE "
					+DbMessage.ID_SESSION+" = '"+friend.getSessionId()+"'"
					);
			pstmt.executeUpdate();
			System.out.println("messages suppressed !");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.out.println("messages suppression failed !");
			pstmt.close();
			return null;
		}
		pstmt.close();
		mConnection.close();
		return friend;
	}

	@Override
	public DbObject update(DbObject dbObject) throws SQLException {
		DbMessage user = (DbMessage) dbObject;
		PreparedStatement pstmt = null;
		try {
			connect();
			pstmt = mConnection.prepareStatement(
					"UPDATE "+DbMessage.TABLE_MESSAGES
					+" SET "
					+DbMessage.HISTORY+" = '"+user.getHistory()+"', "
					+DbMessage.LAST_MESSAGE+" = '"+user.getLastMessage()+"'"
					+" WHERE "
					+DbMessage.ID_SESSION+" = '"+user.getSessionId()+"'"
					);
			pstmt.executeUpdate();
			System.out.println("messages modified !");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.out.println("messages not modified !");
			pstmt.close();
			return null;
		}
		pstmt.close();
		mConnection.close();
		return user;
	}

	@Override
	public ArrayList<DbObject> search(String query) throws SQLException {
		//Not used
		return null;
	}


}