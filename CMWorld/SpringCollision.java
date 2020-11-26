package CMWorld;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import org.jbox2d.common.Vec2;
import Characters.*;

public class SpringCollision implements CollisionListener {

    private Spring[] springArray; //stores each spring from level 4
    private GameWorld gameWorld;

    public SpringCollision(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        springArray = new Spring[]{((Level4) gameWorld).getSpring1(), ((Level4) gameWorld).getSpring2(), ((Level4) gameWorld).getSpring3(), ((Level4) gameWorld).getSpring4()};
    }

    @Override
    public void collide(CollisionEvent c) { //if a player collides with one of these springs, they bounce up vertically
        if ((c.getReportingBody() == springArray[0] || c.getReportingBody() == springArray[1])) {
            if (c.getOtherBody() instanceof Tomato) { //if the other body is a tomato, destroy it
                c.getOtherBody().destroy();
            } else if(c.getOtherBody() instanceof TinCan) {
                ((TinCan) c.getOtherBody()).setLinearVelocity(new Vec2(0, 30));
            } else {
                ((Chef) c.getOtherBody()).jump(30);
            }
        } else if (c.getReportingBody() == springArray[2]) { //if a player collides with this spring, propel them at an angle to the right
            if (c.getOtherBody() instanceof Tomato) { //if the other body is a tomato, destroy it
                c.getOtherBody().destroy();
            } else if (c.getOtherBody() instanceof TinCan) {
                ((TinCan) c.getOtherBody()).setLinearVelocity(new Vec2(30, 15));
            } else {
                ((Chef) c.getOtherBody()).setLinearVelocity(new Vec2(30, 15));
            }
        } else if (c.getReportingBody() == springArray[3]) { //if a player collides with this spring, propel them at an angle to the left
            if (c.getOtherBody() instanceof Tomato) { //if the other body is a tomato, destroy it
                c.getOtherBody().destroy();
            } else if (c.getOtherBody() instanceof TinCan) {
                ((TinCan) c.getOtherBody()).setLinearVelocity(new Vec2(-30, 15));
            } else {
                ((Chef) c.getOtherBody()).setLinearVelocity(new Vec2(-30, 15));
            }
        }
    }
}
