package CMGame;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

public class Background extends UserView {

    private Font scoreFont;
    private Font iceFont;
    private Game game;
    private Image level1;
    private Image level2;
    private Image level3;
    private Image level4;
    private Image greenChef;
    private Image redChef;
    private Image greenChefFrustrated;
    private Image redChefFrustrated;
    private Image greenChefAngry;
    private Image redChefAngry;
    private Image iceWall;
    private Vec2 redPos;
    private Vec2 greenPos;
    private Point2D.Float redPosFloat;
    private Point2D.Float greenPosFloat;

    public Background(World w, int width, int height, Game game) {
        super(w, width, height);
        this.game = game;
        level1 = new ImageIcon("data/Tiles.jpg").getImage();
        level2 = new ImageIcon("data/IceBricks.jpg").getImage();
        level3 = new ImageIcon("data/BunkerWall.jfif").getImage();
        level4 = new ImageIcon("data/Level4B.jpg").getImage();
        greenChef = new ImageIcon("data/GreenChef.png").getImage();
        greenChefFrustrated = new ImageIcon("data/GreenChefFrustrated.png").getImage();
        greenChefAngry = new ImageIcon("data/GreenChefAngry.png").getImage();
        redChef = new ImageIcon("data/RedChef.png").getImage();
        redChefFrustrated = new ImageIcon("data/RedChefFrustrated.png").getImage();
        redChefAngry = new ImageIcon("data/RedChefAngry.png").getImage();
        iceWall = new ImageIcon("data/IceWall.jpg").getImage();
    }

    @Override
    protected void paintBackground(Graphics2D g) { // applies equivalent background image
        super.paintBackground(g);
        if (game.getLevel() == 1) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    g.drawImage(level1, 450 * j, 450 * i, this);
                }
            }
        } else if (game.getLevel() == 2) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    g.drawImage(level2, 450 * j, 450 * i, this);
                }
            }
        } else if (game.getLevel() == 3) {
            g.drawImage(level3, 0, 0, 1300, 900, this);
        } else if (game.getLevel() == 4) {
            g.drawImage(level4, 0, 0, this);
        }
    }

    @Override
    protected void paintForeground(Graphics2D g) { //draw scores, remaining tomato count and image to represent the mood of the chef
        redPos = game.getWorld().getRedChef().getPosition();
        redPosFloat = this.worldToView(redPos);
        greenPos = game.getWorld().getGreenChef().getPosition();
        greenPosFloat = this.worldToView(greenPos);
        g.setFont(IceFont());
        g.drawString(Integer.toString(game.getWorld().getRedChef().getTomatoCount()), redPosFloat.x + 30, redPosFloat.y - 66);
        g.drawString(Integer.toString(game.getWorld().getGreenChef().getTomatoCount()), greenPosFloat.x + 30, greenPosFloat.y - 66);
        g.setFont(ScoreFont());
        g.drawString(Integer.toString(game.getWorld().getRedChef().getChefScore()), 1260, 57);
        g.drawString(Integer.toString(game.getWorld().getGreenChef().getChefScore()), 55, 57);
        if (game.getWorld().getRedChef().getIsFrustrated()) {
            g.drawImage(redChefFrustrated, 1185, 0, 80, 120, null, this);
        } else if (game.getWorld().getRedChef().getIsAngry()) {
            g.drawImage(redChefAngry, 1185, 0, 80, 120, null, this);
        } else {
            g.drawImage(redChef, 1185, 0, 80, 120, null, this);
        }
        if (game.getWorld().getGreenChef().getIsFrustrated()) {
            g.drawImage(greenChefFrustrated, -20, 0, 80, 120, null, this);
        } else if (game.getWorld().getGreenChef().getIsAngry()) {
            g.drawImage(greenChefAngry, -20, 0, 80, 120, null, this);
        } else {
            g.drawImage(greenChef, -20, 0, 80, 120, null, this);
        }
    }

    private Font ScoreFont() { //creates font for score
        try {
            scoreFont = Font.createFont(Font.TRUETYPE_FONT, new File("data/ScoreFont.ttf")).deriveFont(35f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(scoreFont);
        } catch (IOException | FontFormatException e) {
        }
        return scoreFont;
    }

    private Font IceFont() { //creates font for remaining tomato count
        try {
            iceFont = Font.createFont(Font.TRUETYPE_FONT, new File("data/peng.ttf")).deriveFont(35f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(iceFont);
        } catch (IOException | FontFormatException ignored) {
        }
        return iceFont;
    }
}