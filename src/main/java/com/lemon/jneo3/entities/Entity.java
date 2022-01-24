package com.lemon.jneo3.entities;

import com.lemon.jneo3.land.Chunk;
import com.lemon.jneo3.world.Plane;

import java.awt.*;

public abstract class Entity {
    // EvolutionDistance tells how far away an entitys stats must be from
    // its species to be considered a different species.
    // It takes in the number of stats compared and returns the min distance
    protected static float EvolutionDistance(int dimensions) {
        return 2.0f * (float) Math.sqrt(dimensions);
    }

    protected static String NewStrain() {
        int length = (int) (Math.random() * 10) + 5;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append((char) ((int) (Math.random() * 26) + 'a'));
        }
        return sb.toString();
    }

    protected int x;
    protected int y;
    protected Point chunkPos;
    protected Plane plane;
    protected Chunk currentChunk;

    public Entity(int x, int y, Plane plane) {
        this.x = Math.min(Math.max(x, 0), plane.getWidth() - 1);
        this.y = Math.min(Math.max(y, 0), plane.getHeight() - 1);
        this.plane = plane;
        this.currentChunk = plane.getChunk(x, y);
        this.chunkPos = plane.getChunkPos(x, y);
        currentChunk.addEntity(this);
    }

    public abstract void draw(Graphics g, int chunkX, int chunkY, int chunkWidth, int chunkHeight);

    protected abstract void update();

    public void step() {
        update();
        Chunk c = plane.getChunk(x, y);
        if (c != currentChunk) {
            currentChunk.removeEntity(this);
            c.addEntity(this);
        }
        this.chunkPos = plane.getChunkPos(x, y);
    }

    public static float NDistance(int[] a, int[] b) {
        float delta = 0;
        for (int i = 0; i < a.length; i++) {
            delta += Math.pow(a[i] - b[i], 2);
        }
        return (float) Math.sqrt(delta);
    }

}
