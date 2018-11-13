package com.swich.maze.framework;

import com.swich.maze.game.Game;

import java.awt.*;
import java.util.LinkedList;

public abstract class GameObject {

    protected Texture tex = Game.getTexture();

    protected int x;
    protected int y;

    protected int layer;
    protected ObjectId id;

    public GameObject(int x, int y, ObjectId id) {

        this.x = x;
        this.y = y;

        this.id = id;

    }

    public int getLayer() {
        return layer;
    }

    public abstract void update(LinkedList<GameObject> object);

    public abstract void render(Graphics g);

    public ObjectId getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public abstract Rectangle getBounds();

}
