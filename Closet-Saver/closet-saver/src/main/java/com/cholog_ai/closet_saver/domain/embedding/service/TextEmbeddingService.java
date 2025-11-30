package com.cholog_ai.closet_saver.domain.embedding.service;

import com.cholog_ai.closet_saver.domain.embedding.model.dto.EmbeddingResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TextEmbeddingService {


    // TODO : unit, 통합 test 작성
    private static final String EMBEDDING_URL = "https://api.openai.com/v1/embeddings";
    // small : 1536, large : 3072
    private static final String MODEL_NAME = "text-embedding-3-small";

    @Value("${openai.key}")
    private String API_KEY;
    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private static final Map<String,Object> BASE_BODY = Map.of(
            "model",MODEL_NAME,
            "encoding_format","float"
            );
    /*
    * 텍스트를 OpenAI 임베딩 API를 사용해서 double 배열로 변환함.
     */
    public double[] embedText(String text){
        try {
            String requestJson = buildRequestJson(text);

            RequestBody body = RequestBody.create(
                    requestJson,
                    MediaType.parse("application/json")
            );

            Request request = new Request.Builder()
                    .url(EMBEDDING_URL)
                    .header("Authorization", "Bearer" + API_KEY)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();


            if (!response.isSuccessful()) {
                log.error("OpenAI Embedding API Error: {}", responseBody);
                throw new RuntimeException("Embedding API 호출 실패"); // Todo : Exception, error code 커스텀
            }

            EmbeddingResponse embeddingResponse = mapper.readValue(responseBody, EmbeddingResponse.class);
            List<Double> embeddingList = embeddingResponse.getData().get(0).getEmbedding();

            return embeddingList.stream()
                    .mapToDouble(Double::doubleValue)
                    .toArray();

        } catch(Exception e){
            log.error("텍스트 임베딩 실패: {}", e.getMessage());
            throw new RuntimeException("텍스트 임베딩 중 오류 발생"); // Todo : Exception, error code 커스텀
        }
    }

    private String buildRequestJson(String text) throws JsonProcessingException {
        Map<String,Object> body = new HashMap<>(BASE_BODY);
        body.put("input",text);
        return mapper.writeValueAsString(body);
    }
}
