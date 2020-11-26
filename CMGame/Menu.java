package CMGame;

import LoadSave.LoadGame;
import LoadSave.SaveGame;
import city.cs.engine.UserView;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import CMWorld.*;
import java.io.IOException;

/**
 * @author Peter, Farkas, peter.farkas@city.ac.uk
 */
public class Menu {

    private Game game;
    private UserView view;
    private JPanel menuPanel;
    private JButton exit = new JButton("Exit");
    private JButton pause = new JButton("Pause");
    private JButton restart = new JButton("Restart");
    private JButton muteAll = new JButton("Disable Sound");
    private JButton save = new JButton("Save");
    private JButton load = new JButton("Load");
    private JComboBox<String> slotSelect = new JComboBox<>();
    private JComboBox<String> levelSelect = new JComboBox<>();
    private JSlider volumeSlider = new JSlider(0, 4);
    private JLabel sliderLabel = new JLabel("Volume:", JLabel.CENTER);

    private float volumeMultiplier = 0.5f;
    private boolean muteSound = false;
    private boolean paused = false;
    private SaveGame sg;
    private LoadGame lg;

    /**
     * Constructor for class Menu.java.
     * @param view - Currently active UserView.
     * @param game - Gives access to Game.java.
     */
    public Menu(UserView view, Game game) {
        this.game = game;
        menuPanel = new JPanel();
        this.view = view;
        menuPanel.add(exit);
        exit.setFocusable(false);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menuPanel.add(pause);  //create pause button and apply what it should do
        pause.setFocusable(false);
        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (view.getWorld().isRunning()) {
                    view.getWorld().stop();
                    pause.setText("Play");
                } else {
                    view.getWorld().start();
                    pause.setText("Pause");
                }
            }
        });

        menuPanel.add(restart); //create restart button and apply what it should do
        restart.setFocusable(false);
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (game.getLevel() == 2) {
                    Level2 currentLevel2 = (Level2) game.getWorld();
                    currentLevel2.getIcicleTimer().cancel();
                }
                if (game.getLevel() == 1) {
                    try {
                        game.startLevel1();
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                        ex.printStackTrace();
                    }
                } else if (game.getLevel() == 2) {
                    try {
                        game.startLevel2();
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                        ex.printStackTrace();
                    }
                } else if (game.getLevel() == 3) {
                    try {
                        game.startLevel3();
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                        ex.printStackTrace();
                    }
                } else if (game.getLevel() == 4) {
                    try {
                        game.startLevel4();
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        menuPanel.add(levelSelect); //create a drop down menu to select level and if an index is selected, start assigned level
        levelSelect.setFocusable(false);
        levelSelect.addItem("Choose Level");
        levelSelect.addItem("Level 1");
        levelSelect.addItem("Level 2");
        levelSelect.addItem("Level 3");
        levelSelect.addItem("Level 4");
        levelSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (levelSelect.getSelectedIndex() == 1) {
                    game.getWorld().stop();
                    try {
                        game.startLevel1();
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                        ex.printStackTrace();
                    }
                } else if (levelSelect.getSelectedIndex() == 2) {
                    game.getWorld().stop();
                    try {
                        game.startLevel2();
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                        ex.printStackTrace();
                    }
                } else if (levelSelect.getSelectedIndex() == 3) {
                    game.getWorld().stop();
                    try {
                        game.startLevel3();
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                        ex.printStackTrace();
                    }
                } else if (levelSelect.getSelectedIndex() == 4) {
                    game.getWorld().stop();
                    try {
                        game.startLevel4();
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        //creates a drop down menu allowing the player to choose open of the three available save slots, which can then be saved to or loaded from using the load and save buttons
        menuPanel.add(slotSelect);
        slotSelect.setFocusable(false);
        slotSelect.addItem("Select Save Slot");
        slotSelect.addItem("Slot 1");
        slotSelect.addItem("Slot 2");
        slotSelect.addItem("Slot 3");
        slotSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (slotSelect.getSelectedIndex() == 1) {
                    sg = new SaveGame("data/saveGame1.txt");
                    lg = new LoadGame("data/saveGame1.txt");
                } else if (slotSelect.getSelectedIndex() == 2) {
                    sg = new SaveGame("data/saveGame2.txt");
                    lg = new LoadGame("data/saveGame2.txt");
                } else if (slotSelect.getSelectedIndex() == 3) {
                    sg = new SaveGame("data/saveGame3.txt");
                    lg = new LoadGame("data/saveGame3.txt");
                }
            }
        });

        menuPanel.add(save); //saves current state/progress of game to the selected save slot
        save.setFocusable(false);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sg == null) {
                } else {
                    try {
                        sg.saveProgress(game.getLevel(), game.getWorld().getRedChef().getChefScore(),
                                game.getWorld().getRedChef().getTomatoCount(), game.getWorld().getRedChef().getPosition(),
                                game.getWorld().getGreenChef().getChefScore(), game.getWorld().getGreenChef().getTomatoCount(),
                                game.getWorld().getGreenChef().getPosition());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        menuPanel.add(load); //loads data from save selected save file
        load.setFocusable(false);
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lg == null) {
                } else {
                    try {
                        game.loadLevel();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        menuPanel.add(muteAll); //mutes all sound
        muteAll.setFocusable(false);
        muteAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (muteAll.getText().equals("Disable Sound")) {
                    if (!paused) {
                        muteSound = true;
                        game.getTheme().pause();
                        muteAll.setText("Enable Sound");
                    }
                } else if (muteAll.getText().equals("Enable Sound")) {
                    muteSound = false;
                    game.getTheme().resume();
                    muteAll.setText("Disable Sound");
                }
            }
        });

        sliderLabel.setAlignmentX(Component.RIGHT_ALIGNMENT); //displays "Volume: " next to volume slider
        menuPanel.add(sliderLabel);

        volumeSlider.setToolTipText("Volume"); //creates a slider that controls the volume
        volumeSlider.setMajorTickSpacing(1);
        volumeSlider.setPaintTicks(true);
        menuPanel.add(volumeSlider);
        volumeSlider.setFocusable(false);
        volumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                sliderChanged();
            }
        });
    }

    /**
     * Determines what happens when the position of the slider changes.
     * <p>
     *     Depending on the position of the slider, the volume is altered. If the slider is set fully to the left,
     *     the sound is muted. The further right the slider is, the greater of a multiplying value it uses to
     *     change the volume.
     * </p>
     */
    public void sliderChanged() { //alters the volume depending on the position of the slider
        if (volumeSlider.getValue() == 0) {
            muteSound = true;
            paused = true;
            game.getTheme().pause();
        } else if (volumeSlider.getValue() == 1) {
            if (paused) {
                muteSound = false;
                paused = false;
                game.getTheme().resume();
            }
            volumeMultiplier = 0.25f;
        } else if (volumeSlider.getValue() == 2) {
            if (paused) {
                muteSound = false;
                paused = false;
                game.getTheme().resume();
            }
            volumeMultiplier = 0.5f;
        } else if (volumeSlider.getValue() == 3) {
            if (paused) {
                muteSound = false;
                paused = false;
                game.getTheme().resume();
            }
            volumeMultiplier = 0.75f;
        } else {
            if (paused) {
                muteSound = false;
                paused = false;
                game.getTheme().resume();
            }
            volumeMultiplier = 1;
        }
        changeVolume(volumeMultiplier);
    }

    /**
     * Getter for menuPanel.
     * @return - Returns the menu panel containing the buttons and etc.
     */
    public JPanel getMenuPanel() {
        return menuPanel;
    }

    /**
     * Getter for the volume multiplier.
     * @return - The value to multiply the base volume of each sound file.
     */
    public float getVolumeMultiplier() {
        return volumeMultiplier;
    }

    /**
     * Getter for the boolean muteSound.
     * @return - The boolean keeping track whether the game is muter.
     */
    public boolean getMuteSound() {
        return muteSound;
    }

    /**
     * Changes the volume of a sound file and takes into account the assigned base volume.
     * @param changer - The value to multiply the base volume value with.
     */
    private void changeVolume(float changer) {
        game.getTheme().setVolume(0.6 * changer);
    }

    /**
     * Getter for lg.
     * @return - Instance of LoadGame used to load the game.
     */
    public LoadGame getLg() {
        return lg;
    }
}