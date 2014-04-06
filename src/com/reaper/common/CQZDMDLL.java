package com.reaper.common;



import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.sun.jna.Library;
import com.sun.jna.Native;




/**
 * 寮�彂鑰呰禋閽卞钩鍙�www.uuwise.com
 * 鏇村鍑芥暟缁嗚妭锛歞ll.uuwise.com
 * 
 */

public class CQZDMDLL
{
	public static String	USERNAME	= "xiaoyaouu";							//UU鐢ㄦ埛鍚�
	public static String	PASSWORD	= "123456789uu";							//UU瀵嗙爜
	public static String	DLLPATH		= "lib\\UUWiseHelper";					//DLL
	public static String	IMGPATH		= "img\\huawei.gif";
	public static int		SOFTID		= 96520;								//杞欢ID
	public static String	SOFTKEY		= "f882db60885e4b1cb4ebc81e8db89ed4";	//杞欢KEY
	

	
	public interface DM extends Library
	{
		DM	INSTANCE	= (DM) Native.loadLibrary(DLLPATH, DM.class);		
		public int uu_reportError(int id);		
		public int uu_setTimeOut(int nTimeOut);
		public void uu_setSoftInfoA(int softId, String softKey);
		public int uu_loginA(String UserName, String passWord);
		public int uu_getScoreA (String UserName, String passWord);
		public int uu_recognizeByCodeTypeAndBytesA (byte[] picContent, int piclen, int codeType, byte[] returnResult);
		public void uu_getResultA(int nCodeID,String pCodeResult);
	}
	
	
	public static void main(String[] args) throws Exception
	{
		/*	浼樹紭浜慏LL 鏂囦欢MD5鍊兼牎楠�
		 *  鐢ㄥ锛氳繎鏈熸湁涓嶆硶浠藉瓙閲囩敤鏇挎崲浼樹紭浜戝畼鏂筪ll鏂囦欢鐨勬柟寮忥紝鏋佸ぇ鐨勭牬鍧忎簡寮�彂鑰呯殑鍒╃泭
		 *  鐢ㄦ埛浣跨敤鏇挎崲杩囩殑DLL鎵撶爜锛屽鑷村紑鍙戣�鍒嗘垚鍙樻垚鍒汉鐨勶紝鍒╃泭鍙楁崯锛�
		 *  鎵�互寤鸿鎵�湁寮�彂鑰呭湪杞欢閲岄潰澧炲姞鏍￠獙瀹樻柟MD5鍊肩殑鍑芥暟
		 *  闆嗘垚鎴愬姛鍙厤璐硅幏鍙�00鍏冨厖鍊煎崱
		 *  checkAPiFile.java鏄凡缁忓啓濂界殑鏍￠獙鏂囦欢
		 */
				
		int userID;
		
		boolean apiCheckStatus=false;
		apiCheckStatus=checkApiFile.check(DLLPATH+".dll", SOFTID, SOFTKEY);
		
		if(apiCheckStatus){	//鍒ゆ柇API鏂囦欢鏄惁琚慨鏀�
			DM.INSTANCE.uu_setSoftInfoA(SOFTID, SOFTKEY);	//setsoftinfo鍜宭ogin鍑芥暟鍙渶瑕佹墽琛屼竴娆★紝灏卞彲浠ユ棤闄愭墽琛屽浘鐗囪瘑鍒嚱鏁颁簡
			userID=DM.INSTANCE.uu_loginA(USERNAME, PASSWORD);
			if(userID>0){
				System.out.println("userID is:"+userID);
				System.out.println("user score is:"+DM.INSTANCE.uu_getScoreA(USERNAME, PASSWORD)); 
				
				File f = new File(IMGPATH);
				byte[] by = toByteArray(f);
				byte[] resultBtye=new byte[30];		//涓鸿瘑鍒粨鏋滅敵璇峰唴瀛樼┖闂�
				int codeID=DM.INSTANCE.uu_recognizeByCodeTypeAndBytesA(by, by.length, 1, resultBtye);	//璋冪敤璇嗗埆鍑芥暟,resultBtye涓鸿瘑鍒粨鏋�
				String  resultResult = new String(resultBtye,"UTF-8");
				resultResult=resultResult.trim();
				System.out.println("this img codeID:"+codeID);
				System.out.println("return recongize Result:"+resultResult); 
				
			}else{
				System.out.println("鐧诲綍澶辫触锛岄敊璇唬鐮佷负锛�+userID);	//閿欒浠ｇ爜璇峰搴攄ll.uuwise.com鍚勫嚱鏁板�鏌ョ湅");
			} 
		}else{
			System.out.println("瀵逛笉璧凤紝鎮ㄦ浛鎹簡API鏂囦欢銆侫PI鏂囦欢涓嬭浇鍦板潃锛歨ttp://wiki.uuwise.com");
		}
	}

	
	public static byte[] toByteArray(File imageFile) throws Exception
	{
		BufferedImage img = ImageIO.read(imageFile);
		ByteArrayOutputStream buf = new ByteArrayOutputStream((int) imageFile.length());
		try
		{
			ImageIO.write(img, "jpg", buf);
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return buf.toByteArray();
	}

	public static byte[] toByteArrayFromFile(String imageFile) throws Exception
	{
		InputStream is = null;

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try
		{
			is = new FileInputStream(imageFile);
			byte[] b = new byte[1024];
			int n;
			while ((n = is.read(b)) != -1)
			{
				out.write(b, 0, n);
			}// end while

		} catch (Exception e)
		{
			throw new Exception("System error,SendTimingMms.getBytesFromFile", e);
		} finally
		{

			if (is != null)
			{
				try
				{
					is.close();
				} catch (Exception e)
				{}// end try
			}// end if

		}// end try
		return out.toByteArray();
	}

	public static String getCheckResult(String checkImgPath) {
		try{
			int userID;
			
			boolean apiCheckStatus=false;
			apiCheckStatus=checkApiFile.check(DLLPATH+".dll", SOFTID, SOFTKEY);
			
			if(apiCheckStatus){	
				DM.INSTANCE.uu_setSoftInfoA(SOFTID, SOFTKEY);	
				userID=DM.INSTANCE.uu_loginA(USERNAME, PASSWORD);
				if(userID>0){
					System.out.println("userID is:"+userID);
					System.out.println("user score is:"+DM.INSTANCE.uu_getScoreA(USERNAME, PASSWORD)); 
					
					File f = new File(checkImgPath);
					byte[] by = toByteArray(f);
					byte[] resultBtye=new byte[30];		
					int codeID=DM.INSTANCE.uu_recognizeByCodeTypeAndBytesA(by, by.length, 1, resultBtye);	
					String  resultResult = new String(resultBtye,"UTF-8");
					resultResult=resultResult.trim();
					System.out.println("this img codeID:"+codeID);
					System.out.println("return recongize Result:"+resultResult); 
					return resultResult;
				}else{
					return null;
				} 
			}else{
				return null;
			}
		}catch(Exception e){
			return null;
		}
	}
	
	
}




