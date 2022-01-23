package com.lemon.jneo3.items;

public class Resource {
    private final String name;
    private final int code;

    public Resource(String name) {
        this.name = name;
        this.code = name.hashCode();
    }

    public boolean equals(Resource r) {
        return this.code == r.code;
    }

    public String getName() {
        return name;
    }
}
