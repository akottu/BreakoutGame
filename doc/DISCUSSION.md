## Lab Discussion
### Team 7
### Anish Kottu, Eddie Kong

**Accidentally did not create the refactoringLab branch before completing DISCUSSION and doing some
refactoring**

### Issues in Current Code

#### handleBlockBallCollision in CollisionHandler
 * A long method with 33 lines. 

#### StatusDisplay
 * public static Text gameOver;
    - Public static variables should either be constants or shouldn't exist at all.


### Refactoring Plan

 * What are the code's biggest issues?
    - The existence of methods that are too long. This suggests that some methods have more than a
    single responsibility.
    - Another issue is the inconsistencies in declaring variables public or static. In many
    instances, the code isn't exactly closed for modification.
    - There are a lot of magic numbers for coordinates and locations of objects.

 * Which issues are easy to fix and which are hard?
    - The magic number issues should be very easy to fix.
    - Making sure the instance variables are properly declared also should not take too long.
    - However, making the methods and classes shorter and have a single responsiblity could be 
    harder as it requires more thinking and planning.

 * What are good ways to implement the changes "in place"?
    - An easy way to implement the changes "in place" is just going down the results from the static
    code analysis and fixing each instances of the errors.

### Refactoring Work

 * Issue chosen: Fix and Alternatives
    - Switch case clauses having too many lines of code: Swtich cases are used to handle user control
    in this program. We can shorten the switch case clauses by creating a new class with methods that
    handle each key's function.

 * Issue chosen: Fix and Alternatives
    - Magic numbers: Turn all the magic numbers into named constants.