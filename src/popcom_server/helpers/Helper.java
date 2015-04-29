package popcom_server.helpers;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

import popcom_server.struct.PopComSession;
import popcom_server.struct.PopComUser;

public class Helper {

	public static JsonObject toJsonObject(PopComUser user){
		JsonObjectBuilder userBuilder=Json.createObjectBuilder()
				.add("login", user.getLogin())
				.add("password", user.getPassword());
		
		return userBuilder.build();
	}
	
	public static JsonObject toJsonObject(PopComSession session){
		JsonObjectBuilder sessionBuilder=Json.createObjectBuilder();

		JsonArrayBuilder userListBuilder=Json.createArrayBuilder();
		for(PopComUser u:session.getUserConnectedList()){
			userListBuilder.add(toJsonObject(u));
		}
		
		sessionBuilder.add("user_list", userListBuilder.build());
		
		return sessionBuilder.build();
	}
	
	public static JsonObject toJsonObject(String s){
		JsonReader jsonReader = Json.createReader(new StringReader(s));
		JsonObject json = jsonReader.readObject();
		jsonReader.close();
		
		return json;
	}
	
	public static PopComUser toUser(JsonObject userJson){
		return new PopComUser(userJson.getString("login"), userJson.getString("password"));
	}
	
	
}
