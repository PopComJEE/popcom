package popcom_server.controller;

import java.util.ArrayList;
import java.util.Arrays;

import popcom_server.struct.PopComSession;
import popcom_server.struct.PopComUser;

public class SessionController {

	private static ArrayList<PopComSession> mActiveSessions = new ArrayList<PopComSession>();

	private static ArrayList<PopComSession> mAllSessions = new ArrayList<PopComSession>(
			Arrays.asList(
					new PopComSession(UserController._getAllusers()),

					new PopComSession(new ArrayList<PopComUser>(
							Arrays.asList(
									UserController.getUserByLogin("ali"),
									UserController.getUserByLogin("ronan"),
									UserController.getUserByLogin("benoit"),
									UserController.getUserByLogin("laura"),
									UserController.getUserByLogin("silene")
									)
							)),

							new PopComSession(new ArrayList<PopComUser>(
									Arrays.asList(
											UserController.getUserByLogin("ali"),
											UserController.getUserByLogin("benoit"),
											UserController.getUserByLogin("laura"),
											UserController.getUserByLogin("silene")
											)
									)),

									new PopComSession(new ArrayList<PopComUser>(
											Arrays.asList(
													UserController.getUserByLogin("ali"),
													UserController.getUserByLogin("benoit"),
													UserController.getUserByLogin("laura")
													)
											)),

											new PopComSession(new ArrayList<PopComUser>(
													Arrays.asList(
															UserController.getUserByLogin("ali"),
															UserController.getUserByLogin("ronan"),
															UserController.getUserByLogin("benoit"),
															UserController.getUserByLogin("laura")
															)
													)),

													new PopComSession(new ArrayList<PopComUser>(
															Arrays.asList(
																	UserController.getUserByLogin("ronan"),
																	UserController.getUserByLogin("benoit")
																	)
															)),

															new PopComSession(new ArrayList<PopComUser>(
																	Arrays.asList(
																			UserController.getUserByLogin("ali"),
																			UserController.getUserByLogin("benoit")
																			)
																	)),

																	new PopComSession(new ArrayList<PopComUser>(
																			Arrays.asList(
																					UserController.getUserByLogin("ronan"),
																					UserController.getUserByLogin("benoit"),
																					UserController.getUserByLogin("silene")
																					)
																			)),

																			new PopComSession(new ArrayList<PopComUser>(
																					Arrays.asList(
																							UserController.getUserByLogin("ali"),
																							UserController.getUserByLogin("benoit"),
																							UserController.getUserByLogin("laura")
																							)
																					)),

																					new PopComSession(new ArrayList<PopComUser>(
																							Arrays.asList(
																									UserController.getUserByLogin("benoit"),
																									UserController.getUserByLogin("laura"),
																									UserController.getUserByLogin("silene")
																									)
																							)),

																							new PopComSession(new ArrayList<PopComUser>(
																									Arrays.asList(
																											UserController.getUserByLogin("ali"),
																											UserController.getUserByLogin("ronan"),
																											UserController.getUserByLogin("benoit")
																											)
																									))

					)
			);

	//Not used
	public static PopComSession getSessionById(int id){
		if(mAllSessions.size()<=id)
			return null;
		return mAllSessions.get(id);
	}

	public static ArrayList<PopComSession> getAllSessions(){
		return mAllSessions;
	}

	public static ArrayList<PopComSession> getUserSessions(PopComUser user){
		ArrayList<PopComSession> userSessionList=new ArrayList<PopComSession>();
		for(PopComSession s : mAllSessions){
			if(s.getUserConnectedList().contains(user)){
				userSessionList.add(s);
			}
		}
		return userSessionList;
	}





}
