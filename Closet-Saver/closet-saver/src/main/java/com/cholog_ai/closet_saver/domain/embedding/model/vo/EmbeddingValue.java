package com.cholog_ai.closet_saver.domain.embedding.model.vo;
import lombok.Builder;

import java.util.Map;
public class EmbeddingValue {
    private final double[] vector;
    private final EmbeddingType type;
    private static final Map<EmbeddingType, Integer> DIMENSION_RULE = Map.of(
            EmbeddingType.TEXT,1536,
            EmbeddingType.IMAGE,512
    );
    @Builder
    public EmbeddingValue(final double[] vector, final EmbeddingType type) {
        validateDimensionOfVector(vector,type);
        this.vector = vector;
        this.type = type;
    }
    public double[] getVector(){
        return vector.clone();
    }
    public EmbeddingType getType(){
        return type;
    }

    public double calculateSimilarity(EmbeddingValue other){

        if (other.getType() != this.getType()){
            throw new IllegalArgumentException("일치하는 타입의 임베딩 값에 대한 유사도만 계산 가능합니다.");
        }

        if (other.getVector() == null || other.getVector().length == 0){
            throw new IllegalArgumentException("비교하고자 하는 EmbeddingValue의 vector 값은 비어있거나 길이가 0이면 안됩니다.");
        }

        if(other.getVector().length != this.vector.length) {
            throw new IllegalArgumentException("벡터의 길이가 다르면 유사도 계산을 진행할 수 없습니다.");
        }

        return cosineSimilarity(this.vector, other.getVector());
    }

    private double cosineSimilarity(double[] a, double[] b){
        double dot = 0, normA = 0, normB = 0;
        for(int i=0; i<a.length; i++){
            dot += a[i] * b[i];
            normA += a[i] * a[i];
            normB += b[i] * b[i];
        }
        return dot / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    private void validateDimensionOfVector(double[] vector, final EmbeddingType type){
        if (vector == null || vector.length == 0){
            throw new IllegalArgumentException("vector는 비어있을 수 없습니다.");
        }

        int expectedDimension = DIMENSION_RULE.get(type);
        if(vector.length != expectedDimension){
            throw new IllegalArgumentException(
                    "Embedding 길이가 맞지 않습니다. type=" + type +
                            ", expected=" + expectedDimension +
                            ", actual=" + vector.length
            );
        }
    }
}
