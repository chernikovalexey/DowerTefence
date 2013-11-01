package com.twopeople.td.world;

import com.twopeople.td.entity.Entity;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Created by Alexey
 * At 9:58 PM on 9/21/13
 */

public class Pathfinder {
    private World world;
    private float cellWidth, cellHeight;

    private static final Vector2f[] dirs = new Vector2f[]{
            new Vector2f(1, 0), new Vector2f(-1, 0), new Vector2f(0, 1), new Vector2f(0, -1)
    };

    private Node goal;
    private HashMap<String, Node> nodes = new HashMap<String, Node>();

    public Pathfinder(World world) {
        this.world = world;
    }

    private Node createNode(int cx, int cy) {
        Node node = new Node(cx * cellWidth, cy * cellHeight, cellWidth, cellHeight);
        nodes.put(Node.getHash(cx, cy), node);
        return node;
    }

    private Node getNode(int cx, int cy) {
        return nodes.get(Node.getHash(cx, cy));
    }

    private boolean isPassable(Node node) {
        return !world.getEntities().isCollidingShape(node.getBounds());
    }

    private void addNeighbours(Node node) {
        Node n;
        int cellX = (int) (node.x / node.width);
        int cellY = (int) (node.y / node.height);

        for (Vector2f d : dirs) {
            n = getNode((int) (cellX + d.x), (int) (cellY + d.y));
            if (n != null) {
                node.addNeighbour(n);
            }
        }
    }

    private Path constructPath(Node goalNode) {
        Path path = new Path(false);
        Node node = goalNode;
        while (node != null) {
            path.addNodeFront(node);
            node = node.getParent();
        }
        return path;
    }

    public Path trace(Entity from, Entity to) {
        nodes.clear();

        Node current;
        this.cellWidth = from.getWidth();
        this.cellHeight = from.getHeight();

        System.out.println("New Pathfinder: " + cellWidth + ", " + cellHeight);

        for (int x = 0; x < world.getWidth() / cellWidth; ++x) {
            for (int y = 0; y < world.getHeight() / cellHeight; ++y) {
                createNode(x, y);
            }
        }

        Node start = createNode((int) (from.getX() / cellWidth), (int) (from.getZ() / cellHeight));
        this.goal = createNode((int) (to.getX() / cellWidth), (int) (to.getZ() / cellHeight));

        PriorityQueue<Node> queue = new PriorityQueue<Node>();
        queue.add(start);

        Path bestPath = null;

        while (queue.size() > 0) {
            current = queue.poll();

            if (current.isVisited()) {
                continue;
            }

            if (current.isIntersecting(goal)) {
                bestPath = constructPath(current);
                break;
            }

            addNeighbours(current);

            current.visit();

            for (Node neighbour : current.getNeighbours()) {
                if (neighbour.isVisited() || (!isPassable(neighbour) && !neighbour.isIntersecting(goal))) {
                    continue;
                }

                float distance = current.getPathDistance() + current.getPosition().distance(neighbour.getPosition());

                if (neighbour.getParent() != null && distance >= neighbour.getPathDistance()) {
                    continue;
                }

                neighbour.setPathDistance(distance);
                neighbour.setHeuristicDistance(neighbour.getPosition().distance(goal.getPosition()) + distance);
                neighbour.setPriority(neighbour.getHeuristicDistance());
                if (neighbour.getParent() == null) {
                    neighbour.setParent(current);
                    queue.add(neighbour);
                }
            }
        }

        return bestPath == null ? new Path(true) : bestPath;
    }

    public void render(Camera camera, Graphics g) {
        g.setColor(new Color(200, 200, 15, 50));
        Iterator it = nodes.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            Node node = (Node) pairs.getValue();
            g.drawLine(camera.getX(node.x), camera.getZ(node.y), camera.getX(node.x + node.width), camera.getZ(node.y));
            g.drawLine(camera.getX(node.x), camera.getZ(node.y), camera.getX(node.x), camera.getZ(node.y + node.height));
        }
    }
}