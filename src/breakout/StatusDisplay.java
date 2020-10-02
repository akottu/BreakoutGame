package breakout;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Operates display for status of game, including level, lives, score, etc.
 */
public class StatusDisplay {

  private static final int FONT_SIZE = 20;

  private static final String SCORE_TITLE = "Score: ";
  private static final String SCORE_ID = "Score";
  private static final int SCORE_X_POSITION = 20;
  private static final int SCORE_Y_POSITION = BreakoutGame.VERTICAL_SIZE - 50;
  private static final Paint SCORE_COLOR = Color.BLACK;
  private static final int SCORE_INCREASE = 10;

  private static final String HIGH_SCORE = "High Score: ";
  private static final String HIGH_SCORE_ID = "HighScore";
  private static final int HIGH_SCORE_Y_POSITION = BreakoutGame.VERTICAL_SIZE - 70;
  private static final Paint HIGH_SCORE_COLOR = Color.MAROON;

  private static final String LEVEL_TITLE = "Level: ";
  private static final String LEVEL_ID = "LevelDisplay";
  private static final int LEVEL_X_POSITION = 700;
  private static final int LEVEL_Y_POSITION = BreakoutGame.VERTICAL_SIZE - 20;
  private static final Paint LEVEL_COLOR = Color.BLACK;

  private static final int LIFE_SIZE = 20;
  private static final int LIFE_OFFSET = 30;
  private static final Paint LIFE_COLOR = Color.RED;
  private static final String LIFE_ID = "life";

  private static final String GAME_OVER_EMPTY = "";
  private static final String GAME_OVER_ID = "GameOver";
  private static final int GAME_OVER_X_POSITION = 350;
  private static final int GAME_OVER_Y_POSITION = BreakoutGame.VERTICAL_SIZE - 100;
  private static final Paint GAME_OVER_COLOR = Color.BLACK;


  private static int currentScore = 0;
  private static Text scoreDisplay;

  private static int highScore;
  private static Text highScoreDisplay;

  private static Text levelDisplay;
  private static int numberOfLives;
  private static List<Circle> lives = new ArrayList<>();

  public static Text gameOver;

  BreakoutGame game;
  /**
   * default constructor creates a new BreakoutGame object.
   */
  public StatusDisplay() {
    game = new BreakoutGame();
  }

  /**
   * this constructor passes in existing game
   *
   * @param breakoutGame
   */
  public StatusDisplay(BreakoutGame breakoutGame) {
    game = breakoutGame;
  }

  /**
   * creates display for tracking score
   *
   * @param root
   * @return
   */
  public static Text createScoreDisplay(Group root) {
    scoreDisplay = new Text();
    scoreDisplay.setText(SCORE_TITLE + currentScore);
    scoreDisplay.setId(SCORE_ID);
    scoreDisplay.setX(SCORE_X_POSITION);
    scoreDisplay.setY(SCORE_Y_POSITION);
    scoreDisplay.setFill(SCORE_COLOR);
    scoreDisplay.setFont(Font.font(null, FontWeight.BOLD, FONT_SIZE));
    root.getChildren().add(scoreDisplay);
    return scoreDisplay;
  }

  /**
   * adds 10 to score and updates the display
   */
  public static void addScore() {
    HighScoreEditor highScoreEditor = new HighScoreEditor();
    currentScore = currentScore + SCORE_INCREASE;
    scoreDisplay.setText(SCORE_TITLE + currentScore);
    if (currentScore >= highScore) {
      highScoreDisplay.setText(HIGH_SCORE + currentScore);
      highScoreEditor.writeHighScore(Integer.toString(currentScore));
    }
  }

  /**
   * creates High Score display
   *
   * @param root
   * @return
   */
  public Text createHighScoreDisplay(Group root) {
    HighScoreEditor highScoreEditor = new HighScoreEditor();
    highScore = highScoreEditor.readHighScore();
    highScoreDisplay = new Text();
    highScoreDisplay.setText(HIGH_SCORE + highScore);
    highScoreDisplay.setId(HIGH_SCORE_ID);
    highScoreDisplay.setX(SCORE_X_POSITION);
    highScoreDisplay.setY(HIGH_SCORE_Y_POSITION);
    highScoreDisplay.setFill(HIGH_SCORE_COLOR);
    highScoreDisplay.setFont(Font.font(null, FontWeight.BOLD, FONT_SIZE));
    root.getChildren().add(highScoreDisplay);
    return highScoreDisplay;
  }

  /**
   * creates display for tracking level
   *
   * @param root
   * @param level
   * @return
   */
  public Text createLevelDisplay(Group root, int level) {
    levelDisplay = new Text();
    levelDisplay.setText(LEVEL_TITLE + level);
    levelDisplay.setId(LEVEL_ID);
    levelDisplay.setX(LEVEL_X_POSITION);
    levelDisplay.setY(LEVEL_Y_POSITION);
    levelDisplay.setFill(LEVEL_COLOR);
    levelDisplay.setFont(Font.font(null, FontWeight.BOLD, FONT_SIZE));
    root.getChildren().add(levelDisplay);
    return levelDisplay;
  }

  /**
   * Updates the level display
   *
   * @param level
   */
  public static void updateLevelDisplay(int level) {
    levelDisplay.setText(LEVEL_TITLE + level);
  }

  /**
   * creates display for tracking lives
   *
   * @param root
   * @return
   */
  public List<Circle> createLivesDisplay(Group root) {
    numberOfLives = BreakoutGame.STARTING_LIVES;
    Circle life;

    for (int i = 1; i <= numberOfLives; i++) {
      life = new Circle(i * LIFE_OFFSET, BreakoutGame.VERTICAL_SIZE - LIFE_SIZE, LIFE_SIZE / 2);
      life.setFill(LIFE_COLOR);
      life.setId(LIFE_ID + i);
      lives.add(life);
      root.getChildren().add(life);
    }
    return lives;
  }

  /**
   * creates display for lives "balls"
   *
   * @param root
   */
  public void addLifeDisplay(Group root) {
    numberOfLives = game.getCurrentLife();
    Circle addLife = new Circle(numberOfLives * LIFE_OFFSET, BreakoutGame.VERTICAL_SIZE - LIFE_SIZE,
        LIFE_SIZE / 2);
    addLife.setFill(LIFE_COLOR);
    addLife.setId(LIFE_ID + numberOfLives);
    lives.add(addLife);
    root.getChildren().add(addLife);
  }

  /**
   * creates display for tracking game over message display
   *
   * @param root
   * @return
   */
  public static Text createGameOverDisplay(Group root) {
    gameOver = new Text();
    gameOver.setText(GAME_OVER_EMPTY);
    gameOver.setId(GAME_OVER_ID);
    gameOver.setX(GAME_OVER_X_POSITION);
    gameOver.setY(GAME_OVER_Y_POSITION);
    gameOver.setFill(GAME_OVER_COLOR);
    gameOver.setFont(Font.font(null, FontWeight.BOLD, FONT_SIZE));
    root.getChildren().add(gameOver);
    return gameOver;
  }

  public Text getScoreDisplay(){
    return scoreDisplay;
  }

  public Text getHighScoreDisplay(){
    return highScoreDisplay;
  }

}
