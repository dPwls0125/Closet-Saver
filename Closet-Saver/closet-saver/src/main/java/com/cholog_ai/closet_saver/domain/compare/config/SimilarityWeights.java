package com.cholog_ai.closet_saver.domain.compare.config;

import com.cholog_ai.closet_saver.domain.embedding.model.vo.EmbeddingType;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class SimilarityWeights {

    private final Map<EmbeddingType, Double> weights;

    public SimilarityWeights(final Map<EmbeddingType, Double> weights) {
        this.weights = new EnumMap<>(Objects.requireNonNull(weights, "weights must not be null"));
    }

    public static SimilarityWeights defaultWeights() {
        Map<EmbeddingType, Double> defaults = new EnumMap<>(EmbeddingType.class);
        defaults.put(EmbeddingType.TEXT, 0.4);
        defaults.put(EmbeddingType.IMAGE, 0.6);
        return new SimilarityWeights(defaults);
    }

    public double weightFor(final EmbeddingType type) {
        return weights.getOrDefault(type, 0.0);
    }

    public Map<EmbeddingType, Double> asMap() {
        return Map.copyOf(weights);
    }
}
