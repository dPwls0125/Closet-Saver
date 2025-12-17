package com.cholog_ai.closet_saver.domain.embedding.service;

import com.cholog_ai.closet_saver.domain.embedding.model.dto.ImageEmbeddingResponse;
import com.cholog_ai.closet_saver.domain.embedding.model.vo.EmbeddingType;
import com.cholog_ai.closet_saver.domain.embedding.model.vo.EmbeddingValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;

@Service
@Slf4j
public class ImageEmbeddingService {

    private final OkHttpClient client;
    private final ObjectMapper mapper;

    @Value("${embedding.image.url}")
    private String imageEmbeddingUrl;

    public ImageEmbeddingService(ObjectMapper mapper) {
        this.mapper = mapper;
        this.client = new OkHttpClient.Builder()
                .connectTimeout(Duration.ofSeconds(5))
                .readTimeout(Duration.ofSeconds(10))
                .writeTimeout(Duration.ofSeconds(10))
                .build();

    }

    public EmbeddingValue embeddingImage(MultipartFile file) {

        validateInputMultiipartFile(file);

        try {
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(
                            "file",
                            file.getOriginalFilename(),
                            RequestBody.create(file.getBytes(), MediaType.parse(file.getContentType()))
                    )
                    .build();

            Request request = new Request.Builder()
                    .url(imageEmbeddingUrl)
                    .post(requestBody)
                    .build();

            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                log.error("이미지 임베딩에 실패하였습니다.");
                throw new RuntimeException("Image Embedding API 호출 실패");
            }

            String responseBody = response.body().string();

            ImageEmbeddingResponse dto = mapper.readValue(responseBody, ImageEmbeddingResponse.class);

            double[] vector = dto.embeddingVector().stream()
                    .mapToDouble(Double::doubleValue)
                    .toArray();

            return new EmbeddingValue(vector, EmbeddingType.IMAGE);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("이미지 임베딩 중 오류 발생", e);
        }
    }


    private void validateInputMultiipartFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("파일이 null이거나 비어있습니다.");
        }

        String filename = file.getOriginalFilename();
        if (filename.isBlank()) {
            throw new IllegalArgumentException("파일명이 유효하지 않습니다.");
        }
    }
}
