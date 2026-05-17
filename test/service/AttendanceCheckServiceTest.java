package service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import model.Attendance;
import model.CheckResult;

public class AttendanceCheckServiceTest {

    @Test
    void testNormalAttendance() {

        List<Attendance> list = new ArrayList<>();

        list.add(new Attendance(
                "1001",
                "2025-01-01",
                "08:55",
                "17:30"
        ));

        AttendanceCheckService service =
                new AttendanceCheckService();

        List<CheckResult> result =
                service.check(list);
        
        System.out.println(result.get(0).getStatus());

        assertEquals(
                "正常",
                result.get(0).getStatus()
        );
    }
}