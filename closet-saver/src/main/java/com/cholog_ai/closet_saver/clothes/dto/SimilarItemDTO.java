package com.cholog_ai.closet_saver.clothes.dto;


import java.time.LocalDate;

/**
 * 개별 유사 의류 항목의 DTO
 * @param clothId 엔티티 고유 ID
 * @param imageUrl GitHub Raw URL 등 이미지 주소
 * @param name 정규화된 의류 이름
 * @param purchaseDate 구매 시점 (언제 샀는지 알려주는 핵심 정보)
 * @param color 색상 그룹 (BLACK, WHITE, GREY)
 * @param thickness 두께 (THIN, MEDIUM, THICK)
 */
public record SimilarItemDTO(
        Long clothId,
        String imageUrl,
        String name,
        LocalDate purchaseDate, // LocalDate 사용
        String color,
        String thickness
) {
    // Record는 DTO 역할에 필요한 모든 표준 메서드를 자동으로 제공합니다.
}
