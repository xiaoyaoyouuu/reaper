package com.reaper.reap;

import java.util.ArrayList;

import com.reaper.common.AccountInfo;
import com.reaper.common.AccountInfoLoader;

public class Reaper {
	
	public static void go() {
		Reaper reaper = new Reaper();
		ArrayList<AccountInfo> allAccounts = AccountInfoLoader.loadAccountInfo("account_info.xls");
		for(AccountInfo account : allAccounts){
			SingleThread goThread = reaper.new  SingleThread(account);
			goThread.start();
		}
	}
	
	 class SingleThread extends Thread{
		 AccountInfo thisAccount; 
		 SingleThread(AccountInfo accountInfo){
			thisAccount = accountInfo;
		}
		
		public void run() {
			try{
				SingleProcess singleProcess = new SingleProcess();
				singleProcess.setUp();
				singleProcess.testHuaweiLogin(thisAccount);
				singleProcess.tearDown();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	 
	 public static void main(String[] args) {
		 Reaper.go();
	}
}
