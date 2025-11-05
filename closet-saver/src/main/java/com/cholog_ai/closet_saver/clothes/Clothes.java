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

import java.time.LocalDateTime;

@Entity
@Table(name = "clothes")
public class Clothes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clothId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // users 테이블의 user_id를 참조하는 FK

    @Column(name = "item_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    @Column(name = "color_group", nullable = false)
    private ColorGroup colorGroup;

    @Column(name = "feature_neck", nullable = false)
    private NeckType neckType;

    @Column(name = "fit_type", nullable = false)
    private FitType fitType;

    @Column(name = "thickness", nullable = false)
    private Thickness thickness;

    @Column(name = "feature_sleeve", nullable = false)
    private SleeveType sleeveType;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected Clothes(){
        this.createdAt = LocalDateTime.now();
    }

}
