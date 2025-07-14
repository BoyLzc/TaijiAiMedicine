package org.example.medicine_ai;

import org.example.medicine_ai.entity.Appointment;
import org.example.medicine_ai.service.AppointmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AppointmentServiceTest {
    @Autowired
    private AppointmentService appointmentService;

    @Test
    public void testFind() {
        Appointment appointment = new Appointment();
        appointment.setUsername("张三");
        appointment.setIdCard("123456789012345678");
        appointment.setDepartment("DEPARTMENT_1");
        appointment.setDate("2023-05-01");
        appointment.setTime("10:00");
        appointment.setDoctorName("DOCTOR_1");
        // 查询
        Appointment one = appointmentService.getOne(appointment);
        System.out.println(one);
    }

    @Test
    public void testSave() {
        Appointment appointment = new Appointment();
        appointment.setUsername("张三");
        appointment.setIdCard("123456789012345678");
        appointment.setDepartment("DEPARTMENT_1");
        appointment.setDate("2023-05-01");
        appointment.setTime("10:00");
        appointment.setDoctorName("DOCTOR_1");
        // 保存预约信息
        appointmentService.save(appointment);
    }
}
