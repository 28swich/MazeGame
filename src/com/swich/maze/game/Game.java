package com.swich.maze.game;

import com.swich.maze.IO.Input;
import com.swich.maze.display.Display;
import com.swich.maze.framework.Texture;
import com.swich.maze.utils.Time;

import java.awt.*;

public class Game implements Runnable {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final String TITLE = "Maze Game";
    private static final int CLEAR_COLOR = 0x30303030;
    private static final int NUM_BUFFERS = 3;

    private static final float UPDATE_RATE = 60.0f;
    private static final float UPDATE_INTERVAL = Time.SECOND / UPDATE_RATE;
    private static final long IDLE_TIME = 1;

    private static Handler handler;
    private static Texture tex;

    private static Input input;

    public static Handler getHandler() {
        return handler;
    }

    public static Texture getTexture() {
        return tex;
    }

    public static Input getInput() {
        return input;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    private Thread gameThread;
    private boolean running;
    private Graphics2D graphics;


    public Game() {

        running = false;
        input = new Input();

        Display.create(WIDTH, HEIGHT, TITLE, CLEAR_COLOR, NUM_BUFFERS);
        graphics = Display.getGraphics();

        handler = new Handler();
        tex = new Texture();
        handler.setupGame();

        Display.addKeyInput();

    }

    public synchronized void start() {

        if (running) {
            return;
        }

        running = true;
        gameThread = new Thread(this);
        gameThread.start();

    }

    public synchronized void stop() {

        if (!running) {
            return;
        }

        running = false;

        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        cleanUp();

    }

    private void update() {
        handler.update();
    }

    private void render() {

        Display.clear();

        handler.render(graphics);

        Display.swapBuffers();

    }

    @Override
    public void run() {

        int fps = 0;
        int upd = 0;
        int updl = 0;

        long count = 0;

        float delta = 0;

        long lastTime = Time.get();
        while (running) {

            long now = Time.get();
            long elapsedTime = now - lastTime;
            lastTime = now;

            count += elapsedTime;

            boolean render = false;
            delta += (elapsedTime / UPDATE_INTERVAL);
            while (delta > 1) {
                update();
                upd++;
                delta--;
                if (render) {
                    updl++;
                } else {
                    render = true;
                }
            }

            if (render) {
                render();
                fps++;
            } else {
                try {
                    Thread.sleep(IDLE_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (count >= Time.SECOND) {
//                Display.setTitle(TITLE + " || FPS: " + fps + " ||");
                upd = 0;
                fps = 0;
                updl = 0;
                count = 0;
            }
        }

    }

    private void cleanUp() {
        Display.destroy();
    }

}
