package com.cholog_ai.closet_saver.e2e;

import com.cholog_ai.closet_saver.domain.embedding.model.vo.EmbeddingType;
import com.cholog_ai.closet_saver.domain.embedding.model.vo.EmbeddingValue;
import com.cholog_ai.closet_saver.domain.embedding.service.TextEmbeddingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TextEmbeddingServiceE2ETest {

    @Autowired
    private TextEmbeddingService textEmbeddingService;

    // TODO : 매번 돌릴 필요 없는 test이므로 CI/CD 시에, test에서 제외할 수 있도록 하기.
    @Test
    void embedText_realOpenAI_success() {

        // given
        String inputText = "Hello world! This is an E2E test.";

        // when
        EmbeddingValue result = textEmbeddingService.embedText(inputText);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getType()).isEqualTo(EmbeddingType.TEXT);

        // text-embedding-3-small → 1536차원
        assertThat(result.getVector().length)
                .isGreaterThan(100)           // 최소 길이 검증
                .isEqualTo(1536);             // 정확한 길이 검증

        System.out.println("Embedding dimension = " + result.getVector().length);
    }
}
