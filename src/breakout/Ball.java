package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 * Contains attributes and methods to act on ball object used in game
 */
public class Ball extends Circle {

  public static final Paint DEFAULT_BALL_COLOR = Color.HOTPINK;
  public static final int DEFAULT_BALL_SIZE = 30;
  public static final int DEFAULT_BALL_SPEED_X = 50;
  public static final int DEFAULT_BALL_SPEED_Y = -50;
  public static final int DEFAULT_POWERUP_TIME = -1;
  public static final int DEFAULT_BALL_Y_OFFSET = 30;
  public static final double HALF = 0.5;
  public static final String DEFAULT_BALL_ID = "ball";


  private int speedX;
  private int speedY;
  private int powerupTimeSlowBall;
  private int powerupTimeBigBall;

  /**
   * Creates ball based on width and height of screen, establishing a position for the ball.
   *
   * @param sceneWidth
   * @param sceneHeight
   */
  public Ball(int sceneWidth, int sceneHeight) {
    super(sceneWidth * HALF, sceneHeight - DEFAULT_BALL_Y_OFFSET, DEFAULT_BALL_SIZE * HALF);
    setFill(DEFAULT_BALL_COLOR);
    setId(DEFAULT_BALL_ID);
    this.speedX = DEFAULT_BALL_SPEED_X;
    this.speedY = DEFAULT_BALL_SPEED_Y;
    this.powerupTimeBigBall = DEFAULT_POWERUP_TIME;
    this.powerupTimeSlowBall = DEFAULT_POWERUP_TIME;
  }

  public int getSpeedX() {
    return speedX;
  }

  public void setSpeedX(int speedX) {
    this.speedX = speedX;
  }

  public int getSpeedY() {
    return speedY;
  }

  public void setSpeedY(int speedY) {
    this.speedY = speedY;
  }

  public int getPowerupTimeSlowBall() {
    return powerupTimeSlowBall;
  }

  public void setPowerupTimeSlowBall(int powerupTimeSlowBall) {
    this.powerupTimeSlowBall = powerupTimeSlowBall;
  }

  public int getPowerupTimeBigBall() {
    return powerupTimeBigBall;
  }

  public void setPowerupTimeBigBall(int powerupTimeBigBall) {
    this.powerupTimeBigBall = powerupTimeBigBall;
  }
}
