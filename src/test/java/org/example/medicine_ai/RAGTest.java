package org.example.medicine_ai;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.onnx.HuggingFaceTokenizer;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RAGTest {
    @Test
    public void testReadDocument() {
        Document document = FileSystemDocumentLoader.loadDocument("D:/IdeaProjects/Medicine_AI/src/main/resources/RAG.txt");
        System.out.println(document.text());
    }

    @Test
    public void testReadDocumentAndStore() {
        Document document = FileSystemDocumentLoader.loadDocument("D:/IdeaProjects/Medicine_AI/src/main/resources/RAG.txt");
        // 基于内存的向量存储
        InMemoryEmbeddingStore<TextSegment> objectInMemoryEmbeddingStore = new InMemoryEmbeddingStore<>();
        // ingest
        // 1 分割文档（默认使用递归分割器，将文档转化为多个文本片段，每个片段不超过300个token）
        // 2 文本向量化
        // 3 将原始文本和向量存储至向量数据库中
        EmbeddingStoreIngestor.ingest(document, objectInMemoryEmbeddingStore);
        // 查看向量数据库中内容
        System.out.println(objectInMemoryEmbeddingStore);
    }

    // 自定义文档分割器
    @Test
    public void testReadDocumentAndStore2() {
        Document document = FileSystemDocumentLoader.loadDocument("D:/IdeaProjects/Medicine_AI/src/main/resources/RAG.txt");
        // 基于内存的向量存储
        InMemoryEmbeddingStore<TextSegment> objectInMemoryEmbeddingStore = new InMemoryEmbeddingStore<>();
        // 每个片段不超过300个token 并且有 30个重叠部分
        DocumentByParagraphSplitter documentByParagraphSplitter = new DocumentByParagraphSplitter
                (300, 30,
                        new HuggingFaceTokenizer());

//        EmbeddingStoreIngestor.ingest(document, objectInMemoryEmbeddingStore);
        EmbeddingStoreIngestor.builder()
                .embeddingStore(objectInMemoryEmbeddingStore)
                .documentSplitter(documentByParagraphSplitter)
                .build()
                .ingest(document);
        // 查看向量数据库中内容
        System.out.println(objectInMemoryEmbeddingStore);
    }

}
