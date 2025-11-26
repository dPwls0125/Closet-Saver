package com.cholog_ai.closet_saver.domain.embedding.service;

import org.springframework.stereotype.Service;

@Service
public interface EmbeddingService {

    // 이미지 임베딩 생성
    double[] generateImageEmbedding(byte[] imageBytes);
    double[] generateTextEmbedding(String text);

}
