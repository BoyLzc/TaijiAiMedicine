package org.example.medicine_ai.config;

import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;
import org.example.medicine_ai.store.MongoChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class getIsolateMemoryInfo {
    @Autowired
    private MongoChatMemoryStore mongoChatMemoryStore;

    @Bean
    public ChatMemoryProvider chatMemoryProvider() {
        return memoryId -> MessageWindowChatMemory.
                builder().
                id(memoryId).
                maxMessages(10).
//                chatMemoryStore(new InMemoryChatMemoryStore()).
                chatMemoryStore(mongoChatMemoryStore). // 聊天记忆持久化
                build();
    }
}
