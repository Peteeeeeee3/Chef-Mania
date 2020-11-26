package CMWorld;

import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;
import CMGame.*;
import Characters.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Level4 extends GameWorld {

    private Window pointWindow;
    private final float friction = 0.2f;
    private Shape shape;
    private Spring spring1;
    private Spring spring2;
    private Spring spring3;
    private Spring spring4;


    public Level4(Game game, Controller controller) {
        super(game, controller);
        game.setLevel(4);
        shape = new BoxShape(1, 0.5f);
    }

    @Override
    public void populate(Game game) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super.populate(game);

        pointWindow = this.getPointWindow();

        //fridge platform left
        for (int i = 0; i < 2; i++) {
            Shape leftShape = new BoxShape(2.5f, 0.5f);
            Body leftBody = new StaticBody(this, leftShape);
            SolidFixture leftPlat = new SolidFixture(leftBody, leftShape, 2400);
            leftBody.addImage(clouds);
            leftPlat.setFriction(friction);
            leftBody.setPosition(new Vec2(-14.5f + 5 * i, -17.5f));
        }

        //fridge platform right
        for (int i = 0; i < 2; i++) {
            Shape rightShape = new BoxShape(2.5f, 0.5f);
            Body rightBody = new StaticBody(this, rightShape);
            SolidFixture rightPlat = new SolidFixture(rightBody, rightShape, 2400);
            rightBody.addImage(clouds);
            rightPlat.setFriction(friction);
            rightBody.setPosition(new Vec2(14.5f - 5 * i, -17.5f));
        }

        //oven platform left
        for (int i = 0; i < 2; i++) {
            Shape leftOShape = new BoxShape(2.5f, 0.5f);
            Body leftOBody = new StaticBody(this, leftOShape);
            SolidFixture leftOPlat = new SolidFixture(leftOBody, leftOShape, 2400);
            leftOBody.addImage(clouds);
            leftOPlat.setFriction(friction);
            leftOBody.setPosition(new Vec2(-29.5f + 5 * i, -0.5f));
        }
        //oven platform right
        for (int i = 0; i < 2; i++) {
            Shape leftOShape = new BoxShape(2.5f, 0.5f);
            Body leftOBody = new StaticBody(this, leftOShape);
            SolidFixture leftOPlat = new SolidFixture(leftOBody, leftOShape, 2400);
            leftOBody.addImage(clouds);
            leftOPlat.setFriction(friction);
            leftOBody.setPosition(new Vec2(29.5f - 5 * i, -0.5f));
        }

        //vertical wall low
        Shape lowWallShape = new BoxShape(0.5f, 10);
        Body lowWall = new StaticBody(this, lowWallShape);
        lowWall.setPosition(new Vec2(0, -10));

        //vertical wall high
        Shape highWallShape = new BoxShape(0.5f, 1);
        Body highWall = new StaticBody(this, highWallShape);
        highWall.setPosition(new Vec2(0, 8.5f));

        //roof
        Shape roofShape = new BoxShape(35, 0.5f);
        Body roof = new StaticBody(this, roofShape);
        roof.setPosition(new Vec2(0, 18));

        pointWindow.setPosition(new Vec2(0, 13.5f));
        getRedChef().destroy();
        getGreenChef().destroy();
        getRedChef().setDead(true);
        getGreenChef().setDead(true);
        respawnGreen(-25, 2, this, null, null, null, null);
        respawnRed(25, 2, this, null, null, null, null);
        this.getGreenOven().setPosition(new Vec2(-30, 2));
        this.getRedOven().setPosition(new Vec2(30, 2));
        this.getGreenFridge().setPosition(new Vec2(15, -13));
        this.getRedFridge().setPosition(new Vec2(-15, -13));

        //springs
        spring1 = new Spring(this);
        spring1.setPosition(new Vec2(-8, -16.75f));
        spring1.addCollisionListener(new SpringCollision(this));
        spring2 = new Spring(this);
        spring2.setPosition(new Vec2(8, -16.75f));
        spring2.addCollisionListener(new SpringCollision(this));
        spring3 = new Spring(this);
        spring3.setPosition(new Vec2(-20, -5));
        spring3.setAngleDegrees(-45);
        spring3.addCollisionListener(new SpringCollision(this));
        spring4 = new Spring(this);
        spring4.setPosition(new Vec2(20, -5));
        spring4.setAngleDegrees(45);
        spring4.addCollisionListener(new SpringCollision(this));
    }

    public Spring getSpring1() {
        return spring1;
    }

    public Spring getSpring2() {
        return spring2;
    }

    public Spring getSpring3() {
        return spring3;
    }

    public Spring getSpring4() {
        return spring4;
    }
}