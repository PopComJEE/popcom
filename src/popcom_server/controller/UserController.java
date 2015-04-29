package popcom_server.controller;

import java.util.ArrayList;
import java.util.Arrays;

import popcom_server.struct.PopComUser;

public class UserController {

	public static ArrayList<PopComUser> mActiveUsers = new ArrayList<PopComUser>();
	public static ArrayList<PopComUser> mAllUsers = new ArrayList<PopComUser>(
			Arrays.asList(
					new PopComUser("ronan", "tat"),
					new PopComUser("ali", "moh"),
					new PopComUser("silene", "cha"),
					new PopComUser("laura", "dou"),
					new PopComUser("benoit", "gar")
					)
			);
	
	public static ArrayList<PopComUser>_getAllusers(){
		return mAllUsers;
	}

	public static PopComUser getUserById(int id){
		if(mAllUsers.size()<=id)
			return null;
		return mAllUsers.get(id);
	}

	public static PopComUser getUserByLogin(String login){
		for(PopComUser u:mAllUsers){
			if(u.getLogin().equals(login))
				return u;
		}
		return null;
	}

}
