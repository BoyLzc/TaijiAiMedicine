package org.example.medicine_ai.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Schedules {
    // MyBatis-Plus提供的注解，用于标识实体类中的主键字段，并指定其在数据库中的行为
    @TableId(type = IdType.AUTO)
    private Long scheduleId;
    private Long doctorId;
    private String workDate; // 日期
    private String timeSlot; // 时间段
    private int maxAppointments; // 最大预约数
    private int availableAppointments; // 可预约数
    private int status; // 启用/停用状态
}
