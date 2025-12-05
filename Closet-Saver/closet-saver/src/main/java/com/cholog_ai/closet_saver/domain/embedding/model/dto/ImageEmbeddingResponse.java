package com.cholog_ai.closet_saver.domain.embedding.model.dto;

import java.util.List;

public record ImageEmbeddingResponse(List<Double> embeddingVector) {
}
