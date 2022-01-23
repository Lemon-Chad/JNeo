package com.lemon.jneo3.land;

import com.lemon.jneo3.items.Loot;
import com.lemon.jneo3.tables.Table;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class BiomeTerrain {
    private final String name;
    private final Table<Loot> resources;
    private final Set<Chunk> chunks;
    private int nutrients;
    private final int nutrientRegen;
    private final Color color;

    BiomeTerrain(String name, Table<Loot> resources, int nutrients, int nutrientRegen, Color color) {
        this.name = name;
        this.resources = resources;
        this.chunks = new HashSet<>();
        this.nutrients = nutrients;
        this.nutrientRegen = nutrientRegen;
        this.color = color;
    }

    void addChunk(Chunk chunk) {
        chunks.add(chunk);
    }

    int nutrients() {
        return nutrients;
    }

    int drawNutrients(int amount) {
        amount = Math.min(amount, nutrients);
        nutrients -= amount;
        return amount;
    }

    boolean canDrawNutrients(int amount) {
        return nutrients >= amount;
    }

    void regenNutrients() {
        nutrients += nutrientRegen;
    }

    Loot scavenge() {
        return resources.random();
    }

    Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return name;
    }
}
