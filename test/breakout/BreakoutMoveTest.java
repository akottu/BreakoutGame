package breakout;


import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.BreakoutTestUtil;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Tests for BreakoutGame class.
 *
 * @author Anish Kottu, Eddie Kong
 */
public class BreakoutMoveTest extends BreakoutTestUtil {

    private final BreakoutGame myGame = new BreakoutGame();
    private Scene myScene;

    private Ball ball;
    private Rectangle paddle;

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
        myScene = myGame.setupScene(BreakoutGame.HORIZONTAL_SIZE, BreakoutGame.VERTICAL_SIZE, BreakoutGame.BACKGROUND, 2);
        stage.setScene(myScene);
        stage.show();

        // find individual items within game by ID (must have been set in your code using setID())
        ball = lookup("#ball").query();
        paddle = lookup("#paddle").query();
    }

    @Test
    public void testBlockMoving () {
        Block block = lookup("#block33").query();
        double blockXPosition = block.getX();

        sleep(1, TimeUnit.SECONDS);    // PAUSE: not typically recommended in tests
        myGame.step(BreakoutGame.SECOND_DELAY);

        assertTrue(blockXPosition <= block.getX());

        resetBreakoutGameState();
    }

    private void resetBreakoutGameState() {
        ball.setSpeedX(50);
        ball.setSpeedY(-50);
        myGame.setCurrentLife(3);
    }

}
