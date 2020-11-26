package CMWorld;

import city.cs.engine.*;

public class Fridge extends StaticBody { //creates levers for level 3
    private BodyImage redFridge = new BodyImage("data/RedFridge.png", 8);
    private BodyImage greenFridge = new BodyImage("data/GreenFridge.png", 8);


    //-------------Creating Fridges-----------------------------------------------------------------------------------
    public Fridge(String team, World w) {

        super(w, new BoxShape(2, 4));
        if (team == "red") { //Red Fridge
            this.addImage(redFridge);
        }
        if (team == "green") { //Green Fridge
            this.addImage(greenFridge);
        }
    }
}
