package org.example.medicine_ai.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pinecone.PineconeEmbeddingStore;
import dev.langchain4j.store.embedding.pinecone.PineconeServerlessIndexConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddingStoreConfig {
    @Autowired
    private EmbeddingModel embeddingModel;

    @Bean // 以便于自动注入
    public EmbeddingStore<TextSegment> embeddingStore() {
        // 创建向量存储
        EmbeddingStore<TextSegment> embeddingStore = PineconeEmbeddingStore.builder()
                .apiKey(System.getenv("PINECONE_API_KEY")) // 从环境变量读取
                .index("xiaozhi-index") // 如果指定所有不存在，将创建一个新的索引
                .nameSpace("xiaozhi-namespace") // 如果指定的名称空间不存在，将创建一个新的名称空间
                .createIndex(PineconeServerlessIndexConfig
                        .builder()
                        .cloud("AWS") // 指定索引部署在AWS云服务上
                        .region("us-east-1") // 指定索引所在AWS区域为 us-east-1
                        .dimension(embeddingModel.dimension()) // 指定索引的向量维度
                        .build())
                .build();
        return embeddingStore;
    }
}
