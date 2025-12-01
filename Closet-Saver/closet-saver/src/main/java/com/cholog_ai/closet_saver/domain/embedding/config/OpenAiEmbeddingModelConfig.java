package com.cholog_ai.closet_saver.domain.embedding.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OpenAiEmbeddingModelConfig implements EmbeddingModelConfig{

    @Value("${openai.key}")
    private String API_KEY;

    @Override
    public String getUrl() {
        return "https://api.openai.com/v1/embeddings";
    }

    @Override
    public String getModelName() {
        return "text-embedding-3-small";
    }

    @Override
    public String getEncodingFormat() {
        return "float";
    }

    @Override
    public String getKey() {
        return API_KEY;
    }

}
