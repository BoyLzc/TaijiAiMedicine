package org.example.medicine_ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.medicine_ai.entity.Doctors;
import org.example.medicine_ai.mapper.DoctorsMapper;
import org.example.medicine_ai.service.DoctorsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorsServiceImpl extends ServiceImpl<DoctorsMapper, Doctors> implements DoctorsService {

    @Override
    public Doctors getOne(Doctors doctors) {
        // 查询医生信息
        LambdaQueryWrapper<Doctors> objectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        objectLambdaQueryWrapper.eq(Doctors::getDoctorId, doctors.getDoctorId());
        objectLambdaQueryWrapper.eq(Doctors::getDoctorName, doctors.getDoctorName());
        objectLambdaQueryWrapper.eq(Doctors::getDepartment, doctors.getDepartment());
        return baseMapper.selectOne(objectLambdaQueryWrapper);
    }
    @Override
    public Doctors getDoctorById(long doctorId) {
        // 根据医生id 获取医生实体
        LambdaQueryWrapper<Doctors> objectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        objectLambdaQueryWrapper.eq(Doctors::getDoctorId, doctorId);
        return baseMapper.selectOne(objectLambdaQueryWrapper);
    }

    @Override
    public Doctors getDoctorByDepartmentAndName(String department, String doctorName) {
        // 根据科室名称、医生名字 获取医生实体
        LambdaQueryWrapper<Doctors> objectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        objectLambdaQueryWrapper.eq(Doctors::getDepartment, department);
        objectLambdaQueryWrapper.eq(Doctors::getDoctorName, doctorName);
        return baseMapper.selectOne(objectLambdaQueryWrapper);
    }

    // 根据科室名称 获取医生列表
    @Override
    public List<Doctors> getDoctorsByDepartment(String department) {
        LambdaQueryWrapper<Doctors> objectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        objectLambdaQueryWrapper.eq(Doctors::getDepartment, department);
        return baseMapper.selectList(objectLambdaQueryWrapper);
    }
}
