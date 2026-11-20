package eus.martico.schwarz.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eus.martico.schwarz.config.tmsConstantes;

public class MyServiceContext {

	protected static final Logger logger = LoggerFactory.getLogger(MyServiceContext.class);

	public static MyServiceContext getInstance() {
		MyServiceContext context = new MyServiceContext();
		return context;
	}
	public static MyServiceContext getInstance(Long _userNi) {
		MyServiceContext context = new MyServiceContext();
		context.setUsuarioNi(_userNi);
		return context;
	}
	
	
	private Long usuarioNi = tmsConstantes.DEFAULT_USER_NI;
	private String username;
	private String sessionId;
	private String[] userRoles;

	private MyServiceContext(){
		super();
	}
	
	public Long getUsuarioNi() {
		return usuarioNi;
	}
	public void setUsuarioNi(Long usuarioNi) {
		this.usuarioNi = usuarioNi;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String[] getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(String[] userRoles) {
		this.userRoles = userRoles;
	}
}
