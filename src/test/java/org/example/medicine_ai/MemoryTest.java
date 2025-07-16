package org.example.medicine_ai;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.example.medicine_ai.assistant.Assistant;
import org.example.medicine_ai.assistant.MemoryAssistant;
import org.example.medicine_ai.assistant.SeparateChatAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemoryTest {

    @Autowired
    private OpenAiChatModel openAiChatModel;

    @Test
    public void testMemory() {
        MessageWindowChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);
        Assistant assistant = AiServices.builder(Assistant.class).
                chatLanguageModel(openAiChatModel).
                chatMemory(chatMemory).build();

        String answer = assistant.chat("我是boylzc");
        System.out.println(answer);
        String answer2 = assistant.chat("我是谁？");
        System.out.println(answer2);
    }

    @Autowired
    MemoryAssistant memoryAssistant;
    @Test
    public void testMemoryByMemoryAssistant() {
        String answer = memoryAssistant.chat("李华","我是刘翔");
        System.out.println(answer);
        String answer2 = memoryAssistant.chat("李华","我是谁？");
        System.out.println(answer2);
    }

    // 用户记忆隔离测试
    @Autowired
    SeparateChatAssistant separateChatAssistant;
    @Test
    public void testMemoryBySeparateChatAssistant() {
        String chat = separateChatAssistant.chat(1, "我是boylzc");
        System.out.println(chat);
        String chat2 = separateChatAssistant.chat(1, "我是谁？");
        System.out.println(chat2);
/*        String chat3 = separateChatAssistant.chat(2, "我是xxxx");
        System.out.println(chat3);
        String chat4 = separateChatAssistant.chat(2, "我是谁？");
        System.out.println(chat4);
        String chat5 = separateChatAssistant.chat(1, "我是谁？");
        System.out.println(chat5);*/
    }
}
