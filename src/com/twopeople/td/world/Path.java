package com.twopeople.td.world;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;

/**
 * Created by Alexey
 * At 2:22 AM on 9/22/13
 */

public class Path {
    private ArrayList<Node> nodes = new ArrayList<Node>();

    public void addNodeFront(Node node) {
        nodes.add(0, node);
    }

    public void render(Camera camera, Graphics g) {
        g.setColor(Color.magenta);
        for (Node node : nodes) {
            if (node != null) { g.fillRect(camera.getX(node.x), camera.getZ(node.y), node.width, node.height); }
        }
    }
}