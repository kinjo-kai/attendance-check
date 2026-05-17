package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class EncodingUtil {
	
	public static BufferedReader createReader(String filePath) throws Exception{
		
		try {
			//まずUTF-8を読む
			return new BufferedReader(new InputStreamReader(new FileInputStream(filePath),Charset.forName("UTF-8")));
		}catch(Exception e) {
			
			//だめならShift-JIS
		}	return new BufferedReader(new InputStreamReader(new FileInputStream(filePath),Charset.forName("MS932")));
	}

}
