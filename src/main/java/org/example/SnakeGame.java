package org.example; // Declaración del paquete para organizar las clases
import javax.swing.*;
public class SnakeGame {

    public static void main(String[] args) {
        // Inicia el juego y muestra la pantalla
        SwingUtilities.invokeLater(() -> {
            GameFrame gameFrame = new GameFrame();
            // Hace visible la ventana del juego
            gameFrame.setVisible(true);
        });


    }
}