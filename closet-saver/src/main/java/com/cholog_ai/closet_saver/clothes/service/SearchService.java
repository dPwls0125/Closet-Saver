package com.cholog_ai.closet_saver.clothes.service;

import com.cholog_ai.closet_saver.clothes.*;
import com.cholog_ai.closet_saver.clothes.repository.ClothesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SearchService {

    private final ClothesRepository clothesRepository;

    public SearchService(ClothesRepository clothesRepository) {
        this.clothesRepository = clothesRepository;
    }

    // (생성자 주입 생략)

    /**
     * F-2.1: 7가지 ENUM 필터 조건에 따라 사용자의 유사 의류 목록을 조회합니다.
     * Controller에서 넘어온 String 값을 ENUM으로 변환하고,
     * 누락된 값은 NULL로 처리하여 Repository에 전달합니다.
     */
    public List<Clothes> searchSimilarItems(
            Long userId,
            String itemTypeStr,
            String colorGroupStr,
            String neckTypeStr,
            String fitTypeStr,
            String thicknessStr,
            String sleeveTypeStr,
            String patternStr
    ) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID must not be null.");
        }

        // 1. String 값을 ENUM으로 안전하게 변환 (변환 실패 시 NULL 반환)
        ItemType itemType = safeValueOf(ItemType.class, itemTypeStr);
        ColorGroup colorGroup = safeValueOf(ColorGroup.class, colorGroupStr);
        NeckType neckType = safeValueOf(NeckType.class, neckTypeStr);
        FitType fitType = safeValueOf(FitType.class, fitTypeStr);
        Thickness thickness = safeValueOf(Thickness.class, thicknessStr);
        SleeveType sleeveType = safeValueOf(SleeveType.class, sleeveTypeStr);
        Pattern pattern = safeValueOf(Pattern.class, patternStr);

        // 2. Repository 호출 (NULL 값은 JPQL에서 무시됨)
        return clothesRepository.findSimilarByFilters(
                userId, itemType, colorGroup, neckType, fitType, thickness, sleeveType, pattern
        );
    }

    /**
     * 안전하게 String 값을 ENUM으로 변환하는 헬퍼 메서드
     * (유효하지 않은 값이 들어오면 NULL을 반환하여 쿼리에서 무시되도록 함)
     */
    private <T extends Enum<T>> T safeValueOf(Class<T> enumClass, String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        try {
            return Enum.valueOf(enumClass, name.toUpperCase());
        } catch (IllegalArgumentException e) {
            // 유효하지 않은 ENUM 값이 들어왔을 경우 NULL 반환 (오류 처리)
            return null;
        }
    }
}
