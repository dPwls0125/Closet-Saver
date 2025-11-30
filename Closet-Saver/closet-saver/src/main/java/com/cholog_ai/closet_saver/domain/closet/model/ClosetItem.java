package com.cholog_ai.closet_saver.domain.closet.model;

import com.cholog_ai.closet_saver.domain.closet.model.vo.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class ClosetItem {

    private Long id;                // 옷 식별자
    private String imageUrl;        // 이미지 경로

    private Category category;
    private Season season;
    private Color color;
    private Material material;
    private Style style;

    private double[] embedding;     // 이미지 임베딩

    public ClosetItem(
            Long id,
            String imageUrl,
            Color color,
            Category category,
            Season season,
            Material material,
            Style style,
            double[] embedding
    ) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.color = color;
        this.category = category;
        this.season = season;
        this.material = material;
        this.style = style;
        this.embedding = embedding;
    }

    // --- 도메인 행동 (Business Logic) ---

    /** 특정 속성이 일치하는지 */
    public boolean matchesColor(Color other) {
        return this.color.equals(other);
    }

    public boolean matchesCategory(Category other) {
        return this.category.equals(other);
    }

    public boolean matchesSeason(Season other) {
        return this.season.equals(other);
    }

    public boolean matchesMaterial(Material other) {
        return this.material.equals(other);
    }

    public boolean matchesStyle(Style other) {
        return this.style.equals(other);
    }

    /** 속성 기반 유사도 계산을 위한 벡터 생성 (one-hot or normalized) */
    public double[] toAttributeVector() {
        return new double[]{
                color.getIndex(),
                category.getIndex(),
                season.getIndex(),
                material.getIndex(),
                style.getIndex()
        };
    }
}


