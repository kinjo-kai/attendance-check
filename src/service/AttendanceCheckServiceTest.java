package service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import model.Attendance;

class AttendanceCheckServiceTest {

	@Test
	void 出勤0859は正常() {
		
		Attendance a = new Attendance(
				"001",
				"2024-01-01",
				"09:01",
				"18:00"
				);
		
		AttendanceCheckService service =
				new AttendanceCheckService();
		
		var result = service.check(List.of(a));
		
		assertEquals("正常",
				result.get(0).getStatus());
	}

}
