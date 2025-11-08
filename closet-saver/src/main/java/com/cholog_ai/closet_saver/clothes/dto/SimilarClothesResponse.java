package com.cholog_ai.closet_saver.clothes.dto;

import java.util.List;

/**
 * API 응답 최상위 객체 (F-2.2 정보 제시)
 * @param foundCount 발견된 유사 의류의 총 개수
 * @param similarItems 유사 의류 목록
 */
public record SimilarClothesResponse(
        int foundCount,
        List<SimilarItemDTO> similarItems
) {
    // Record는 모든 필드를 포함하는 생성자, getter, equals(), hashCode(), toString()을 자동으로 제공합니다.
}
