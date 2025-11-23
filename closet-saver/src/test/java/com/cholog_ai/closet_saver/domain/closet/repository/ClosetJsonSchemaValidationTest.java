package com.cholog_ai.closet_saver.domain.closet.repository;

import com.cholog_ai.closet_saver.domain.closet.model.ClosetItem;
import com.cholog_ai.closet_saver.domain.closet.model.vo.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Closet JSON 파일 스키마 검증 테스트")
class ClosetJsonSchemaValidationTest {

    private static final String JSON_FILE_PATH = "/mock/closet.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("JSON 파일이 존재하고 읽을 수 있음")
    void jsonFile_existsAndIsReadable() {
        // when
        InputStream is = getClass().getResourceAsStream(JSON_FILE_PATH);

        // then
        assertThat(is).isNotNull();
    }

    @Test
    @DisplayName("JSON 파일이 유효한 JSON 형식")
    void jsonFile_isValidJson() {
        assertThatCode(() -> {
            InputStream is = getClass().getResourceAsStream(JSON_FILE_PATH);
            objectMapper.readTree(is);
        }).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("JSON 파일이 배열 형식")
    void jsonFile_isArray() throws Exception {
        // when
        InputStream is = getClass().getResourceAsStream(JSON_FILE_PATH);
        JsonNode root = objectMapper.readTree(is);

        // then
        assertThat(root.isArray()).isTrue();
    }

    @Test
    @DisplayName("JSON 파일의 모든 객체가 필수 필드를 포함")
    void jsonFile_allObjectsHaveRequiredFields() throws Exception {
        // when
        InputStream is = getClass().getResourceAsStream(JSON_FILE_PATH);
        JsonNode root = objectMapper.readTree(is);

        // then
        assertThat(root.isArray()).isTrue();
        for (JsonNode node : root) {
            assertThat(node.has("id")).isTrue();
            assertThat(node.has("imageUrl")).isTrue();
            assertThat(node.has("color")).isTrue();
            assertThat(node.has("category")).isTrue();
            assertThat(node.has("season")).isTrue();
            assertThat(node.has("material")).isTrue();
            assertThat(node.has("style")).isTrue();
            assertThat(node.has("embedding")).isTrue();
        }
    }

    @Test
    @DisplayName("JSON 파일의 모든 ID가 고유함")
    void jsonFile_allIdsAreUnique() throws Exception {
        // when
        InputStream is = getClass().getResourceAsStream(JSON_FILE_PATH);
        JsonNode root = objectMapper.readTree(is);

        // then
        Set<Long> ids = new HashSet<>();
        for (JsonNode node : root) {
            long id = node.get("id").asLong();
            assertThat(ids.add(id))
                    .withFailMessage("Duplicate ID found: " + id)
                    .isTrue();
        }
    }

    @Test
    @DisplayName("JSON 파일의 모든 color 값이 유효한 enum 값")
    void jsonFile_allColorsAreValid() throws Exception {
        // when
        InputStream is = getClass().getResourceAsStream(JSON_FILE_PATH);
        JsonNode root = objectMapper.readTree(is);

        // then
        Set<String> validColors = Set.of("WHITE", "BLACK", "BEIGE", "GREY", "BLUE");
        for (JsonNode node : root) {
            String color = node.get("color").asText();
            assertThat(validColors)
                    .withFailMessage("Invalid color value: " + color)
                    .contains(color);
        }
    }

    @Test
    @DisplayName("JSON 파일의 모든 category 값이 유효한 enum 값")
    void jsonFile_allCategoriesAreValid() throws Exception {
        // when
        InputStream is = getClass().getResourceAsStream(JSON_FILE_PATH);
        JsonNode root = objectMapper.readTree(is);

        // then
        Set<String> validCategories = Set.of("KNIT", "OUTER", "SHIRT", "PANTS", "ONEPIECE");
        for (JsonNode node : root) {
            String category = node.get("category").asText();
            assertThat(validCategories)
                    .withFailMessage("Invalid category value: " + category)
                    .contains(category);
        }
    }

    @Test
    @DisplayName("JSON 파일의 모든 season 값이 유효한 enum 값")
    void jsonFile_allSeasonsAreValid() throws Exception {
        // when
        InputStream is = getClass().getResourceAsStream(JSON_FILE_PATH);
        JsonNode root = objectMapper.readTree(is);

        // then
        Set<String> validSeasons = Set.of("SPRING", "SUMMER", "FALL", "WINTER");
        for (JsonNode node : root) {
            String season = node.get("season").asText();
            assertThat(validSeasons)
                    .withFailMessage("Invalid season value: " + season)
                    .contains(season);
        }
    }

    @Test
    @DisplayName("JSON 파일의 모든 material 값이 유효한 enum 값")
    void jsonFile_allMaterialsAreValid() throws Exception {
        // when
        InputStream is = getClass().getResourceAsStream(JSON_FILE_PATH);
        JsonNode root = objectMapper.readTree(is);

        // then
        Set<String> validMaterials = Set.of("COTTON", "WOOL", "POLY", "LINEN", "LEATHER");
        for (JsonNode node : root) {
            String material = node.get("material").asText();
            assertThat(validMaterials)
                    .withFailMessage("Invalid material value: " + material)
                    .contains(material);
        }
    }

    @Test
    @DisplayName("JSON 파일의 모든 style 값이 유효한 enum 값")
    void jsonFile_allStylesAreValid() throws Exception {
        // when
        InputStream is = getClass().getResourceAsStream(JSON_FILE_PATH);
        JsonNode root = objectMapper.readTree(is);

        // then
        Set<String> validStyles = Set.of("MINIMAL", "CASUAL", "STREET", "CLASSIC", "FEMININE");
        for (JsonNode node : root) {
            String style = node.get("style").asText();
            assertThat(validStyles)
                    .withFailMessage("Invalid style value: " + style)
                    .contains(style);
        }
    }

    @Test
    @DisplayName("JSON 파일의 모든 embedding이 배열 형식")
    void jsonFile_allEmbeddingsAreArrays() throws Exception {
        // when
        InputStream is = getClass().getResourceAsStream(JSON_FILE_PATH);
        JsonNode root = objectMapper.readTree(is);

        // then
        for (JsonNode node : root) {
            JsonNode embedding = node.get("embedding");
            assertThat(embedding.isArray())
                    .withFailMessage("Embedding for id " + node.get("id") + " is not an array")
                    .isTrue();
        }
    }

    @Test
    @DisplayName("JSON 파일의 모든 imageUrl이 비어있지 않음")
    void jsonFile_allImageUrlsAreNotEmpty() throws Exception {
        // when
        InputStream is = getClass().getResourceAsStream(JSON_FILE_PATH);
        JsonNode root = objectMapper.readTree(is);

        // then
        for (JsonNode node : root) {
            String imageUrl = node.get("imageUrl").asText();
            assertThat(imageUrl)
                    .withFailMessage("Empty imageUrl for id " + node.get("id"))
                    .isNotEmpty();
        }
    }

    @Test
    @DisplayName("JSON 파일을 ClosetItem 객체로 역직렬화 가능")
    void jsonFile_canBeDeserializedToClosetItem() {
        assertThatCode(() -> {
            InputStream is = getClass().getResourceAsStream(JSON_FILE_PATH);
            List<ClosetItem> items = objectMapper.readValue(
                    is,
                    new TypeReference<List<ClosetItem>>() {}
            );
            assertThat(items).isNotEmpty();
        }).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("역직렬화된 ClosetItem 객체들이 올바른 데이터를 가짐")
    void deserializedClosetItems_haveCorrectData() throws Exception {
        // when
        InputStream is = getClass().getResourceAsStream(JSON_FILE_PATH);
        List<ClosetItem> items = objectMapper.readValue(
                is,
                new TypeReference<List<ClosetItem>>() {}
        );

        // then
        assertThat(items).isNotEmpty();
        for (ClosetItem item : items) {
            assertThat(item.getId()).isNotNull();
            assertThat(item.getImageUrl()).isNotEmpty();
            assertThat(item.getColor()).isNotNull();
            assertThat(item.getCategory()).isNotNull();
            assertThat(item.getSeason()).isNotNull();
            assertThat(item.getMaterial()).isNotNull();
            assertThat(item.getStyle()).isNotNull();
            assertThat(item.getEmbedding()).isNotNull();
        }
    }

    @Test
    @DisplayName("JSON 파일에 최소 1개 이상의 아이템이 있음")
    void jsonFile_hasAtLeastOneItem() throws Exception {
        // when
        InputStream is = getClass().getResourceAsStream(JSON_FILE_PATH);
        JsonNode root = objectMapper.readTree(is);

        // then
        assertThat(root.size()).isGreaterThanOrEqualTo(1);
    }
}