package org.example.medicine_ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.medicine_ai.entity.Schedules;
import org.example.medicine_ai.mapper.SchedulesMapper;
import org.example.medicine_ai.service.SchedulesService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchedulesServiceImpl extends ServiceImpl<SchedulesMapper, Schedules> implements SchedulesService {
    @Override
    public int getAvailableAppointments(Long doctorId, String workDate, String timeSlot) {
        // 在排班表里，根据医生id、日期、时间段查询可预约的号源数量
        LambdaQueryWrapper<Schedules> objectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        objectLambdaQueryWrapper.eq(Schedules::getDoctorId, doctorId);
        objectLambdaQueryWrapper.eq(Schedules::getWorkDate, workDate);
        objectLambdaQueryWrapper.eq(Schedules::getTimeSlot, timeSlot);
        // 执行查询并返回结果
        Schedules schedule = baseMapper.selectOne(objectLambdaQueryWrapper);
        if (schedule != null) {
            return schedule.getAvailableAppointments(); // 返回剩余号源数量
        }
        return 0; // 如果没有找到符合条件的排班记录，默认
    }
    @Override
    public List<Schedules> getAvailableDoctors(String workDate, String timeSlot) {
        // 在排班表里，根据日期、时间段查询可预约的医生列表
        LambdaQueryWrapper<Schedules> objectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        objectLambdaQueryWrapper.eq(Schedules::getWorkDate, workDate);
        objectLambdaQueryWrapper.eq(Schedules::getTimeSlot, timeSlot);
        // 执行查询并返回结果
        return baseMapper.selectList(objectLambdaQueryWrapper);
    }

    @Override
    public void deductAppointments(Long doctorId, String workDate, String timeSlot) {
        LambdaQueryWrapper<Schedules> objectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        objectLambdaQueryWrapper.eq(Schedules::getDoctorId, doctorId);
        objectLambdaQueryWrapper.eq(Schedules::getWorkDate, workDate);
        objectLambdaQueryWrapper.eq(Schedules::getTimeSlot, timeSlot);
        Schedules schedules = baseMapper.selectOne(objectLambdaQueryWrapper);
        // 对剩余号源进行扣减
        schedules.setAvailableAppointments(schedules.getAvailableAppointments() - 1);
        baseMapper.updateById(schedules);
    }

    @Override
    public void addAppointments(Long doctorId, String workDate, String timeSlot) {
        LambdaQueryWrapper<Schedules> objectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        objectLambdaQueryWrapper.eq(Schedules::getDoctorId, doctorId);
        objectLambdaQueryWrapper.eq(Schedules::getWorkDate, workDate);
        objectLambdaQueryWrapper.eq(Schedules::getTimeSlot, timeSlot);
        Schedules schedules = baseMapper.selectOne(objectLambdaQueryWrapper);
        // 添加号源
        schedules.setAvailableAppointments(schedules.getAvailableAppointments() + 1);
        baseMapper.updateById(schedules);
    }
}
