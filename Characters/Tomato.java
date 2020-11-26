package Characters;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import java.awt.*;

public class Tomato extends DynamicBody { //create tomato

    public Tomato(World w, int v, float r) {
        super(w, new CircleShape(r));
        setLinearVelocity(new Vec2(v, 0));
        setGravityScale(0.2f);
        setFillColor(Color.red);
    }
}
