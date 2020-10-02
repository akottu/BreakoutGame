package breakout;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.concurrent.TimeUnit;

import util.BreakoutTestUtil;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Tests for BreakoutGame class.
 *
 * @author Anish Kottu, Eddie Kong
 */
public class BreakoutTest extends BreakoutTestUtil {

    private final BreakoutGame myGame = new BreakoutGame();
    private BreakoutScene myScene;
    private StatusDisplay statusDisplay = new StatusDisplay(myGame);

    private Ball ball;
    private Rectangle paddle;

    private Rectangle block00;
    private Rectangle block11;
    private Rectangle block22;
    private Rectangle block415;

    /**
     * Start special test version of application that does not animate on its own before each test.
     *
     * Automatically called @BeforeEach by TestFX.
     */
    @Override
    public void start (Stage stage) {
        CollisionHandler collisionHandlerTest = new CollisionHandlerTest();
        myGame.setCollision(collisionHandlerTest);
        // create game's scene with all shapes in their initial positions and show it
        myScene = (BreakoutScene) myGame.setupScene(BreakoutGame.HORIZONTAL_SIZE, BreakoutGame.VERTICAL_SIZE, BreakoutGame.BACKGROUND, 997);
        stage.setScene(myScene);
        stage.show();

        // find individual items within game by ID (must have been set in your code using setID())
        ball = lookup("#ball").query();
        paddle = lookup("#paddle").query();

        block00 = lookup("#block00").query();
        block11 = lookup("#block11").query();
        block22 = lookup("#block22").query();
        block415 = lookup("#block415").query();
    }

    //Test scoreDisplay by itself before running other tests
    @Test
    public void testScoreDisplay(){
        ball.setSpeedX(50);
        ball.setSpeedY(50);
        ball.setCenterX(760);
        ball.setCenterY(16);

        assertEquals("Score: 0", statusDisplay.getScoreDisplay().getText());

        sleep(1, TimeUnit.SECONDS);    // PAUSE: not typically recommended in tests
        myGame.step(BreakoutGame.SECOND_DELAY);

        assertEquals("Score: 10", statusDisplay.getScoreDisplay().getText());

        resetBreakoutGameState();
    }

    //Test scoreDisplay by itself before running other tests
    @Test
    public void testHighScoreDisplay(){
        HighScoreEditor editor = new HighScoreEditor();
        ball.setSpeedX(50);
        ball.setSpeedY(50);
        ball.setCenterX(760);
        ball.setCenterY(16);

        editor.writeHighScore("0");

        sleep(1, TimeUnit.SECONDS);    // PAUSE: not typically recommended in tests
        myGame.step(BreakoutGame.SECOND_DELAY);

        assertEquals("High Score: 10", statusDisplay.getHighScoreDisplay().getText());
        resetBreakoutGameState();
    }

    @Test
    public void testInitialBall () {
        assertEquals(400, ball.getCenterX());
        assertEquals(370, ball.getCenterY());
        sleep(1, TimeUnit.SECONDS);
        assertEquals(400, ball.getCenterX());
        assertEquals(370, ball.getCenterY());
        assertEquals(15, ball.getRadius());

        myGame.step(BreakoutGame.SECOND_DELAY);
        sleep(1, TimeUnit.SECONDS);    // PAUSE: not typically recommended in tests

        assertTrue(400 < ball.getCenterX() && ball.getCenterX() < 405);
        assertEquals(400 + 50 * 1.0 / BreakoutGame.FRAMES_PER_SECOND, ball.getCenterX(), 0.01);
        assertEquals(370 - 50 * 1.0 / BreakoutGame.FRAMES_PER_SECOND, ball.getCenterY(), 0.01);

        resetBreakoutGameState();
    }

    @Test
    public void testInitialPaddle () {
        assertEquals(350, paddle.getX());
        assertEquals(390, paddle.getY());
        press(myScene, KeyCode.RIGHT);
        assertEquals(358, paddle.getX());
        assertEquals(390, paddle.getY());
        press(myScene, KeyCode.LEFT);
        press(myScene, KeyCode.LEFT);
        assertEquals(342, paddle.getX());
        assertEquals(390, paddle.getY());

        resetBreakoutGameState();
    }

    @Test
    public void testInitialBlocks () {
        assertEquals(0 , block00.getX());
        assertEquals(0, block00.getY());

        assertEquals(50 , block11.getX());
        assertEquals(50, block11.getY());

        assertEquals(100, block22.getX());
        assertEquals(100, block22.getY());

        resetBreakoutGameState();
    }

