package com.lemon.jneo3.land;

import com.lemon.jneo3.items.Loot;
import com.lemon.jneo3.entities.Entity;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Chunk {
    private BiomeTerrain terrain;
    private final int x;
    private final int y;
    private final List<Entity> entities;

    public Chunk(int x, int y) {
        this.terrain = null;
        this.x = x;
        this.y = y;
        this.entities = new ArrayList<>();
    }

    public void setTerrain(BiomeTerrain terrain) {
        this.terrain = terrain;
        this.terrain.addChunk(this);
    }

    public Loot scavenge() {
        return terrain.scavenge();
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    public int drawNutrients(int amount) {
        return terrain.drawNutrients(amount);
    }

    public boolean canDrawNutrients(int amount) {
        return terrain.canDrawNutrients(amount);
    }

    public void regenNutrients() {
        terrain.regenNutrients();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return (terrain == null ? "None" : terrain) + "(" + x + "," + y + ")";
    }

    public void draw(Graphics g, int x, int y, int w, int h) {
        // Draw colored rectangle
        g.setColor(terrain.getColor());
        g.fillRect(x, y, w, h);

        // Draw nutrient level
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(terrain.nutrients()), x, y + h / 2);

        // Draw entities
        for (Entity entity : entities) {
            entity.draw(g, x, y, w, h);
        }
    }

    public boolean biomesGenerated() {
        return terrain != null;
    }

    public BiomeTerrain getTerrain() {
        return terrain;
    }

    public void step() {
        // Terrain must exist at this point
        terrain.regenNutrients();
        for (Entity entity : entities) {
            entity.step();
        }
    }
}
