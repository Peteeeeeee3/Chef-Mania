package CMWorld;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import CMGame.*;
import Characters.*;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Level2 extends GameWorld {
    private Window PointWindow;
    private final float friction = 0.2f;
    private int min = -15;
    private int max = 15;
    private Icicle icicle;
    private int range = max - min + 1;
    private int passedS = 0;
    private int xPos;
    private Random generator = new Random();
    private java.util.Timer IcicleTimer = new Timer();
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() { //every 2 seconds icicle spawns in a random position between x = -15 and x = 15
            passedS++;
            if (passedS % 2 == 0 && getGame().getLevel() == 2) {
                xPos = generator.nextInt(range) - 15;
                icicle = new Icicle(getGame().getWorld(), xPos, getRedChef(), getGreenChef());
            }
        }
    };

    public Level2(Game game, Controller controller) {
        super(game, controller);
        game.setLevel(2);
        setController(new Controller(getRedChef(), getGreenChef(), this));
    }

    @Override
    public void populate(Game game) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super.populate(game);
        startTimer();

        PointWindow = this.getPointWindow();
//---------------Platforms----------------------------------------------------------------------
        //ground
        for (int i = 0; i < 4; i++) {
            Shape bottomShape = new BoxShape(2.5f, 0.5f);
            Body bottom = new StaticBody(this, bottomShape);
            SolidFixture Ground = new SolidFixture(bottom, bottomShape, 2400);
            bottom.setPosition(new Vec2(-7.5f + 5 * i, -15.5f));
            bottom.addImage(FloorTiles);
            Ground.setFriction(friction);
        }
        //platforms
        //left mid
        for (int i = 0; i < 2; i++) {
            Shape leftMidShape = new BoxShape(2.5f, 0.5f);
            Body leftMid = new StaticBody(this, leftMidShape);
            SolidFixture LeftMid = new SolidFixture(leftMid, leftMidShape, 2400);
            leftMid.setPosition(new Vec2(-22.5f + i * 5, -5.5f));
            leftMid.addImage(FloorTiles);
            LeftMid.setFriction(friction);
        }
        //right mid
        for (int i = 0; i < 2; i++) {
            Shape rightMidShape = new BoxShape(2.5f, 0.5f);
            Body rightMid = new StaticBody(this, rightMidShape);
            SolidFixture RightMid = new SolidFixture(rightMid, rightMidShape, 2400);
            rightMid.setPosition(new Vec2(22.5f - i * 5, -5.5f));
            rightMid.addImage(FloorTiles);
            RightMid.setFriction(friction);
        }
        //top right
        for (int i = 0; i < 2; i++) {
            Shape bottomShape = new BoxShape(2.5f, 0.5f);
            Body bottom = new StaticBody(this, bottomShape);
            SolidFixture Ground = new SolidFixture(bottom, bottomShape, 2400);
            bottom.setPosition(new Vec2(-24.5f - 5 * i, 4.5f));
            bottom.addImage(FloorTiles);
            Ground.setFriction(friction);
        }
        //top left
        for (int i = 0; i < 2; i++) {
            Shape bottomShape = new BoxShape(2.5f, 0.5f);
            Body bottom = new StaticBody(this, bottomShape);
            SolidFixture Ground = new SolidFixture(bottom, bottomShape, 2400);
            bottom.setPosition(new Vec2(24.5f + 5 * i, 4.5f));
            bottom.addImage(FloorTiles);
            Ground.setFriction(friction);
        }
        //window platform
        for (int i = 0; i < 6; i++) {
            Shape topShape = new BoxShape(2.5f, 0.5f);
            Body top = new StaticBody(this, topShape);
            SolidFixture PlatTop = new SolidFixture(top, topShape, 2400);
            top.setPosition(new Vec2(-12.5f + 5 * i, 4.5f));
            top.addImage(FloorTiles);
            PlatTop.setFriction(friction);
        }
//Characters==================================================================
        getRedChef().destroy();
        getGreenChef().destroy();
        getRedChef().setDead(true);
        getGreenChef().setDead(true);
        respawnRed(5, -13, this, null, null, null, null);
        respawnGreen(-5, -13, this, null, null, null, null);
        PointWindow.setPosition(new Vec2(0, 10));
        getRedOven().setPosition(new Vec2(2, -13));
        getGreenOven().setPosition(new Vec2(-2, -13));
        getGreenFridge().setPosition(new Vec2(-30, 9));
        getRedFridge().setPosition(new Vec2(30, 9));
    }

    public void startTimer() {
        IcicleTimer.scheduleAtFixedRate(timerTask, 1000, 1000);
    }

    public Timer getIcicleTimer() {
        return IcicleTimer;
    }
}