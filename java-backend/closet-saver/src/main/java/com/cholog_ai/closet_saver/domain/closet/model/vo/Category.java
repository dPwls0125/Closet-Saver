package com.cholog_ai.closet_saver.domain.closet.model.vo;

public enum Category {
    KNIT(1), OUTER(2), SHIRT(3), PANTS(4), ONEPIECE(5);

    private final int index;
    Category(int index) { this.index = index; }
    public int getIndex() { return index; }
}
