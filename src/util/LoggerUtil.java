package util;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerUtil {
	
	private static final Logger logger =
			Logger.getLogger("AttendanceApp");

	static {
		try {

			logger.setUseParentHandlers(false);
			logger.setLevel(Level.ALL);

			// ★ コンソール出力
			ConsoleHandler ch = new ConsoleHandler();
			ch.setFormatter(new SimpleFormatter());
			ch.setLevel(Level.ALL);
			logger.addHandler(ch);

			// ★ ファイル出力
			FileHandler fh = new FileHandler("app.log", true);
			fh.setFormatter(new SimpleFormatter());
			fh.setLevel(Level.ALL);
			logger.addHandler(fh);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Logger getLogger() {
		return logger;
	}
}
