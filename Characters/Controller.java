package Characters;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import CMWorld.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;

/**
 * @author Peter, Farkas, peter.farkas@city.ac.uk
 */
public class Controller extends KeyAdapter implements StepListener {
    private static final float JUMPING_SPEED = 20;
    private static final float WALKING_SPEED = 12;
    private boolean walkLeftR = false;
    private boolean walkRightR = false;
    private boolean walkLeftG = false;
    private boolean walkRightG = false;

    private Chef greenChef;
    private Chef redChef;
    private GameWorld gameWorld;

    private static BodyImage GreenCookedRight = new BodyImage("data/ChefCookedFoodRightGreen.gif", 10);
    private static BodyImage GreenCookedLeft = new BodyImage("data/ChefCookedFoodLeftGreen.gif", 10);
    private static BodyImage RedCookedLeft = new BodyImage("data/ChefCookedFoodLeftRed.gif", 10);
    private static BodyImage RedCookeRight = new BodyImage("data/ChefCookedFoodRightRed.gif", 10);
    private static BodyImage redChefPic = new BodyImage("data/RedChef.png", 10);
    private static BodyImage greenChefPic = new BodyImage("data/GreenChef.png", 10);
    private static BodyImage GreenCookedStand = new BodyImage("data/ChefCookedFoodStandGreen.png", 10);
    private static BodyImage RedCookedStand = new BodyImage("data/ChefCookedFoodStandRed.png", 10);
    private static BodyImage walkLeftGreen = new BodyImage("data/RunLeftGreen.gif", 10);
    private static BodyImage walkRightGreen = new BodyImage("data/RunRightGreen.gif", 10);
    private static BodyImage walkLeftRed = new BodyImage("data/RunLeftRed.gif", 10);
    private static BodyImage walkRightRed = new BodyImage("data/RunRightRed.gif", 10);
    private static BodyImage greenRawStand = new BodyImage("data/GreenChefRaw.png", 10);
    private static BodyImage redRawStand = new BodyImage("data/RedChefRaw.png", 10);
    private static BodyImage greenRawRight = new BodyImage("data/GreenChefRawRight.gif", 10);
    private static BodyImage greenRawLeft = new BodyImage("data/GreenChefRawLeft.gif", 10);
    private static BodyImage redRawLeft = new BodyImage("data/RedChefRawLeft.gif", 10);
    private static BodyImage redRawRight = new BodyImage("data/RedChefRawRight.gif", 10);

    private enum State {
        NORMAL, FRUSTRATED, ANGRY
    }

    private State moodRed;
    private State moodGreen;

    private Vec2 AimForRed;
    private Vec2 AimForGreen;

    /**
     * Constructor for the class Controller.java.
     * @param redChef - the red Chef character.
     * @param greenChef - the green Chef character.
     * @param gameWorld is the level being player.
     */
    public Controller(Chef redChef, Chef greenChef, GameWorld gameWorld) {
        this.redChef = redChef;
        this.greenChef = greenChef;
        this.gameWorld = gameWorld;
        moodRed = State.NORMAL;
        moodGreen = State.NORMAL;
        gameWorld.addStepListener(this);
    }

