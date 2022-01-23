package com.lemon.jneo3.land;

import com.lemon.jneo3.items.Loot;
import com.lemon.jneo3.tables.Table;

import java.awt.*;

public class Biome {
    private final String name;
    private final Table<Loot> resources;
    private final int nutrients;
    private final int nutrientRegen;
    private final Color color;

    public Biome(String name, Table<Loot> resources, int nutrients, int nutrientRegen, Color color) {
        this.name = name;
        this.resources = resources;
        this.nutrients = nutrients;
        this.nutrientRegen = nutrientRegen;
        this.color = color;
    }

    public BiomeTerrain instance() {
        return new BiomeTerrain(name, resources, nutrients, nutrientRegen, color);
    }

}
