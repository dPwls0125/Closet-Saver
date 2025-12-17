package com.cholog_ai.closet_saver.domain.compare.service;

import com.cholog_ai.closet_saver.domain.embedding.model.vo.EmbeddingValue;

public class SimilarityCalculator {

    public double calculate(final EmbeddingValue anchor, final EmbeddingValue candidate) {
        return anchor.calculateSimilarity(candidate);
    }
}
