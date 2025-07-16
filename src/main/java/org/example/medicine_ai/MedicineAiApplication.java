package org.example.medicine_ai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.example.medicine_ai.mapper")
public class MedicineAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicineAiApplication.class, args);
    }

}
