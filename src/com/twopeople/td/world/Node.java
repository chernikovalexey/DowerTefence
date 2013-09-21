package com.twopeople.td.world;

import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;

/**
 * Created by Alexey
 * At 10:40 PM on 9/21/13
 */

public class Node {
    private Vector2f pos;
    private boolean visited = false;
    private float pathDistance = 0f;
    private float heuristicDistance = 0f;
    private float priority = 0f;

    private Node parent = null;
    private ArrayList<Node> neighbours = new ArrayList<Node>();

    public Node(Vector2f pos) {
        this.pos = pos;
    }

    public boolean isVisited() {
        return this.visited;
    }

    public void visit() {
        this.visited = true;
    }

    public Vector2f getPosition() {
        return this.pos;
    }

    public float getPathDistance() {
        return this.pathDistance;
    }

    public void setPathDistance(float dist) {
        this.pathDistance = dist;
    }

    public float getHeuristicDistance() {
        return heuristicDistance;
    }

    public float getPriority() {
        return priority;
    }

    public void setPriority(float priority) {
        this.priority = priority;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setHeuristicDistance(float heuristicDistance) {
        this.heuristicDistance = heuristicDistance;
    }

    public void addNeighbour(Node node) {
        neighbours.add(node);
    }

    public ArrayList<Node> getNeighbours() {
        return this.neighbours;
    }

    public static String getHash(Vector2f pos) {
        return pos.x + "_" + pos.y;
    }
}