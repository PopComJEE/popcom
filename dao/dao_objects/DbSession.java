package dao_objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DbSession extends DbObject{

	public static final String TABLE_SESSIONS = "sessions";
	public static final String ID = "id";
	public static final String ID_SESSION = "id_session";
	public static final String ID_USER = "id_user";
	public static final String UPDATED_AT = "updated_at";
	
	private String mId;
	private String mSessionId;
	private String mUserId;
	private String mUpdatedAt;
	
	@Override
	public String toString() {
		return ID+" : "+mId
				+"\n"+ID_SESSION+" : " + mSessionId
				+"\n"+ID_USER+" : " + mUserId
				+"\n"+UPDATED_AT+" : " + mUpdatedAt;
	}

	public DbSession (DbUser user, String sessionId){
		mSessionId = sessionId;
		mUserId = user.getId();
	}
	
	public DbSession (ResultSet rs){
		try {
			mId = rs.getString(ID);
			mSessionId = rs.getString(ID_SESSION);
			mUpdatedAt = rs.getString(UPDATED_AT);
			mUserId = rs.getString(ID_USER);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//	public DbSession (String login, String date_s, ArrayList<Integer> usersList) {
//		mUpdatedAt = date_s;
//	}

	@Override
	public String getId() {
		return mId;
	}

	@Override
	public void setId(String id) {
		mId = id;
	}

	public String getSessionId() {
		return mSessionId;
	}

	public void setSessionId(String id_session) {
		mSessionId = id_session;
	}

	public String getUpdatedAt() {
		return mUpdatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		mUpdatedAt = updatedAt;
	}

	public String getUserId() {
		return mUserId;
	}

	public void setUsersId(String userId) {
		mUserId = userId;
	}
}
