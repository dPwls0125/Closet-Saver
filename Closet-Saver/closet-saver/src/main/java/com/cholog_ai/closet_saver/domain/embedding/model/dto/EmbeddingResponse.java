package com.cholog_ai.closet_saver.domain.embedding.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmbeddingResponse {

    private List<EmbeddingData> data;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class EmbeddingData {
        private List<Double> embedding;
        private int index;
    }
}
