package com.github.TheTrueHooha.Frame;

import javax.swing.*;


public class GameFrame extends JFrame {

    //defines the game frame for the snake xenzia
    public GameFrame()  {
        this.add(new GamePanel());
        this.setTitle("Snake Xenzia");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }
}
