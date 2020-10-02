game
====

This project implements the game of Breakout.

Name: Anish Kottu, Eddie Kong

### Timeline

Start Date: Sept 11, 2020

Finish Date: Sept 29, 2020

Hours Spent: 40

### Resources Used

Java API, JavaFX API

### Running the Program

Main class: BreakoutGame

Data files needed: 
* data/level1.txt
* data/level2.txt
* data/level3.txt

Key/Mouse inputs: 
* No mouse inputs
* Left arrow key - move paddle left
* Right arrow key - move paddle right
* Spacebar - pause balls and blocks
* Escape - exit program
* r - reset ball and paddle

Cheat keys:
* c - clear level
* n - doesn't let ball fall
* l - add a life
* d - destroy first remaining block
* p - paddle size powerup
* b - big ball powerup
* s - slow ball powerup
* 1 - go to level 1
* 2 - go to level 2
* 3 - go to level 3

Known Bugs:
* Collisions are finnicky, ball doesn't bounce off walls correctly
* Once we complete the third level, nothing happens
* When spash screen is running, ball moves in the background
* When levels change organically, the ball and paddle don't reset

Extra credit:
* Splash screen

### Notes/Assumptions
* We assumed that a different construction of each level could be establish through the movement
 of blocks, both horizontally and vertically
* We decided to not have the powerups fall from the blocks they are located in, and rather have
 the powerup immediately applied to the game when that block is hit instead

### Impressions
* Would be helpful to explain exactly what it means when it states that each level should differ
 functionally from the others
