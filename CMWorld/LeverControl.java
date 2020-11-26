package CMWorld;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import Characters.*;


public class LeverControl implements CollisionListener {
    private Barrier rightBarrier;
    private Barrier leftBarrier;
    private Lever greenL;
    private Lever redL;
    private BodyImage redUp = new BodyImage("data/RedLeverUp.png", 2);
    private BodyImage redDown = new BodyImage("data/RedLeverDown.png", 2);
    private BodyImage greenUp = new BodyImage("data/GreenLeverUp.png", 2);
    private BodyImage greenDown = new BodyImage("data/GreenLeverDown.png", 2);
    private int collisionCountRed = 0;
    private int collisionCountGreen = 0;

    public LeverControl(Lever rL, Lever gL, Barrier right, Barrier left) {
        redL = rL;
        greenL = gL;
        rightBarrier = right;
        leftBarrier = left;
    }

    @Override
    public void collide(CollisionEvent c) { //senses interaction of lever with relevant chef and also applies correct image to lever
        if (c.getOtherBody() == redL && ((Chef) c.getReportingBody()).getTeam().equals("red")) {
            collisionCountRed++;
            interactRed();
        } else if (c.getOtherBody() == greenL && ((Chef) c.getReportingBody()).getTeam().equals("green")) {
            collisionCountGreen++;
            interactGreen();
        }
        if (collisionCountGreen % 2 == 1) {
            greenL.removeAllImages();
            greenL.addImage(greenDown);
        } else if (collisionCountGreen % 2 == 0) {
            greenL.removeAllImages();
            greenL.addImage(greenUp);
        }
        if (collisionCountRed % 2 == 1) {
            redL.removeAllImages();
            redL.addImage(redDown);
        } else if (collisionCountRed % 2 == 0) {
            redL.removeAllImages();
            redL.addImage(redUp);
        }
    }

    private void interactGreen() { //moves barriers either of screen or into its position when lever is pulled
        leftBarrier.setPosition(new Vec2(-500, -500));
        rightBarrier.setPosition(new Vec2(9.4f, -10.5f));
    }

    private void interactRed() { //moves barriers either of screen or into its position when lever is pulled
        rightBarrier.setPosition(new Vec2(-500, -500));
        leftBarrier.setPosition(new Vec2(-9.4f, -10.5f));
    }
}
