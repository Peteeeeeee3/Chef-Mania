package LoadSave;

import org.jbox2d.common.Vec2;
import java.io.FileWriter;
import java.io.IOException;

public class SaveGame {

    private String saveName;

    public SaveGame(String saveName) {
        this.saveName = saveName;
    }

    public void saveProgress(int level, int scoreRed, int tomatoCountRed, Vec2 positionRed, int scoreGreen, int tomatoCountGreen, Vec2 positionGreen) throws IOException {
        FileWriter saver = null;
        try {
            saver = new FileWriter(saveName);
            saver.write(level + "," + scoreRed + "," + tomatoCountRed + "," + positionRed.x + "," + positionRed.y + "," + scoreGreen + "," + tomatoCountGreen + "," + positionGreen.x + "," + positionGreen.y);
        } finally {
            if (saver != null) {
                saver.close();
            }
        }
    }
}