package org.example.medicine_ai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.medicine_ai.entity.Appointment;

public interface AppointmentService extends IService<Appointment> {
    Appointment getOne(Appointment appointment);
}
