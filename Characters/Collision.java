package Characters;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import org.jbox2d.common.Vec2;
import CMWorld.*;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * @author Peter, Farkas, peter.farkas@city.ac.uk
 */
public class Collision implements CollisionListener {
    private Fridge fridge;
    private Oven oven;
    private Window window;
    private DeathBoundaries bottom;
    private DeathBoundaries left;
    private DeathBoundaries right;
    private GameWorld gw;
    private double fV;
    private double oV;
    private double pwV;

    /**
     * Receive and respond to a collision event.
     * @param c is the collision event description
     */
    @Override
    public void collide(CollisionEvent c) { //collision with fridge to get raw food and tomatoes (amo)

        if (c.getOtherBody() == fridge && ((Chef) c.getReportingBody()).getState().equals("none")) { //if colliding with fridge, then set player's state to raw
            ((Chef) c.getReportingBody()).setState("raw");
            ((Chef) c.getReportingBody()).addTomatoes();
            fV = fridgeVolume();
            System.out.println(fV);
            if (!gw.getGame().getMenu().getMuteSound()) {
                gw.getFridgeSound().play();
                gw.getFridgeSound().setVolume(fV);
            }

        } else if (c.getOtherBody() == oven && ((Chef) c.getReportingBody()).getState().equals("raw")) { //collision with oven to get cooked food, state change to cooked
            ((Chef) c.getReportingBody()).setState("cooked");
            oV = ovenVolume();
            System.out.println(oV);
            if (!gw.getGame().getMenu().getMuteSound()) {
                gw.getOvenSound().play();
                gw.getOvenSound().setVolume(oV);
            }

        } else if (c.getOtherBody() == window && ((Chef) c.getReportingBody()).getState().equals("cooked")) { //collision with window to get point, state set back to normal
            ((Chef) c.getReportingBody()).setState("none");
            ((Chef) c.getReportingBody()).chefScoreUp();
            pwV = pointWindowVolume();
            System.out.println(pwV);
            if (!gw.getGame().getMenu().getMuteSound()) {
                gw.getPointWindowSound().play();
                gw.getPointWindowSound().setVolume(pwV);
            }

            if ((((Chef) c.getReportingBody()).getChefScore() == 5 && ((Chef) c.getReportingBody()).getTeam().equals("red")) || (((Chef) c.getReportingBody()).getChefScore() == 5) && ((Chef) c.getReportingBody()).getTeam().equals("green")) { //if score of either player is equal to 5, move on to the next level
                System.out.println(gw.getGreenChef().getChefScore() + " - " + gw.getRedChef().getChefScore());
                gw.getGame().setLevel(gw.getGame().getLevel());
                try {
                    gw.getGame().goNextLevel();
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                    e.printStackTrace();
                }
            }

        } else if (c.getOtherBody() == bottom || c.getOtherBody() == left || c.getOtherBody() == right) { //if out of screen/hit out of screen platforms: destroy and respawn
            c.getReportingBody().setPosition(new Vec2(-50, -50));
            c.getReportingBody().destroy();
            ((Chef) c.getReportingBody()).setDead(true);
            if (gw.getGame().getLevel() == 1) { //applying correct values to variables for respawn depending on current level
                gw.setScoreTempGreen(gw.getGreenChef().getChefScore());
                gw.setScoreTempRed(gw.getRedChef().getChefScore());
                gw.respawnRed(15, -12, gw, null, null, null, null);
                gw.respawnGreen(-15, -12, gw, null, null, null, null);
                gw.getRedChef().setChefScore(gw.getScoreTempRed());
                gw.getGreenChef().setChefScore(gw.getScoreTempGreen());
            } else if (gw.getGame().getLevel() == 2) {
                gw.setScoreTempGreen(gw.getGreenChef().getChefScore());
                gw.setScoreTempRed(gw.getRedChef().getChefScore());
                gw.respawnGreen(-5, -14.5f, gw, null, null, null, null);
                gw.respawnRed(5, -14.5f, gw, null, null, null, null);
                gw.getRedChef().setChefScore(gw.getScoreTempRed());
                gw.getGreenChef().setChefScore(gw.getScoreTempGreen());
            } else if (gw.getGame().getLevel() == 3) {
                gw.setScoreTempGreen(gw.getGreenChef().getChefScore());
                gw.setScoreTempRed(gw.getRedChef().getChefScore());
                gw.respawnGreen(-25, 7, gw, ((Level3) gw.getGame().getWorld()).getRedLever(), ((Level3) gw.getGame().getWorld()).getGreenLever(), ((Level3) gw.getGame().getWorld()).getRightBarrier(), ((Level3) gw.getGame().getWorld()).getLeftBarrier());
                gw.respawnRed(25, 7, gw, ((Level3) gw.getGame().getWorld()).getRedLever(), ((Level3) gw.getGame().getWorld()).getGreenLever(), ((Level3) gw.getGame().getWorld()).getRightBarrier(), ((Level3) gw.getGame().getWorld()).getLeftBarrier());
                gw.getRedChef().setChefScore(gw.getScoreTempRed());
                gw.getGreenChef().setChefScore(gw.getScoreTempGreen());
            } else if (gw.getGame().getLevel() == 4) {
                gw.setScoreTempGreen(gw.getGreenChef().getChefScore());
                gw.setScoreTempRed(gw.getRedChef().getChefScore());
                gw.respawnGreen(-22, 2, gw, null, null, null, null);
                gw.respawnRed(22, 2, gw, null, null, null, null);
                gw.getRedChef().setChefScore(gw.getScoreTempRed());
                gw.getGreenChef().setChefScore(gw.getScoreTempGreen());
            }
        }
    }

