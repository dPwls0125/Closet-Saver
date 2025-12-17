package com.cholog_ai.closet_saver.domain.compare.config;

import java.util.Objects;

public class SimilaritySettings {

    private final SimilarityWeights weights;
    private final int topN;
    private final double threshold;

    public SimilaritySettings(final SimilarityWeights weights, final int topN, final double threshold) {
        if (topN <= 0) {
            throw new IllegalArgumentException("topN must be greater than 0");
        }
        this.weights = Objects.requireNonNull(weights, "weights must not be null");
        this.topN = topN;
        this.threshold = threshold;
    }

    public static SimilaritySettings defaultSettings() {
        return new SimilaritySettings(SimilarityWeights.defaultWeights(), 5, 0.0);
    }

    public SimilarityWeights getWeights() {
        return weights;
    }

    public int getTopN() {
        return topN;
    }

    public double getThreshold() {
        return threshold;
    }
}
