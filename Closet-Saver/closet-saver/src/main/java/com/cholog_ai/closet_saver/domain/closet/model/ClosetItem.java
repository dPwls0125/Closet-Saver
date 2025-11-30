package com.cholog_ai.closet_saver.domain.closet.model;

import jakarta.persistence.Id;
import lombok.NoArgsConstructor;



public class ClosetItem {

    private Long id;                // 옷 식별자
    private String imageUrl;        // 이미지 경로
    private String name;
    private double[] embedding;     // 이미지 임베딩


    public ClosetItem(String imageUrl, String name, double[] embedding){
        this.imageUrl = imageUrl;
        this.name = name;
        this.embedding = embedding;
    }


    public Long getId(){
        return this.id;
    }

}


