package org.example;

import javax.swing.*;

public class GameFrame extends JFrame {
    GameFrame() {
        // Crea una instancia de SnakeGameLogic
        GameLogic gameLogic = new SnakeGameLogic();

        // Agrega un nuevo GamePanel a la ventana, pasando la instancia de GameLogic
        this.add(new GamePanel(gameLogic));

        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}