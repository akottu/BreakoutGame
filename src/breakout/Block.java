package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Contains attributes and methods to act on block objects used in game
 */
public class Block extends Rectangle {

  public static final String DEFAULT_BLOCK_ID = "block";
  private static final int DEFAULT_BLOCK_ROUNDING = 15;
  private static final int DEFAULT_BLOCK_SIZE = 50;
  private static final int DEFAULT_BLOCK_OFFSET = 5;
  private static final Color[] colors = {Color.WHITE, Color.LIGHTBLUE, Color.LIGHTSKYBLUE,
      Color.BLUE, Color.DARKBLUE, Color.BLACK,
      Color.GOLD, Color.DARKORANGE, Color.RED};
  private final int HORIZONTAL_BLOCK_SPEED = 20;
  private final int VERTICAL_BLOCK_SPEED = 20;

  private int lives;
  private final int blockNumber;
  private boolean movingRight;
  private double horizontalDisplacement;
  private boolean movingDown;
  private double verticalDisplacement;
  private boolean readyForCollision;

  /**
   * Creates a block based on its position by column and rowNumber, assigning a blockNumber as well
   * to set color and lives
   *
   * @param column
   * @param rowNumber
   * @param blockNumber
   */
  Block(int column, int rowNumber, int blockNumber) {
    super(DEFAULT_BLOCK_SIZE * column, DEFAULT_BLOCK_SIZE * rowNumber,
        DEFAULT_BLOCK_SIZE - DEFAULT_BLOCK_OFFSET, DEFAULT_BLOCK_SIZE - DEFAULT_BLOCK_OFFSET);
    this.blockNumber = blockNumber;
    this.movingRight = true;
    this.movingDown = true;
    this.readyForCollision = true;
    setArcWidth(DEFAULT_BLOCK_ROUNDING);
    setArcHeight(DEFAULT_BLOCK_ROUNDING);
    setBlockId(this, rowNumber, blockNumber, column);
    initilizeLives();
    setColor();
  }

  /**
   * Moves block horizontally and/or vertically if applicable.
   *
   * @param elapsedTime
   */
  public void move(double elapsedTime, boolean movesHorizontally, boolean movesVertically) {
      if (movesHorizontally) {
          horizontalMove(elapsedTime);
      }
      if (movesVertically) {
          verticalMove(elapsedTime);
      }
  }

  private void horizontalMove(double elapsedTime) {
    if (horizontalDisplacement >= 95) {
      movingRight = false;
    } else if (horizontalDisplacement <= -95) {
      movingRight = true;
    }
    if (movingRight) {
      setX(getX() + HORIZONTAL_BLOCK_SPEED * elapsedTime);
      horizontalDisplacement += HORIZONTAL_BLOCK_SPEED * elapsedTime;
    } else {
      setX(getX() - HORIZONTAL_BLOCK_SPEED * elapsedTime);
      horizontalDisplacement -= HORIZONTAL_BLOCK_SPEED * elapsedTime;
    }
  }

  private void verticalMove(double elapsedTime) {
    if (verticalDisplacement >= 95) {
      movingDown = false;
    } else if (verticalDisplacement <= -95) {
      movingDown = true;
    }
    if (movingDown) {
      setY(getY() + VERTICAL_BLOCK_SPEED * elapsedTime);
      verticalDisplacement += VERTICAL_BLOCK_SPEED * elapsedTime;
    } else {
      setY(getY() - VERTICAL_BLOCK_SPEED * elapsedTime);
      verticalDisplacement -= VERTICAL_BLOCK_SPEED * elapsedTime;
    }
  }

  private static void setBlockId(Rectangle block, int rowNumber, Integer blockNumber, int column) {
    String blockId = DEFAULT_BLOCK_ID + rowNumber + column;
    if (blockNumber == 6) {
      blockId += "_powerupDoublePaddle";
    } else if (blockNumber == 7) {
      blockId += "_powerupSlowBall";
    } else if (blockNumber == 8) {
      blockId += "_powerupBigBall";
    }
    block.setId(blockId);
  }

  public int getLives() {
    return lives;
  }

  public void decreaseLives() {
    lives--;
    setColor();
  }

  public void decreaseAllLives() {
    lives = 0;
  }

  public boolean hasNoLives() {
    return lives == 0;
  }

  private void initilizeLives() {
    if (blockNumber <= 5) {
      this.lives = blockNumber;
    } else {
      this.lives = 1;
    }
  }

  private void setColor() {
    if (blockNumber <= 5) {
      setFill(colors[lives]);
    } else {
      setFill(colors[blockNumber]);
    }
  }

  public boolean isReadyForCollision() {
    return readyForCollision;
  }

  public void setReadyForCollision(boolean readyForCollision) {
    this.readyForCollision = readyForCollision;
  }
}
