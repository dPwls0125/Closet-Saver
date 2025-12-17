package com.cholog_ai.closet_saver.domain.compare.model;

import java.util.Objects;

public class SimilarityResult {

    private final Long itemId;
    private final Double textSimilarity;
    private final Double imageSimilarity;
    private final double combinedSimilarity;
    private final int similarityRank;

    public SimilarityResult(
            final Long itemId,
            final Double textSimilarity,
            final Double imageSimilarity,
            final double combinedSimilarity,
            final int similarityRank
    ) {
        this.itemId = Objects.requireNonNull(itemId, "itemId must not be null");
        this.textSimilarity = textSimilarity;
        this.imageSimilarity = imageSimilarity;
        this.combinedSimilarity = combinedSimilarity;
        this.similarityRank = similarityRank;
    }

    public Long getItemId() {
        return itemId;
    }

    public Double getTextSimilarity() {
        return textSimilarity;
    }

    public Double getImageSimilarity() {
        return imageSimilarity;
    }

    public double getCombinedSimilarity() {
        return combinedSimilarity;
    }

    public int getSimilarityRank() {
        return similarityRank;
    }

    public SimilarityResult withRank(final int rank) {
        return new SimilarityResult(itemId, textSimilarity, imageSimilarity, combinedSimilarity, rank);
    }
}
