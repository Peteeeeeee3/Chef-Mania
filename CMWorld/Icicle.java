package CMWorld;

import city.cs.engine.BodyImage;
import city.cs.engine.DynamicBody;
import city.cs.engine.PolygonShape;
import city.cs.engine.Shape;
import Characters.*;
import org.jbox2d.common.Vec2;

public class Icicle extends DynamicBody { //creates an icicle for level 2 that drops from above every couple seconds

    private BodyImage iciclePic = new BodyImage("data/Icicle.png", 0.8f);

    private static final Shape icicleShape = new PolygonShape(-0.249f,0.42f, 0.217f,0.416f, -0.041f,-0.378f);

    public Icicle(GameWorld w, int x, Chef redChef, Chef greenChef) {
        super(w, icicleShape);
        addImage(iciclePic);
        this.setPosition(new Vec2(x, 3.5f));
        addCollisionListener(new IcicleCollide(w, redChef, greenChef));
    }
}
