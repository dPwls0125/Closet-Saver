package com.cholog_ai.closet_saver.domain.closet.model.vo;

public enum Style {
    MINIMAL(1), CASUAL(2), STREET(3), CLASSIC(4), FEMININE(5);

    private final int index;
    Style(int index) { this.index = index; }
    public int getIndex() { return index; }
}
