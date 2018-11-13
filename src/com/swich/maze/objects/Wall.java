package com.swich.maze.objects;

import com.swich.maze.framework.GameObject;
import com.swich.maze.framework.ObjectId;

import java.awt.*;
import java.util.LinkedList;

public class Wall extends GameObject {

    private boolean[] walls; // TOP RIGHT BOTTOM LEFT

    private static int width = 32;
    private int tough = 2;

    private Rectangle empty = new Rectangle(0, 0, 0, 0);

    public Wall(int x, int y, boolean[] walls, ObjectId id) {

        super(x, y, id);
        this.walls = walls;

    }

    public Wall(int x, int y, ObjectId id) {
        super(x, y, id);
    }

    public static int getWidth() {
        return width;
    }

    public boolean[] getWalls() {
        return walls;
    }

    public void setWalls(boolean[] walls) {
        this.walls = walls;
    }

    @Override
    public void update(LinkedList<GameObject> object) {

    }

    @Override
    public void render(Graphics g) {

        if (walls[0]) {
            g.drawImage(tex.wall[0], x, y, null);
        }
        if (walls[1]) {
            g.drawImage(tex.wall[1], x, y, null);
        }
        if (walls[2]) {
            g.drawImage(tex.wall[2], x, y, null);
        }
        if (walls[3]) {
            g.drawImage(tex.wall[3], x, y, null);
        }

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, width);
    }

    public Rectangle getBoundsTop() {
        return (walls[0] ? new Rectangle(x + 2, y - 2, width, 4) : empty);
    }

    public Rectangle getBoundsRight() {
        return (walls[1] ? new Rectangle(x + width - 2, y + 2, 4, width) : empty);
    }

    public Rectangle getBoundsBottom() {
        return (walls[2] ? new Rectangle(x + 2, y + width - 2, width, 4) : empty);
    }

    public Rectangle getBoundsLeft() {
        return (walls[3] ? new Rectangle(x - 2, y + 2, 4, width) : empty);
    }

}
