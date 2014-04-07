package com.reaper.common;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;

public class AccountInfoLoader {
	
	public static ArrayList<String[]> loadAccountInfo(String accountInfoFilePath) {
		Workbook workbook = null;
		try{
			workbook =  Workbook.getWorkbook(new File(accountInfoFilePath));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(workbook == null){
			return null;
		}
		
		ArrayList<String[]> accountInfos= new ArrayList<String[]>();
		Sheet sheet = workbook.getSheet(0);	
		int rowNum = sheet.getRows();
		for(int i=0;i<rowNum;i++){
			accountInfos.add(new String[]{sheet.getCell(0, i).getContents(),sheet.getCell(1, i).getContents()});
		}
		
		return accountInfos;
	}
	
	public static void main(String[] args) {
		ArrayList<String[]> resultList = AccountInfoLoader.loadAccountInfo("account_info.xls");
		for(Iterator<String[]> iterator = resultList.iterator();iterator.hasNext();){
			String[] temp = iterator.next();
			System.out.println(temp[0] +"," +  temp[1]);
		}
	}
}