    @Test
    public void testBallCorner () {
        ball.setCenterX(BreakoutGame.HORIZONTAL_SIZE/2);
        ball.setCenterY(BreakoutGame.VERTICAL_SIZE - paddle.getHeight() - ball.getRadius());
        assertEquals(50, ball.getSpeedX());
        assertEquals(-50, ball.getSpeedY());
        ball.setCenterX(BreakoutGame.HORIZONTAL_SIZE - ball.getRadius());
        ball.setCenterY(0 + ball.getRadius());

        sleep(1, TimeUnit.SECONDS);    // PAUSE: not typically recommended in tests
        myGame.step(BreakoutGame.SECOND_DELAY);
        sleep(1, TimeUnit.SECONDS);

        assertEquals(-50, ball.getSpeedX());
        assertEquals(50, ball.getSpeedY());

        resetBreakoutGameState();
    }

    @Test
    public void testPaddleBounce () {
        ball.setCenterY(BreakoutGame.HORIZONTAL_SIZE - paddle.getHeight() - ball.getRadius());
        ball.setSpeedY(50);
        assertEquals(50, ball.getSpeedX());
        assertEquals(50, ball.getSpeedY());

        sleep(1, TimeUnit.SECONDS);    // PAUSE: not typically recommended in tests
        myGame.step(BreakoutGame.SECOND_DELAY);
        sleep(1, TimeUnit.SECONDS);

        assertEquals(50, ball.getSpeedX());
        assertEquals(-50, ball.getSpeedY());

        resetBreakoutGameState();
    }

    @Test
    public void testBottomWallBall () {
        ball.setSpeedY(50);
        ball.setCenterX(100);
        ball.setCenterY(BreakoutGame.HORIZONTAL_SIZE - ball.getRadius());

        sleep(1, TimeUnit.SECONDS);    // PAUSE: not typically recommended in tests
        myGame.step(BreakoutGame.SECOND_DELAY);
        sleep(1, TimeUnit.SECONDS);

        assertEquals(400, (int)ball.getCenterX());
        assertEquals(369, (int)ball.getCenterY());

        resetBreakoutGameState();
    }

    @Test
    public void testBlockBallCollisionTopEdge () {
        ball.setSpeedX(50);
        ball.setSpeedY(50);
        ball.setCenterX(775);
        ball.setCenterY(35);

        sleep(1, TimeUnit.SECONDS);    // PAUSE: not typically recommended in tests
        myGame.step(BreakoutGame.SECOND_DELAY);

        assertEquals(50, ball.getSpeedX());
        assertEquals(-50, ball.getSpeedY());

        resetBreakoutGameState();
    }

    @Test
    public void testBlockBallCollisionRightEdge () {
        ball.setSpeedX(50);
        ball.setSpeedY(50);
        ball.setCenterX(760);
        ball.setCenterY(16);

        sleep(1, TimeUnit.SECONDS);    // PAUSE: not typically recommended in tests
        myGame.step(BreakoutGame.SECOND_DELAY);

        assertEquals(-50, ball.getSpeedX());
        assertEquals(50, ball.getSpeedY());

        resetBreakoutGameState();
    }

    @Test
    public void testBlockBallCollisionLives () {
        Block block = null;
        ball.setSpeedX(50);
        ball.setSpeedY(50);
        ball.setCenterX(760 - 0.8333129882812);
        ball.setCenterY(15);

        sleep(1, TimeUnit.SECONDS);    // PAUSE: not typically recommended in tests
        myGame.step(BreakoutGame.SECOND_DELAY);
        for(Node node: myScene.getRoot().getChildrenUnmodifiable()) {
            if(node.getId().contains("block014")) {
                block = (Block) node;
                break;
            }
        }

        assertEquals(4, block.getLives());

        resetBreakoutGameState();
    }

    @Test
    public void testGameOverDisplay () {
        myGame.setCurrentLife(0);
        ball.setSpeedY(50);
        ball.setCenterX(50);
        ball.setCenterY(BreakoutGame.HORIZONTAL_SIZE - ball.getRadius());

        sleep(1, TimeUnit.SECONDS);    // PAUSE: not typically recommended in tests
        myGame.step(BreakoutGame.SECOND_DELAY);

        assertEquals("GAME OVER", statusDisplay.gameOver.getText());

        resetBreakoutGameState();
    }

