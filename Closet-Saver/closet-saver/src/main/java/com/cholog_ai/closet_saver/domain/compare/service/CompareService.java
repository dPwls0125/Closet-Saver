package com.cholog_ai.closet_saver.domain.compare.service;

import com.cholog_ai.closet_saver.domain.compare.config.SimilaritySettings;
import com.cholog_ai.closet_saver.domain.compare.model.ComparisonItem;
import com.cholog_ai.closet_saver.domain.compare.model.SimilarityResult;
import com.cholog_ai.closet_saver.domain.embedding.model.vo.EmbeddingType;
import com.cholog_ai.closet_saver.domain.embedding.model.vo.EmbeddingValue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;

public class CompareService {

    private final SimilarityCalculator similarityCalculator;
    private final WeightedSimilarityCombiner weightedSimilarityCombiner;

    public CompareService() {
        this(new SimilarityCalculator(), new WeightedSimilarityCombiner());
    }

    public CompareService(final SimilarityCalculator similarityCalculator,
                          final WeightedSimilarityCombiner weightedSimilarityCombiner) {
        this.similarityCalculator = Objects.requireNonNull(similarityCalculator, "similarityCalculator must not be null");
        this.weightedSimilarityCombiner = Objects.requireNonNull(weightedSimilarityCombiner, "weightedSimilarityCombiner must not be null");
    }

    public List<SimilarityResult> compare(final ComparisonItem anchor,
                                          final List<ComparisonItem> candidates,
                                          final SimilaritySettings settings) {
        Objects.requireNonNull(anchor, "anchor must not be null");
        Objects.requireNonNull(candidates, "candidates must not be null");
        Objects.requireNonNull(settings, "settings must not be null");

        List<SimilarityResult> results = new ArrayList<>();

        for (ComparisonItem candidate : candidates) {
            Map<EmbeddingType, Double> similarities = new EnumMap<>(EmbeddingType.class);

            calculateSimilarity(anchor.getEmbedding(EmbeddingType.TEXT), candidate.getEmbedding(EmbeddingType.TEXT))
                    .ifPresent(similarity -> similarities.put(EmbeddingType.TEXT, similarity));

            calculateSimilarity(anchor.getEmbedding(EmbeddingType.IMAGE), candidate.getEmbedding(EmbeddingType.IMAGE))
                    .ifPresent(similarity -> similarities.put(EmbeddingType.IMAGE, similarity));

            if (similarities.isEmpty()) {
                continue;
            }

            OptionalDouble combined = weightedSimilarityCombiner.combine(similarities, settings.getWeights());
            if (combined.isEmpty() || combined.getAsDouble() < settings.getThreshold()) {
                continue;
            }

            results.add(new SimilarityResult(
                    candidate.getId(),
                    similarities.get(EmbeddingType.TEXT),
                    similarities.get(EmbeddingType.IMAGE),
                    combined.getAsDouble(),
                    0
            ));
        }

        results.sort(Comparator.comparingDouble(SimilarityResult::getCombinedSimilarity).reversed());
        List<SimilarityResult> rankedResults = new ArrayList<>();
        int rank = 1;
        for (SimilarityResult result : results) {
            rankedResults.add(result.withRank(rank++));
        }

        int endIndex = Math.min(settings.getTopN(), rankedResults.size());
        return rankedResults.subList(0, endIndex);
    }

    private Optional<Double> calculateSimilarity(final Optional<EmbeddingValue> anchorEmbedding,
                                                 final Optional<EmbeddingValue> candidateEmbedding) {
        if (anchorEmbedding.isEmpty() || candidateEmbedding.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(similarityCalculator.calculate(anchorEmbedding.get(), candidateEmbedding.get()));
    }
}
