package com.lemon.jneo3.tables;

public class Table<T> {
    TableEntry<T>[] table;
    float height;

    @SafeVarargs
    public Table(TableEntry<T>... table) {
        //noinspection unchecked
        this.table = new TableEntry[table.length];
        this.height = 0;
        for (int i = 0; i < table.length; i++) {
            this.table[i] = table[i].addHeight(this.height);
            this.height += table[i].weight;
        }
    }

    public T random() {
        if (height == 0) {
            return null;
        }

        float random = (float) Math.random() * height;
        for (TableEntry<T> entry : table) {
            if (random < entry.weight) {
                return entry.value;
            }
        }
        return table[table.length - 1].value;
    }
}
