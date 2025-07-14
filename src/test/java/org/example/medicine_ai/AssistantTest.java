package org.example.medicine_ai;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.spring.AiService;
import org.example.medicine_ai.assistant.Assistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AssistantTest {
    @Autowired
    private OpenAiChatModel openAiChatModel;

    // 不使用AiService注解，手动配置，较为麻烦
    @Test
    public void testAssistant() {
        Assistant assistant = AiServices.create(Assistant.class, openAiChatModel);
        String result = assistant.chat("我是篮球之神");
        System.out.println(result);
    }

    @Autowired
    Assistant assistant;
    @Test
    public void SpringAssistant(){
        String chat = assistant.chat("我是足球之神");
        System.out.println(chat);
    }
}
