package model;

public class Attendance {
	
	private String employeeId;
	private String date;
	private String startTime;
	private String endTime;
	
	public Attendance(String employeeId, String date, String startTime, String endTime) {
		this.employeeId = employeeId;
		this.date =date;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public String getEmployeeId() {
		return employeeId;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getStartTime() {
		return startTime;
	}
	
	public String getEndTime() {
		return endTime;
	}
}
