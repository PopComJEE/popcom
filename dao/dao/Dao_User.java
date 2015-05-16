package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dao_objects.DbObject;
import dao_objects.DbUser;

public class Dao_User extends Dao{

	@Override
	public ArrayList<DbObject> getAll(DbObject dbObject) throws SQLException {
		DbUser user = (DbUser) dbObject;
		Statement stmt = null;
		ResultSet rset = null;
		ArrayList<DbObject> userList = new ArrayList<DbObject>();
		try {
			connect();
			stmt = mConnection.createStatement();
			rset = stmt.executeQuery(
					"SELECT * FROM "+DbUser.TABLE_USERS
					);
			while(rset.next()) {
				DbUser u = new DbUser(rset);
				userList.add(u);
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
		return userList;
	}

	@Override
	public DbObject get(String id) throws SQLException {
		Statement stmt = null;
		ResultSet rset = null;
		DbUser dbUser;
		try {
			connect();
			stmt = mConnection.createStatement();
			rset = stmt.executeQuery(
					"SELECT * FROM "+DbUser.TABLE_USERS
					+" WHERE "
					+DbUser.ID+" = '"+id+"'"
					);
			dbUser=new DbUser(rset);
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
		return dbUser;
	}

	public DbObject getByLogin(String login) throws SQLException {
		Statement stmt = null;
		ResultSet rset = null;
		DbUser dbUser;
		try {
			connect();
			stmt = mConnection.createStatement();
			rset = stmt.executeQuery(
					"SELECT * FROM "+DbUser.TABLE_USERS
					+" WHERE "
					+DbUser.LOGIN+" = '"+login+"'"
					);
			if(rset.next())
				dbUser = new DbUser(rset);
			else dbUser = null;
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
		return dbUser;
	}

	@Override
	public DbObject add(DbObject dbObject) throws SQLException {
		DbUser user = (DbUser) dbObject;
		PreparedStatement pstmt = null;
		try {
			connect();
			System.out.println("INSERT INTO "+DbUser.TABLE_USERS
					+" ("
//					+DbUser.ID+", "
					+DbUser.LAST_NAME+", "
					+DbUser.FIRST_NAME+", "
					+DbUser.BIRTHDAY+", "
					+DbUser.EMAIL+", "
					+DbUser.LOGIN+", "
					+DbUser.PASSWORD+", "
					+DbUser.STATUS+", "
					+DbUser.MESSAGE+", "
					+DbUser.HTML_TOKEN+", "
					+DbUser.IS_ADMIN
					+") values ("
//					+"'null',"
					+"'"+user.getLastName()+"',"
					+"'"+user.getFirstName()+"',"
					+"'"+user.getBirthday()+"',"
					+"'"+user.getEmail()+"',"
					+"'"+user.getLogin()+"',"
					+"'"+user.getPassword()+"',"
					+"'"+user.getStatus()+"',"
					+"'"+user.getMessage()+"',"
					+"'"+user.getToken()+"',"
					+"'"+user.isAdmin()+"'"
					+")");
			pstmt = mConnection.prepareStatement(
					"INSERT INTO "+DbUser.TABLE_USERS
					+" ("
//					+DbUser.ID+", "
					+DbUser.LAST_NAME+", "
					+DbUser.FIRST_NAME+", "
					+DbUser.BIRTHDAY+", "
					+DbUser.EMAIL+", "
					+DbUser.LOGIN+", "
					+DbUser.PASSWORD+", "
					+DbUser.STATUS+", "
					+DbUser.MESSAGE+", "
					+DbUser.HTML_TOKEN+", "
					+DbUser.IS_ADMIN
					+") values ("
//					+"'null',"
					+"'"+user.getLastName()+"',"
					+"'"+user.getFirstName()+"',"
					+"'"+user.getBirthday()+"',"
					+"'"+user.getEmail()+"',"
					+"'"+user.getLogin()+"',"
					+"'"+user.getPassword()+"',"
					+"'"+user.getStatus()+"',"
					+"'"+user.getMessage()+"',"
					+"'"+user.getToken()+"',"
					+"'"+user.isAdmin()+"'"
					+")"
					);
			pstmt.executeUpdate();
			System.out.println("DbUser added !");
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs != null && rs.first()) {
				// on récupère l'id généré
				String generatedId = rs.getString(DbUser.ID);
				user.setId(generatedId);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.out.println("DbUser not added !");
			pstmt.close();
			return null;
		}
		pstmt.close();
		mConnection.close();
		return user;
	}

	@Override
	public DbObject remove(DbObject dbObject) throws SQLException {
		DbUser user = (DbUser) dbObject;
		PreparedStatement pstmt = null;
		try {
			connect();
			pstmt = mConnection.prepareStatement(
					"DELETE FROM "+DbUser.TABLE_USERS
					+" WHERE "
					+DbUser.ID+" = '"+user.getId()+"'"
					);
			pstmt.executeUpdate();
			System.out.println("DbUser deleted !");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.out.println("DbUser not deleted !");
			pstmt.close();
			return null;
		}
		pstmt.close();
		mConnection.close();
		return user;
	}

	@Override
	public DbObject update(DbObject dbObject) throws SQLException {
		DbUser user = (DbUser) dbObject;
		PreparedStatement pstmt = null;
		try {
			connect();
			pstmt = mConnection.prepareStatement(
					"UPDATE "+DbUser.TABLE_USERS
					+" SET "
					+DbUser.LAST_NAME+" = '"+user.getLastName()+"', "
					+DbUser.FIRST_NAME+" = '"+user.getFirstName()+"', "
					+DbUser.BIRTHDAY+" = '"+user.getBirthday()+"', "
					+DbUser.EMAIL+" = '"+user.getEmail()+"', "
					+DbUser.LOGIN+" = '"+user.getLogin()+"', "
					+DbUser.PASSWORD+" = '"+user.getPassword()+"',"
					+DbUser.STATUS+" = '"+user.getStatus()+"', "
					+DbUser.MESSAGE+" = '"+user.getMessage()+"', "
					+DbUser.HTML_TOKEN+" = '"+user.getToken()+"', "
					+DbUser.IS_ADMIN+" = '"+user.isAdmin()+"'"
					+" WHERE "
					+DbUser.ID+" = '"+user.getId()+"'"
					);
			pstmt.executeUpdate();
			System.out.println("user modified !");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.out.println("user not modified !");
			pstmt.close();
			return null;
		}
		pstmt.close();
		mConnection.close();
		return user;
	}

	@Override
	public ArrayList<DbObject> search(String query) throws SQLException {
		Statement stmt = null;
		ResultSet rset = null;
		ArrayList<DbObject> userList = new ArrayList<DbObject>();
		try {
			connect();
			stmt = mConnection.createStatement();
			rset = stmt.executeQuery(
					"SELECT * FROM "+DbUser.TABLE_USERS
					+" WHERE "
					+DbUser.EMAIL+" = '"+query+"'"
					+" OR "
					+DbUser.FIRST_NAME+" = '"+query+"'"
					+" OR "
					+DbUser.LAST_NAME+" = '"+query+"'"
					+" OR "
					+DbUser.LOGIN+" = '"+query+"'"
					);
			while(rset.next()){
				userList.add(new DbUser(rset));
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
		return userList;
	}

}