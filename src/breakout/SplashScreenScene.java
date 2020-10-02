package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * The splash screen before the game
 */
public class SplashScreenScene extends Scene {

  private final Paint FONT_COLOR = Color.BLACK;
  private final String WELCOME_STRING = "WELCOME TO BREAKOUT GAME\n Don't let the ball fall off the bottom!";
  private final String CONTROLS =
      "Controls: \nLeft Arrow = move left\nRight Arrow = move right\nSpacebar = pause\nR = reset\nEsc = exit";

  private final int WELCOME_X_POSITION = 200;
  private final int WELCOME_FONT_SIZE = 30;
  private final int CONTROL_X_POSITION = 250;
  private final int CONTROL_FONT_SIZE = 20;
  private final int START_Y_POSITION = 50;
  private final int Y_OFFSET = 100;

  /**
   * creates the splash screen scene
   * @param width
   * @param height
   * @param background
   * @param root
   */
  public SplashScreenScene(int width, int height, Paint background, Group root){
    super(root, width, height, background);
    welcomeText(root);
    controlsText(root);
  }

  /**
   * creates the welcome text node
   * @param root
   * @return
   */
  private Text welcomeText(Group root){
    Text welcome = new Text();
    welcome.setText(WELCOME_STRING);
    welcome.setX(WELCOME_X_POSITION);
    welcome.setY(START_Y_POSITION);
    welcome.setFill(FONT_COLOR);
    welcome.setFont(Font.font(null, FontWeight.BOLD, WELCOME_FONT_SIZE));
    root.getChildren().add(welcome);
    return welcome;
  }

  /**
   * creates the controls text node
   * @param root
   * @return
   */
  private Text controlsText(Group root){
    Text controls = new Text();
    controls.setText(CONTROLS);
    controls.setX(CONTROL_X_POSITION);
    controls.setY(START_Y_POSITION + Y_OFFSET);
    controls.setFill(FONT_COLOR);
    controls.setFont(Font.font(null, FontWeight.BOLD, CONTROL_FONT_SIZE));
    root.getChildren().add(controls);
    return controls;
  }
}
