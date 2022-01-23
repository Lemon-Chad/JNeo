package com.lemon.jneo3;

import com.lemon.jneo3.graphics.PlaneView;
import com.lemon.jneo3.land.Biome;
import com.lemon.jneo3.tables.Table;
import com.lemon.jneo3.tables.TableEntry;
import com.lemon.jneo3.world.Plane;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Biome desert = new Biome("Desert", new Table<>(), 15, 1, new Color(231, 231, 114));
        Biome ocean = new Biome("Ocean", new Table<>(), 150, 25, new Color(0, 128, 255));
        Biome forest = new Biome("Forest", new Table<>(), 100, 10, new Color(0, 128, 0));
        Biome plains = new Biome("Plains", new Table<>(), 100, 10, new Color(35, 215, 35));

        Plane existence = new Plane(32, 32, 16, 16);
        existence.generate(new Table<>(
                new TableEntry<>(0.25f, desert),
                new TableEntry<>(1f, ocean),
                new TableEntry<>(0.5f, forest),
                new TableEntry<>(0.8f, plains)
        ));

        JFrame frame = new JFrame("JNeo3");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Canvas canvas = new PlaneView(existence, 2);
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);

        int steps = 500;
        for (int i = 0; i < steps; i++) {
            existence.step();
            canvas.repaint();
            Thread.sleep(100);
        }

    }
}
