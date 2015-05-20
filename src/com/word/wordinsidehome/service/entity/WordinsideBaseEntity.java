package com.word.wordinsidehome.service.entity;

import java.io.Serializable;

public class WordinsideBaseEntity implements Serializable {
    private int positionInItemView;

    public WordinsideBaseEntity() {
        super();
        this.positionInItemView = 0;
    }

    public int getPositionInItemView() {
        return this.positionInItemView;
    }

    public void setPositionInItemView(int positionInItemView) {
        this.positionInItemView = positionInItemView;
    }
}