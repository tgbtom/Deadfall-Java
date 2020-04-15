package com.novaclangaming.model;

public class Password {
	private String hashedPass;
	String salt;
	
	public Password(String hashedPass, String salt) {
		super();
		this.hashedPass = hashedPass;
		this.salt = salt;
	}

	public String getHashedPass() {
		return hashedPass;
	}

	public void setHashedPass(String hashedPass) {
		this.hashedPass = hashedPass;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
	
}
