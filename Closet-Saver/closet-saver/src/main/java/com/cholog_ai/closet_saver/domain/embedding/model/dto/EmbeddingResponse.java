package com.cholog_ai.closet_saver.domain.embedding.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class EmbeddingResponse {
    private List<EmbeddingData> data;
    @Data
    public static class EmbeddingData{
        private List<Double> embedding;
        private int index;
    }
}
