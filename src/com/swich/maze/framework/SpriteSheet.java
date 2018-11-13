package com.swich.maze.framework;

import java.awt.image.BufferedImage;

public class SpriteSheet {

    private BufferedImage image;

    public SpriteSheet(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage grabImage(int col, int row, int width, int height) {
        return image.getSubimage((col * width) - width, (row * height) - height, width, height);
    }

    public BufferedImage grabImageXY(int x, int y, int width, int height) {
        return image.getSubimage(x, y, width, height);
    }

}
