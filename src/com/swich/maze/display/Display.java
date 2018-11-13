package com.swich.maze.display;

import com.swich.maze.IO.KeyInput;
import com.swich.maze.game.Game;
import com.swich.maze.utils.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public abstract class Display {

    private static boolean created = false;
    private static JFrame window;

    private static BufferedImage buffer;
    private static int[] bufferData;
    private static Graphics bufferGraphics;
    private static int clearColor;

    private static BufferStrategy bufferStrategy;


    public static void create(int width, int height, String title, int _clearColor, int numBuffers) {

        if (created) {
            return;
        }

        window = new JFrame(title);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon icon = new ImageIcon(ResourceLoader.loadImage("icon.png"));
        window.setIconImage(icon.getImage());

        Canvas content = new Canvas();

        window.getContentPane().add(Game.getInput());

        Dimension size = new Dimension(width, height);
        content.setPreferredSize(size);

        window.setResizable(false);
        window.getContentPane().add(content);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        bufferData = ((DataBufferInt) buffer.getRaster().getDataBuffer()).getData();
        bufferGraphics = buffer.getGraphics();
        ((Graphics2D) bufferGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        clearColor = _clearColor;

        content.createBufferStrategy(numBuffers);
        bufferStrategy = content.getBufferStrategy();

        created = true;

    }

    public static void clear() {
        Arrays.fill(bufferData, clearColor);
    }

    public static void addKeyInput() {
        window.addKeyListener(new KeyInput());
    }

    public static void swapBuffers() {

        Graphics g = bufferStrategy.getDrawGraphics();
        g.drawImage(buffer, 0, 0, null);
        bufferStrategy.show();

    }

    public static Graphics2D getGraphics() {
        return (Graphics2D) bufferGraphics;
    }

    public static void destroy() {

        if (!created) {
            return;
        }

        window.dispose();

    }

    public static void setTitle(String title) {
        window.setTitle(title);
    }

}
