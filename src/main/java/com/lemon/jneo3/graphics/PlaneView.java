package com.lemon.jneo3.graphics;

import com.lemon.jneo3.world.Plane;

import java.awt.*;

public class PlaneView extends Canvas {
    private final Plane p;
    private final float scale;

    public PlaneView(Plane p, float scale) {
        this.p = p;
        this.scale = scale;

        setSize(p.getWidth(), p.getHeight());
        setBackground(Color.white);
    }

    public void paint(Graphics g) {
        p.draw(g, 0, 0, scale);
    }
}
