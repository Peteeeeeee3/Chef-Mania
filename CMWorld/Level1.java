package CMWorld;

import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;
import CMGame.*;
import Characters.*;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Level1 extends GameWorld {

    private Window PointWindow;

    public Level1(Game game, Controller controller) {
        super(game, controller);
        game.setLevel(1);
    }

    @Override
    public void populate(Game game) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super.populate(game);

        PointWindow = this.getPointWindow();
        //---------------Platforms----------------------------------------------------------------------
        //ground
        for (int i = 0; i < 10; i++) {
            Shape bottomShape = new BoxShape(2.5f, 0.5f);
            Body bottom = new StaticBody(this, bottomShape);
            SolidFixture Ground = new SolidFixture(bottom, bottomShape, 2400);
            bottom.setPosition(new Vec2(-22.5f + 5 * i, -14.5f));
            bottom.addImage(FloorTiles);
            Ground.setFriction(3);
        }

        //Platform middle left
        for (int i = 0; i < 3; i++) {
            Shape leftMid = new BoxShape(2.5f, 0.5f);
            Body fridgePlatLeft = new StaticBody(this, leftMid);
            SolidFixture PlatLeftMid = new SolidFixture(fridgePlatLeft, leftMid, 2400);
            fridgePlatLeft.setPosition(new Vec2(-27.5f + 5 * i, -3));
            fridgePlatLeft.addImage(FloorTiles);
            PlatLeftMid.setFriction(3);
        }

        //Platform middle right
        for (int i = 0; i < 3; i++) {
            Shape rightMid = new BoxShape(2.5f, 0.5f);
            Body fridgePlatRight = new StaticBody(this, rightMid);
            SolidFixture PlatRightMid = new SolidFixture(fridgePlatRight, rightMid, 2400);
            fridgePlatRight.setPosition(new Vec2(27.5f - 5 * i, -3));
            fridgePlatRight.addImage(FloorTiles);
            PlatRightMid.setFriction(3);
        }

        //top Platform
        for (int i = 0; i < 6; i++) {
            Shape topShape = new BoxShape(2.5f, 0.5f);
            Body top = new StaticBody(this, topShape);
            SolidFixture PlatTop = new SolidFixture(top, topShape, 2400);
            top.setPosition(new Vec2(-12.5f + 5 * i, 6.5f));
            top.addImage(FloorTiles);
            PlatTop.setFriction(3);
        }

        PointWindow.setPosition(PWPosition(0, 13));
        getRedOven().setPosition(new Vec2(23, -12));
        getGreenOven().setPosition(new Vec2(-23, -12));
        getRedFridge().setPosition(new Vec2(-28, 1.5f));
        getGreenFridge().setPosition(new Vec2(28, 1.5f));
    }
}
