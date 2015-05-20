package dao_objects;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DbFriend extends DbObject {

	public static final String TABLE_FRIENDS = "friends";
	public static final String ID = "id";
	public static final String USER_ID = "id_friend_1";
	public static final String FRIEND_ID = "id_friend_2";

	private String mId;
	private String mUserId;
	private String mFriendId;

	@Override
	public String toString() {
		return ID+" : "+mId
				+"\n"+USER_ID+" : " + mUserId
				+"\n"+FRIEND_ID+" : " + mFriendId;
	}
	
	public DbFriend (String userId, String friendId){
		mFriendId = friendId;
		mUserId = userId;
	}
	
	public DbFriend (DbUser user, String friendId){
		mFriendId = friendId;
		mUserId = user.getId();
	}

	public DbFriend (ResultSet rs){
		try {
			mId = rs.getString(ID);
			mUserId = rs.getString(USER_ID);
			mFriendId = rs.getString(FRIEND_ID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//	public DbFriend (int friend_id_1, int friend_id_2){
	//		mUserId = friend_id_1;
	//		mFriendId = friend_id_2;
	//	}

	@Override
	public String getId() {
		return mId;
	}

	@Override
	public void setId(String id) {
		mId = id;
	}

	public String getUserId() {
		return mUserId;
	}

	public void setUserId(String friendId1) {
		mUserId = friendId1;
	}

	public String getFriendId() {
		return mFriendId;
	}

	public void setFriendId(String friendId2) {
		mFriendId = friendId2;
	}

}
