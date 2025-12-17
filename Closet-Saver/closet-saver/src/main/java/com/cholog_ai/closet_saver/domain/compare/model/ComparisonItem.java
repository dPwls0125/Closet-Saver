package com.cholog_ai.closet_saver.domain.compare.model;

import com.cholog_ai.closet_saver.domain.embedding.model.vo.EmbeddingType;
import com.cholog_ai.closet_saver.domain.embedding.model.vo.EmbeddingValue;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class ComparisonItem {

    private final Long id;
    private final Map<EmbeddingType, EmbeddingValue> embeddings;

    private ComparisonItem(final Long id, final Map<EmbeddingType, EmbeddingValue> embeddings) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.embeddings = new EnumMap<>(Objects.requireNonNull(embeddings, "embeddings must not be null"));
    }

    public static Builder builder(final Long id) {
        return new Builder(id);
    }

    public Long getId() {
        return id;
    }

    public Optional<EmbeddingValue> getEmbedding(final EmbeddingType type) {
        return Optional.ofNullable(embeddings.get(type));
    }

    public Map<EmbeddingType, EmbeddingValue> getEmbeddings() {
        return Map.copyOf(embeddings);
    }

    public static class Builder {
        private final Long id;
        private final Map<EmbeddingType, EmbeddingValue> embeddings = new EnumMap<>(EmbeddingType.class);

        private Builder(final Long id) {
            this.id = id;
        }

        public Builder textEmbedding(final EmbeddingValue embeddingValue) {
            if (embeddingValue != null && embeddingValue.getType() != EmbeddingType.TEXT) {
                throw new IllegalArgumentException("textEmbedding must have type TEXT");
            }
            if (embeddingValue != null) {
                embeddings.put(EmbeddingType.TEXT, embeddingValue);
            }
            return this;
        }

        public Builder imageEmbedding(final EmbeddingValue embeddingValue) {
            if (embeddingValue != null && embeddingValue.getType() != EmbeddingType.IMAGE) {
                throw new IllegalArgumentException("imageEmbedding must have type IMAGE");
            }
            if (embeddingValue != null) {
                embeddings.put(EmbeddingType.IMAGE, embeddingValue);
            }
            return this;
        }

        public Builder embedding(final EmbeddingValue embeddingValue) {
            if (embeddingValue != null) {
                embeddings.put(embeddingValue.getType(), embeddingValue);
            }
            return this;
        }

        public ComparisonItem build() {
            return new ComparisonItem(id, embeddings);
        }
    }
}
