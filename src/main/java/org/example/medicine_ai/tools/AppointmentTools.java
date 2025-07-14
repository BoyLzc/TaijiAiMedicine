package org.example.medicine_ai.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.example.medicine_ai.entity.Appointment;
import org.example.medicine_ai.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppointmentTools {
    @Autowired
    private AppointmentService appointmentService;

    @Tool("根据参数，先执行工具方法queryDepartment查询是否可预约，并直接回答是否可预约，并让用户确认所有预约信息是否正确，" +
            "然后执行该方法，对预约记录进行持久化保存。")
//    @Tool
    public String bookAppointment(Appointment appointment) {
        // 先查询有无号源
//        queryDepartment(appointment.getDepartment(),
//                appointment.getDate(),
//                appointment.getTime(),
//                appointment.getDoctorName());
        // 查找数据库是否包含对应预约记录
        System.out.println("预约————查询预约记录");
        Appointment appointmentDB = appointmentService.getOne(appointment);
        if (appointmentDB == null) {
            appointment.setId(null); // 防止大模型幻觉设置了 id
            if(appointmentService.save(appointment)) {
                System.out.println("预约成功，并返回预约详情");
                return "预约成功，并返回预约详情";
            } else {
                System.out.println("预约失败");
                return "预约失败";
            }
        }
        System.out.println("您已预约过该科室、时间、医生，请勿重复预约");
        return "您在相同的科室和时间已有预约";
    }

    @Tool("取消预约")
//    @Tool
    public String cancelAppointment(Appointment appointment) {
        System.out.println("取消预约————查询预约记录");
        Appointment appointmentDB = appointmentService.getOne(appointment);
        if (appointmentDB != null) {
            System.out.println("预约记录不为空");
            // 删除数据库中的预约记录
            if (appointmentService.removeById(appointmentDB.getId())) {
                System.out.println("取消成功");
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
            @P(value = "时间，可选值：上午、下午") String time,
            @P(value = "医生名称") String doctor) {
        // 模拟查询数据库，返回是否可预约
        System.out.println("查询是否有号源");
        System.out.println("科室名称：" + department);
        System.out.println("日期：" + date);
        System.out.println("时间：" + time);
        System.out.println("医生名称：" + doctor);
        return true;
    }
}
