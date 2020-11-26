package CMWorld;

import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;
import CMGame.*;
import Characters.*;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Level3 extends GameWorld {

    private Window PointWindow;
    private Lever redLever = new Lever(this, "red");
    private Lever greenLever = new Lever(this, "green");
    private Barrier rightBarrier = new Barrier(this);
    private Barrier leftBarrier = new Barrier(this);
    private BodyImage redUp = new BodyImage("data/RedLeverUp.png", 2);
    private BodyImage greenUp = new BodyImage("data/GreenLeverUp.png", 2);
    private BodyImage wallGreen = new BodyImage("data/WallGreen.png", 9);
    private BodyImage wallRed = new BodyImage("data/WallRed.png", 9);

    public Level3(Game game, Controller controller) {
        super(game, controller);
        game.setLevel(3);
    }

    @Override
    public void populate(Game game) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super.populate(game);

        PointWindow = this.getPointWindow();
        int friction = 3;
//---------------Platforms----------------------------------------------------------------------
        //ground
        for (int i = 0; i < 10; i++) {
            Shape bottomShape = new BoxShape(2.5f, 0.5f);
            Body bottom = new StaticBody(this, bottomShape);
            bottom.addImage(concrete);
            SolidFixture Ground = new SolidFixture(bottom, bottomShape, 2400);
            bottom.setPosition(new Vec2(-22.5f + 5 * i, -15.5f));
            Ground.setFriction(friction);
        }

        //window platform
        for (int i = 0; i < 4; i++) {
            Shape windowPlatform = new BoxShape(2.5f, 0.5f);
            Body WindowPlatform = new StaticBody(this, windowPlatform);
            WindowPlatform.addImage(concrete);
            SolidFixture PlatWindow = new SolidFixture(WindowPlatform, windowPlatform, 2400);
            WindowPlatform.setPosition(new Vec2(-7.5f + 5 * i, -5.5f));
            PlatWindow.setFriction(friction);
        }

        //top left
        for (int i = 0; i < 2; i++) {
            Shape topLeft = new BoxShape(2.5f, 0.5f);
            Body TopLeft = new StaticBody(this, topLeft);
            TopLeft.addImage(concrete);
            SolidFixture LeftOvenPlatform = new SolidFixture(TopLeft, topLeft, 2400);
            TopLeft.setPosition(new Vec2(-29.5f + 5 * i, 6.5f));
            LeftOvenPlatform.setFriction(friction);
        }

        //top right
        for (int i = 0; i < 2; i++) {
            Shape topRight = new BoxShape(2.5f, 0.5f);
            Body TopRight = new StaticBody(this, topRight);
            TopRight.addImage(concrete);
            SolidFixture RightOvenPlatform = new SolidFixture(TopRight, topRight, 2400);
            TopRight.setPosition(new Vec2(29.5f - 5 * i, 6.5f));
            RightOvenPlatform.setFriction(friction);
        }

        //left platform
        Shape a = new BoxShape(2.5f, 0.5f);
        Body b = new StaticBody(this, a);
        b.addImage(concrete);
        SolidFixture c = new SolidFixture(b, a, 2400);
        b.setPosition(new Vec2(-15, 5));
        c.setFriction(friction);

        //right platform
        Shape x = new BoxShape(2.5f, 0.5f);
        Body y = new StaticBody(this, x);
        y.addImage(concrete);
        SolidFixture z = new SolidFixture(y, x, 2400);
        y.setPosition(new Vec2(15, 5));
        z.setFriction(friction);

        //right wall
        Shape rw = new BoxShape(0.5f, 4);
        Body RW = new StaticBody(this, rw);
        RW.addImage(wall);
        RW.setPosition(new Vec2(24.5f, -11));

        //left wall
        Shape lw = new BoxShape(0.5f, 4);
        Body LW = new StaticBody(this, lw);
        LW.addImage(wall);
        LW.setPosition(new Vec2(-24.5f, -11));

        //left barrier (door)
        leftBarrier.setPosition(new Vec2(-9.4f, -10.5f));
        leftBarrier.addImage(wallGreen);

        //right barrier (door)
        rightBarrier.setPosition(new Vec2(9.4f, -10.5f));
        rightBarrier.addImage(wallRed);
//Characters====================================================================================
        getRedChef().destroy();
        getGreenChef().destroy();
        getRedChef().setDead(true);
        getGreenChef().setDead(true);
        respawnRed(25, 7, this, redLever, greenLever, rightBarrier, leftBarrier);
        respawnGreen(-25, 7, this, redLever, greenLever, rightBarrier, leftBarrier);
        PointWindow.setPosition(new Vec2(0, 0));
        getRedOven().setPosition(new Vec2(30, 9));
        getGreenOven().setPosition(new Vec2(-30, 9));
        getGreenFridge().setPosition(new Vec2(-2, -11));
        getRedFridge().setPosition(new Vec2(2, -11));
        greenLever.setPosition(new Vec2(23.5f, -10));
        redLever.setPosition(new Vec2(-23.5f, -10));
        greenLever.addImage(greenUp);
        redLever.addImage(redUp);
    }

    public Lever getGreenLever() {
        return greenLever;
    }

    public Lever getRedLever() {
        return redLever;
    }

    public Barrier getRightBarrier() {
        return rightBarrier;
    }

    public Barrier getLeftBarrier() {
        return leftBarrier;
    }
}
