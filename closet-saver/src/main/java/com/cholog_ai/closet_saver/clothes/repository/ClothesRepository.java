package com.cholog_ai.closet_saver.clothes.repository;

import com.cholog_ai.closet_saver.clothes.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClothesRepository extends JpaRepository<Clothes, Long> {

    /**
     * F-2.1 유사 상품 분류 로직: 7가지 필터 조건으로 동적 검색을 수행합니다.
     * 모든 파라미터는 NULL일 수 있으며, NULL일 경우 해당 WHERE 조건은 무시됩니다 (Coalesce 기능 사용).
     */
    @Query("SELECT c FROM Clothes c " +
            "WHERE c.user.userId = :userId " +
            "AND (:itemType IS NULL OR c.itemType = :itemType) " +
            "AND (:colorGroup IS NULL OR c.colorGroup = :colorGroup) " +
            "AND (:neckType IS NULL OR c.neckType = :neckType) " +
            "AND (:fitType IS NULL OR c.fitType = :fitType) " +
            "AND (:thickness IS NULL OR c.thickness = :thickness) " +
            "AND (:sleeveType IS NULL OR c.sleeveType = :sleeveType) " +
            "AND (:pattern IS NULL OR c.pattern = :pattern)")
    List<Clothes> findSimilarByFilters(
            @Param("userId") Long userId,
            @Param("itemType") ItemType itemType,
            @Param("colorGroup") ColorGroup colorGroup,
            @Param("neckType") NeckType neckType,
            @Param("fitType") FitType fitType,
            @Param("thickness") Thickness thickness,
            @Param("sleeveType") SleeveType sleeveType,
            @Param("pattern") Pattern pattern
    );
}
