package breakout;

import javafx.scene.Node;

/**
 * Handles all power-up features for game
 */
public class PowerupHandler {

  public static final int ZERO = 0;
  public static final int ONE = 1;
  public static final int TWO = 2;

  public static final int DEFAULT_POWERUP_TIME = 500;

  /**
   * handles power-up actions based on type of power-up called for
   *
   * @param paddle
   * @param block
   * @param ball
   */
  public void handle(Paddle paddle, Node block, Ball ball) {
    if (isPowerupDoublePaddle(block)) {
      handlePaddlePowerup(paddle);
    } else if (isPowerupSlowBall(block)) {
      handleSlowBallPowerup(ball);
    } else if (isPowerupBigBall(block)) {
      handleBigBallPowerup(ball);
    }
  }

  private void handleBigBallPowerup(Ball ball) {
    if (!(ball.getPowerupTimeBigBall() > ZERO)) {
      ball.setRadius(ball.getRadius() * TWO);
    }
    ball.setPowerupTimeBigBall(DEFAULT_POWERUP_TIME);
  }

  private void handlePaddlePowerup(Paddle paddle) {
    if (!(paddle.getPowerupTimeDoublePaddleSize() > ZERO)) {
      paddle.setWidth(paddle.getWidth() * TWO);
    }
    paddle.setPowerupTimeDoublePaddleSize(DEFAULT_POWERUP_TIME);
  }

  private void handleSlowBallPowerup(Ball ball) {
    if (!(ball.getPowerupTimeSlowBall() > ZERO)) {
      ball.setSpeedX(ball.getSpeedX() / TWO);
      ball.setSpeedY(ball.getSpeedY() / TWO);
    }
    ball.setPowerupTimeSlowBall(DEFAULT_POWERUP_TIME);
  }

  /**
   * updates status of each power-up
   *
   * @param breakoutScene
   */
  public void updateStatus(BreakoutScene breakoutScene) {
    updatePaddlePowerupStatus(breakoutScene);
    updateSlowBallPowerupStatus(breakoutScene);
    updateBigBallPowerupStatus(breakoutScene);
  }

  private void updatePaddlePowerupStatus(BreakoutScene breakoutScene) {
    Paddle paddle = breakoutScene.getPaddle();
    if (paddle.getPowerupTimeDoublePaddleSize() > ZERO) {
      paddle.setPowerupTimeDoublePaddleSize(paddle.getPowerupTimeDoublePaddleSize() - ONE);
    }
    if (paddle.getPowerupTimeDoublePaddleSize() == ZERO) {
      paddle.setPaddleWidth(Paddle.DEFAULT_PADDLE_WIDTH);
    }
  }

  private void updateSlowBallPowerupStatus(BreakoutScene breakoutScene) {
    Ball ball = breakoutScene.getBall();
    ball.setPowerupTimeSlowBall(ball.getPowerupTimeBigBall() - ONE);
    if (ball.getPowerupTimeSlowBall() == ZERO) {
      ball.setSpeedX(ball.getSpeedX() * TWO);
      ball.setSpeedY(ball.getSpeedY() * TWO);
    }
  }

  private void updateBigBallPowerupStatus(BreakoutScene breakoutScene) {
    Ball ball = breakoutScene.getBall();
    ball.setPowerupTimeBigBall(ball.getPowerupTimeBigBall() - ONE);
    if (ball.getPowerupTimeBigBall() == ZERO) {
      ball.setRadius(ball.getRadius() / TWO);
    }
  }

  private boolean isPowerupDoublePaddle(Node block) {
    return block.getId().contains("_powerupDoublePaddle");
  }

  private boolean isPowerupSlowBall(Node block) {
    return block.getId().contains("_powerupSlowBall");
  }

  private boolean isPowerupBigBall(Node block) {
    return block.getId().contains("_powerupBigBall");
  }


}
