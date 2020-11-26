package CMWorld;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import Characters.*;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class IcicleCollide implements CollisionListener {

    private Chef rc;
    private Chef gc;
    private GameWorld gw;

    @Override
    public void collide(CollisionEvent c) {
        if (c.getOtherBody() == rc) { //if red chef touches icicle, destroy both
            gw.setScoreTempRed(gw.getRedChef().getChefScore());
            c.getOtherBody().destroy();
            c.getReportingBody().destroy();
            ((Chef) c.getOtherBody()).setDead(true);
            gw.respawnRed(5, -14.5f, gw, null, null, null, null);
            gw.getRedChef().setChefScore(gw.getScoreTempRed());
        } else if (c.getOtherBody() == gc) { //if green chef touches icicle, destroy both
            gw.setScoreTempGreen(gw.getGreenChef().getChefScore());
            c.getOtherBody().destroy();
            c.getReportingBody().destroy();
            ((Chef) c.getOtherBody()).setDead(true);
            gw.respawnGreen(-5, -14.5f, gw, null, null, null, null);
            gw.getGreenChef().setChefScore(gw.getScoreTempGreen());
        } else { //if icicle touches anything else, destroy only icicle
            c.getReportingBody().destroy();
        }
    }

    public IcicleCollide(GameWorld gameWorld, Chef redChef, Chef greenChef) {
        rc = redChef;
        gc = greenChef;
        gw = gameWorld;
    }
}