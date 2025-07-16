package org.example.medicine_ai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.medicine_ai.entity.Doctors;
import org.example.medicine_ai.entity.Schedules;

import java.util.Date;
import java.util.List;

public interface SchedulesService extends IService<Schedules> {
    // 号源查询 根据医生id、日期、时间段，查询剩余号源
    public int getAvailableAppointments(Long doctorId, String workDate, String timeSlot);
    // 根据日期、时间段、查询有号源的排班信息（后期在业务层，根据医生id 获取医生信息）
    public List<Schedules> getAvailableDoctors(String workDate, String timeSlot);
    // 根据医生id、日期、时间段，扣减号源
    public void deductAppointments(Long doctorId, String workDate, String timeSlot);
    // 根据医生id、日期、时间段，添加号源
    public void addAppointments(Long doctorId, String workDate, String timeSlot);
}
