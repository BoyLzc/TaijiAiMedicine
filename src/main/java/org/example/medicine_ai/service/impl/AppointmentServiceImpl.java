package org.example.medicine_ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.medicine_ai.entity.Appointment;
import org.example.medicine_ai.mapper.AppointmentMapper;
import org.example.medicine_ai.service.AppointmentService;
import org.springframework.stereotype.Service;

@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements AppointmentService {
    @Override
    public Appointment getOne(Appointment appointment) {
        // 根据指定预约信息，查询数据库中是否存在该用户的预约信息
        LambdaQueryWrapper<Appointment> objectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        objectLambdaQueryWrapper.eq(Appointment::getUsername, appointment.getUsername());
        objectLambdaQueryWrapper.eq(Appointment::getIdCard, appointment.getIdCard());
        objectLambdaQueryWrapper.eq(Appointment::getDepartment, appointment.getDepartment());
        objectLambdaQueryWrapper.eq(Appointment::getDate, appointment.getDate());
        objectLambdaQueryWrapper.eq(Appointment::getTime, appointment.getTime());
        return baseMapper.selectOne(objectLambdaQueryWrapper);
    }

}
