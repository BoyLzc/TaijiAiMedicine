package org.example.medicine_ai;

import org.example.medicine_ai.entity.Appointment;
import org.example.medicine_ai.entity.Doctors;
import org.example.medicine_ai.service.AppointmentService;
import org.example.medicine_ai.service.DoctorsService;
import org.example.medicine_ai.service.SchedulesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AppointmentServiceTest {
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private SchedulesService schedulesService;
    @Autowired
    private DoctorsService doctorsService;

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

    @Test
    public void testDoctorAndSchedules(){
        // 查医生
        Doctors doctor = doctorsService
                .getDoctorByDepartmentAndName("耳鼻喉科", "朱立新");
        System.out.println(doctor);
        // 查号源
        System.out.println(schedulesService.getAvailableAppointments(doctor.getDoctorId(), "2025-07-18", "上午"));
    }

    @Test
    public void testAcountAvailable(){
        // 扣减号源
        schedulesService.deductAppointments(1L, "2025-07-18", "上午");
    }
}
