package org.aktin.broker.admin.auth;

import java.util.HashMap;
import java.util.Map;

public class TokenManager {
	private Map<String,Token> map;

	public TokenManager(){
		this.map = new HashMap<>();
	}
	/**
	 * Authenticate user and register token
	 * @param username name
	 * @param charArray password
	 * @return token
	 */
	Token authenticate(String username, char[] charArray){
		Token t = new Token(username);
		map.put(t.getGUID(), t);
		return t;
	}

	public Token lookupToken(String guid){
		return map.get(guid);
	}
}