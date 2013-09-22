package com.twopeople.td.world;

import com.twopeople.td.entity.Entity;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

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

    private HashMap<String, Node> nodes = new HashMap<String, Node>();

    public Pathfinder(World world) {
        this.world = world;
    }

    public Node createNode(int cx, int cy) {
        Node node = new Node(cx * cellWidth, cy * cellHeight, cellWidth, cellHeight);
        nodes.put(Node.getHash(cx, cy), node);
        return node;
    }

    public Node getNode(int cx, int cy) {
        return nodes.get(Node.getHash(cx, cy));
    }

    public boolean isPassable(Node node) {
        return !world.getEntities().isCollidingShape(node.getBounds());
    }

    public void addNeighbours(Node node) {
        Node n;
        int cellX = (int) (node.x / node.width);
        int cellY = (int) (node.y / node.height);

        for (int x = cellX - 1 < 0 ? 0 : cellX - 1; x <= cellX + 1; ++x) {
            for (int y = cellY - 1 < 0 ? 0 : cellY - 1; y <= cellY + 1; ++y) {
                n = getNode(x, y);
                System.out.println("Checking whether passable for " + x + ", " + y + ": " + isPassable(n));
                if (x != cellX || y != cellY) {
                    node.addNeighbour(n);
                }
            }
        }
    }

    private Path constructPath(Node goalNode) {
        Path path = new Path();
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

        for (int x = 0; x < world.getWidth() / cellWidth; ++x) {
            for (int y = 0; y < world.getHeight() / cellHeight; ++y) {
                createNode(x, y);
            }
        }

        Node start = createNode((int) (from.getX() / cellWidth), (int) (from.getZ() / cellHeight));
        Node goal = createNode((int) (to.getX() / cellWidth), (int) (to.getZ() / cellHeight));

        PriorityQueue<Node> queue = new PriorityQueue<Node>();
        queue.add(start);

        Path bestPath = null;

        while (queue.size() > 0) {
            current = queue.poll();

            if (current.isVisited()) {
                continue;
            }

            //            System.out.println("Current: " + current.getBounds().getX() + ", " + current.getBounds().getY() + "; " + current.getBounds().getWidth() + ", " + current.getBounds().getHeight());

            if (current.isIntersecting(goal)) {
                System.out.println("Seems to be found!");
                bestPath = constructPath(current);
                break;
            }

            addNeighbours(current);

            current.visit();

            //System.out.println("Neighbours: " + current.getNeighbours().size());

            for (Node neighbour : current.getNeighbours()) {
                if (neighbour.isVisited()) {
                    continue;
                }

                //System.out.println("  Neighbour: " + neighbour.getBounds().getX() / cellWidth + ", " + neighbour.getBounds().getY() / cellHeight + "; " + neighbour.getBounds().getWidth() + ", " + neighbour.getBounds().getHeight());

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

        return bestPath;
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