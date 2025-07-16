package org.example.medicine_ai.config;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.example.medicine_ai.store.MongoChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class XiaoZhiAgentConfig {
    @Autowired
    private MongoChatMemoryStore mongoChatMemoryStore;

    @Bean
    public ChatMemoryProvider chatMemoryProviderXiaoZhi() {
        return memoryId -> MessageWindowChatMemory.
                builder().
                id(memoryId).
                maxMessages(20).
        chatMemoryStore(mongoChatMemoryStore). // 聊天记忆持久化
                        build();
    }

    // 存入内存向量数据库 构建RAG
    @Bean
    public ContentRetriever contentRetrieverXiaoZhi() {

        Document document1 = FileSystemDocumentLoader.loadDocument("D:\\IdeaProjects\\Medicine_AI\\src\\main\\resources\\医院地址.txt");
        Document document2 = FileSystemDocumentLoader.loadDocument("D:\\IdeaProjects\\Medicine_AI\\src\\main\\resources\\医院简介.txt");
        Document document3 = FileSystemDocumentLoader.loadDocument("D:\\IdeaProjects\\Medicine_AI\\src\\main\\resources\\消化内科.txt");
        Document document4 = FileSystemDocumentLoader.loadDocument("D:\\IdeaProjects\\Medicine_AI\\src\\main\\resources\\耳鼻喉科.txt");
        List<Document> list = Arrays.asList(document1, document2, document3, document4);

        // 内存向量存储
        InMemoryEmbeddingStore<TextSegment> objectInMemoryEmbeddingStore = new InMemoryEmbeddingStore<>();
        // 将文档进行分词、向量转化、存储至向量数据库
        EmbeddingStoreIngestor.ingest(list, objectInMemoryEmbeddingStore);

        return EmbeddingStoreContentRetriever.from(objectInMemoryEmbeddingStore);
    }

    @Autowired
    private EmbeddingModel embeddingModel;
    @Autowired
    private EmbeddingStore embeddingStore;
    // 持久化存储
    @Bean
    public ContentRetriever contentRetrieverXiaoZhiPincone() {
        // 本文向量化并存入向量数据库
        return EmbeddingStoreContentRetriever.builder().
                embeddingModel(embeddingModel).
                embeddingStore(embeddingStore).
                maxResults(1).
                minScore(0.8).
                build();
    }

}
