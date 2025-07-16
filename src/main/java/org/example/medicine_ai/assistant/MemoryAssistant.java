package org.example.medicine_ai.assistant;

import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "openAiChatModel",
        chatMemory = "chatMemory" // 记忆注入
)
public interface MemoryAssistant {
    @UserMessage(fromResource = "userSystem.txt")
    // 用 @V指定参数 一般用于多个参数的情况下
    String chat(@V("message") String userMessage, @V("message2") String userMessage2);
}
