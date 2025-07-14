package org.example.medicine_ai.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

@AiService(
        wiringMode = EXPLICIT,
        chatModel = "openAiChatModel", // 模型注入
        chatMemoryProvider = "chatMemoryProviderXiaoZhi", // 记忆注入
        tools = "appointmentTools",  // 工具注入
        contentRetriever = "contentRetrieverXiaoZhiPincone" // 知识库注入
)
public interface XiaoZhiAgent {
    @SystemMessage(fromResource = "xiaozhi-prompt-template.txt")
    String chat(@MemoryId Long memoryId, @UserMessage String userMessage);
}
