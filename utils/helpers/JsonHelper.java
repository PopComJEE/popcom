package helpers;

import java.io.StringReader;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

import objects.PcSession;
import objects.PcUser;
import dao_objects.DbFriend;
import dao_objects.DbMessage;
import dao_objects.DbSession;
import dao_objects.DbUser;

public class JsonHelper {

	public static final String MESSAGE_ID_SESSION = DbMessage.ID_SESSION;
	public static final String MESSAGE_HISTORY = DbMessage.HISTORY;
	public static final String LAST_MESSAGE = DbMessage.LAST_MESSAGE;

	public static final String FRIENDHSIP_ID = DbFriend.ID;
	public static final String FRIENDHSIP_USER_ID = DbFriend.USER_ID;
	public static final String FRIENDHSIP_FRIEND_ID = DbFriend.USER_ID;

	public static final String SESSION_ID = DbSession.ID;
	public static final String SESSION_ID_SESSION = DbSession.ID_SESSION;
	public static final String SESSION_ID_USER = DbSession.ID_USER;
	public static final String SESSION_UPDATED_AT = DbSession.UPDATED_AT;
	public static final String SESSION_USER_LIST = "user_list";

	public static final String USER_ID = DbUser.ID;
	public static final String USER_LOGIN = DbUser.LOGIN;
	public static final String USER_PASSWORD = DbUser.PASSWORD;
	public static final String USER_BIRTHDAY = DbUser.BIRTHDAY;
	public static final String USER_EMAIL = DbUser.EMAIL;
	public static final String USER_LAST_NAME = DbUser.LAST_NAME;
	public static final String USER_FIRST_NAME = DbUser.FIRST_NAME;
	public static final String USER_STATUS = DbUser.STATUS;
	public static final String USER_MESSAGE = DbUser.MESSAGE;
	public static final String USER_HTML_TOKEN = DbUser.HTML_TOKEN;
	public static final String USER_IS_ADMIN = DbUser.IS_ADMIN;
	public static final String USER_SESSION_LIST = "session_list";
	public static final String USER_FRIEND_LIST = "friend_list";
	public static final String USER_DATA = "user_data";

	public static JsonObject toJsonObject(PcUser user){

		JsonObjectBuilder userBuilder=Json.createObjectBuilder()
				.add(USER_DATA, toJsonObject(user.getUser()));

		JsonArrayBuilder sessionListBuilder=Json.createArrayBuilder();
		for(PcSession session : user.getSessionList()){
			sessionListBuilder.add(toJsonObject(session));
		}

		userBuilder.add(USER_SESSION_LIST, sessionListBuilder.build());
		userBuilder.add(USER_FRIEND_LIST, toJsonArray(user.getFriendList()));

		return userBuilder.build();
	}

	public static JsonObject toJsonObject(DbUser user){
		JsonObjectBuilder userBuilder=Json.createObjectBuilder()
				.add(USER_ID, user.getId())
				.add(USER_LOGIN, user.getLogin())
				.add(USER_PASSWORD, user.getPassword())
				.add(USER_BIRTHDAY, user.getBirthday())
				.add(USER_EMAIL, user.getEmail())
				.add(USER_LAST_NAME, user.getLastName())
				.add(USER_FIRST_NAME, user.getFirstName())
				.add(USER_STATUS, user.getStatus())
				.add(USER_MESSAGE, user.getMessage())
				.add(USER_HTML_TOKEN, user.getToken())
				.add(USER_IS_ADMIN, user.isAdmin());
		return userBuilder.build();
	}

	public static JsonObject toJsonObject(PcSession session){
		JsonObjectBuilder sessionBuilder=Json.createObjectBuilder();
		JsonArrayBuilder userListBuilder=Json.createArrayBuilder();
		for(DbUser user : session.getUserList()){
			userListBuilder.add(toJsonObject(user));
		}
		sessionBuilder.add(SESSION_ID_SESSION, session.getSessionId());
		sessionBuilder.add(SESSION_USER_LIST, userListBuilder.build());
		if(session.getHistory()!=null&&session.getLastMessage()!=null){
			sessionBuilder.add(MESSAGE_HISTORY, session.getHistory());
			sessionBuilder.add(LAST_MESSAGE, session.getLastMessage());
		}else{
			sessionBuilder.add(MESSAGE_HISTORY, "");
			sessionBuilder.add(LAST_MESSAGE, "");
		}
		return sessionBuilder.build();
	}

	public static JsonArray toJsonArray(ArrayList<PcUser> friendList){
		JsonArrayBuilder friendListBuilder=Json.createArrayBuilder();
		for(PcUser u : friendList){
			friendListBuilder.add(toJsonObject(u));
		}
		return friendListBuilder.build();
	}

	public static JsonObject toJsonObject(String s){
		JsonReader jsonReader = Json.createReader(new StringReader(s));
		JsonObject json = jsonReader.readObject();
		jsonReader.close();
		return json;
	}

	public static DbUser toDbUser(JsonObject user){
		return new DbUser(
				user.getString(USER_ID),
				user.getString(USER_LOGIN),
				user.getString(USER_PASSWORD), 
				user.getString(USER_BIRTHDAY), 
				user.getString(USER_EMAIL), 
				user.getString(USER_LAST_NAME), 
				user.getString(USER_FIRST_NAME), 
				user.getString(USER_STATUS), 
				user.getString(USER_MESSAGE), 
				user.getString(USER_HTML_TOKEN), 
				user.getBoolean(USER_IS_ADMIN)
				);
	}

	public static PcSession toSession(JsonObject session){
		ArrayList<DbUser> userList = new ArrayList<DbUser>();
		JsonArray userJsonList = session.getJsonArray(SESSION_USER_LIST);
		for(int i=0;i<userJsonList.size();i++){
			userList.add(toDbUser((JsonObject) userJsonList.get(i)));
		}
		return new PcSession(session.getString(SESSION_ID_SESSION), userList);
	}

	public static ArrayList<PcUser> toFriendList(JsonArray jsonFriendList){
		ArrayList<PcUser> friendList = new ArrayList<PcUser>();
		for(int i=0;i<jsonFriendList.size();i++){
			friendList.add(toPcUser((JsonObject) jsonFriendList.get(i)));
		}
		return friendList;
	}

	public static PcUser toPcUser(JsonObject user){
		DbUser dbUser = toDbUser(user.getJsonObject(USER_DATA));
		ArrayList<PcUser> friendList = toFriendList(user.getJsonArray(USER_FRIEND_LIST));
		ArrayList<PcSession> sessionList = new ArrayList<PcSession>();
		JsonArray jsonSessionList = user.getJsonArray(USER_SESSION_LIST);
		for(int i=0;i<jsonSessionList.size();i++){
			sessionList.add(toSession(jsonSessionList.getJsonObject(i)));
		}
		return new PcUser(dbUser, sessionList, friendList);
	}


}
