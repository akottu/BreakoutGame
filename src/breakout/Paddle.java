package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Contains attributes and methods to act on paddle object used in game
 */
public class Paddle extends Rectangle {

  public final static Paint DEFAULT_PADDLE_COLOR = Color.DARKRED;
  public final static int DEFAULT_PADDLE_WIDTH = 100;
  public final static int DEFAULT_PADDLE_HEIGHT = 10;
  public final static int DEFAULT_PADDLE_ROUNDING = 5;
  public final static int DEFAULT_PADDLE_SPEED = 8;
  public final static String DEFAULT_PADDLE_ID = "paddle";

  private int paddleWidth;
  private int paddleHeight;
  private int paddleRounding;
  private int paddleSpeed;
  private int powerupTimeDoublePaddleSize;

  /**
   * Creates paddle based on width and height of screen, establishing a position for the ball.
   *
   * @param sceneWidth
   * @param sceneHeight
   */
  public Paddle(int sceneWidth, int sceneHeight) {
    super(sceneWidth / 2 - DEFAULT_PADDLE_WIDTH / 2, sceneHeight - DEFAULT_PADDLE_HEIGHT, DEFAULT_PADDLE_WIDTH,
        DEFAULT_PADDLE_HEIGHT);
    paddleWidth = DEFAULT_PADDLE_WIDTH;
    paddleHeight = DEFAULT_PADDLE_HEIGHT;
    paddleRounding = DEFAULT_PADDLE_ROUNDING;
    paddleSpeed = DEFAULT_PADDLE_SPEED;
    this.setFill(DEFAULT_PADDLE_COLOR);
    this.setId(DEFAULT_PADDLE_ID);
  }

  public int getPowerupTimeDoublePaddleSize() {
    return powerupTimeDoublePaddleSize;
  }

  public void setPowerupTimeDoublePaddleSize(int powerupTimeDoublePaddleSize) {
    this.powerupTimeDoublePaddleSize = powerupTimeDoublePaddleSize;
  }

  public int getPaddleWidth() {
    return paddleWidth;
  }

  public void setPaddleWidth(int paddleWidth) {
    super.setWidth(paddleWidth);
    this.paddleWidth = paddleWidth;
  }

  public int getPaddleSpeed() {
    return paddleSpeed;
  }
}
