package com.swich.maze.framework;

import com.swich.maze.utils.ResourceLoader;

import java.awt.image.BufferedImage;

public class Texture {

    public BufferedImage[] wall = new BufferedImage[4];
    public BufferedImage[] player = new BufferedImage[1];
    public BufferedImage[] finish = new BufferedImage[1];
    public BufferedImage[] checkpoint = new BufferedImage[2];
    public BufferedImage[] text = new BufferedImage[1];


    private SpriteSheet ws;
    private SpriteSheet ps;
    private SpriteSheet fs;
    private SpriteSheet cs;
    private SpriteSheet ts;

    private BufferedImage wallSheet = null;
    private BufferedImage playerSheet = null;
    private BufferedImage finishSheet = null;
    private BufferedImage checkpointSheet = null;
    private BufferedImage textSheet = null;

    public Texture() {

        wallSheet = ResourceLoader.loadImage("wall_sheet.png");
        playerSheet = ResourceLoader.loadImage("player_sheet.png");
        finishSheet = ResourceLoader.loadImage("finish_sheet.png");
        checkpointSheet = ResourceLoader.loadImage("checkpoint_sheet.png");
        textSheet = ResourceLoader.loadImage("text_sheet.png");

        ws = new SpriteSheet(wallSheet);
        ps = new SpriteSheet(playerSheet);
        fs = new SpriteSheet(finishSheet);
        cs = new SpriteSheet(checkpointSheet);
        ts = new SpriteSheet(textSheet);

        getTextures();

    }

    private void getTextures() {

        player[0] = ps.grabImageXY(0, 0, 16, 16);

        for (int i = 0; i < wall.length; i++) {
            wall[i] = ws.grabImage(i + 1, 1, 32, 32);
        }

        finish[0] = fs.grabImageXY(0, 0, 20, 20);

        for (int i = 0; i < checkpoint.length; i++) {
            checkpoint[i] = cs.grabImage(i + 1, 1, 20, 20);
        }

        text[0] = ts.grabImageXY(0, 0, 540, 200);

    }

}