    @Test
    public void testGameOverDisplayNotGameOver () {
        ball.setSpeedY(50);
        ball.setCenterX(50);
        ball.setCenterY(BreakoutGame.HORIZONTAL_SIZE - ball.getRadius());

        sleep(1, TimeUnit.SECONDS);    // PAUSE: not typically recommended in tests
        myGame.step(BreakoutGame.SECOND_DELAY);

        assertEquals("", statusDisplay.gameOver.getText());

        resetBreakoutGameState();
    }

    @Test
    public void testPaddlePowerup () {
        ball.setSpeedY(50);
        ball.setCenterX(525);
        ball.setCenterY(245);

        sleep(1, TimeUnit.SECONDS);    // PAUSE: not typically recommended in tests
        myGame.step(BreakoutGame.SECOND_DELAY);

        assertEquals(200, paddle.getWidth());

        resetBreakoutGameState();
    }

    @Test
    public void testSlowBallPowerup () {
        ball.setSpeedX(50);
        ball.setSpeedY(50);
        ball.setCenterX(30);
        ball.setCenterY(260);

        sleep(1, TimeUnit.SECONDS);    // PAUSE: not typically recommended in tests
        myGame.step(BreakoutGame.SECOND_DELAY);

        assertEquals(-25, ball.getSpeedX());
        assertEquals(25, ball.getSpeedY());


        resetBreakoutGameState();
    }

    @Test
    public void testBigBallPowerup () {
        ball.setSpeedY(50);
        ball.setCenterX(75);
        ball.setCenterY(250);

        sleep(1, TimeUnit.SECONDS);    // PAUSE: not typically recommended in tests
        myGame.step(BreakoutGame.SECOND_DELAY);

        assertEquals(30, ball.getRadius());

        resetBreakoutGameState();
    }

    @Test
    public void testPaddlePowerupKey(){
        assertEquals(100, paddle.getWidth());

        press(myScene, KeyCode.P);

        assertEquals(200, paddle.getWidth());
    }

    @Test
    public void testSlowBallPowerupKey() {
        assertEquals(50, ball.getSpeedX());
        assertEquals(-50, ball.getSpeedY());

        press(myScene, KeyCode.S);

        assertEquals(25, ball.getSpeedX());
        assertEquals(-25, ball.getSpeedY());

        resetBreakoutGameState();
    }

    @Test
    public void testBigBallPowerUpKey(){
        assertEquals(15, ball.getRadius());
        press(myScene, KeyCode.B);
        assertEquals(30, ball.getRadius());
    }

    @Test
    public void testPause(){
        assertEquals(50, ball.getSpeedX());
        assertEquals(-50, ball.getSpeedY());
        assertEquals(400, ball.getCenterX());
        assertEquals(370, ball.getCenterY());

        press(myScene, KeyCode.SPACE);

        myGame.step(BreakoutGame.SECOND_DELAY);
        sleep(1, TimeUnit.SECONDS);

        assertEquals(50, ball.getSpeedX());
        assertEquals(-50, ball.getSpeedY());
        assertEquals(400, ball.getCenterX());
        assertEquals(370, ball.getCenterY());

        press(myScene, KeyCode.SPACE);

        myGame.step(BreakoutGame.SECOND_DELAY);
        sleep(1, TimeUnit.SECONDS);

        assertEquals(50, ball.getSpeedX());
        assertEquals(-50, ball.getSpeedY());
        assertTrue(ball.getCenterX() > 400);
        assertTrue(ball.getCenterY() < 370);
    }

    @Test
    public void testResetKey(){
        ball.setCenterX(50);
        ball.setCenterY(50);
        paddle.setX(50);
        paddle.setY(50);

        press(myScene, KeyCode.R);
        assertEquals(350, paddle.getX());
        assertEquals(50, paddle.getY());
        assertEquals(400, ball.getCenterX());
        assertEquals(370, ball.getCenterY());

        resetBreakoutGameState();
    }

    @Test
    public void testAddLifeDisplay(){
        press(myScene, KeyCode.L);
        Circle life = lookup("#life4").query();

        assertEquals(120, life.getCenterX());
        assertEquals(380, life.getCenterY());
    }

    private void resetBreakoutGameState() {
        ball.setSpeedX(50);
        ball.setSpeedY(-50);
        myGame.setCurrentLife(3);
    }

}