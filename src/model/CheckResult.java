package model;

public class CheckResult {
	
	private String employeeId;
	private String date;
	private String status;
	private String remark;
	
	public CheckResult(String employeeId, String date, String status, String remark){
		this.employeeId = employeeId;
		this.date = date;
		this.status = status;
		this.remark = remark;
	}
	
	@Override
	public String toString() {
		return employeeId + "," +
				date+ ","+
				status + "," +
				remark;
	}
	
	public String getStatus() {
		return status;
	}
	
	public String getEmployeeId() {
		return employeeId;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public String toCsvLine() {
		return employeeId + "," + date + "," + status + "," + remark;
	}
	
}
