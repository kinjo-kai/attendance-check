package util;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerUtil {
	
	private static final Logger logger =
			Logger.getLogger("AttendanceApp");
	
	static {
			ConsoleHandler handler  = new ConsoleHandler();
			handler.setFormatter(new SimpleFormatter());
			handler.setLevel(Level.ALL);
			
			logger.addHandler(handler);
			logger.setUseParentHandlers(false);
			logger.setLevel(Level.ALL);
			
		
	}
	
	public static Logger getLogger() {
		return logger;
	}

}
