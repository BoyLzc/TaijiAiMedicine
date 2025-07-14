package org.example.medicine_ai;

import org.example.medicine_ai.assistant.SeparateChatAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ToolsTest {
    @Autowired
    private SeparateChatAssistant separateChatAssistant;

    @Test
    public void testTools() {
        String result = separateChatAssistant.chat(15, "请计算1+1，475695037565的平方根是多少？");
        System.out.println(result);
    }
}
