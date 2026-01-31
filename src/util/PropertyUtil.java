package util;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyUtil {
	
	private static Properties prop = new Properties();
	
	static {
		try(FileInputStream fis = new FileInputStream("config.properties")){
			prop.load(fis);
		}catch(Exception e) {
			throw new RuntimeException("config.properties 読み込み失敗", e);
		}
	}
	
	public static String get(String key) {
		return prop.getProperty(key);
	}
}
