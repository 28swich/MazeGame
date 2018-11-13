package com.swich.maze.objects;

import com.swich.maze.framework.ObjectId;
import com.swich.maze.game.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

public class MazeBuilder {

    private Wall[] walls;

    private Checkpoint[] checkpoints;
    private int checkpointCount = 5;

    private int wallWidth = Wall.getWidth();
    private int width = Game.getWIDTH() / wallWidth;
    private int height = Game.getHEIGHT() / wallWidth;
    private int wallCount = width * height;

    private boolean[] visited;
    private Stack<Wall> stack;

    public Wall[] getWalls() {
        return walls;
    }

    public Checkpoint[] getCheckpoints() {
        return checkpoints;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void generateMaze() {

        walls = new Wall[wallCount];

        checkpoints = new Checkpoint[checkpointCount];

        int[][] chp = new int[checkpoints.length][2];

        Random r = new Random();
        for (int i = 0; i < chp.length; i++) {
            chp[i][0] = r.nextInt(width);
            chp[i][1] = r.nextInt(height);
            while ((chp[i][0] == 0 && chp[i][1] == 0) || chp[i][0] == 24 && chp[i][1] == 24) {
                chp[i][0] = r.nextInt(width);
                chp[i][1] = r.nextInt(height);
            }
            for (int j = 0; j < chp.length; j++) {
                while (i != j && chp[j][0] == chp[i][0] && chp[j][1] == chp[i][1]) {
                    chp[i][0] = r.nextInt(width);
                    chp[i][1] = r.nextInt(height);
                    j = 0;
                }
            }
        }

        for (int i = 0; i < checkpoints.length; i++) {
            int chpX = chp[i][0] * wallWidth + 5;
            int chpY = chp[i][1] * wallWidth + 5;
            checkpoints[i] = new Checkpoint(chpX, chpY, ObjectId.Checkpoint);
        }

        visited = new boolean[wallCount];
        Arrays.fill(visited, false);

        stack = new Stack<>();

        visited[index(0, 0)] = true;

        boolean[] startWalls = {true, true, true, true};
        Wall curr = new Wall(0, 0, startWalls, ObjectId.Wall);

        walls[index(0, 0)] = curr;

        while (isNotVisited()) {

            int x = getX(curr);
            int y = getY(curr);

            boolean[] neighbours = {false, false, false, false};

            if (x < width - 1 && !visited[index(x + 1, y)]) {
                neighbours[1] = true;
            }
            if (x > 0 && !visited[index(x - 1, y)]) {
                neighbours[3] = true;
            }
            if (y < height - 1 && !visited[index(x, y + 1)]) {
                neighbours[2] = true;
            }
            if (y > 0 && !visited[index(x, y - 1)]) {
                neighbours[0] = true;
            }

            ArrayList<Integer> n = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                if (neighbours[i]) {
                    n.add(i);
                }
            }

            if (n.size() > 0) {

                int nn = n.get((int) (Math.random() * n.size()));

                int nextX = x;
                int nextY = y;
                boolean[] w = new boolean[4];
                Arrays.fill(w, true);
                boolean[] cw = curr.getWalls();
                switch (nn) {
                    case 0:
                        nextY--;
                        cw[0] = false;
                        w[2] = false;
                        curr.setWalls(cw);
                        break;
                    case 1:
                        nextX++;
                        cw[1] = false;
                        w[3] = false;
                        curr.setWalls(cw);
                        break;
                    case 2:
                        nextY++;
                        cw[2] = false;
                        w[0] = false;
                        curr.setWalls(cw);
                        break;
                    case 3:
                        nextX--;
                        cw[3] = false;
                        w[1] = false;
                        curr.setWalls(cw);
                        break;
                }

                stack.push(curr);

                Wall next = new Wall(setX(nextX), setY(nextY), w, ObjectId.Wall);

                visited[index(nextX, nextY)] = true;
                walls[index(nextX, nextY)] = next;

                curr = next;

            } else if (!stack.isEmpty()) {
                curr = stack.pop();
            }

        }

    }

    private boolean isNotVisited() {

        for (boolean v : visited) {
            if (!v) {
                return true;
            }
        }
        return false;

    }

    private int index(int x, int y) {
        return x + y * height;
    }

    private int getX(Wall w) {
        return w.getX() / wallWidth;
    }

    private int getY(Wall w) {
        return w.getY() / wallWidth;
    }

    private int setX(int x) {
        return x * wallWidth;
    }

    private int setY(int y) {
        return y * wallWidth;
    }

}
