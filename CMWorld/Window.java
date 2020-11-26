package CMWorld;

import city.cs.engine.*;

public class Window extends StaticBody { //create window (gives point if fridge, oven and window have been interacted with in correct order)
    BodyImage windowPic = new BodyImage("data/window.png", 8.0f);

    public Window(World w) {
        super(w, new PolygonShape(5.99f, -3.96f, -5.97f, -3.96f, -5.99f, 4.0f, 5.99f, 3.98f));
        addImage(windowPic);
    }
}