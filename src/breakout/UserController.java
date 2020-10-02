package breakout;

import java.util.List;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;

/**
 * All the methods used to handle game control and cheat keys
 */
public class UserController {

  private static final int TWO = 2;
  private static final int ZERO = 0;


  private final int firstBlockColumnIndex = BlockGenerator.getTotalColumns() - 1;
  private final int firstBlockRowIndex = BlockGenerator.getTotalRowNumber() - 1;

  BreakoutGame game;
  BreakoutScene scene;
  StatusDisplay statusDisplay;

  /**
   * constructor
   *
   * @param breakoutGame
   * @param breakoutScene
   */
  public UserController(BreakoutGame breakoutGame, BreakoutScene breakoutScene) {
    game = breakoutGame;
    scene = breakoutScene;
    statusDisplay = new StatusDisplay(game);
  }

  /**
   * Moves paddles to the left
   *
   * @param paddle
   */
  public void moveLeft(Paddle paddle) {
    paddle.setX(paddle.getX() - paddle.getPaddleSpeed());
  }

  /**
   * Moves paddles to the right
   *
   * @param paddle
   */
  public void moveRight(Paddle paddle) {
    paddle.setX(paddle.getX() + paddle.getPaddleSpeed());
  }

  /**
   * Quits application
   */
  public void exitGame() {
    Platform.exit();
    System.exit(0);
  }

  /**
   * Resets the ball and paddle
   *
   * @param paddle
   * @param ball
   */
  public void handleReset(Paddle paddle, Ball ball) {
    paddle.setX(BreakoutGame.HORIZONTAL_SIZE / TWO - paddle.getPaddleWidth() / TWO);
    ball.setCenterX(BreakoutGame.HORIZONTAL_SIZE / TWO);
    ball.setCenterY(BreakoutGame.VERTICAL_SIZE - TWO * ball.getRadius());
    ball.setSpeedX(Ball.DEFAULT_BALL_SPEED_X);
    ball.setSpeedY(Ball.DEFAULT_BALL_SPEED_Y);
  }

  /**
   * destroys the "first" remaining block
   */
  public void destroyBlock() {
    try {
      Group root = scene.getGroupRoot();
      List<Node> blocks = root.getChildren();
      Node firstBlock = findFirstBlock(blocks);
      root.getChildren().remove(firstBlock);
    } catch (Exception e) {
      return;
    }
  }

  /**
   * Clears current level and goes to specific level
   *
   * @param level
   */
  public void goToLevel(int level) {
    Group root = scene.getGroupRoot();
    game.clearLevel();
    game.setLevel(level);
    BlockGenerator.generateBlocks(root, level);
    statusDisplay.updateLevelDisplay(level);
  }

  /**
   * Adds a life to the game
   */
  public void addLife() {
    int newLife = game.getCurrentLife() + 1;
    game.setCurrentLife(newLife);
    statusDisplay.addLifeDisplay(scene.getGroupRoot());
  }

  /**
   * Applies the big ball powerup
   * @param ball
   */
  public void bigBallPowerUp(Ball ball) {
    if (!(ball.getPowerupTimeBigBall() > ZERO)) {
      ball.setRadius(ball.getRadius() * TWO);
    }
    ball.setPowerupTimeBigBall(PowerupHandler.DEFAULT_POWERUP_TIME);
  }

  /**
   * Applies wide paddle powerup
   * @param paddle
   */
  public void widePaddlePowerUp(Paddle paddle) {
    if (!(paddle.getPowerupTimeDoublePaddleSize() > ZERO)) {
      paddle.setWidth(paddle.getWidth() * TWO);
    }
    paddle.setPowerupTimeDoublePaddleSize(PowerupHandler.DEFAULT_POWERUP_TIME);
  }

  /**
   * Applies slow ball powerup
   * @param ball
   */
  public void slowBallPowerUp(Ball ball) {
    if (!(ball.getPowerupTimeSlowBall() > ZERO)) {
      ball.setSpeedX(ball.getSpeedX() / TWO);
      ball.setSpeedY(ball.getSpeedY() / TWO);
    }
    ball.setPowerupTimeSlowBall(PowerupHandler.DEFAULT_POWERUP_TIME);
  }

  /**
   * Stops the ball from falling off the bottom
   * @param collisionHandler
   */
  public void noFall(CollisionHandler collisionHandler) {
    collisionHandler.setBottomWallBounce();
  }

  /**
   * Finds the "first" remaining block
   * @param nodes
   * @return
   */
  private Node findFirstBlock(List<Node> nodes) {
    String blockID;
    for (int r = firstBlockRowIndex; r >= 0; r--) {
      for (int c = firstBlockColumnIndex; c >= 0; c--) {
        blockID = Block.DEFAULT_BLOCK_ID + r + c;
        for (Node blockNode : nodes) {
          if (blockNode.getId().contains(blockID)) {
            return blockNode;
          }
        }
      }
    }
    return null;
  }


}
