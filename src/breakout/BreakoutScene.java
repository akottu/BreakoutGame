package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;

/**
 * The "display" for the game - what the user sees at any given moment consisting of the shapes and
 * status of the game.
 */
public class BreakoutScene extends Scene {

  StatusDisplay statusDisplay = new StatusDisplay();
  private Ball ball;
  private Paddle paddle;
  private Group groupRoot;

  /**
   * Create the game's "scene": what shapes will be in the game and their starting properties
   *
   * @param width
   * @param height
   * @param background
   * @param level
   * @param root
   */
  public BreakoutScene(int width, int height, Paint background, int level, Group root) {
    super(root, width, height, background);
    BlockGenerator.generateBlocks(root, level);
    this.ball = new Ball(width, height);
    root.getChildren().add(this.ball);
    Paddle paddle = new Paddle(width, height);
    root.getChildren().add(paddle);
    statusDisplay.createLivesDisplay(root);
    statusDisplay.createScoreDisplay(root);
    statusDisplay.createHighScoreDisplay(root);
    statusDisplay.createLevelDisplay(root, level);
    statusDisplay.createGameOverDisplay(root);
    groupRoot = root;
    this.paddle = paddle;
  }

  public Ball getBall() {
    return ball;
  }

  public void setBall(Ball ball) {
    this.ball = ball;
  }

  public Paddle getPaddle() {
    return paddle;
  }

  public void setPaddle(Paddle paddle) {
    this.paddle = paddle;
  }

  public Group getGroupRoot() {
    return groupRoot;
  }

  public void setGroupRoot(Group groupRoot) {
    this.groupRoot = groupRoot;
  }

}
