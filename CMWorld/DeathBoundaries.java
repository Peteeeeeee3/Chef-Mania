package CMWorld;

import city.cs.engine.BoxShape;
import city.cs.engine.StaticBody;
import org.jbox2d.common.Vec2;

public class DeathBoundaries extends StaticBody { //create out of screen platforms (act as boundary, stop from escaping level
    public DeathBoundaries(GameWorld gw, float w, float h, float x, float y) {
        super(gw, new BoxShape(w, h));
        setPosition(new Vec2(x, y));
    }
}

