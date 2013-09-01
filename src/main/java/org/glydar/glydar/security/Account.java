package org.glydar.glydar.security;

import org.glydar.glydar.models.GPlayer;

public class Account {
	private GPlayer player;
	private boolean verified = false;
	private boolean admin = false;
	private boolean banned = false;
	
	public Account (GPlayer p) {
		this.player = p;
	}
	
	public boolean isVerified(){
		return verified;
	}
	
	public void setVerified(boolean ver){
		this.verified = ver;
	}
	
	public boolean isAdmin(){
		return admin;
	}
	
	public void setAdmin(boolean adm){
		this.admin = adm;
	}
}
