package com.lemon.jneo3.items;

public class Loot {
    private final Resource resource;
    private final int amount;

    public Loot(Resource resource, int amount, float chance) {
        this.resource = resource;
        this.amount = amount;
    }

    public Resource getResource() {
        return resource;
    }

    public int getAmount() {
        return amount;
    }

    public boolean equals(Object o) {
        if (o instanceof Loot) {
            Loot loot = (Loot) o;
            return loot.resource.equals(resource) && loot.amount == amount;
        }
        return false;
    }
}
