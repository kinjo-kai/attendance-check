package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import model.Attendance;
import model.CheckResult;

public class CsvUtil {
	
	public static List<Attendance> readAttendanceCsv(String filePath) throws Exception{
		List<Attendance>list = new ArrayList<>();
			
		try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
			String line;
			boolean isHeader = true;
			int lineNo = 0;
			
			while((line = br.readLine()) != null) {
				lineNo++;
				
			//ヘッダー除外
				if(isHeader) {
					isHeader = false;
					continue;
				}
				
				//空行チェック
				if(line.trim().isEmpty()) {
					LoggerUtil.getLogger()
						.warning("空行スキップ　行番号＝" + lineNo);
					continue;
				}
				
				String[] data = line.split(",");
				
				Attendance attendance = new Attendance(
						data[0].trim(),
						data[1].trim(),
						data[2].trim(),
						data.length >= 4 ? data[3].trim(): ""
						);
				
				list.add(attendance);
			}
		}
		
		return list;
	}
	
	public static void writeErrorCsv(
			String filePath,
			List<CheckResult> resultList) throws Exception{
	try(PrintWriter pw =
			new PrintWriter(new FileWriter(filePath))) {
		
		//ヘッダー
		pw.println("社員ID,日付,ステータス,備考");
		
		for(CheckResult r : resultList) {
			if("エラー".equals(r.getStatus())) {
				pw.println(
						r.getEmployeeId() + "," +
						r.getDate() + "," +
						r.getStatus() + "," +
						r.getRemark()
						);
			}
		}
		
	}
	
}
		
	
	public static void writeResultCsv(String filePath,
			List<CheckResult> results) throws Exception {
		
		try(PrintWriter pw = new PrintWriter(new FileWriter(filePath))){
			
			pw.println("社員ID,日時,判定,備考");
			
			for(CheckResult r : results) {
				pw.println(r.toCsvLine());
			}
		}
	}
}
