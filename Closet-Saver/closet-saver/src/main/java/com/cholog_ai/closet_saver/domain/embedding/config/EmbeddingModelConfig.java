package com.cholog_ai.closet_saver.domain.embedding.config;

public interface EmbeddingModelConfig {
    String getUrl();
    String getModelName();
    String getEncodingFormat();
    String getKey();
}