    /**
     * Constructor for the class Collision.java.
     * @param f is the fridge the player will want to interact wit.h
     * @param o is the oven the player will want to interact with.
     * @param w is the window the player will want to interact with.
     * @param b is the bottom out of screen boundary, the player will touch and then respawn.
     * @param l is the left out of screen boundary, the player will touch and then respawn.
     * @param r is the right out of screen boundary, the player will touch and then respawn.
     * @param gameWorld is the level being played.
     */
    public Collision(Fridge f, Oven o, Window w, DeathBoundaries b, DeathBoundaries l, DeathBoundaries r, GameWorld gameWorld) {
        this.fridge = f;
        this.oven = o;
        this.window = w;
        this.bottom = b;
        this.left = l;
        this.right = r;
        this.gw = gameWorld;
    }

    /*Due to base sound of the individual sound files differing,
      to achieve a similar volume on all of them, a base value for the method setVolume() is applied,
      which then gets multiplied by a value given by the slider in the GUI (in class Menu.java) */

    /**
     * Adjusts the fridges' sound's volume.
     * <p>
     *     Takes the multiplier given by the volume slider in Menu.java and multiplies the base volume value of the sound file.
     * </p>
     * @return The value to apply to a setVolume() method to change the volume of the fridge sound in form of a double.
     */
    public double fridgeVolume() {
        return 2.0 * gw.getGame().getMenu().getVolumeMultiplier();
    }

    /**
     * Adjusts the ovens' sound's volume.
     * <p>
     *     Takes the multiplier given by the volume slider in Menu.java and multiplies the base volume value of the sound file.
     * </p>
     * @return The value to apply to a setVolume() method to change the volume of the oven sound in form of a double.
     */
    public double ovenVolume() {
        return 0.7 * gw.getGame().getMenu().getVolumeMultiplier();
    }

    /**
     * Adjusts the point window's sound's volume.
     * <p>
     *     Takes the multiplier given by the volume slider in Menu.java and multiplies the base volume value of the sound file.
     * </p>
     * @return The value to apply to a setVolume() method to change the volume of the point window sound in form of a double.
     */
    public double pointWindowVolume() {
        return 0.7 * gw.getGame().getMenu().getVolumeMultiplier();
    }
}