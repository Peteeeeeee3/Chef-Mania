package CMWorld;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import Characters.*;

public class TinCollision implements CollisionListener {

    private Chef red;
    private Chef green;

    @Override
    public void collide(CollisionEvent c) {
        if (c.getOtherBody() == red || c.getOtherBody() == green) {
            ((Chef) c.getOtherBody()).setTomatoCount(((Chef) c.getOtherBody()).getTomatoCount() + 2);
            c.getReportingBody().destroy();
        }
    }

    public TinCollision(Chef red, Chef green) {
        this.red = red;
        this.green = green;
    }
}
