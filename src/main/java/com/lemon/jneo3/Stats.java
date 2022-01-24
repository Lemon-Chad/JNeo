package com.lemon.jneo3;

import com.lemon.jneo3.entities.Plant;

import java.util.HashSet;
import java.util.Set;

public class Stats {
    private static final Set<Plant.PlantGenes> PlantGenes = new HashSet<>();

    public static void AddPlantSpecies(Plant.PlantGenes genes) {
        PlantGenes.add(genes);
    }

    public static String PlantInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("PLANTS\n");
        sb.append("======\n");
        sb.append("Total: ").append(PlantGenes.size());
        for (Plant.PlantGenes plantGenes : PlantGenes) {
            sb.append("\n");
            sb.append(plantGenes.toString()).append(":");
            String[] keys = { "Nutrient Consumption", "Reproduction Age", "Reproduction Cost", "Max Age" };
            int[] stats = plantGenes.getStats();
            for (int i = 0; i < keys.length; i++) {
                sb.append("\n\t- ").append(keys[i]).append(": ").append(stats[i]);
            }
        }
        return sb.toString();
    }

}
