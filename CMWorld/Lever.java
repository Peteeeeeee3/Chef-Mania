package CMWorld;

import city.cs.engine.BoxShape;
import city.cs.engine.StaticBody;

public class Lever extends StaticBody { //creates a level for level 3

    public Lever(GameWorld w, String team) {
        super(w, new BoxShape(0.5f, 1));
    }
}
