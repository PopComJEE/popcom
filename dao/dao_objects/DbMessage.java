package dao_objects;

import java.io.StringReader;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class DbMessage extends DbObject{

	public static final String TABLE_MESSAGES = "messages";
	public static final String ID = "id";
	public static final String ID_SESSION = "session_id";
	public static final String HISTORY = "message_history";
	public static final String LAST_MESSAGE = "last_message";

	private String mId;
	private String mSessionId;
	private JsonArray mHistory;
	private JsonObject mLastMessage;

	@Override
	public String toString() {
		return ID+" : "+mId
				+"\n"+ID_SESSION+" : "+mSessionId
				+"\n"+HISTORY+" : "+mHistory
				+"\n"+LAST_MESSAGE+" : "+mLastMessage;
	}

	private JsonObject parseJsonObject(String s){
		JsonReader jsonReader = Json.createReader(new StringReader(s));
		JsonObject object = jsonReader.readObject();
		jsonReader.close();
		return object;
	}

	private JsonArray parseJsonArray(String s){
		JsonReader jsonReader = Json.createReader(new StringReader(s));
		JsonArray array = jsonReader.readArray();
		jsonReader.close();
		return array;
	}

	public DbMessage (ResultSet rs){
		try {
			System.out.println("ID : "+rs.getString(ID));
			mId = rs.getString(ID);
			System.out.println("ID_SESSION : "+ rs.getString(ID_SESSION));
			mSessionId = rs.getString(ID_SESSION);
			System.out.println("HISTORY : "+rs.getString(HISTORY));
			mHistory = parseJsonArray(rs.getString(HISTORY));
			System.out.println("LAST_MESSAGE : "+rs.getString(LAST_MESSAGE));
			mLastMessage = parseJsonObject(rs.getString(LAST_MESSAGE));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public DbMessage (){
	}


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

	public JsonArray getHistory() {
		return mHistory;
	}

	public void setHistory(JsonArray history) {
		mHistory = history;
	}

	public JsonObject getLastMessage() {
		return mLastMessage;
	}

	public void setLastMessage(JsonObject lastMessage) {
		mLastMessage = lastMessage;
	}
}
