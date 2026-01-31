package App;

import java.util.List;
import java.util.logging.Logger;

import model.Attendance;
import model.CheckResult;
import service.AttendanceCheckService;
import util.CsvUtil;
import util.LoggerUtil;

public class Main {
	
	private static final Logger logger =
			LoggerUtil.getLogger();
	
	public static void main(String[] args) {
		
		if(args.length < 2) {
			logger.severe("引数不足：入力CSVとエラーCSVを指定してください");
			System.exit(2);
		}
		
		String inputCsv = args[0];
		String errorCsv = args[1];
		String resultCsv = "result_attendance.csv";
		
		try {
			
			logger.info("処理開始");
			
			List<Attendance> attendanceList =
					CsvUtil.readAttendanceCsv(inputCsv);
			
			AttendanceCheckService service =
					new AttendanceCheckService();
			
			List<CheckResult> resultList =
					service.check(attendanceList);
					
			boolean hasError = false;
			
			for(CheckResult r : resultList) {
				logger.info(r.toString());
				
				if("エラー".equals(r.getStatus())) {
					logger.warning("エラー検出：" + r.toString());
					hasError = true;
				}
			}
			
			CsvUtil.writeResultCsv(resultCsv, resultList);
			
			if(hasError) {
				CsvUtil.writeErrorCsv(errorCsv, resultList);
				logger.warning("処理終了（業務エラーあり）");
				System.exit(1);
			}else {
				logger.info("処理終了（正常）");
				System.exit(0);
			}
		}catch(Exception e) {
			logger.severe("システムエラー");
			logger.severe(e.toString());
			System.exit(2);
		}
	}
}
