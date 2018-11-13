package com.swich.maze.objects;

import com.swich.maze.framework.GameObject;
import com.swich.maze.framework.ObjectId;
import com.swich.maze.game.Game;

import java.awt.*;
import java.util.LinkedList;

public class Checkpoint extends GameObject {

    private boolean checked = false;

    public Checkpoint(int x, int y, ObjectId id) {
        super(x, y, id);
    }

    @Override
    public void update(LinkedList<GameObject> object) {

        if (getBounds().intersects(Game.getHandler().getPlayer().getBounds())) {
            checked = true;
        }

    }

    @Override
    public void render(Graphics g) {

        if (checked) {
            g.drawImage(tex.checkpoint[0], x, y, null);
        } else {
            g.drawImage(tex.checkpoint[1], x, y, null);
        }

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 20, 20);
    }

    public static boolean getChecks(Checkpoint[] checkpoints) {

        for (Checkpoint chp : checkpoints) {
            if (!chp.checked) {
                return false;
            }
        }
        return true;

    }

}
