package com.lemon.jneo3.tables;

public class TableEntry<T> {
    final float weight;
    final T value;

    public TableEntry(float weight, T value) {
        this.weight = weight;
        this.value = value;
    }

    public TableEntry<T> addHeight(float height) {
        return new TableEntry<>(weight + height, value);
    }
}
