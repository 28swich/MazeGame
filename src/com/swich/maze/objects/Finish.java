package com.swich.maze.objects;

import com.swich.maze.framework.GameObject;
import com.swich.maze.framework.ObjectId;
import com.swich.maze.game.Game;

import java.awt.*;
import java.util.LinkedList;

public class Finish extends GameObject {

    private boolean finished = false;
    private Font f1 = new Font("Monospaced", Font.PLAIN, 64);
    private Font f2 = new Font("Monospaced", Font.PLAIN, 28);
    private Font f3 = new Font("Monospaced", Font.PLAIN, 24);

    public Finish(int x, int y, ObjectId id) {

        super(x, y, id);
        layer = 2;

    }

    public boolean isFinished() {
        return finished;
    }

    @Override
    public void update(LinkedList<GameObject> object) {

    }

    @Override
    public void render(Graphics g) {

        if (Checkpoint.getChecks(Game.getHandler().getMazeBuilder().getCheckpoints())) {
            if (Game.getHandler().getPlayer().getBounds().intersects(getBounds())) {
                Game.getHandler().getPlayer().setShowPath(true);
                g.drawImage(tex.text[0], Game.getWIDTH() / 2 - 270, Game.getHEIGHT() - 100 - 200, null);
                g.setColor(new Color(21, 21, 21));

                g.setFont(f1);
                int a = (int) (g.getFont().getStringBounds("You Win", ((Graphics2D) g).getFontRenderContext()).getWidth() / 2);
                g.drawString("You Win", Game.getWIDTH() / 2 - a, Game.getHEIGHT() - 210);

                g.setFont(f2);
                int b = (int) (g.getFont().getStringBounds("To Exit pres ESCAPE", ((Graphics2D) g).getFontRenderContext()).getWidth() / 2);
                g.drawString("To Exit pres ESCAPE", Game.getWIDTH() / 2 - b, Game.getHEIGHT() + 54 - 200);

                g.setFont(f3);
                String s = "Distance: " + Game.getHandler().getPlayer().getDistance();
                int c = (int) (g.getFont().getStringBounds(s, ((Graphics2D) g).getFontRenderContext()).getWidth() / 2);
                g.drawString(s, Game.getWIDTH() / 2 - c, Game.getHEIGHT() + 20 - 200);
                finished = true;
            }
        }

        g.drawImage(tex.finish[0], x, y, null);

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 20, 20);
    }

}
