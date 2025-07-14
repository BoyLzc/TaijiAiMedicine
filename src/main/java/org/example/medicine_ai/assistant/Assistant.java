package org.example.medicine_ai.assistant;

import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

// 要指明模型 因为配置了多个
@AiService(wiringMode = AiServiceWiringMode.EXPLICIT, chatModel = "openAiChatModel")
public interface Assistant {
    String chat(String userMessage);
}
