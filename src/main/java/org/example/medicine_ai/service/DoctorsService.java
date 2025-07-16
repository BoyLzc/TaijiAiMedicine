package org.example.medicine_ai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.medicine_ai.entity.Doctors;

import java.util.List;

public interface DoctorsService extends IService<Doctors> {
    public Doctors getOne(Doctors doctors);
    // 根据医生id，获取医生实体
    public Doctors getDoctorById(long doctorId);
    // 根据科室名称、医生名字，获取医生实体
    public Doctors getDoctorByDepartmentAndName(String department, String doctorName);
    public List<Doctors> getDoctorsByDepartment(String department);
}
