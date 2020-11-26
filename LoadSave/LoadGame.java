package LoadSave;

import CMWorld.*;
import org.jbox2d.common.Vec2;
import CMGame.*;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoadGame {

    private String saveName;

    public LoadGame(String saveName) {
        this.saveName = saveName;
    }

    public GameWorld loadProgress(Game game) throws IOException {
        FileReader saveReader = null;
        BufferedReader reader = null;
        try {
            System.out.println("Reading: " + saveName);
            saveReader = new FileReader(saveName);
            reader = new BufferedReader(saveReader);
            String line = reader.readLine();
            while (line != null) {
                String values[] = line.split(",");
                int level = Integer.parseInt(values[0]);
                int redScore = Integer.parseInt(values[1]);
                int redTomatoCount = Integer.parseInt(values[2]);
                float positionRedX = Float.parseFloat(values[3]);
                float positionRedY = Float.parseFloat(values[4]);
                Vec2 positionRed = new Vec2(positionRedX, positionRedY);
                int greenScore = Integer.parseInt(values[5]);
                int greenTomatoCount = Integer.parseInt(values[6]);
                float positionGreenX = Float.parseFloat(values[7]);
                float positionGreenY = Float.parseFloat(values[8]);
                Vec2 positionGreen = new Vec2(positionGreenX, positionGreenY);

                GameWorld newWorld;
                System.out.println(level + " " + redScore + " " + redTomatoCount + " " + positionRed + " " + greenScore + " " + greenTomatoCount + " " + positionGreen);
                if (level == 1) {
                    newWorld = new Level1(game, game.getController());
                    newWorld.populate(game);
                    newWorld.setLevelNumber(1);
                } else if (level == 2) {
                    newWorld = new Level2(game, game.getController());
                    newWorld.populate(game);
                    newWorld.setLevelNumber(2);
                } else if (level == 3) {
                    newWorld = new Level3(game, game.getController());
                    newWorld.populate(game);
                    newWorld.setLevelNumber(3);
                } else {
                    newWorld = new Level4(game, game.getController());
                    newWorld.populate(game);
                    newWorld.setLevelNumber(4);
                }
                newWorld.getRedChef().setPosition(positionRed);
                newWorld.getRedChef().setChefScore(redScore);
                newWorld.getRedChef().setTomatoCount(redTomatoCount);

                newWorld.getGreenChef().setPosition(positionGreen);
                newWorld.getGreenChef().setChefScore(greenScore);
                newWorld.getGreenChef().setTomatoCount(greenTomatoCount);

                return newWorld;
            }
        } catch (UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (saveReader != null) {
                saveReader.close();
            }
        }
        return null;
    }
}
