package org.example.medicine_ai.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
// 医生实体
public class Doctors {
    @TableId(type = IdType.AUTO)
    private Long doctorId;
    private String doctorName;
    private String department;
}
