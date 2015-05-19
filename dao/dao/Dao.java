package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import dao_objects.DbObject;

public abstract class Dao {
	
	private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DATABASE_ADRESS = "jdbc:mysql://localhost:3306/popcom";
	private static final String DATABASE_LOGIN = "root";
	private static final String DATABASE_PASSWORD = "";
	Connection mConnection;
	
	public Dao(){
		mConnection = null;
	}
	
	
	public boolean connect(){
		try {
			Class.forName(DATABASE_DRIVER).newInstance();
			DriverManager.setLoginTimeout(10);
//			System.out.println("Connecting to database...");
			mConnection = DriverManager.getConnection(DATABASE_ADRESS, DATABASE_LOGIN, DATABASE_PASSWORD);
//			System.out.println("Connection Success");
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (InstantiationException e) {
			e.printStackTrace();
			return false;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public abstract ArrayList<DbObject> getAll(DbObject dbObject) throws SQLException;
	public abstract DbObject get(String id) throws SQLException;
	public abstract DbObject add(DbObject dbObject) throws SQLException;
	public abstract DbObject remove(DbObject dbObject) throws SQLException;
	public abstract DbObject update(DbObject dbObject) throws SQLException;
	public abstract ArrayList<DbObject> search(String query) throws SQLException;

}