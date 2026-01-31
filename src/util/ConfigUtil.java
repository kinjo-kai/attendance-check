package util;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigUtil {
	
	private static Properties prop = new Properties();
	
	static {
		try {
			System.out.println("設定ファイル読込開始");
			prop.load(new FileInputStream("config.properties"));
			System.out.println("設定読込完了");
		}catch(Exception e) {
			System.out.println("設定ファイルが見つかりません!");
			e.printStackTrace();
		}
	}
	
	public static String get(String key) {
		String value = prop.getProperty(key);
		
		if(value == null) {
			throw new RuntimeException("設定キーが存在しません：" + key);
		}
		return value;
	}

}
