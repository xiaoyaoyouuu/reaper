package com.reaper.common;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class checkApiFile {
	
	/*	浼樹紭浜慏LL 鏂囦欢MD5鍊兼牎楠�
	 *  鐢ㄥ锛氳繎鏈熸湁涓嶆硶浠藉瓙閲囩敤鏇挎崲浼樹紭浜戝畼鏂筪ll鏂囦欢鐨勬柟寮忥紝鏋佸ぇ鐨勭牬鍧忎簡寮�彂鑰呯殑鍒╃泭
	 *  鐢ㄦ埛浣跨敤鏇挎崲杩囩殑DLL鎵撶爜锛屽鑷村紑鍙戣�鍒嗘垚鍙樻垚鍒汉鐨勶紝鍒╃泭鍙楁崯锛�
	 *  鎵�互寤鸿鎵�湁寮�彂鑰呭湪杞欢閲岄潰澧炲姞鏍￠獙瀹樻柟MD5鍊肩殑鍑芥暟
	 *  闆嗘垚鎴愬姛鍙厤璐硅幏鍙�00鍏冨厖鍊煎崱
	 */
	
	public static boolean check(String ApiFile,int softId,String softKey) throws IOException{
	    /**
	     * 楠岃瘉API鏂囦欢鏄惁鏄畼鏂圭増鏈�
	     * http鏍￠獙鎺ュ彛璇存槑锛歨ttp://dll.uuwise.com/index.php?n=ApiDoc.ApiFileCheck
	     * 
	     * @param ApiFile
	     *            API鏂囦欢鐨勭浉瀵硅矾寰�
	     * @param softId
	     *            杞欢ID
	     * @param softKey
	     *            杞欢KEY
	     * @return 鎵�唬琛ㄨ繙绋嬭祫婧愮殑鍝嶅簲缁撴灉
	     * 			  鏍￠獙鎴愬姛杩斿洖 1 澶辫触杩斿洖 0
	     */
		
		/*	API鏂囦欢楠岃瘉鏈嶅姟鍣ㄥ垪琛�*/
		String[] checkServer={"http://v.uuwise.com/service/verify.aspx","http://v.uuwise.net/service/verify.aspx","http://v.uudama.com/service/verify.aspx","http://v.uudati.cn/service/verify.aspx","http://v.taskok.com/service/verify.aspx"};
		int i=0;
		
		String apiFileMd5=checkApiFile.GetFileMD5(ApiFile);		//api鏂囦欢鐨凪D5鍊�
		StringBuffer k=new StringBuffer();
		StringBuffer o=new StringBuffer();
		k.append(softId).append(apiFileMd5.toUpperCase()).toString();	
		String key=checkApiFile.Md5(k.toString());			//key = md5(SoftID+ dllkey.澶у啓)
		o.append(softId).append(softKey.toUpperCase()).toString();
		String ok=checkApiFile.Md5(o.toString());			//md5(softID+softkey.澶у啓)
		
		String c="SID="+softId+"&dllkey="+apiFileMd5+"&key="+key;	//post鍙傛暟
		
		while(i<5){
			String status=checkApiFile.sendPost(checkServer[i],c);
			if(status.equals(ok)){
				return true;
			}
			i++;
		}
		return false;
	}
	
	
	//MD5鏍￠獙鍑芥暟寮�
    /**
     * 鑾峰彇鎸囧畾鏂囦欢鐨凪D5鍊�
     * 
     * @param inputFile
     *            鏂囦欢鐨勭浉瀵硅矾寰�
     */
	public static String GetFileMD5(String inputFile) throws IOException {
		int bufferSize = 256 * 1024;
		FileInputStream fileInputStream = null;
		DigestInputStream digestInputStream = null;
		try {
			MessageDigest messageDigest =MessageDigest.getInstance("MD5");
			fileInputStream = new FileInputStream(inputFile);
			digestInputStream = new DigestInputStream(fileInputStream,messageDigest);
			byte[] buffer =new byte[bufferSize];
			while (digestInputStream.read(buffer) > 0);
			messageDigest= digestInputStream.getMessageDigest();
			byte[] resultByteArray = messageDigest.digest();
			return byteArrayToHex(resultByteArray);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}finally {
			try {
				digestInputStream.close();
			}catch (Exception e) {
				
			}try {
				fileInputStream.close();
			}catch (Exception e) {
				
			}
		}
	}
	public static String Md5(String s) throws IOException{
		try {
			byte[] btInput = s.getBytes();
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			return byteArrayToHex(md);
		}catch (Exception e) {
            e.printStackTrace();
            return null;
        }
		
	}
	public static String byteArrayToHex(byte[] byteArray) {
		char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'a','b','c','d','e','f' };
		char[] resultCharArray =new char[byteArray.length * 2];
		int index = 0;
		for (byte b : byteArray) {
			resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];
			resultCharArray[index++] = hexDigits[b& 0xf];
		}
		return new String(resultCharArray);
	}
	
	//MD5鏍￠獙鍑芥暟缁撴潫
    /**
     * 鍚戞寚瀹�URL 鍙戦�POST鏂规硶鐨勮姹�
     * 
     * @param url
     *            鍙戦�璇锋眰鐨�URL
     * @param param
     *            璇锋眰鍙傛暟锛岃姹傚弬鏁板簲璇ユ槸 name1=value1&name2=value2 鐨勫舰寮忋�
     * @return 鎵�唬琛ㄨ繙绋嬭祫婧愮殑鍝嶅簲缁撴灉
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 鎵撳紑鍜孶RL涔嬮棿鐨勮繛鎺�
            URLConnection conn = realUrl.openConnection();
            // 璁剧疆閫氱敤鐨勮姹傚睘鎬�
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent","VersionClient");
            // 鍙戦�POST璇锋眰蹇呴』璁剧疆濡備笅涓よ
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 鑾峰彇URLConnection瀵硅薄瀵瑰簲鐨勮緭鍑烘祦
            out = new PrintWriter(conn.getOutputStream());
            // 鍙戦�璇锋眰鍙傛暟
            out.print(param);
            // flush杈撳嚭娴佺殑缂撳啿
            out.flush();
            // 瀹氫箟BufferedReader杈撳叆娴佹潵璇诲彇URL鐨勫搷搴�
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("鍙戦� POST 璇锋眰鍑虹幇寮傚父锛�+e");
            e.printStackTrace();
        }
        //浣跨敤finally鍧楁潵鍏抽棴杈撳嚭娴併�杈撳叆娴�
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }  
}
