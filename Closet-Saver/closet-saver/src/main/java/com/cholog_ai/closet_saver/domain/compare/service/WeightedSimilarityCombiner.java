package com.cholog_ai.closet_saver.domain.compare.service;

import com.cholog_ai.closet_saver.domain.compare.config.SimilarityWeights;
import com.cholog_ai.closet_saver.domain.embedding.model.vo.EmbeddingType;

import java.util.Map;
import java.util.OptionalDouble;

public class WeightedSimilarityCombiner {

    public OptionalDouble combine(final Map<EmbeddingType, Double> similarities, final SimilarityWeights weights) {
        double weightedSum = 0.0;
        double totalWeight = 0.0;

        for (Map.Entry<EmbeddingType, Double> entry : similarities.entrySet()) {
            double weight = weights.weightFor(entry.getKey());
            if (weight <= 0) {
                continue;
            }
            weightedSum += entry.getValue() * weight;
            totalWeight += weight;
        }

        if (totalWeight == 0) {
            return OptionalDouble.empty();
        }

        return OptionalDouble.of(weightedSum / totalWeight);
    }
}
