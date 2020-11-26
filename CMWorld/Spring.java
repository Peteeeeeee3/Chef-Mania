package CMWorld;

import city.cs.engine.*;

public class Spring extends StaticBody { //creates springs for level 4

    private BodyImage springImage = new BodyImage("data/Spring.png", 0.5f);

    public Spring(GameWorld w) {
        super(w, new BoxShape(1, 0.25f));
        addImage(springImage);
    }
}

