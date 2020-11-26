package CMGame;

import city.cs.engine.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import CMWorld.*;
import Characters.*;

/**
 * @author Peter, Farkas, peter.farkas@city.ac.uk
 */
public class Game {

    private int level;
    private GameWorld world;
    private UserView view;
    private JFrame frame;
    private Menu menu;
    private JPanel viewPanel;
    private Controller controller;
    private SoundClip theme = new SoundClip("data/SoundTrack.wav");

    /**
     * Constructor for class Game.java
     * @throws UnsupportedAudioFileException - Required by world.populate(this).
     * @throws IOException - Required by world.populate(this).
     * @throws LineUnavailableException - Required by world.populate(this).
     */
    public Game() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        level = 0;
        // basics beginning (start, close, etc.) ==============================
        frame = new JFrame("Chef Mania");
        world = new Level1(this, null);
        world.populate(this);
        controller = new Controller(world.getRedChef(), world.getGreenChef(), world);
        controller.setGameWorld(world);
        controller.setGreenChef(world.getGreenChef());
        controller.setRedChef(world.getRedChef());
        world.setController(controller);
        frame.addKeyListener(controller);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view = new Background(world, 1300, 700, this);
        viewPanel = new JPanel();
        viewPanel.add(view);
        menu = new Menu(view, this);
        frame.add(menu.getMenuPanel(), BorderLayout.NORTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.add(viewPanel);
        frame.setResizable(false);
        frame.setVisible(true);
        // uncomment this to make a debugging view
        //JFrame debugView = new DebugViewer(world, 500, 500);
        viewPanel.addKeyListener(controller);
        frame.pack();
        // uncomment this to draw a 1-metre grid over the view
        //view.setGridResolution(0.1f);
        world.start();
        theme.loop();
        theme.setVolume(0.6);
        // basics end ========================================================
    }

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        new Game();
    }

    /**
     * Take player to next level.
     * @throws UnsupportedAudioFileException - Required by startLevel1(), startLevel2(), startLevel3(), startLevel4().
     * @throws IOException - Required by startLevel1(), startLevel2(), startLevel3(), startLevel4().
     * @throws LineUnavailableException - Required by startLevel1(), startLevel2(), startLevel3(), startLevel4().
     */
    public void goNextLevel() throws UnsupportedAudioFileException, IOException, LineUnavailableException { //takes player to appropriate next level
        world.stop();
        if (level == 0) {
            startLevel1();
        } else if (level == 1) {
            startLevel2();
        } else if (level == 2) {
            startLevel3();
        } else if (level == 3) {
            startLevel4();
        } else if (level == 4) {
            startLevel1();
        }
    }


//     For easier control over level selecting, individual methods were created for starting each level.
//     This way goNextLevel() calls one of these methods to start rhe equivalent level and when using the level selector (a JComboBox in Menu.java)
//     the startLevel function for the equivalent level is called.

    /**
     * Creates and starts level 1.
     * @throws UnsupportedAudioFileException - Required by world.populate(this).
     * @throws IOException - Required by world.populate(this).
     * @throws LineUnavailableException - Required by world.populate(this).
     */
    public void startLevel1() throws UnsupportedAudioFileException, IOException, LineUnavailableException { //starts level one
        if (level == 2) { //if previous level was level 2, then cancel its timer
            Level2 currentLevel2 = (Level2) world;
            currentLevel2.getIcicleTimer().cancel();
        }
        level = 1;
        world = new Level1(this, new Controller(world.getRedChef(), world.getGreenChef(), world));
        world.populate(this);
        world.setController(controller);
        controller.setGameWorld(world);
        controller.setGreenChef(world.getGreenChef());
        controller.setRedChef(world.getRedChef());
        view.setWorld(world);
        world.start();
    }

    /**
     * Creates and starts level 2.
     * @throws UnsupportedAudioFileException - Required by world.populate(this).
     * @throws IOException - Required by world.populate(this).
     * @throws LineUnavailableException - Required by world.populate(this).
     */
    public void startLevel2() throws UnsupportedAudioFileException, IOException, LineUnavailableException { //starts level two
        if (level == 2) { //if previous level was level 2, then cancel its timer
            Level2 currentLevel2 = (Level2) world;
            currentLevel2.getIcicleTimer().cancel();
        }
        level = 2;
        world = new Level2(this, controller);
        world.populate(this);
        world.setController(controller);
        controller.setGameWorld(world);
        controller.setGreenChef(world.getGreenChef());
        controller.setRedChef(world.getRedChef());
        view.setWorld(world);
        world.start();
    }

    /**
     * Creates and starts level 3.
     * @throws UnsupportedAudioFileException - Required by world.populate(this).
     * @throws IOException - Required by world.populate(this).
     * @throws LineUnavailableException - Required by world.populate(this).
     */
    public void startLevel3() throws UnsupportedAudioFileException, IOException, LineUnavailableException { //starts level three
        if (level == 2) { //if previous level was level 2, then cancel its timer
            Level2 currentLevel2 = (Level2) world;
            currentLevel2.getIcicleTimer().cancel();
        }
        level = 3;
        world = new Level3(this, new Controller(world.getRedChef(), world.getGreenChef(), world));
        world.populate(this);
        controller.setGameWorld(world);
        controller.setGreenChef(world.getGreenChef());
        controller.setRedChef(world.getRedChef());
        view.setWorld(world);
        world.setController(controller);
        world.start();
    }

    /**
     * Creates and starts level 4.
     * @throws UnsupportedAudioFileException - Required by world.populate(this).
     * @throws IOException - Required by world.populate(this).
     * @throws LineUnavailableException - Required by world.populate(this).
     */
    public void startLevel4() throws UnsupportedAudioFileException, IOException, LineUnavailableException { //starts level four
        if (level == 2) { //if previous level was level 2, then cancel its timer
            Level2 currentLevel2 = (Level2) world;
            currentLevel2.getIcicleTimer().cancel();
        }
        level = 4;
        world = new Level4(this, new Controller(world.getRedChef(), world.getGreenChef(), world));
        world.populate(this);
        controller.setGameWorld(world);
        controller.setGreenChef(world.getGreenChef());
        controller.setRedChef(world.getRedChef());
        view.setWorld(world);
        world.setController(controller);
        world.start();
    }

    /**
     * Starts level read from the save file.
     * @throws IOException - Input error may happen.
     */
    public void loadLevel() throws IOException { //loads level saved in loaded save file
        if (level == 2) { //if previous level was level 2, then cancel its timer
            Level2 currentLevel2 = (Level2) world;
            currentLevel2.getIcicleTimer().cancel();
        }
        world.stop();
        world = menu.getLg().loadProgress(this);
        level = world.getLevelNumber();
        controller.setGameWorld(world);
        controller.setGreenChef(world.getGreenChef());
        controller.setRedChef(world.getRedChef());
        view.setWorld(world);
        world.setController(controller);
        world.start();
    }

    /**
     * Getter for level.
     * @return - Number of current level.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Getter for world.
     * @return - Current level.
     */
    public GameWorld getWorld() {
        return world;
    }

    /**
     * Setter for level.
     * @param level - Assigns new level number.
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Getter for controller.
     * @return - controller.
     */
    public Controller getController() {
        return controller;
    }

    /**
     * Getter for theme.
     * @return - them.
     */
    public SoundClip getTheme() {
        return theme;
    }

    /**
     * Getter for menu.
     * @return - menu.
     */
    public Menu getMenu() {
        return menu;
    }
}