    /**
     * Handle key press events for walking and jumping.
     * @param e description of the key event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) { // W = jump
            Vec2 v = greenChef.getLinearVelocity();
            if (Math.abs(v.y) < 0.01f) {
                greenChef.jump(JUMPING_SPEED);
            }
        } else if (code == KeyEvent.VK_UP) { // Up = jump
            Vec2 v = redChef.getLinearVelocity();
            if (Math.abs(v.y) < 0.01f) {
                redChef.jump(JUMPING_SPEED);
            }
        } else if (code == KeyEvent.VK_A) {
            greenChef.startWalking(-WALKING_SPEED); // A = walk left g
            switch (greenChef.getState()) {
                case "none":
                    greenChef.setBI(walkLeftGreen);
                    break;
                case "raw":
                    greenChef.setBI(greenRawLeft);
                    break;
                case "cooked":
                    greenChef.setBI(GreenCookedLeft);
                    break;
            }
            walkRightG = false;
            walkLeftG = true;
        } else if (code == KeyEvent.VK_LEFT) {
            redChef.startWalking(-WALKING_SPEED); // <- = walk left r
            switch (redChef.getState()) {
                case "none":
                    redChef.setBI(walkLeftRed);
                    break;
                case "raw":
                    redChef.setBI(redRawLeft);
                    break;
                case "cooked":
                    redChef.setBI(RedCookedLeft);
                    break;
            }
            walkRightR = false;
            walkLeftR = true;
        } else if (code == KeyEvent.VK_D) {
            greenChef.startWalking(WALKING_SPEED); // D = walk right g
            switch (greenChef.getState()) {
                case "none":
                    greenChef.setBI(walkRightGreen);
                    break;
                case "raw":
                    greenChef.setBI(greenRawRight);
                    break;
                case "cooked":
                    greenChef.setBI(GreenCookedRight);
                    break;
            }
            walkLeftG = false;
            walkRightG = true;
        } else if (code == KeyEvent.VK_RIGHT) {
            redChef.startWalking(WALKING_SPEED); // -> = walk right r
            switch (redChef.getState()) {
                case "none":
                    redChef.setBI(walkRightRed);
                    break;
                case "raw":
                    redChef.setBI(redRawRight);
                    break;
                case "cooked":
                    redChef.setBI(RedCookeRight);
                    break;
            }
            walkLeftR = false;
            walkRightR = true;
            //shooting
        } else if (code == KeyEvent.VK_M && walkRightR && redChef.getTomatoCount() > 0) { //combat right red
            if (redChef.getIsAngry()) {
                autoAimThrow(redChef, greenChef);
            } else {
                throwRight(redChef, greenChef);
            }
            if (redChef.getIsFrustrated() && redChef.getTomatoCount() > 2) {
                for (int i = 0; i < 3; i++) {
                    redChef.shotTomatoes();
                }
            } else if (redChef.getIsFrustrated() && redChef.getTomatoCount() == 2) {
                redChef.shotTomatoes();
                redChef.shotTomatoes();
            } else {
                redChef.shotTomatoes();
            }
        } else if (code == KeyEvent.VK_F && walkRightG && greenChef.getTomatoCount() > 0) { //combat right green
            if (greenChef.getIsAngry()) {
                autoAimThrow(greenChef, redChef);
            } else {
                throwRight(greenChef, redChef);
            }
            if (greenChef.getIsFrustrated() && greenChef.getTomatoCount() > 2) {
                for (int i = 0; i < 3; i++) {
                    greenChef.shotTomatoes();
                }
            } else if (greenChef.getIsFrustrated() && greenChef.getTomatoCount() == 2) {
                greenChef.shotTomatoes();
                greenChef.shotTomatoes();
            } else {
                greenChef.shotTomatoes();
            }
        } else if (code == KeyEvent.VK_M && walkLeftR && redChef.getTomatoCount() > 0) { //combat left red
            if (redChef.getIsAngry()) {
                autoAimThrow(redChef, greenChef);
            } else {
                throwLeft(redChef, greenChef);
            }
            if (redChef.getIsFrustrated() && redChef.getTomatoCount() > 2) {
                for (int i = 0; i < 3; i++) {
                    redChef.shotTomatoes();
                }
            } else if (redChef.getIsFrustrated() && redChef.getTomatoCount() == 2) {
                redChef.shotTomatoes();
                redChef.shotTomatoes();
            } else {
                redChef.shotTomatoes();
            }
        } else if (code == KeyEvent.VK_F && walkLeftG && greenChef.getTomatoCount() > 0) { //combat left green
            if (greenChef.getIsAngry()) {
                autoAimThrow(greenChef, redChef);
            } else {
                throwLeft(greenChef, redChef);
            }
            //counting amount of tomatoes shot
            if (greenChef.getIsFrustrated() && greenChef.getTomatoCount() > 2) {
                for (int i = 0; i < 3; i++) {
                    greenChef.shotTomatoes();
                }
            } else if (greenChef.getIsFrustrated() && greenChef.getTomatoCount() == 2) {
                greenChef.shotTomatoes();
                greenChef.shotTomatoes();
            } else {
                greenChef.shotTomatoes();
            }
        }
    }

    /**
     * Handle key release events (stop walking).
     * @param e description of the key event
     */
    @Override
    public void keyReleased(KeyEvent e) { //stop walking and reset animation
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_D) {
            greenChef.stopWalking();
            switch (greenChef.getState()) {
                case "none":
                    greenChef.setBI(greenChefPic);
                    break;
                case "raw":
                    greenChef.setBI(greenRawStand);
                    break;
                case "cooked":
                    greenChef.setBI(GreenCookedStand);
                    break;
            }
        } else if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_RIGHT) {
            redChef.stopWalking();
            switch (redChef.getState()) {
                case "none":
                    redChef.setBI(redChefPic);
                    break;
                case "raw":
                    redChef.setBI(redRawStand);
                    break;
                case "cooked":
                    redChef.setBI(RedCookedStand);
                    break;
            }
        }
    }

    /**
     * Throw a tomato(es) to the left.
     * <p>
     *     Depending on the mood and the tomato count of a Chef, this method will throw up to 3 tomatoes to the left.
     * </p>
     * @param c - The chef throwing the tomato.
     * @param target - The chef the tomato will potentially hit.
     */
    private void throwLeft(Chef c, Chef target) { //attack left (tomato)
        if (c.getIsFrustrated() && c.getTomatoCount() > 2) { //if the chef is frustrated and has more than 2 tomatoes, create and shoot 3 tomatoes
            Tomato up = new Tomato(gameWorld, -40, 0.2f);
            up.setPosition(new Vec2(c.getPosition().x - 2, c.getPosition().y + 4));
            up.addCollisionListener(new TomatoCollide(target, gameWorld));
            Tomato down = new Tomato(gameWorld, -40, 0.2f);
            down.setPosition(new Vec2(c.getPosition().x - 2, c.getPosition().y));
            down.addCollisionListener(new TomatoCollide(target, gameWorld));
            Tomato to = new Tomato(gameWorld, -40, 0.2f);
            to.setPosition(new Vec2(c.getPosition().x - 2, c.getPosition().y + 2));
            to.addCollisionListener(new TomatoCollide(target, gameWorld));
        } else if (c.getIsFrustrated() && c.getTomatoCount() == 2) { //if chef is frustrated and has two tomatoes, create and shoot 2 tomatoes
            Tomato up = new Tomato(gameWorld, -40, 0.2f);
            up.setPosition(new Vec2(c.getPosition().x - 2, c.getPosition().y + 4));
            up.addCollisionListener(new TomatoCollide(target, gameWorld));
            Tomato to = new Tomato(gameWorld, -40, 0.2f);
            to.setPosition(new Vec2(c.getPosition().x - 2, c.getPosition().y + 2));
            to.addCollisionListener(new TomatoCollide(target, gameWorld));
        } else { //otherwise, create and shoot one tomato
            Tomato to = new Tomato(gameWorld, -40, 0.2f);
            to.setPosition(new Vec2(c.getPosition().x - 2, c.getPosition().y + 2));
            to.addCollisionListener(new TomatoCollide(target, gameWorld));
        }
    }

    /**
     * Throw a tomato(es) to the right.
     * <p>
     *     Depending on the mood and the tomato count of a Chef, this method will throw up to 3 tomatoes to the right.
     * </p>
     * @param c - The chef throwing the tomato.
     * @param target - The chef the tomato will potentially hit.
     */
    private void throwRight(Chef c, Chef target) { //attack right (tomato)
        if (c.getIsFrustrated() && c.getTomatoCount() > 2) { //if the chef is frustrated and has more than 2 tomatoes, create and shoot 3 tomatoes
            Tomato up = new Tomato(gameWorld, 40, 0.2f);
            up.setPosition(new Vec2(c.getPosition().x + 2, c.getPosition().y + 4));
            up.addCollisionListener(new TomatoCollide(target, gameWorld));
            Tomato down = new Tomato(gameWorld, 40, 0.2f);
            down.setPosition(new Vec2(c.getPosition().x + 2, c.getPosition().y));
            down.addCollisionListener(new TomatoCollide(target, gameWorld));
            Tomato to = new Tomato(gameWorld, 40, 0.2f);
            to.setPosition(new Vec2(c.getPosition().x + 2, c.getPosition().y + 2));
            to.addCollisionListener(new TomatoCollide(target, gameWorld));
        } else if (c.getIsFrustrated() && c.getTomatoCount() == 2) { //if chef is frustrated and has two tomatoes, create and shoot 2 tomatoes
            Tomato up = new Tomato(gameWorld, 40, 0.2f);
            up.setPosition(new Vec2(c.getPosition().x + 2, c.getPosition().y + 4));
            up.addCollisionListener(new TomatoCollide(target, gameWorld));
            Tomato to = new Tomato(gameWorld, 40, 0.2f);
            to.setPosition(new Vec2(c.getPosition().x + 2, c.getPosition().y + 2));
            to.addCollisionListener(new TomatoCollide(target, gameWorld));
        } else { //otherwise, create and shoot one tomato
            Tomato to = new Tomato(gameWorld, 40, 0.2f);
            to.setPosition(new Vec2(c.getPosition().x + 2, c.getPosition().y + 2));
            to.addCollisionListener(new TomatoCollide(target, gameWorld));
        }
    }

    /**
     * Automatically aims at the targeted chef.
     * <p>
     *     Applied when the throwing chef's mood is angry. Throws a tomato straight at the current position of the
     *     targeted chef.
     * </p>
     * @param c - The chef throwing the tomato.
     * @param target - The chef the tomato will potentially hit.
     */
    private void autoAimThrow(Chef c, Chef target) { //create tomato
        Tomato to = new Tomato(gameWorld, 0, 0.2f);
        to.addCollisionListener(new TomatoCollide(target, gameWorld));
        if (c == redChef) { //set position according to bulletPlacementRed() or bulletPlacementGreen()
            to.setPosition(Objects.requireNonNull(bulletPlacementRed()));
        } else {
            to.setPosition(Objects.requireNonNull(bulletPlacementGreen()));
        }
        if (c == redChef) { //apply a vector for linear movement
            to.setLinearVelocity(AimForGreen);
        } else if (c == greenChef) {
            to.setLinearVelocity(AimForRed);
        }
    }

    /**
     * Setter for gameWorld.
     * @param gameWorld - New value of gameWorld.
     */
    public void setGameWorld(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        this.gameWorld.addStepListener(this);
    }

    /**
     * Setter for redChef.
     * @param redChef - New value of redChef.
     */
    public void setRedChef(Chef redChef) {
        this.redChef = redChef;
    }

    /**
     * Setter for greenChef.
     * @param greenChef - New value of greenChef.
     */
    public void setGreenChef(Chef greenChef) {
        this.greenChef = greenChef;
    }

    /**
     * Called immediatly before each simulation step.
     * @param stepEvent - The event descriptor.
     */
    @Override
    public void preStep(StepEvent stepEvent) { //step listener regularly changes moods of chefs if conditions apply
        if (normalRed()) {
            moodRed = State.NORMAL;
        } else if (frustratedRed()) {
            moodRed = State.FRUSTRATED;
        } else if (angryRed()) {
            moodRed = State.ANGRY;
        }

        if (normalGreen()) {
            moodGreen = State.NORMAL;
        } else if (frustratedGreen()) {
            moodGreen = State.FRUSTRATED;
        } else if (angryGreen()) {
            moodGreen = State.ANGRY;
        }
        doWhenMood();
        setAim();
    }

    private void doWhenMood() { //change booleans of chefs
        switch (moodRed) {
            case NORMAL:
                redChef.setIsFrustrated(false);
                redChef.setIsAngry(false);
                break;
            case FRUSTRATED:
                redChef.setIsFrustrated(true);
                redChef.setIsAngry(false);
                break;
            case ANGRY:
                redChef.setIsAngry(true);
                redChef.setIsFrustrated(false);
                break;
        }
        switch (moodGreen) {
            case NORMAL:
                greenChef.setIsFrustrated(false);
                greenChef.setIsAngry(false);
                break;
            case FRUSTRATED:
                greenChef.setIsFrustrated(true);
                greenChef.setIsAngry(false);
                break;
            case ANGRY:
                greenChef.setIsAngry(true);
                greenChef.setIsFrustrated(false);
                break;
        }
    }

    /**
     * Called immediately after each simulation step.
     * @param stepEvent - The event descriptor.
     */
    @Override
    public void postStep(StepEvent stepEvent) {

    }

    /**
     * Returns a boolean for the mood normal for redChef.
     * @return - True if the red chef's mood is normal.
     */
    private boolean normalRed() {
        if (greenChef.getChefScore() < 2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns a boolean for the mood normal for greenChef.
     * @return - True if the green chef's mood is normal.
     */
    private boolean normalGreen() {
        if (redChef.getChefScore() < 2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns a boolean for the mood frustrated for redChef.
     * @return - True if the red chef's mood is frustrated.
     */
    private boolean frustratedRed() {
        if (greenChef.getChefScore() >= 2 && greenChef.getChefScore() < 4) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns a boolean for the mood frustrated for greenChef.
     * @return - True if the green chef's mood is frustrated.
     */
    private boolean frustratedGreen() {
        if (redChef.getChefScore() >= 2 && redChef.getChefScore() < 4) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns a boolean for the mood angry for redChef.
     * @return - True if the red chef's mood is angry.
     */
    private boolean angryRed() {
        if (greenChef.getChefScore() >= 4) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns a boolean for the mood angry for greenChef.
     * @return - True if the green chef's mood is angry.
     */
    private boolean angryGreen() {
        if (redChef.getChefScore() >= 4) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sets the vector for the throw for autoAimThrow().
     */
    private void setAim() { //specifies vectors for linear movement
        AimForRed = new Vec2((redChef.getPosition().x - greenChef.getPosition().x) * 30, (redChef.getPosition().y - greenChef.getPosition().y) * 30);
        AimForGreen = new Vec2((greenChef.getPosition().x - redChef.getPosition().x) * 30, (greenChef.getPosition().y - redChef.getPosition().y) * 30);
    }

    /**
     * Determines where to shoot tomato from for autoAimThrow().
     * <p>
     *     To avoid the tomato from hitting the player that is actually throwing it, the position of where
     *     to throw the tomato, from is determined by the position of the targeted chef relative to the throwing chef.
     *     Specific to redChef.
     * </p>
     * @return - The position of where to shoot the tomato from as a Vec2.
     */
    private Vec2 bulletPlacementRed() { //specifies placement of spawned tomato to avoid premature collision with red chef
        if (redChef.getPosition().x > greenChef.getPosition().x) {
            return new Vec2(redChef.getPosition().x - 2, redChef.getPosition().y);
        } else if (redChef.getPosition().x == greenChef.getPosition().x) {
            if (redChef.getPosition().y > greenChef.getPosition().y) {
                return new Vec2(redChef.getPosition().x, redChef.getPosition().y - 2);
            } else if (redChef.getPosition().y > greenChef.getPosition().y) {
                return new Vec2(redChef.getPosition().x, redChef.getPosition().y + 2);
            }
        } else {
            return new Vec2(redChef.getPosition().x + 2, redChef.getPosition().y);
        }
        return null;
    }

    /**
     * Determines where to shoot tomato from for autoAimThrow().
     * <p>
     *     To avoid the tomato from hitting the player that is actually throwing it, the position of where
     *     to throw the tomato, from is determined by the position of the targeted chef relative to the throwing chef.
     *     Specific to greenChef.
     * </p>
     * @return
     */
    private Vec2 bulletPlacementGreen() { //specifies placement of spawned tomato to avoid premature collision with green chef
        if (greenChef.getPosition().x > redChef.getPosition().x) {
            return new Vec2(greenChef.getPosition().x - 2, greenChef.getPosition().y);
        } else if (greenChef.getPosition().x == redChef.getPosition().x) {
            if (greenChef.getPosition().y > redChef.getPosition().y) {
                return new Vec2(greenChef.getPosition().x, greenChef.getPosition().y - 2);
            } else if (greenChef.getPosition().y > redChef.getPosition().y) {
                return new Vec2(greenChef.getPosition().x, greenChef.getPosition().y + 2);
            }
        } else {
            return new Vec2(greenChef.getPosition().x + 2, greenChef.getPosition().y);
        }
        return null;
    }
}