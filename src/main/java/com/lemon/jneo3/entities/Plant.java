package com.lemon.jneo3.entities;

import com.lemon.jneo3.Stats;
import com.lemon.jneo3.world.Plane;

import java.awt.*;

public class Plant extends Entity {
    public static class PlantGenes {
        final String root;
        String strain;

        int age;

        final int nutrientConsumption;
        final int reproduceAge;
        final int nutrientReproduction;
        final int maxAge;

        public PlantGenes(String root, int nutrientConsumption, int reproduceAge, int nutrientReproduction, int maxAge) {
            this.root = root;
            this.nutrientConsumption = nutrientConsumption;
            this.reproduceAge = reproduceAge;
            this.nutrientReproduction = nutrientReproduction;
            this.maxAge = maxAge;

            age = 0;
        }

        public float distance(PlantGenes other) {
            return Entity.NDistance(getStats(), other.getStats());
        }

        public int[] getStats() {
            return new int[]{ nutrientConsumption, reproduceAge, nutrientReproduction, maxAge };
        }

        public PlantGenes mutate() {
            int newNutrientConsumption = nutrientConsumption + (int) (Math.random() * 2) - 1;
            int newReproduceAge = reproduceAge + (int) (Math.random() * 2) - 1;
            int newNutrientReproduction = nutrientReproduction + (int) (Math.random() * 2) - 1;
            int newMaxAge = maxAge + (int) (Math.random() * 2) - 1;
            PlantGenes genes = new PlantGenes(root, newNutrientConsumption, newReproduceAge, newNutrientReproduction, newMaxAge);
            genes.strain = strain;
            return genes;
        }

        @Override
        public String toString() {
            if (strain == null) {
                return root + "-Ω";
            }
            return root + "-" + strain;
        }

    }

    private final PlantGenes stats;
    private final PlantGenes species;

    public Plant(int x, int y, Plane plane, PlantGenes species) {
        super(x, y, plane);
        this.species = species;
        stats = species;
    }

    public Plant(int x, int y, Plane plane, PlantGenes parent, PlantGenes species) {
        super(x, y, plane);
        stats = parent.mutate();
        if (species.distance(stats) >= Entity.EvolutionDistance(3)) {
            this.species = stats;
            this.species.strain = Entity.NewStrain();
            Stats.AddPlantSpecies(species);
        }
        else {
            this.species = species;
        }
    }

    @Override
    public void draw(Graphics g, int chunkX, int chunkY, int chunkWidth, int chunkHeight) {
        g.setColor(Color.WHITE);
        g.fillOval(chunkX + chunkPos.x / plane.getChunkWidth() * chunkWidth, chunkY + chunkPos.y / plane.getChunkHeight() * chunkHeight, 3, 3);
    }

    private void destroy() {
        currentChunk.removeEntity(this);
    }

    @Override
    protected void update() {
        int intake = currentChunk.drawNutrients(stats.nutrientConsumption);
        if (intake < stats.nutrientConsumption)
            destroy();

        stats.age++;
    }
}
