package com.lemon.jneo3.world;

import com.lemon.jneo3.entities.Plant;
import com.lemon.jneo3.land.Biome;
import com.lemon.jneo3.land.BiomeTerrain;
import com.lemon.jneo3.land.Chunk;
import com.lemon.jneo3.tables.Table;

import java.awt.*;

public class Plane {
    private final Chunk[] chunks;

    private final Dimension chunkDimensions;
    private final Dimension chunkArrayDimensions;
    private final Dimension planeDimensions;

    public Plane(int width, int height, int chunkW, int chunkH) {
        this.chunkDimensions = new Dimension(chunkW, chunkH);
        this.chunkArrayDimensions = new Dimension(width, height);
        this.planeDimensions = new Dimension(width * chunkW, height * chunkH);

        this.chunks = new Chunk[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                setChunkAt(x, y, new Chunk(x, y));
            }
        }
    }

    private Chunk chunkAt(int x, int y) {
        return chunks[y * chunkArrayDimensions.width + x];
    }

    private void setChunkAt(int x, int y, Chunk chunk) {
        chunks[y * chunkArrayDimensions.width + x] = chunk;
    }

    private boolean biomesGenerated() {
        for (Chunk chunk : chunks) {
            if (!chunk.biomesGenerated()) {
                return false;
            }
        }
        return true;
    }

    public void generate(Table<Biome> biomes) {
        while (!biomesGenerated()) {
            // Choose a random biome
            BiomeTerrain biome = biomes.random().instance();
            // Start at random position
            int x = (int) (Math.random() * chunkArrayDimensions.width);
            int y = (int) (Math.random() * chunkArrayDimensions.height);
            if (chunkAt(x, y).biomesGenerated()) {
                continue;
            }
            chunkAt(x, y).setTerrain(biome);
            // Randomly spread out to other chunks
            int radius = 2;
            while (trySpread(x, y, radius - 1, 1 / (radius * radius))) {
                radius++;
            }
        }
    }

    public boolean trySpread(int x, int y, int radius, int chance) {
        boolean spread = false;
        for (int xx = -1; xx <= 1; xx++) {
            for (int yy = -1; yy <= 1; yy++) {
                if (xx == 0 && yy == 0) {
                    continue;
                }
                spread |= trySpread(x, y, xx * radius, yy * radius, chance);
            }
        }
        return spread;
    }

    public boolean trySpread(int x, int y, int xOffset, int yOffset, int chance) {
        if (x + xOffset < 0 || x + xOffset >= chunkArrayDimensions.width || y + yOffset < 0 || y + yOffset >= chunkArrayDimensions.height) {
            return false;
        }
        if (Math.random() > chance) {
            return false;
        }
        Chunk chunk = chunkAt(x, y);
        Chunk otherChunk = chunkAt(x + xOffset, y + yOffset);
        if (otherChunk.biomesGenerated()) {
            return false;
        }
        otherChunk.setTerrain(chunk.getTerrain());
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < chunkArrayDimensions.height; y++) {
            for (int x = 0; x < chunkArrayDimensions.width; x++) {
                sb.append(chunks[y * chunkArrayDimensions.width + x]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void draw(Graphics g, int x, int y, float scale) {
        for (int yy = 0; yy < chunkArrayDimensions.height; yy++) {
            for (int xx = 0; xx < chunkArrayDimensions.width; xx++) {
                chunkAt(xx, yy).draw(g,
                        Math.round(x + xx * chunkDimensions.width * scale),
                        Math.round(y + yy * chunkDimensions.height * scale),
                        Math.round(chunkDimensions.width * scale),
                        Math.round(chunkDimensions.height * scale)
                );
            }
        }
    }

    public int getWidth() {
        return planeDimensions.width;
    }

    public int getHeight() {
        return planeDimensions.height;
    }

    public void step() {
        for (Chunk chunk : chunks) {
            chunk.step();
        }
    }

    public Chunk getChunk(int x, int y) {
        return chunkAt(x / chunkDimensions.width, y / chunkDimensions.height);
    }

    public Point getChunkPos(int x, int y) {
        return new Point(Math.floorMod(x, chunkDimensions.width), Math.floorMod(y, chunkDimensions.height));
    }

    public int getChunkWidth() {
        return chunkDimensions.width;
    }

    public int getChunkHeight() {
        return chunkDimensions.height;
    }

    public void seed(Plant.PlantGenes genes, int count) {
        for (int i = 0; i < count; i++) {
            int x = (int) (Math.random() * getWidth());
            int y = (int) (Math.random() * getHeight());
            new Plant(x, y, this, genes);
        }
    }
}
