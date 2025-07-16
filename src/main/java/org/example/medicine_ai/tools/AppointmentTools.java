package org.example.medicine_ai.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.example.medicine_ai.entity.Appointment;
import org.example.medicine_ai.entity.Doctors;
import org.example.medicine_ai.entity.Schedules;
import org.example.medicine_ai.service.AppointmentService;
import org.example.medicine_ai.service.DoctorsService;
import org.example.medicine_ai.service.SchedulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component // 作为 bean才能被注入
// 业务层
public class AppointmentTools {
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private DoctorsService doctorsService;
    @Autowired
    private SchedulesService schedulesService;

    @Tool("根据参数，先执行工具方法queryDepartment查询是否有号源，并直接回答是否可预约，并让用户确认预约信息是否正确，" +
            "然后执行该方法，对预约记录进行持久化保存。")
    public String bookAppointment(Appointment appointment) {
        // 查找数据库是否包含对应预约记录
        System.out.println("预约————查询预约记录");
        Appointment appointmentDB = appointmentService.getOne(appointment);
        // 如果数据库中没有该预约记录（则说明可以保存）
        if (appointmentDB == null) {
            // 防止大模型幻觉设置id 手动设置其为空 Id 属于 Long封装类型 所以可使用null
            appointment.setId(null);
            // 保存预约记录
            if(appointmentService.save(appointment)) {
                System.out.println("预约成功，并返回预约详情");
                // 预约成功以后还要扣减预约数量
                // 根据医生名字，获取医生id
                Doctors doctors = doctorsService.
                        getDoctorByDepartmentAndName(appointment.getDepartment(), appointment.getDoctorName());
                // 根据医生id、日期、时间段 在排班表里进行扣减
                schedulesService.deductAppointments(doctors.getDoctorId(), appointment.getDate(), appointment.getTime());
                System.out.println("-->号源扣减成功");
                return "预约成功，并返回预约详情";
            } else {
                System.out.println("预约失败");
                return "预约失败";
            }
        }
        System.out.println("您已预约过该科室、时间、医生，请勿重复预约");
        return "您在相同的科室和时间已有预约";
    }

    @Tool("取消用户预约信息")
    public String cancelAppointment(Appointment appointment) {
        System.out.println("取消预约————查询预约记录");
        Appointment appointmentDB = appointmentService.getOne(appointment);
        if (appointmentDB != null) {
            System.out.println("预约记录不为空");
            // 删除数据库中的预约记录
            if (appointmentService.removeById(appointmentDB.getId())) {
                System.out.println("取消成功");
                // 取消成功以后，还要将号源信息添加回去。
                // 根据医生名字，获取医生id
                Doctors doctors = doctorsService.
                        getDoctorByDepartmentAndName(appointmentDB.getDepartment(), appointmentDB.getDoctorName());
                // 根据医生id、日期、时间段 在排班表里进行扣减
                schedulesService.addAppointments(doctors.getDoctorId(), appointmentDB.getDate(), appointmentDB.getTime());
                return "取消成功";
            } else {
                System.out.println("取消失败");
                return "取消失败";
            }
        }
        System.out.println("您没有预约记录，请核对预约科室和时间");
        return "您没有预约记录，请核对预约科室和时间";
    }

    @Tool("在获取用户预约信息后，根据科室名称、日期、时间和医生查询是否有号源，返回给用户。如果用户没有指定医生名称，则从向量数据库中随机选择一位医生。")
    public boolean queryDepartment(
            @P(value = "科室名称") String department,
            @P(value = "日期") String date,
            @P(value = "时间，可选值：上午、下午、晚上") String time,
            @P(value = "医生名称") String doctorName) {
        // 模拟查询数据库，返回是否可预约
        System.out.println("查询是否有号源");
        System.out.println("科室名称：" + department);
        System.out.println("日期：" + date);
        System.out.println("时间：" + time);
        System.out.println("医生名称：" + doctorName);
        // 如果用户给出了医生名称 则查询其号源
            // 首先要根据科室名称、医生姓名，获取科室对应的医生实体
        Doctors doctor = doctorsService.getDoctorByDepartmentAndName(department, doctorName);
        // 根据医生id、日期、时间，获取可预约的医生列表
        int availableAppointments = schedulesService.getAvailableAppointments(doctor.getDoctorId(), date, time);
        if (availableAppointments == 0) {
            System.out.println("没有号源");
            return false;
        }
        System.out.println("有" + availableAppointments + "个号源");
        return true;
    }

    @Tool("用户根据科室信息、日期、时间段，查询坐诊医生及其号源信息时，通过该方法输出键值对（医生名字：号源数量）")
    public Map<String, Integer> queryAppointmentsInfo(
            @P(value = "科室名称") String department,
            @P(value = "日期") String date,
            @P(value = "时间（上午、下午、晚上）")String time) {
        Map<String, Integer> stringIntegerMap = new HashMap<>();
        // 查询出医生及其号源信息
        // 1、根据科室名称查询出医生id(群)
        List<Doctors> doctors = doctorsService.getDoctorsByDepartment(department);
        for (Doctors doctor : doctors) {
            // 剩余号源
            // 2、根据医生id、日期、时间查询号源信息
            int availableAppointments = schedulesService.
                    getAvailableAppointments(doctor.getDoctorId(), date, time);
            // 记录号源的医生及号源数量
            if (availableAppointments > 0) {
                // 将名字、号源数量 添加到map中
                stringIntegerMap.put(doctor.getDoctorName(), availableAppointments);
            }
        }
        return stringIntegerMap;
    }
}
