package com.twopeople.td.world;

import com.twopeople.td.entity.Entity;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;

/**
 * Created by Alexey
 * At 2:22 AM on 9/22/13
 */

public class Path {
    private int index = 0;
    private ArrayList<Node> nodes = new ArrayList<Node>();

    public void addNodeFront(Node node) {
        nodes.add(0, node);
    }

    public Vector2f getCurrentGoalPosition(Entity entity) {
        Node node = nodes.get(index);
        float cx = node.getBounds().getCenterX();
        float cy = node.getBounds().getCenterY();

        if (entity.isCollidingWith(new Rectangle(cx, cy, 1, 1))) {
            next();
        }

        return new Vector2f(cx, cy);
    }

    public void previous() {
        if (index > 0) {
            --index;
        }
    }

    public void next() {
        ++index;
        if (index >= nodes.size()) {
            previous();
        }
    }

    public void render(Camera camera, Graphics g) {
        g.setColor(Color.magenta);
        for (Node node : nodes) {
            if (node != null) { g.fillRect(camera.getX(node.x), camera.getZ(node.y), node.width, node.height); }
        }
    }
}