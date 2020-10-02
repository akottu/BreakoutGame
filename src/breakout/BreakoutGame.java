package breakout;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Breakout game based on creation by Steve Wozniak and Steve Jobs Three levels, adjusted by level
 * variable
 *
 * @author Anish Kottu, Eddie Kong
 */
public class BreakoutGame extends Application {

  public static final String TITLE = "Breakout";
  public static final int HORIZONTAL_SIZE = 800;
  public static final int VERTICAL_SIZE = 400;
  public static final int FRAMES_PER_SECOND = 60;
  public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  public static final Paint BACKGROUND = Color.AZURE;
  public static final int STARTING_LIVES = 3;

  private static final int STARTING_LEVEL = 1;
  private static final int LEVEL_THREE = 3;
  private static final boolean[] horizontalMovement = {false, false, true, true};
  private static final boolean[] verticalMovement = {false, false, false, true};

  private static final String SPLASH_SCREEN_BUTTON = "Press to Start BreakoutGame!";
  private static final int SPLASH_BUTTON_X = 315;
  private static final int SPLASH_BUTTON_Y = 350;

  private Scene splashScene;
  private Scene myScene;
  BreakoutScene breakoutScene;
  private int level = 1;
  private boolean pause = false;
  private boolean clearedBlocks;
  PowerupHandler powerupHandler = new PowerupHandler();
  CollisionHandler collisionHandler = new CollisionHandler();

  private int currentLife = STARTING_LIVES;

  /**
   * Initialize what will be displayed and how it will be updated.
   */
  @Override
  public void start(Stage stage) {
    stage.setTitle(TITLE);
    setupSplashScene(stage);
    myScene = setupScene(HORIZONTAL_SIZE, VERTICAL_SIZE, BACKGROUND, STARTING_LEVEL);
    stage.setScene(splashScene);
    stage.show();
    KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY));
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
  }

  public void setupSplashScene (Stage stage) {
    Group splashRoot = new Group();
    Button startButton = new Button(SPLASH_SCREEN_BUTTON);
    startButton.setLayoutX(SPLASH_BUTTON_X);
    startButton.setLayoutY(SPLASH_BUTTON_Y);
    startButton.setOnAction(e -> stage.setScene(myScene));
    splashRoot.getChildren().add(startButton);
    splashScene = new SplashScreenScene(HORIZONTAL_SIZE, VERTICAL_SIZE, BACKGROUND, splashRoot);
  }

  /**
   * Create scene for the breakout game
   *
   * @param width
   * @param height
   * @param background
   * @return
   */
  public Scene setupScene(int width, int height, Paint background, int level) {
    Group root = new Group();
    breakoutScene = new BreakoutScene(width, height, background, level, root);
    breakoutScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    return breakoutScene;
  }

  /**
   * Handle the game's "rules" for every "moment" - movement, how do things change over time -
   * collisions, did things intersect and, if so, what should happen - power-ups, handle game based
   * on specific power-up times - goals, did the game or level end?
   *
   * @param elapsedTime
   */
  public synchronized void step(double elapsedTime) {
    collisionHandler.handle(breakoutScene, this);
    updateShapes(elapsedTime);
    powerupHandler.updateStatus(breakoutScene);
    checkGameStatus();
  }

  public void clearLevel() {
    Group root = breakoutScene.getGroupRoot();
    List<Node> blocksList = new ArrayList<>(root.getChildren());

    for (Node blockNode : blocksList) {
      if (blockNode.getId().contains(Block.DEFAULT_BLOCK_ID)) {
        root.getChildren().remove(blockNode);
      }
    }
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public int getCurrentLife() {
    return currentLife;
  }

  public void setCurrentLife(int currentLife) {
    this.currentLife = currentLife;
  }

  public void setCollision(CollisionHandler collisionHandler) {
    this.collisionHandler = collisionHandler;
  }

  /**
   * Start the program.
   */
  public static void main(String[] args) {
    launch(args);
  }


  private void handleKeyInput(KeyCode code) {
    UserController controller = new UserController(this, breakoutScene);
    Ball ball = breakoutScene.getBall();
    Paddle paddle = breakoutScene.getPaddle();
    handleCheatKeyInput(code, controller, ball, paddle);
    switch (code) {
      case LEFT -> controller.moveLeft(paddle);
      case RIGHT -> controller.moveRight(paddle);
      case SPACE -> pause = !pause;
      case ESCAPE -> controller.exitGame();
      case C -> clearLevel();
      case R -> controller.handleReset(paddle, ball);
    }
  }

  private void handleCheatKeyInput(KeyCode code, UserController controller, Ball ball,
      Paddle paddle) {
    switch (code) {
      case N -> controller.noFall(collisionHandler);
      case L -> controller.addLife();
      case D -> controller.destroyBlock();
      case P -> controller.widePaddlePowerUp(paddle);
      case B -> controller.bigBallPowerUp(ball);
      case S -> controller.slowBallPowerUp(ball);
      case DIGIT1 -> {
        controller.goToLevel(1);
        controller.handleReset(paddle, ball);
      }
      case DIGIT2 -> {
        controller.goToLevel(2);
        controller.handleReset(paddle, ball);
      }
      case DIGIT3 -> {
        controller.goToLevel(3);
        controller.handleReset(paddle, ball);
      }
    }
  }

  private void updateShapes(double elapsedTime) {
    Ball ball = breakoutScene.getBall();
    if (!pause) {
      ball.setCenterX(ball.getCenterX() + ball.getSpeedX() * elapsedTime);
      ball.setCenterY(ball.getCenterY() + ball.getSpeedY() * elapsedTime);
      moveBlocks(elapsedTime);
    }
  }

  private void moveBlocks(double elapsedTime) {
    if (horizontalMovement[level] || verticalMovement[level]) {
      if (isSceneNotEmpty()) {
        moveAllBlocks(elapsedTime);
      }
    }
  }

  private void moveAllBlocks(double elapsedTime) {
    for (Node node : myScene.getRoot().getChildrenUnmodifiable()) {
      if (node.getId().contains("block")) {
        Block block = (Block) node;
        block.move(elapsedTime, horizontalMovement[level], verticalMovement[level]);
      }
    }
  }

  private boolean isSceneNotEmpty() {
    return myScene != null && myScene.getRoot() != null
        && myScene.getRoot().getChildrenUnmodifiable() != null;
  }

  private void checkGameStatus(){
    Group root = breakoutScene.getGroupRoot();
    List<Node> blocksList = new ArrayList<>(root.getChildren());
    clearedBlocks = true;
    for (Node blockNodes : blocksList){
      if (blockNodes.getId().contains(Block.DEFAULT_BLOCK_ID)){
        clearedBlocks = false;
      }
    }
    if (clearedBlocks){
      level++;
      if(level <= LEVEL_THREE) {
        jumpToNextLevel(level);
      }
    }
  }

  private void jumpToNextLevel(int level){
    Group root = breakoutScene.getGroupRoot();
    BlockGenerator.generateBlocks(root, level);
    StatusDisplay.updateLevelDisplay(level);
  }
}
