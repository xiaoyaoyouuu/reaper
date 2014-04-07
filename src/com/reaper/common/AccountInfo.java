package com.reaper.common;

public class AccountInfo {
	private String accountName;
	private String pwd;
	
	public AccountInfo(String accountName,String pwd) {
		this.accountName = accountName;
		this.pwd = pwd;
	}
	
	public String getAccountName() {
		return this.accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getPwd() {
		return this.pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
}
