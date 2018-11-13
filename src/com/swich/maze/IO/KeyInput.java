package com.swich.maze.IO;

import com.swich.maze.game.Game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }

        if (key == KeyEvent.VK_SPACE) {
            Game.getHandler().getPlayer().toggleShowPath();
        }

    }

}
