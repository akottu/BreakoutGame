package breakout;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**
 * Specifically handles any case in which the ball collides with a wall, paddle, or block.
 */
public class CollisionHandler {

  public static final int TWO = 2;
  public static final int ONE = 1;
  public static final int ZERO = 0;
  public static final int NEGATIVE_ONE = -1;
  PowerupHandler powerupHandler = new PowerupHandler();
  private boolean bottomWallBounce = false;

  /**
   * toggles between having the bottom wall have bouncing capabilities and not
   */
  public void setBottomWallBounce() {
    bottomWallBounce = !bottomWallBounce;
  }

  /**
   * called from step function meant to call helper methods to handle specific types of collisions
   * based on the objects colliding
   *
   * @param breakoutScene
   * @param game
   */
  public void handle(BreakoutScene breakoutScene, BreakoutGame game) {
    if (bottomWallBounce) {
      handleBottomWallBallBounce(breakoutScene);
    } else {
      handleBottomWallBallCollision(breakoutScene, game);
    }
    handlePaddleBallCollision(breakoutScene);
    handleWallBallCollision(breakoutScene);
    handleBlockBallCollision(breakoutScene);
  }

  // Determine if specific things collided and respond appropriately
  private void handlePaddleBallCollision(BreakoutScene breakoutScene) {
    // can check bounding box (for some kinds of shapes, like images, that is the only option)
    Ball ball = breakoutScene.getBall();
    Rectangle paddle = breakoutScene.getPaddle();
    if (paddle.getBoundsInParent().intersects(ball.getBoundsInParent())) {
      ball.setSpeedY(ball.getSpeedY() * NEGATIVE_ONE);
    }
  }

  // Determine if the ball collides with the wall and responds appropriately
  private void handleWallBallCollision(BreakoutScene breakoutScene) {
    Ball ball = breakoutScene.getBall();
    if (ball.getCenterX() <= ZERO + ball.getRadius()
        || ball.getCenterX() >= BreakoutGame.HORIZONTAL_SIZE - ball.getRadius()) {
      ball.setSpeedX(ball.getSpeedX() * NEGATIVE_ONE);
    }
    if (ball.getCenterY() <= ZERO + ball.getRadius()) {
      ball.setSpeedY(ball.getSpeedY() * NEGATIVE_ONE);
    }
  }

  // Determine if the ball collides with the block and responds appropriately
  private synchronized void handleBlockBallCollision(BreakoutScene breakoutScene) {
    Ball ball = breakoutScene.getBall();
    Paddle paddle = breakoutScene.getPaddle();
    Group root = breakoutScene.getGroupRoot();
    List<Node> blocks = root.getChildren();
    List<Node> blocksForProcessing = new ArrayList<>();
    blocksForProcessing.addAll(blocks);

    for (Node blockNode : blocksForProcessing) {
      if (processBlockCollisions(breakoutScene, ball, paddle, root, blockNode)) {
        break;
      }
    }
  }

  private boolean processBlockCollisions(BreakoutScene breakoutScene, Ball ball, Paddle paddle,
      Group root, Node blockNode) {
    if (blockNode.getId().contains("block")) {
      Bounds blockDimensions = blockNode.getBoundsInParent();
      Bounds ballDimensions = ball.getBoundsInParent();
      return processBlockIntersections(breakoutScene, ball, paddle, root, blockNode,
          blockDimensions, ballDimensions);
    }
    return false;
  }

  private boolean processBlockIntersections(BreakoutScene breakoutScene, Ball ball, Paddle paddle,
      Group root, Block blockNode, Bounds blockDimensions, Bounds ballDimensions) {
    if (blockDimensions.intersects(ballDimensions)) {
      Block block = (Block) blockNode;
      if (block.isReadyForCollision()) {
        processBlockReadyForCollision(breakoutScene, ball, paddle, root, blockNode, blockDimensions,
            ballDimensions, block);
        return true;
      }
    } else {
      Block block = (Block) blockNode;
      block.setReadyForCollision(true);
    }
    return false;
  }

