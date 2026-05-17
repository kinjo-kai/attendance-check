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
		
		String inputCsv =
			    args.length > 0 ? args[0] : "attendance.csv";
		String errorCsv =
				args.length > 1 ? args[1] : "error_attendance.csv";
		String resultCsv = "result_attendance.csv";
		
		try {
			
			logger.info("処理開始");
			
			List<Attendance> attendanceList =
					CsvUtil.readAttendanceCsv(inputCsv);
			
			AttendanceCheckService service =
					new AttendanceCheckService();
			
			List<CheckResult> resultList =
					service.check(attendanceList);
			
			logger.info("結果件数=" + resultList.size());
			
			CsvUtil.writeResultCsv(resultCsv, resultList);
			
			for(CheckResult r : resultList) {
				logger.info("DEBUG→"+ r.toString());
			}
			
					
			boolean hasError = false;
			
			for(CheckResult r : resultList) {
				logger.info(r.toString());
				
				if("エラー".equals(r.getStatus())) {
					logger.warning("エラー検出：" + r.toString());
					hasError = true;
				}
			}
			
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
