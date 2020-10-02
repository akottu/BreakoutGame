# Game Plan
## NAMEs
Anish Kottu, Eddie Kong

### Breakout Variation Ideas
* One new Breakout Variation Idea is you control two separate paddles, one with the
arrow keys and the other with the mouse. Each time you get above a certain number of points,
a new ball is added into the round. You lose when you drop a ball.

### Interesting Existing Game Variations

 * Game 1
    * Brick n Balls variation. This variation does not require a paddle. Unlike the original
    Breakout, this variation is less interactive and more strategic.
 * Game 2
    * Vortex variation. It is cool how the entire game is adapted to an iPod scroll wheel.
    Perhaps this game can be implemented onto a computer, and scrolling of the mouse is 
    used to move the paddle.


#### Block Ideas

Block 1.
Blue - 1 point - speed of the ball does not increase
Block 2.
Red - 2 points - speed of the ball increases by 0.25%
Block 3. 
Red - 3 points - speed of the ball increases by 0.5%
Block 4. 
Orange - 4 points - speed of the ball increases by 0.75%
Block 5. 
Cyan - 5 points - speed of the ball increases by 1%


#### Power Up Ideas

 * Ball continue upward movement even it hits a block

 * Reduces the speed of the ball

 * Doubles the size of the ball


#### Cheat Key Ideas

 * Cheat Key 1
    * Reset
 * Cheat Key 2
    * Pause
 * Cheat Key 3
    * Bounce off floor
 * Cheat Key 4
    * Gain a life

#### Level Descriptions

 * Level 1
   * 3 rows of static rows
   * Row closest to ball initial position gets 1 point, and the highest row gets 2 points so on... 

 * Level 2
   * 6 rows of static rows
   * Row closest to ball initial position gets 1 point, and the highest row gets 2 points so on...

 * Level 3
   * 5 rows and each row is 50% filled
   * They  move back and forth horizontally


### Possible Classes

 * Block
   * Encapsulates the properties of the Block object
   * width, height, color, curveRadius
   * constructor, get and set methods

 * Ball
   * Encapsulates the properties of the Ball object
   * properties - radius, color, speed
   * constructor, get and set methods
   
* Paddle
  * Encapsulates the properties of the Paddle object
  * properties - width, length, color, speed
  * constructor, get and set methods

 * BreakoutScene
   * Encapsulates the properties of the Block object
   * constructor parameters - constructor, rows, columns, colors, intialLocationOfTheBall
   * properties - ball, list of blocks, paddle, and rules
   * constructor, set and get methods

 * StepHandler
   * Handles the game rules for every moment
   * handle(BreakoutScene scene, KeyCode code) - handles the game every moment

 * BlockHandler
   * Handles disappearance of the blocks
   * handle(BreakoutScene scene, KeyCode code)
   * handle(BreakoutScene scene, double x, double y)
   
 * BallHandler
    * Handles direction of the rebound, out of bounds, and speed etc.
    * handle(BreakoutScene scene, KeyCode code)
    * handle(BreakoutScene scene, double x, double y)
   
 
