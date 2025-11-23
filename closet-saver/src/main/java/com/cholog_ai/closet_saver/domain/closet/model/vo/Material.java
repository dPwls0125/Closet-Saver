package com.cholog_ai.closet_saver.domain.closet.model.vo;

public enum Material {
    COTTON(1), WOOL(2), POLY(3), LINEN(4), LEATHER(5);

    private final int index;
    Material(int index) { this.index = index; }
    public int getIndex() { return index; }
}
