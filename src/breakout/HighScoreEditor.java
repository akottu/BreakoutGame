package breakout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Updates the current high score.
 */
public class HighScoreEditor {

  private final String DIRECTORY = "data/highscore.txt";

  /**
   * reads in the high score from directory
   * @return
   */
  public int readHighScore() {
    File path = new File(DIRECTORY);
    try {
      Scanner file = new Scanner(path);
      return file.nextInt();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return 0;
  }

  /**
   * writes in the high score to directory
   * @param highScore
   */
  public void writeHighScore(String highScore) {
    File path = new File(DIRECTORY);
    try {
      FileWriter writer = new FileWriter(path, false);
      writer.write(highScore);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
