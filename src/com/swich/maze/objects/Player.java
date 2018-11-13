package com.swich.maze.objects;

import com.swich.maze.IO.Input;
import com.swich.maze.framework.GameObject;
import com.swich.maze.framework.ObjectId;
import com.swich.maze.game.Game;
import com.swich.maze.game.Handler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class Player extends GameObject {

    private Input input;
    private Handler handler;

    private int width = 16;

    private double velocity = 0.6;
    private double slow = 0.4;

    private double speedX = 0;
    private double speedY = 0;

    private double maxSpeed = 3;

    private boolean showPath = false;

    private ArrayList<Integer> xS = new ArrayList<>();
    private ArrayList<Integer> yS = new ArrayList<>();

    public int getDistance() {
        return xS.size();
    }

    public void toggleShowPath() {
        showPath = !showPath;
    }

    public void setShowPath(boolean showPath) {
        this.showPath = showPath;
    }

    public Player(int x, int y, ObjectId id) {

        super(x, y, id);
        layer = 1;
        this.input = Game.getInput();
        this.handler = Game.getHandler();

    }

    @Override
    public void update(LinkedList<GameObject> object) {

        if (input.getKey(KeyEvent.VK_RIGHT)) {
            speedX += velocity;
        } else {
            if (speedX > 0) {
                speedX -= velocity * slow;
                if (speedX < 0) {
                    speedX = 0;
                }
            }
        }

        if (input.getKey(KeyEvent.VK_LEFT)) {
            speedX -= velocity;
        } else {
            if (speedX < 0) {
                speedX += velocity * slow;
                if (speedX > 0) {
                    speedX = 0;
                }
            }
        }

        if (input.getKey(KeyEvent.VK_DOWN)) {
            speedY += velocity;
        } else {
            if (speedY > 0) {
                speedY -= velocity * slow;
                if (speedY < 0) {
                    speedY = 0;
                }
            }
        }
        if (input.getKey(KeyEvent.VK_UP)) {
            speedY -= velocity;
        } else {
            if (speedY < 0) {
                speedY += velocity * slow;
                if (speedY > 0) {
                    speedY = 0;
                }
            }
        }

        if (speedX > maxSpeed) {
            speedX = maxSpeed;
        }
        if (speedY > maxSpeed) {
            speedY = maxSpeed;
        }
        if (speedX < -maxSpeed) {
            speedX = -maxSpeed;
        }
        if (speedY < -maxSpeed) {
            speedY = -maxSpeed;
        }

        int widthWalls = handler.getMazeBuilder().getWidth();
        int heightWalls = handler.getMazeBuilder().getHeight();

        int ix = x / Wall.getWidth();
        int iy = y / Wall.getWidth();

        Wall[] walls = handler.getWalls();

        HashSet<Wall> neighbours = new HashSet<>();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (ix + j > 0 && ix + j < widthWalls - 1) {
                    if (iy + i > 0 && iy + i < heightWalls - 1) {
                        neighbours.add(walls[(ix + j) + (iy + i) * heightWalls]);
                    }
                }
            }
        }

        if (ix > 0 && iy > 0) {
            neighbours.add(walls[(ix - 1) + (iy - 1) * heightWalls]);
        }

        if (ix < widthWalls - 1 && iy > 0) {
            neighbours.add(walls[(ix + 1) + (iy - 1) * heightWalls]);
        }

        if (ix > 0 && iy < heightWalls - 1) {
            neighbours.add(walls[(ix - 1) + (iy + 1) * heightWalls]);
        }

        if (ix < widthWalls - 1 && iy > heightWalls - 1) {
            neighbours.add(walls[(ix + 1) + (iy + 1) * heightWalls]);
        }

        neighbours.add(walls[ix + iy * heightWalls]);

        if (collisionRight(neighbours) && speedX > 0) {
            speedX = 0;
        }

        if (collisionLeft(neighbours) && speedX < 0) {
            speedX = 0;
        }

        if (collisionBottom(neighbours) && speedY > 0) {
            speedY = 0;
        }

        if (collisionTop(neighbours) && speedY < 0) {
            speedY = 0;
        }

        if (xS.size() < 1 || (Math.pow(xS.get(xS.size() - 1) - x - width / 2, 2) + Math.pow(yS.get(yS.size() - 1) - y - width / 2, 2) > 64)) {
            xS.add(x + width / 2);
            yS.add(y + width / 2);
        }

        x += speedX;
        y += speedY;

    }

    private boolean collisionRight(HashSet<Wall> walls) {
        for (Wall w : walls) {
            if (w.getBoundsRight().intersects(getBoundsRight())) {
                return true;
            }
        }
        return false;
    }

    private boolean collisionLeft(HashSet<Wall> walls) {
        for (Wall w : walls) {
            if (w.getBoundsLeft().intersects(getBoundsLeft())) {
                return true;
            }
        }
        return false;
    }

    private boolean collisionTop(HashSet<Wall> walls) {
        for (Wall w : walls) {
            if (w.getBoundsTop().intersects(getBoundsTop())) {
                return true;
            }
        }
        return false;
    }

    private boolean collisionBottom(HashSet<Wall> walls) {
        for (Wall w : walls) {
            if (w.getBoundsBottom().intersects(getBoundsBottom())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void render(Graphics g) {

        g.drawImage(tex.player[0], x, y, null);

        if (showPath) {
            g.setColor(new Color(64, 255, 64));
            for (int i = 0; i < xS.size() - 1; i++) {
                g.drawLine(xS.get(i), yS.get(i), xS.get(i + 1), yS.get(i + 1));
            }
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x - width / 2, y - width / 2, width, width);
    }

    public Rectangle getBoundsRight() {
        return new Rectangle(x + width - 2, y + 2, 4, width - 4);
    }

    public Rectangle getBoundsLeft() {
        return new Rectangle(x - 2, y + 2, 4, width - 4);
    }

    public Rectangle getBoundsTop() {
        return new Rectangle(x + 2, y - 2, width - 4, 4);
    }

    public Rectangle getBoundsBottom() {
        return new Rectangle(x + 2, y + width - 2, width - 4, 4);
    }

}