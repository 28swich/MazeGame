package com.swich.maze;

import com.swich.maze.game.Game;

public class Main {

    public static Game maze;

    public static void main(String[] args) {

        maze = new Game();
        maze.start();

    }

}
