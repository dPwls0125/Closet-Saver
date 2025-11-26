package com.cholog_ai.closet_saver.domain.closet.model.vo;

public enum Season {
    SPRING(1), SUMMER(2), FALL(3), WINTER(4);

    private final int index;
    Season(int index) { this.index = index; }
    public int getIndex() { return index; }
}

