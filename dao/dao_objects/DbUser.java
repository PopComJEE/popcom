package dao_objects;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DbUser extends DbObject{

	public static final String TABLE_USERS = "users";
	public static final String ID = "id";
	public static final String LOGIN = "login";
	public static final String PASSWORD = "password";
	public static final String BIRTHDAY = "birthday";
	public static final String EMAIL = "email";
	public static final String LAST_NAME = "last_name";
	public static final String FIRST_NAME = "first_name";
	public static final String STATUS = "status";
	public static final String MESSAGE = "message";
	public static final String HTML_TOKEN = "html_token";
	public static final String IS_ADMIN = "is_admin";

	private String mId;
	private String mLogin;
	private String mPassword;
	private String mBirthday;
	private String mEmail;
	private String mLastName;
	private String mFirstName;
	private String mStatus;
	private String mMessage;
	private String mToken;
	private boolean mIsAdmin;
	
	@Override
	public String toString() {
		return ID+" : "+mId
				+"\n"+LAST_NAME+" : "+mLastName
				+"\n"+FIRST_NAME+" : "+mFirstName
				+"\n"+BIRTHDAY+" : "+mBirthday
				+"\n"+EMAIL+" : "+mEmail
				+"\n"+LOGIN+" : "+mLogin
				+"\n"+PASSWORD+" : "+mPassword
				+"\n"+STATUS+" : "+mStatus
				+"\n"+MESSAGE+" : "+mMessage
				+"\n"+IS_ADMIN+" : "+mIsAdmin;
	}

	public DbUser (ResultSet rs){
		try {
			mId = rs.getString(ID);
			mLogin = rs.getString(LOGIN);
			mPassword = rs.getString(PASSWORD);
			mBirthday = rs.getString(BIRTHDAY);
			mEmail = rs.getString(EMAIL);
			mLastName = rs.getString(LAST_NAME);
			mFirstName = rs.getString(FIRST_NAME);
			mStatus = rs.getString(STATUS);
			mMessage = rs.getString(MESSAGE);
			mToken = rs.getString(HTML_TOKEN);
			mIsAdmin = rs.getBoolean(IS_ADMIN);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public DbUser(String id, String login, String password, String birthday,
			String email, String lastName, String firstName, String status,
			String message, String token, boolean isAdmin) {
		mId = id;
		mLogin = login;
		mPassword = password;
		mBirthday = birthday;
		mEmail = email;
		mLastName = lastName;
		mFirstName = firstName;
		mStatus = status;
		mMessage = message;
		mToken = token;
		mIsAdmin = isAdmin;
	}

	public DbUser(String login, String password, String birthday,
			String email, String lastName, String firstName) {
		mLogin = login;
		mPassword = password;
		mBirthday = birthday;
		mEmail = email;
		mLastName = lastName;
		mFirstName = firstName;
	}

	@Override
	public String getId() {
		return mId;
	}
	
	@Override
	public void setId(String id) {
		mId = id;
	}

	public String getLastName() {
		return mLastName;
	}

	public void setLastName(String nom) {
		mLastName = nom;
	}

	public String getFirstName() {
		return mFirstName;
	}

	public void setFirstName(String prenom) {
		mFirstName = prenom;
	}

	public String getBirthday() {
		return mBirthday;
	}

	public void setBirthday(String date) {
		mBirthday = date;
	}

	public String getEmail() {
		return mEmail;
	}

	public void setEmail(String mail) {
		mEmail = mail;
	}

	public String getLogin() {
		return mLogin;
	}

	public void setLogin(String login) {
		mLogin = login;
	}

	public String getPassword() {
		return mPassword;
	}

	public void setPassword(String password) {
		mPassword = password;
	}

	public String getStatus() {
		return mStatus;
	}

	public void setStatus(String status) {
		mStatus = status;
	}

	public String getMessage() {
		return mMessage;
	}

	public void setMessage(String msg_perso) {
		mMessage = msg_perso;
	}
	
	public String getToken() {
		return mToken;
	}
	
	public void setToken(String token) {
		mToken = token;
	}
	
	public boolean isAdmin() {
		return mIsAdmin;
	}
	
	public void setIsAdmin(boolean isAdmin) {
		mIsAdmin = isAdmin;
	}
	
}
