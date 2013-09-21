package com.twopeople.td.world;

import com.twopeople.td.entity.Entity;
import org.newdawn.slick.geom.Vector2f;

import java.util.HashMap;
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

    private static final Vector2f[] dirs = {new Vector2f(-1, 0), new Vector2f(1, 0),
            new Vector2f(0, 1), new Vector2f(0, -1)};

    public void addNeighbours(Node node) {
        Vector2f pos = node.getPosition();

        for (Vector2f p : dirs) {
            Vector2f dir = p.copy();
            dir.x *= 16;
            dir.y *= 16;


            Node n = getNode(p.add(dir));
            if (isPassable(n)) {
                node.addNeighbour(n);
            }
        }
    }


    public void trace(Entity from, Entity to) {
        nodes.clear();

        Node current;
        this.cellWidth = from.getWidth();
        this.cellHeight = from.getHeight();

        for (int x = 0; x < world.getWidth() / cellWidth; ++x) {
            for (int y = 0; y < world.getHeight() / cellHeight; ++y) {
                nodes.add(createNode(x, y));
            }
        }

        Vector2f startPosition = new Vector2f(from.getX(), from.getZ());
        Node start = createNode(startPosition);
        Vector2f goalPosition = new Vector2f(to.getX(), to.getZ());
        Node goal = createNode(goalPosition);

        PriorityQueue<Node> queue = new PriorityQueue<Node>();
        queue.add(start);

        while (queue.size() > 0) {
            current = queue.poll();

            if (current.isVisited()) {
                continue;
            }

            //            System.out.println("Current: " + current.getBounds().getX() + ", " + current.getBounds().getY() + "; " + current.getBounds().getWidth() + ", " + current.getBounds().getHeight());

            if (current.isIntersecting(to)) {
                System.out.println("Seems to be found!");
                break;
            }

            addNeighbours(current);

            current.visit();

            for (Node neighbour : current.getNeighbours()) {
                if (neighbour.isVisited()) {
                    continue;
                }

                //                System.out.println("  Neighbour: " + neighbour.getBounds().getX() + ", " + neighbour.getBounds().getY() + "; " + neighbour.getBounds().getWidth() + ", " + neighbour.getBounds().getHeight());

                float distance = current.getPathDistance() + current.getPosition().distance(neighbour.getPosition());

                //                System.out.println(distance);

                if (neighbour.getParent() != null && distance >= neighbour.getPathDistance()) {
                    continue;
                }

                neighbour.setPathDistance(distance);
                neighbour.setHeuristicDistance(neighbour.getPosition().distance(goalPosition) + distance);
                neighbour.setPriority(neighbour.getHeuristicDistance());
                if (neighbour.getParent() == null) {
                    neighbour.setParent(current);
                    queue.add(neighbour);
                }
            }
        }
    }
}