package CMWorld;

import Characters.Chef;
import city.cs.engine.*;
import org.jbox2d.common.Vec2;

public class TinCan extends DynamicBody {

    private BodyImage tinPic;

    public TinCan(World w, float x, float y, Chef chef) { //creates a tin can at the position where the chef was hit by the tomato and assigns the equivalent color coded image to it
        super(w, new PolygonShape(-0.432f, 0.711f, 0.435f, 0.705f, 0.411f, -0.651f, -0.402f, -0.66f));
        setPosition(new Vec2(x, y));
        if (chef == ((GameWorld) w).getGreenChef()) {
            tinPic = new BodyImage("data/TinCanRed.jpg", 1.5f);
        } else if (chef == ((GameWorld) w).getRedChef()) {
            tinPic = new BodyImage("data/TinCanGreen.jpg", 1.5f);
        }
        addImage(tinPic);
    }
}
