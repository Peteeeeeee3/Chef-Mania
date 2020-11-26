package Characters;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.SoundClip;
import org.jbox2d.common.Vec2;
import CMWorld.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class TomatoCollide implements CollisionListener {
    private Chef chef;
    private GameWorld gameWorld;
    private static SoundClip splat;
    private double sV;
    private TinCan tinCan;

    static {
        try {
            splat = new SoundClip("data/Splat.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void collide(CollisionEvent c) {
        if (c.getOtherBody() == chef) { //tomato and chef disappear when hit
            System.out.println(gameWorld.getGame().getMenu().getMuteSound());
            if (!gameWorld.getGame().getMenu().getMuteSound()) {
                splat.play();
                sV = splatVolume();
                splat.setVolume(sV);
            }
            c.getOtherBody().setPosition(new Vec2(-50, -50)); //relocate to avoid second position for tomatoes when throwing
            c.getReportingBody().destroy();
            c.getOtherBody().destroy();
            tinCan = new TinCan(gameWorld, c.getReportingBody().getPosition().x, c.getReportingBody().getPosition().y, chef); //create tin when chef hit
            tinCan.addCollisionListener(new TinCollision(gameWorld.getRedChef(), gameWorld.getGreenChef()));
            ((Chef) c.getOtherBody()).setDead(true);
            if (((Chef) c.getOtherBody()).getTeam().equals("red")) { //apply correct values for variables for respawn for red chef according to level
                if (gameWorld.getGame().getLevel() == 1) {
                    gameWorld.setScoreTempRed(gameWorld.getRedChef().getChefScore());
                    gameWorld.respawnRed(15, -12, gameWorld, null, null, null, null);
                    gameWorld.getRedChef().setChefScore(gameWorld.getScoreTempRed());
                } else if (gameWorld.getGame().getLevel() == 2) {
                    gameWorld.setScoreTempRed(gameWorld.getRedChef().getChefScore());
                    gameWorld.respawnRed(5, -14.5f, gameWorld, null, null, null, null);
                    gameWorld.getRedChef().setChefScore(gameWorld.getScoreTempRed());
                } else if (gameWorld.getGame().getLevel() == 3) {
                    gameWorld.setScoreTempRed(gameWorld.getRedChef().getChefScore());
                    gameWorld.respawnRed(25, 7, gameWorld, ((Level3) gameWorld.getGame().getWorld()).getRedLever(), ((Level3) gameWorld.getGame().getWorld()).getGreenLever(), ((Level3) gameWorld.getGame().getWorld()).getRightBarrier(), ((Level3) gameWorld.getGame().getWorld()).getLeftBarrier());
                    gameWorld.getRedChef().setChefScore(gameWorld.getScoreTempRed());
                } else if (gameWorld.getGame().getLevel() == 4) {
                    gameWorld.setScoreTempRed(gameWorld.getRedChef().getChefScore());
                    gameWorld.respawnRed(25, 2, gameWorld, null, null, null, null);
                    gameWorld.getRedChef().setChefScore(gameWorld.getScoreTempRed());
                }
            } else { //apply correct values for variables for respawn for green chef according to level
                if (gameWorld.getGame().getLevel() == 1) {
                    gameWorld.setScoreTempGreen(gameWorld.getGreenChef().getChefScore());
                    gameWorld.respawnGreen(-15, -12, gameWorld, null, null, null, null);
                    gameWorld.getGreenChef().setChefScore(gameWorld.getScoreTempGreen());
                } else if (gameWorld.getGame().getLevel() == 2) {
                    gameWorld.setScoreTempGreen(gameWorld.getGreenChef().getChefScore());
                    gameWorld.respawnGreen(-5, -14.5f, gameWorld, null, null, null, null);
                    gameWorld.getGreenChef().setChefScore(gameWorld.getScoreTempGreen());
                } else if (gameWorld.getGame().getLevel() == 3) {
                    gameWorld.setScoreTempGreen(gameWorld.getGreenChef().getChefScore());
                    gameWorld.respawnGreen(-25, 7, gameWorld, ((Level3) gameWorld.getGame().getWorld()).getRedLever(), ((Level3) gameWorld.getGame().getWorld()).getGreenLever(), ((Level3) gameWorld.getGame().getWorld()).getRightBarrier(), ((Level3) gameWorld.getGame().getWorld()).getLeftBarrier());
                    gameWorld.getGreenChef().setChefScore(gameWorld.getScoreTempGreen());
                } else if (gameWorld.getGame().getLevel() == 4) {
                    gameWorld.setScoreTempGreen(gameWorld.getGreenChef().getChefScore());
                    gameWorld.respawnGreen(-25, 2, gameWorld, null, null, null, null);
                    gameWorld.getGreenChef().setChefScore(gameWorld.getScoreTempGreen());
                }
            }
        } else if (c.getOtherBody() == gameWorld.getRight() || c.getOtherBody() == gameWorld.getLeft() || c.getOtherBody() == gameWorld.getBottom()) { //if tomato hits out of screen walls, destroy tomato without making a sound
            c.getReportingBody().destroy();
        } else { //if tomato hits anything else other than chefs or out of screen walls, destroy tomato and play splat sound
            c.getReportingBody().destroy();
            if (!gameWorld.getGame().getMenu().getMuteSound()) {
                splat.play();
                sV = splatVolume();
                splat.setVolume(sV);
            }
        }
    }

    public TomatoCollide(Chef c, GameWorld gameWorld) {
        chef = c;
        this.gameWorld = gameWorld;
    }

    public double splatVolume() {
        return gameWorld.getGame().getMenu().getVolumeMultiplier();
    }

    public TinCan getTinCan() {
        return tinCan;
    }
}