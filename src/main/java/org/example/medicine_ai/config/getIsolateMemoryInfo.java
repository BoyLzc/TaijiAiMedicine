package org.example.medicine_ai.config;

import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import org.example.medicine_ai.store.MongoChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class getIsolateMemoryInfo {
    @Autowired // 注入
    private MongoChatMemoryStore mongoChatMemoryStore;
    @Bean
    public ChatMemoryProvider chatMemoryProvider() {
        return memoryId -> MessageWindowChatMemory.
                builder().
                id(memoryId).
                maxMessages(10).
                chatMemoryStore(mongoChatMemoryStore). // 聊天记忆持久化
                build();
    }
}