  private void processBlockReadyForCollision(BreakoutScene breakoutScene, Ball ball, Paddle paddle,
      Group root, Node blockNode, Bounds blockDimensions, Bounds ballDimensions, Block block) {
    powerupHandler.handle(paddle, blockNode, ball);
    StatusDisplay.addScore();
    breakoutScene.setRoot(root);
    handleBoundaryIntersection(ball, blockDimensions, ballDimensions);
    block.setReadyForCollision(true);
    block.decreaseLives();
    if (block.hasNoLives()) {
      removeChildNode(root, blockNode);
    }
  }

  private void handleBoundaryIntersection(Ball ball, Bounds blockDimensions,
      Bounds ballDimensions) {
    Line topLine = new Line(blockDimensions.getMinX(), blockDimensions.getMinY(),
        blockDimensions.getMaxX(), blockDimensions.getMinY());
    Line bottomLine = new Line(blockDimensions.getMinX(), blockDimensions.getMaxY(),
        blockDimensions.getMaxX(), blockDimensions.getMaxY());
    Line leftLine = new Line(blockDimensions.getMinX(), blockDimensions.getMinY(),
        blockDimensions.getMinX(), blockDimensions.getMaxY());
    Line rightLine = new Line(blockDimensions.getMaxX(), blockDimensions.getMinY(),
        blockDimensions.getMaxX(), blockDimensions.getMaxY());

    checkForBorderCollisions(ball, ballDimensions, topLine, bottomLine, leftLine, rightLine);
  }

  private void checkForBorderCollisions(Ball ball, Bounds ballDimensions, Line topLine,
      Line bottomLine, Line leftLine, Line rightLine) {
    if (ballDimensions.intersects(leftLine.getBoundsInParent()) || ballDimensions
        .intersects(rightLine.getBoundsInParent())) {
      ball.setSpeedX(ball.getSpeedX() * NEGATIVE_ONE);
    } else if (ballDimensions.intersects(topLine.getBoundsInParent()) || ballDimensions
        .intersects(bottomLine.getBoundsInParent())) {
      ball.setSpeedY(ball.getSpeedY() * NEGATIVE_ONE);
    }
  }

  // Determine if any two shapes collided, might be useful to check different instances that react similarly
  private void handleBottomWallBallCollision(BreakoutScene breakoutScene, BreakoutGame game) {
    // with shapes, can check precisely
    Ball ball = breakoutScene.getBall();
    Paddle paddle = breakoutScene.getPaddle();
    Group root = breakoutScene.getGroupRoot();
    List<Node> lives = root.getChildren();
    List<Node> livesProcessing = new ArrayList<>();
    livesProcessing.addAll(lives);

    if (ball.getCenterY() >= BreakoutGame.VERTICAL_SIZE) {
      handleBallBottomCollision(game, ball, paddle, root, livesProcessing);
    }
  }

  private void handleBallBottomCollision(BreakoutGame game, Ball ball, Paddle paddle, Group root,
      List<Node> livesProcessing) {
    paddle.setX(BreakoutGame.HORIZONTAL_SIZE / TWO - paddle.getPaddleWidth() / TWO);
    resetBall(ball);
    handleLives(game, root, livesProcessing);
    if (game.getCurrentLife() == ZERO) {
      StatusDisplay.gameOver.setText("GAME OVER");
    }
  }

  private void resetBall(Ball ball) {
    ball.setCenterX(BreakoutGame.HORIZONTAL_SIZE / TWO);
    ball.setCenterY(BreakoutGame.VERTICAL_SIZE - TWO * ball.getRadius());
    ball.setSpeedX(Ball.DEFAULT_BALL_SPEED_X);
    ball.setSpeedY(Ball.DEFAULT_BALL_SPEED_Y);
  }

  private void handleLives(BreakoutGame game, Group root, List<Node> livesProcessing) {
    for (Node life : livesProcessing) {
      if (life.getId().contains("Life" + game.getCurrentLife())) {
        removeChildNode(root, life);
        game.setCurrentLife(game.getCurrentLife() - 1);
      }
    }
  }

  private void handleBottomWallBallBounce(BreakoutScene breakoutScene) {
    Ball ball = breakoutScene.getBall();
    if (ball.getCenterY() >= BreakoutGame.VERTICAL_SIZE - ball.getRadius()) {
      ball.setSpeedY(ball.getSpeedY() * NEGATIVE_ONE);
    }
  }

  protected void removeChildNode(Group root, Node node) {
    root.getChildren().remove(node);
  }

}
