package org.example.medicine_ai.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

// 自动注入时的上下文配置
@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "openAiChatModel",
        chatMemoryProvider = "chatMemoryProvider", // bean 记忆注入
        tools = "calculatorTools" // 工具类 计算
)
// 每个用户的记忆隔离
public interface SeparateChatAssistant {
    // 提示词
//    @SystemMessage("你是我的好朋友，请用东北话回答问题， 今天是{{current_date}}")
    @SystemMessage(fromResource = "system.txt")
    String chat(@MemoryId int memoryId, @UserMessage String userMessage);
}
