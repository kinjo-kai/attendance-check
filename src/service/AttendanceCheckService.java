package service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import model.Attendance;
import model.CheckResult;

public class AttendanceCheckService{
	
	private static final LocalTime START_LIMIT =
			LocalTime.parse(
					util.ConfigUtil.get("start.limit"));
	
	private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
	
	public List<CheckResult> check(List<Attendance> attendanceList){
		
		List<CheckResult> resultList = new ArrayList<>();
		
		for(Attendance a : attendanceList) {
				
			String status = "正常";
			String remark = "";
			
			if(a.getEmployeeId() == null || a.getEmployeeId().isEmpty()) {
				status = "エラー";
				remark = "社員ID未入力";
			}
			
			else if(a.getEndTime() == null || a.getEndTime().isEmpty()) {
				status="エラー";
				remark="退勤時刻未入力";
				
			}
			
			else{
				try {
					LocalTime startTime =
							LocalTime.parse(a.getStartTime(), TIME_FORMAT);
					
					if(startTime.isAfter(START_LIMIT)) {
					status = "遅刻";
					remark= "出勤"+ a.getStartTime();
					}
					
				}catch(DateTimeParseException e) {
					status = "エラー";
					remark = "出勤時刻フォーマット不正";
				}
			}
			
			CheckResult result = new CheckResult(
					a.getEmployeeId(),
					a.getDate(),
					status,
					remark
					);
			resultList.add(result);
		}
		return resultList;
	}
}