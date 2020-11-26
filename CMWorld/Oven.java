package CMWorld;

import city.cs.engine.*;

public class Oven extends StaticBody{ //creates oven
    private BodyImage redOven = new BodyImage("data/RedOven.png", 4);
    private BodyImage greenOven = new BodyImage("data/GreenOven.png", 4);
//---------Creating Ovens-----------------------------------------------------------------------------------------
    public Oven(String team, World w){
        super(w, new BoxShape(2, 2));
        if(team == "red"){ //Oven Red Team
            addImage(redOven);
        }
        if(team == "green"){ //Oven Green Team
            addImage(greenOven);
        }
    }
}
