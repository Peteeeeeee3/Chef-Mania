package CMWorld;

import city.cs.engine.BoxShape;
import city.cs.engine.StaticBody;
import city.cs.engine.World;

public class Barrier extends StaticBody { //creates barriers for level 3
    public Barrier(World w) {
        super(w, new BoxShape(0.5f, 4.5f));
    }
}
