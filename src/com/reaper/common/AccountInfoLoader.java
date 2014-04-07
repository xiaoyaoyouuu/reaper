package com.reaper.common;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import jxl.Sheet;
import jxl.Workbook;


public class AccountInfoLoader {
	
	public static ArrayList<AccountInfo> loadAccountInfo(String accountInfoFilePath) {
		Workbook workbook = null;
		try{
			workbook =  Workbook.getWorkbook(new File(accountInfoFilePath));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(workbook == null){
			return null;
		}
		
		ArrayList<AccountInfo> accountInfos= new ArrayList<AccountInfo>();
		Sheet sheet = workbook.getSheet(0);	
		int rowNum = sheet.getRows();
		for(int i=0;i<rowNum;i++){
			accountInfos.add(new AccountInfo(sheet.getCell(0, i).getContents(),sheet.getCell(1, i).getContents()));
		}
		
		return accountInfos;
	}
	
	public static void main(String[] args) {
		ArrayList<AccountInfo> resultList = AccountInfoLoader.loadAccountInfo("account_info.xls");
		for(Iterator<AccountInfo> iterator = resultList.iterator();iterator.hasNext();){
			AccountInfo temp = iterator.next();
			System.out.println(temp.getAccountName() +"," +  temp.getPwd());
		}
	}
}
