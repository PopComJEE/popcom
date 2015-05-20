package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dao_objects.DbFriend;
import dao_objects.DbObject;
import dao_objects.DbUser;

public class Dao_Friend extends Dao {

	@Override
	public ArrayList<DbObject> getAll(DbObject dbObject) throws SQLException {
		Statement stmt = null;
		ResultSet rset = null;
		ArrayList<DbObject> al = new ArrayList<DbObject>();
		DbFriend f;
		try {
			connect();
			stmt = mConnection.createStatement();
			rset = stmt.executeQuery(
					"SELECT * FROM friend"
					);
			while(rset.next()) {
				f = new DbFriend(rset);
				al.add(f);
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
		return al;
	}

	@Override
	public DbObject get(String id) throws SQLException {
		DbFriend friend;
		Statement stmt = null;
		ResultSet rset = null;
		try {
			connect();
			stmt = mConnection.createStatement();
			rset = stmt.executeQuery(
					"SELECT * FROM "+DbFriend.TABLE_FRIENDS
					+" WHERE "
					+DbFriend.ID+" = '"+id+"'"
					);
			friend = new DbFriend(rset);
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
		DbFriend friend = (DbFriend) dbObject;
		PreparedStatement pstmt = null;
		try {
			connect();
			pstmt = mConnection.prepareStatement(
					"INSERT INTO "+DbFriend.TABLE_FRIENDS
					+" ("
					+DbFriend.USER_ID+", "
					+DbFriend.FRIEND_ID
					+") values ("
					+"'"+friend.getUserId()+"',"
					+"'"+friend.getFriendId()+"'"
					+")"
					);
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs != null && rs.first()) {
				// on récupère l'id généré
				String generatedId = rs.getString(DbUser.ID);
				friend.setId(generatedId);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			pstmt.close();
			return null;
		}
		pstmt.close();
		mConnection.close();
		return friend;
	}

	@Override
	public DbObject remove(DbObject dbObject) throws SQLException {
		DbFriend friend = (DbFriend) dbObject;
		PreparedStatement pstmt = null;
		try {
			connect();
			pstmt = mConnection.prepareStatement(
					"DELETE FROM "+DbFriend.TABLE_FRIENDS
					+" WHERE "
					+DbFriend.FRIEND_ID+" = '"+friend.getFriendId()+"'"
					+" AND "
					+DbFriend.USER_ID+" = '"+friend.getUserId()+"'"
					);
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			pstmt.close();
			return null;
		}
		pstmt.close();
		mConnection.close();
		return friend;
	}

	@Override
	public DbObject update(DbObject dbObject) throws SQLException {
		//Not used
		return null;
	}

	@Override
	public ArrayList<DbObject> search(String query) throws SQLException {
		Statement stmt = null;
		ResultSet rset = null;
		ArrayList<DbObject> friendList = new ArrayList<DbObject>();
		try {
			connect();
			stmt = mConnection.createStatement();
			rset = stmt.executeQuery(
					"SELECT * FROM "+DbFriend.TABLE_FRIENDS
					+" WHERE "
					+DbFriend.USER_ID+" = '"+query+"'"
					);
			while(rset.next()){
				friendList.add(new DbFriend(rset));
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
		return friendList;
	}

}