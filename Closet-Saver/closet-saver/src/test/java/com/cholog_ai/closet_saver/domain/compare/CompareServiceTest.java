package com.cholog_ai.closet_saver.domain.compare;

import com.cholog_ai.closet_saver.domain.compare.config.SimilaritySettings;
import com.cholog_ai.closet_saver.domain.compare.config.SimilarityWeights;
import com.cholog_ai.closet_saver.domain.compare.model.ComparisonItem;
import com.cholog_ai.closet_saver.domain.compare.model.SimilarityResult;
import com.cholog_ai.closet_saver.domain.compare.service.CompareService;
import com.cholog_ai.closet_saver.domain.embedding.model.vo.EmbeddingType;
import com.cholog_ai.closet_saver.domain.embedding.model.vo.EmbeddingValue;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CompareServiceTest {

    private final CompareService compareService = new CompareService();

    @Test
    void calculates_combined_similarity_with_available_embeddings_and_ranks() {
        EmbeddingValue anchorText = textEmbedding(1.0);
        EmbeddingValue anchorImage = imageEmbedding(1.0);

        ComparisonItem anchor = ComparisonItem.builder(0L)
                .textEmbedding(anchorText)
                .imageEmbedding(anchorImage)
                .build();

        ComparisonItem fullMatch = ComparisonItem.builder(1L)
                .textEmbedding(textEmbedding(1.0))
                .imageEmbedding(imageEmbedding(1.0))
                .build();

        ComparisonItem partialMatch = ComparisonItem.builder(2L)
                .textEmbedding(textEmbedding(1.0))
                .imageEmbedding(imageEmbeddingWithOppositeHalves())
                .build();

        ComparisonItem imageOnlyStrong = ComparisonItem.builder(3L)
                .imageEmbedding(imageEmbedding(1.0))
                .build();

        List<SimilarityResult> results = compareService.compare(
                anchor,
                List.of(fullMatch, partialMatch, imageOnlyStrong),
                SimilaritySettings.defaultSettings()
        );

        assertThat(results).hasSize(3);
        assertThat(results.get(0).getItemId()).isEqualTo(1L);
        assertThat(results.get(0).getCombinedSimilarity()).isEqualTo(1.0);
        assertThat(results.get(1).getItemId()).isEqualTo(3L);
        assertThat(results.get(1).getCombinedSimilarity()).isEqualTo(1.0);
        assertThat(results.get(2).getItemId()).isEqualTo(2L);
        assertThat(results.get(2).getCombinedSimilarity()).isEqualTo(0.4);

        assertThat(results)
                .extracting(SimilarityResult::getSimilarityRank)
                .containsExactly(1, 2, 3);
    }

    @Test
    void skips_candidates_without_shared_embeddings() {
        EmbeddingValue anchorText = textEmbedding(1.0);

        ComparisonItem anchor = ComparisonItem.builder(0L)
                .textEmbedding(anchorText)
                .build();

        ComparisonItem textMatch = ComparisonItem.builder(10L)
                .textEmbedding(textEmbedding(1.0))
                .build();

        ComparisonItem imageOnly = ComparisonItem.builder(11L)
                .imageEmbedding(imageEmbedding(1.0))
                .build();

        List<SimilarityResult> results = compareService.compare(
                anchor,
                List.of(textMatch, imageOnly),
                SimilaritySettings.defaultSettings()
        );

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getItemId()).isEqualTo(10L);
        assertThat(results.get(0).getTextSimilarity()).isEqualTo(1.0);
        assertThat(results.get(0).getImageSimilarity()).isNull();
        assertThat(results.get(0).getCombinedSimilarity()).isEqualTo(1.0);
    }

    @Test
    void applies_threshold_and_top_n_limits() {
        EmbeddingValue anchorImage = imageEmbedding(1.0);

        ComparisonItem anchor = ComparisonItem.builder(0L)
                .imageEmbedding(anchorImage)
                .build();

        ComparisonItem candidateHigh = ComparisonItem.builder(21L)
                .imageEmbedding(imageEmbedding(1.0))
                .build();

        ComparisonItem candidateMid = ComparisonItem.builder(22L)
                .imageEmbedding(imageEmbeddingWithMixedSigns(400, 112))
                .build();

        ComparisonItem candidateLow = ComparisonItem.builder(23L)
                .imageEmbedding(imageEmbeddingWithOppositeHalves())
                .build();

        SimilaritySettings settings = new SimilaritySettings(
                SimilarityWeights.defaultWeights(),
                2,
                0.5
        );

        List<SimilarityResult> results = compareService.compare(
                anchor,
                List.of(candidateHigh, candidateMid, candidateLow),
                settings
        );

        assertThat(results).hasSize(2);
        assertThat(results.get(0).getItemId()).isEqualTo(21L);
        assertThat(results.get(1).getItemId()).isEqualTo(22L);
    }

    private EmbeddingValue textEmbedding(double fillValue) {
        double[] vector = filledVector(1536, fillValue);
        return EmbeddingValue.builder()
                .vector(vector)
                .type(EmbeddingType.TEXT)
                .build();
    }

    private EmbeddingValue imageEmbedding(double fillValue) {
        double[] vector = filledVector(512, fillValue);
        return EmbeddingValue.builder()
                .vector(vector)
                .type(EmbeddingType.IMAGE)
                .build();
    }

    private EmbeddingValue imageEmbeddingWithOppositeHalves() {
        double[] vector = new double[512];
        for (int i = 0; i < vector.length; i++) {
            vector[i] = i < 256 ? 1.0 : -1.0;
        }
        return EmbeddingValue.builder()
                .vector(vector)
                .type(EmbeddingType.IMAGE)
                .build();
    }

    private EmbeddingValue imageEmbeddingWithMixedSigns(int positiveCount, int negativeCount) {
        double[] vector = new double[512];
        for (int i = 0; i < vector.length; i++) {
            if (i < positiveCount) {
                vector[i] = 1.0;
            } else if (i < positiveCount + negativeCount) {
                vector[i] = -1.0;
            } else {
                vector[i] = 0.0;
            }
        }
        return EmbeddingValue.builder()
                .vector(vector)
                .type(EmbeddingType.IMAGE)
                .build();
    }

    private double[] filledVector(int length, double fillValue) {
        double[] vector = new double[length];
        for (int i = 0; i < length; i++) {
            vector[i] = fillValue;
        }
        return vector;
    }
}
