package com.well.demo;

import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaEmbeddingOptions;

import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;



@Configuration
public class AiConfig {
    // ... 在代码中
    RestClient.Builder customRestClientBuilder = RestClient.builder()
            // 可以在这里配置认证拦截器等，但 Ollama 通常不需要
            ;
    WebClient.Builder customWebClientBuilder = WebClient.builder();
    @Value("${spring.ai.ollama.base-url}")
    private String ollamaBaseUrl;

    public static final String COLLECTION_NAME = "my_knowledge_base";
/*
    @Bean
    public EmbeddingModel embeddingModel() {
        OllamaApi ollamaApi = OllamaApi.builder()
                .baseUrl(ollamaBaseUrl)
                .restClientBuilder(customRestClientBuilder)     // 注入自定义的 RestClient
                .webClientBuilder(customWebClientBuilder)       // 注入自定义的 WebClient (用于流式调用)
                .build();
        return OllamaEmbeddingModel.builder()
                .ollamaApi(ollamaApi)
                // ② 使用 .defaultOptions()
                .defaultOptions(OllamaEmbeddingOptions.builder()
                        .model("nomic-embed-text")
                        .build())
                .build();
    }*/

    @Bean
    public QdrantClient qdrantClient() {
        return new QdrantClient(
                QdrantGrpcClient.newBuilder("192.168.0.241", 6334, false).build()
        );
    }

/*    @Bean
    public VectorStore vectorStore(QdrantClient qdrantClient, EmbeddingModel embeddingModel) {
        // 使用新的 Builder 模式，通过 .builder(...) 构建，并显式声明自动创建集合
        return QdrantVectorStore.builder(qdrantClient, embeddingModel)
                .collectionName(COLLECTION_NAME)
                .initializeSchema(true) // '选择加入'，确保集合被创建
                .build();
    }*/

}