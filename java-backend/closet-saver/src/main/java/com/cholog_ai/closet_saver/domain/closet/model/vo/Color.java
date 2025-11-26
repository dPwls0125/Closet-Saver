package com.cholog_ai.closet_saver.domain.closet.model.vo;

public enum Color {
    WHITE(1), BLACK(2), BEIGE(3), GREY(4), BLUE(5);

    Color(int index) {
        this.index = index;
    }
    private final int index;
    public int getIndex() { return index; }
}
