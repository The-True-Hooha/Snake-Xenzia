package com.github.TheTrueHooha.Frame;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements ActionListener {

    //final variables that declare the game screen parameters
    static final int GAME_SCREEN_WIDTH = 1600;
    static final int GAME_SCREEN_HEIGHT = 1600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (GAME_SCREEN_WIDTH*GAME_SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;

    //array holds the body parts for the game
    final int X[] = new int[GAME_UNITS];
    final int Y[] = new int[GAME_UNITS];

    //initial amount of body parts for the snake
    int initialSnakeBodyParts = 5;

    //declares the snake food that is eaten
    int snakeFoodEaten;

    //random positions for the snake food
    int snakeFoodPositionX;
    int snakeFoodPositionY;

    //declares the snake direction
    char snakeDirection = 'R';

    //declares the amount of speed the snake acquire per food eaten
    boolean snakeRunningFast = false;
    Timer timer;
    Random random;

    //sets the game interface parameters
    public GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(GAME_SCREEN_WIDTH, GAME_SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new keyAdapter());
        startSnakeXenzia();

    }

    //starts the game
    public void startSnakeXenzia(){
        newSnakeFood();
        snakeRunningFast = true;
        timer = new Timer(DELAY, this);
        timer.start();

    }

    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        drawGameComponent(graphics);

    }

    //for loop that creates a grid around the interface
    public void drawGameComponent(Graphics drawGraphics){

        if (snakeRunningFast) {

            //loops the grid lines on the screen
            /*
            for (int i = 0; i < GAME_SCREEN_HEIGHT/UNIT_SIZE; i++){
                drawGraphics.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, GAME_SCREEN_HEIGHT);
                drawGraphics.drawLine(0, i*UNIT_SIZE, GAME_SCREEN_WIDTH, i*UNIT_SIZE);
            }
             */

            drawGraphics.setColor(Color.green);
            drawGraphics.fillOval(snakeFoodPositionX, snakeFoodPositionY, UNIT_SIZE, UNIT_SIZE);

            //snake body
            for (int i = 0; i < initialSnakeBodyParts; i++){
                if (i == 0) {
                    drawGraphics.setColor(Color.YELLOW);
                    drawGraphics.fillRect(X[i], Y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    drawGraphics.setColor(Color.PINK);
                    drawGraphics.fillRect(X[i], Y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            drawGraphics.setColor(Color.red);
            drawGraphics.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics fontMetrics = getFontMetrics(drawGraphics.getFont());
            drawGraphics.drawString("Score: "+snakeFoodEaten,
                    (GAME_SCREEN_WIDTH -
                            fontMetrics.stringWidth("Score: "+snakeFoodEaten))/2, drawGraphics.getFont().getSize());
        } else {
            gameOver(drawGraphics);
        }

    }

    //randomly generates the position of the snake food
    public void newSnakeFood(){
        snakeFoodPositionX = random.nextInt((int) (GAME_SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        snakeFoodPositionY = random.nextInt((int) (GAME_SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }

    public void moveGameOptions(){
        for(int i = initialSnakeBodyParts; i>0; i--){
            X[i] = X[i-1];
            Y[i] = Y[i-1];
        }

        //switch statement that changes the key direction
        switch (snakeDirection){
            case 'U' :
               Y[0] =  Y[0] - UNIT_SIZE;
               break;
            case 'D' :
                Y[0] = Y[0] + UNIT_SIZE;
                break;
            case 'L' :
                X[0] = X[0] - UNIT_SIZE;
                break;
            case 'R' :
                X[0] = X[0] + UNIT_SIZE;
                break;
        }
    }

    public void checkSnakeFood(){
        if ((X[0] == snakeFoodPositionX) && Y[0] == snakeFoodPositionX) {
            initialSnakeBodyParts++;
            snakeFoodEaten++;
            newSnakeFood();
        }
    }

    public void checkAnyCollisons(){
        //checks if the snake touches its body
        for (int i = initialSnakeBodyParts; i>0; i--){
            if ((X[0] == X[i] && (Y[0] == Y[i]))){
                snakeRunningFast = false;
            }
        }
        //checks if the snake touches the left frame
        if (X[0] < 0 ){
            snakeRunningFast = false;
        }
        //checks if the snake touches the right frame
        if (Y[0] > GAME_SCREEN_WIDTH ){
            snakeRunningFast = false;
        }
        //checks if the snake touches the top frame
        if (Y[0] < 0 ){
            snakeRunningFast = false;
        }
        //checks if the snake touches the bottom frame
        if (X[0] > GAME_SCREEN_HEIGHT){
            snakeRunningFast = false;
        }

        if (!snakeRunningFast){
            timer.stop();
        }
    }

    public void gameOver(Graphics graphics){
        graphics.setColor(Color.red);
        graphics.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics fontMetrics = getFontMetrics(graphics.getFont());
        graphics.drawString("Score: "+snakeFoodEaten,
                (GAME_SCREEN_WIDTH -
                        fontMetrics.stringWidth("Score: "+snakeFoodEaten))/2, graphics.getFont().getSize());
        graphics.setColor(Color.red);
        graphics.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(graphics.getFont());
        graphics.drawString("Game over",
                (GAME_SCREEN_WIDTH - metrics.stringWidth("Game over"))/2, GAME_SCREEN_HEIGHT/2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (snakeRunningFast){
            moveGameOptions();
            checkSnakeFood();
            checkAnyCollisons();
        }
        repaint();
    }

    //new innerclass that listens to the key buttons events
    public class keyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent keyEvent){
            switch (keyEvent.getKeyCode()){
                case KeyEvent.VK_LEFT :
                    if (snakeDirection != 'R'){
                        snakeDirection = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (snakeDirection != 'L'){
                        snakeDirection = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (snakeDirection != 'D'){
                        snakeDirection = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (snakeDirection != 'U'){
                        snakeDirection = 'D';
                    }
                    break;
            }
        }

    }
}
