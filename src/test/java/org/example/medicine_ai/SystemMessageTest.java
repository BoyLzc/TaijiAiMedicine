package org.example.medicine_ai;

import org.example.medicine_ai.assistant.MemoryAssistant;
import org.example.medicine_ai.assistant.SeparateChatAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SystemMessageTest {
    @Autowired
    private SeparateChatAssistant separateChatAssistant;
    @Test
    public void testSystemMessage() {
        String answer = separateChatAssistant.chat(6, "我是boylzc");
        System.out.println(answer);
        String answer2 = separateChatAssistant.chat(6, "我是一名程序员");
        System.out.println(answer2);
    }

    @Autowired
    private MemoryAssistant memoryAssistant;
    @Test
    public void testUserMessage(){
        String answer = memoryAssistant.chat("我是boylzc", "李华");
        System.out.println(answer);
        String answer2 = memoryAssistant.chat("我24岁了", "李华");
        System.out.println(answer2);
        String answer3 = memoryAssistant.chat("你叫什么名字", "李华");
        System.out.println(answer3);
    }
}
