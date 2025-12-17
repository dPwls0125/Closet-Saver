package com.cholog_ai.closet_saver.unit;

import com.cholog_ai.closet_saver.domain.embedding.model.vo.EmbeddingType;
import com.cholog_ai.closet_saver.domain.embedding.model.vo.EmbeddingValue;
import com.cholog_ai.closet_saver.domain.embedding.service.ImageEmbeddingService;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ImageEmbeddingServiceTest {

    @Autowired
    ImageEmbeddingService imageEmbeddingService;
    private MockWebServer mockServer;

    @BeforeEach
    void setUp() throws Exception {
        mockServer = new MockWebServer();
        mockServer.start();

        // 테스트용 URL 주입
        ReflectionTestUtils.setField(
                imageEmbeddingService, // 대상 객체
                "imageEmbeddingUrl", // 대상 필드
                mockServer.url("/embed/image").toString() // 주입할 값
        );
    }

    @AfterEach
    void tearDown() throws Exception {
        mockServer.shutdown();
    }

    @Test
    void 이미지_임베딩_응답이_파싱된다() throws Exception {

        List<Double> vector512 = new ArrayList<>();
        for(int i=0; i<512; i++){
            vector512.add(i / 1000.0);
        }
        String vectorJson = vector512.toString();

        mockServer.enqueue(
                new MockResponse()
                        .setBody("""
                                {
                                    "embeddingVector" : %s
                                }
                                """.formatted(vectorJson)
                        )
                        .setResponseCode(200)
        );

        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "test.png",
                "image/png",
                "dummy image".getBytes()
        );

        EmbeddingValue result = imageEmbeddingService.embeddingImage(mockFile);
        assertThat(result.getVector()).hasSize(512);
        assertThat(result.getType()).isEqualTo(EmbeddingType.IMAGE);
    }
}
