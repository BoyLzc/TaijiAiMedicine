package org.example.medicine_ai.bean;

import lombok.Data;

@Data
public class ChatForm {
    private Long memoryId; // 对话id
    private String userMessage; // 用户问题
}
