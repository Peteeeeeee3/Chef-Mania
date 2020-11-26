package CMWorld;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import Characters.*;
import CMGame.*;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public abstract class GameWorld extends World {

    private Chef redChef;
    private Chef greenChef;
    private Oven greenOven;
    private Oven redOven;
    private Fridge redFridge;
    private Fridge greenFridge;
    private Window PointWindow;
    private DeathBoundaries bottom;
    private DeathBoundaries left;
    private DeathBoundaries right;
    private Game game;
    private int scoreTempRed;
    private int scoreTempGreen;
    private int LevelNumber;

    private static BodyImage redChefPic = new BodyImage("data/RedChef.png", 10);
    private static BodyImage greenChefPic = new BodyImage("data/GreenChef.png", 10);
    static BodyImage FloorTiles = new BodyImage("data/FloorTiles.png", 1);
    static BodyImage clouds = new BodyImage("data/Cloud.png", 1.2f);
    static BodyImage concrete = new BodyImage("data/ConcreteFloor.png", 1);
    static BodyImage wall = new BodyImage("data/Wall.png", 8);
    private Controller controller;

    private static SoundClip ovenSound;
    private static SoundClip pointWindowSound;
    private static SoundClip fridgeSound;

    static {
        try {
            ovenSound = new SoundClip("data/OvenSound.wav");
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            pointWindowSound = new SoundClip("data/PointWindowSound.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            fridgeSound = new SoundClip("data/FridgeDoor4.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public GameWorld(Game game, Controller controller) {
        super();
        this.game = game;
        this.controller = controller;
    }

    public void populate(Game game) throws UnsupportedAudioFileException, IOException, LineUnavailableException { //generates objects that are seen in every level (such as fridge, oven, etc.)

        //window
        PointWindow = new Window(this);
//---------------Objects & Characters----------------------------------------------------------------------------------------------
        redOven = new Oven("red", this);
        greenOven = new Oven("green", this);
        redFridge = new Fridge("red", this);
        greenFridge = new Fridge("green", this);

        redChef = new Chef("red", this);
        greenChef = new Chef("green", this);
//---------------DeathBoundaries----------------------------------------------------------------------------------------------------
        bottom = new DeathBoundaries(this, 40, 0.5f, 0, -20);
        left = new DeathBoundaries(this, 0.5f, 40, -38, 0);
        right = new DeathBoundaries(this, 0.5f, 40, 38, 0);
//---------------Characters--------------------------------------------------------------------------------------------------------
        if (redChef.getDead()) {
            redChef.setBI(redChefPic);
            redChef.setPosition(new Vec2(15, -12)); //spawn in correct position
            redChef.addCollisionListener(new Collision(redFridge, redOven, PointWindow, bottom, left, right, this));
            redChef.setDead(false);
        }

        if (greenChef.getDead()) {
            greenChef.setBI(greenChefPic);
            greenChef.setPosition(new Vec2(-15, -12)); //spawn in correct position
            greenChef.addCollisionListener(new Collision(greenFridge, greenOven, PointWindow, bottom, left, right, this)); //Collision
            greenChef.setDead(false);
        }
        setGravity(15);
    }

    public void respawnRed(float x, float y, GameWorld gw, Lever rl, Lever gl, Barrier rb, Barrier gb) { //create new equivalent character when destroyed
        if (redChef.getDead()) {
            redChef = new Chef("red", gw);
            redChef.setChefScore(scoreTempRed);
            redChef.setBI(redChefPic);
            redChef.setPosition(new Vec2(x, y)); //spawn in correct position
            redChef.setDead(false);
            controller.setRedChef(redChef);
            controller.setGameWorld(this);
            redChef.addCollisionListener(new Collision(redFridge, redOven, PointWindow, bottom, left, right, gw));
            if (getGame().getLevel() == 3) {
                redChef.addCollisionListener(new LeverControl(rl, gl, rb, gb));
            }
            if (getGame().getLevel() == 4) {
                redChef.addCollisionListener(new SpringCollision(gw));
            }
        }
    }

    public void respawnGreen(float x, float y, GameWorld gw, Lever rl, Lever gl, Barrier rb, Barrier gb) { //create new equivalent character when destroyed
        if (greenChef.getDead()) {
            greenChef = new Chef("green", gw);
            greenChef.setChefScore(scoreTempGreen);
            greenChef.setBI(greenChefPic);
            greenChef.setPosition(new Vec2(x, y)); //spawn in correct position
            greenChef.setDead(false);
            controller.setGreenChef(greenChef);
            controller.setGameWorld(this);
            greenChef.addCollisionListener(new Collision(greenFridge, greenOven, PointWindow, bottom, left, right, gw)); //Collision
            if (getGame().getLevel() == 3) {
                greenChef.addCollisionListener(new LeverControl(rl, gl, rb, gb));
            }
            if (getGame().getLevel() == 4) {
                greenChef.addCollisionListener(new SpringCollision(gw));
            }
        }
    }

    public Vec2 PWPosition(int x, int y) {
        return new Vec2(x, y);
    }

    public Chef getRedChef() {
        return redChef;
    }

    public Chef getGreenChef() {
        return greenChef;
    }

    public Fridge getRedFridge() {
        return redFridge;
    }

    public Fridge getGreenFridge() {
        return greenFridge;
    }

    public Oven getRedOven() {
        return redOven;
    }

    public Oven getGreenOven() {
        return greenOven;
    }

    public Window getPointWindow() {
        return PointWindow;
    }

    public DeathBoundaries getBottom() {
        return bottom;
    }

    public DeathBoundaries getRight() {
        return right;
    }

    public DeathBoundaries getLeft() {
        return left;
    }

    public Game getGame() {
        return game;
    }

    public void setScoreTempRed(int scoreTemp) {
        this.scoreTempRed = scoreTemp;
    }

    public int getScoreTempRed() {
        return scoreTempRed;
    }

    public void setScoreTempGreen(int scoreTempGreen) {
        this.scoreTempGreen = scoreTempGreen;
    }

    public int getScoreTempGreen() {
        return scoreTempGreen;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public SoundClip getFridgeSound() {
        return fridgeSound;
    }

    public SoundClip getOvenSound() {
        return ovenSound;
    }

    public SoundClip getPointWindowSound() {
        return pointWindowSound;
    }

    public int getLevelNumber() {
        return LevelNumber;
    }

    public void setLevelNumber(int levelNumber) {
        LevelNumber = levelNumber;
    }
}