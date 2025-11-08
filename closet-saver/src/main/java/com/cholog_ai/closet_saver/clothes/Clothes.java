package com.cholog_ai.closet_saver.clothes;

import com.cholog_ai.closet_saver.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "clothes")
@Getter
public class Clothes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clothId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // users 테이블의 user_id를 참조하는 FK

    @Column(name = "normalized_name", nullable = false)
    private String name;

    @Column(name = "image_url", nullable = false, length = 200)
    private String imageUrl;

    @Column(name = "item_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    @Column(name = "color_group", nullable = false)
    @Enumerated(EnumType.STRING)
    private ColorGroup colorGroup;

    @Column(name = "feature_neck", nullable = false)
    @Enumerated(EnumType.STRING)
    private NeckType neckType;

    @Column(name = "feature_pattern", nullable = false)
    @Enumerated(EnumType.STRING)
    private Pattern pattern;

    @Column(name = "fit_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private FitType fitType;

    @Column(name = "thickness", nullable = false)
    @Enumerated(EnumType.STRING)
    private Thickness thickness;

    @Column(name = "feature_sleeve", nullable = false)
    @Enumerated(EnumType.STRING)
    private SleeveType sleeveType;

    @Column(name = "purchase_date", nullable = false)
    private LocalDate purchasedDate;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected Clothes(){
        this.createdAt = LocalDateTime.now();
    }

}
