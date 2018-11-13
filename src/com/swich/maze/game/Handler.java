package com.swich.maze.game;

import com.swich.maze.framework.GameObject;
import com.swich.maze.framework.ObjectId;
import com.swich.maze.objects.Finish;
import com.swich.maze.objects.MazeBuilder;
import com.swich.maze.objects.Player;
import com.swich.maze.objects.Wall;

import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

public class Handler {

    private LinkedList<GameObject> object = new LinkedList<>();

    private Wall[] walls;

    private Player player;

    private Finish finish;

    private MazeBuilder mazeBuilder = new MazeBuilder();

    public MazeBuilder getMazeBuilder() {
        return mazeBuilder;
    }

    public Wall[] getWalls() {
        return walls;
    }

    public Player getPlayer() {
        return player;
    }

    public void update() {
        if (!finish.isFinished()) {
            object.forEach(o -> o.update(object));
        }
    }

    public void render(Graphics g) {
        object.stream().sorted(Comparator.comparingInt(GameObject::getLayer)).forEach(e -> e.render(g));
    }

    public void addObject(GameObject object) {
        this.object.add(object);
    }

    public void removeObject(GameObject object) {
        this.object.remove(object);
    }

    public void setupGame() {

        player = new Player(8, 8, ObjectId.Player);
        addObject(player);
        mazeBuilder.generateMaze();
        walls = mazeBuilder.getWalls();
        Arrays.stream(walls).forEach(this::addObject);
        Arrays.stream(mazeBuilder.getCheckpoints()).forEach(this::addObject);
        finish = new Finish(Game.getWIDTH() - 26, Game.getHEIGHT() - 26, ObjectId.Finish);
        addObject(finish);

    }

}
