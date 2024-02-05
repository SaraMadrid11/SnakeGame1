package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

// Interfaz que define la lógica del juego
interface GameLogic {
    void startGame();
    void move();
    void checkApple();
    void checkCollisions(GamePanel gamePanel);
    boolean isRunning();
    int getAppleX();
    int getAppleY();
    int getX(int index);
    int getY(int index);
    int getBodyParts();
    char getDirection();
    void setDirection(char direction);
    void displayGameOver(Graphics g);
    int getScore(); // Método para obtener la puntuación
    void setGamePanel(GamePanel gamePanel);
}

// Interfaz que define la renderización del juego
interface GameRenderer {
    void paintComponent(Graphics g);
}

// Implementación de la lógica del juego (Tamaño de pantalla, velocidad de la serpiente, partes del cuerpo, aparición aleatoria de la manzana)
public class SnakeGameLogic implements GameLogic {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 130;

    private final int[] x = new int[GAME_UNITS];
    private final int[] y = new int[GAME_UNITS];

    private int bodyParts = 6;
    private int applesEaten;
    private int appleX;
    private int appleY;
    private char direction = 'R';

    private boolean running = false;
    private final Timer timer;
    private final Random random;

    private GamePanel gamePanel;
    private int score = 0;

    public SnakeGameLogic() {
        random = new Random();
        timer = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                move();
                checkApple();
                checkCollisions(gamePanel);
            }
        });
    }

    @Override
    public void startGame() {
        // Inicia el juego generando una nueva manzana, estableciendo el estado de ejecución y comenzando el temporizador.
        newApple();
        running = true;
        timer.start();
    }

    @Override
    public void move() {
        // Mueve la serpiente avanzando cada parte del cuerpo y ajustando la cabeza según la dirección actual.
        // El movimiento se basa en las unidades de tamaño definidas.
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            // Actualiza la posición de la cabeza de la serpiente según la dirección.
            case 'U':
                y[0] = y[0] - UNIT_SIZE; // Mueve hacia arriba restando la unidad de tamaño.
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE; // Mueve hacia abajo sumando la unidad de tamaño.
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE; // Mueve hacia la izquierda restando la unidad de tamaño.
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE; // Mueve hacia la derecha sumando la unidad de tamaño.
                break;
        }
    }

    @Override
    public void checkApple() {
        // Verifica si la serpiente ha comido una manzana
        if ((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            score += 10;
            newApple();
        }
    }

    @Override
    public void checkCollisions(GamePanel gamePanel) {
        // Verifica si la serpiente se choca con algún borde de la pantalla
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }

        if (x[0] < 0 || x[0] >= SCREEN_WIDTH || y[0] < 0 || y[0] >= SCREEN_HEIGHT) {
            running = false;
        }

        if (!running) {
            timer.stop();
            if (gamePanel != null) {
                gamePanel.repaint();
            }
        }
    }

    @Override
    public void displayGameOver(Graphics g) {
        // Muestra el mensaje de fin de juego y como es su fuente y su tamaño asignados
        g.setColor(Color.orange);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
    }

    private void newApple() {
        appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public int getAppleX() {
        return appleX;
    }

    @Override
    public int getAppleY() {
        return appleY;
    }

    @Override
    public int getX(int index) {
        return x[index];
    }

    @Override
    public int getY(int index) {
        return y[index];
    }

    @Override
    public int getBodyParts() {
        return bodyParts;
    }

    @Override
    public char getDirection() {
        return direction;
    }

    @Override
    public void setDirection(char direction) {
        this.direction = direction;
    }

    @Override
    public int getScore() {
        return score;
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
}

// Implementación de la renderización del juego
class SnakeGameRenderer implements GameRenderer {
    private static final int UNIT_SIZE = 25;
    private final GameLogic gameLogic;

    SnakeGameRenderer(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    @Override
    public void paintComponent(Graphics g) {
        //Renderiza elementos como el tamaño de la pantalla de juego, el color de la serpiente o añadirle partes del cuerpo
        for (int i = 0; i < SnakeGameLogic.SCREEN_HEIGHT / UNIT_SIZE; i++) {
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SnakeGameLogic.SCREEN_HEIGHT);
            g.drawLine(0, i * UNIT_SIZE, SnakeGameLogic.SCREEN_WIDTH, i * UNIT_SIZE);
        }
        g.setColor(Color.red);
        g.fillOval(gameLogic.getAppleX(), gameLogic.getAppleY(), UNIT_SIZE, UNIT_SIZE);

        for (int i = 0; i < gameLogic.getBodyParts(); i++) {
            Color snakeColor;
            if (i == 0) {
                snakeColor = Color.orange;
            } else {
                int red = 45 + i * 10;
                int green = 150 - i * 10;
                int blue = 0;
                snakeColor = new Color(red, green, blue);
            }

            g.setColor(snakeColor);
            g.fillRect(gameLogic.getX(i), gameLogic.getY(i), UNIT_SIZE, UNIT_SIZE);
        }
        // Selecciona el tipo de fuente del apartado de puntuación o score, el tamaño y el color
        Font scoreFont = new Font("Arial", Font.BOLD, 20);
        g.setFont(scoreFont);
        g.setColor(Color.white);
        g.drawString("Score: " + gameLogic.getScore(), 10, 30);
    }
}

// Panel de juego
 class GamePanel extends JPanel {
    private final GameLogic gameLogic;
    private final GameRenderer gameRenderer;

    public GamePanel(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
        this.gameRenderer = new SnakeGameRenderer(gameLogic);

        gameLogic.setGamePanel(this);

        setPreferredSize(new Dimension(SnakeGameLogic.SCREEN_WIDTH, SnakeGameLogic.SCREEN_HEIGHT));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());

        gameLogic.startGame();
        startGameLoop();
    }

    // Inicia el bucle del juego
    private void startGameLoop() {
        Timer timer = new Timer(120, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameLogic.isRunning()) {
                    gameLogic.move();
                    gameLogic.checkApple();
                    gameLogic.checkCollisions(GamePanel.this);
                    repaint();
                }
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        gameRenderer.paintComponent(g);

        if (!gameLogic.isRunning()) {
            gameLogic.displayGameOver(g);
        }
    }

    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            // Hace que al presionar las flechas del teclado, se mueva la serpiente según la dirección deseada (Right,Left,Up y Down)
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (gameLogic.isRunning() && gameLogic.getDirection() != 'R') {
                        gameLogic.setDirection('L');
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (gameLogic.isRunning() && gameLogic.getDirection() != 'L') {
                        gameLogic.setDirection('R');
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (gameLogic.isRunning() && gameLogic.getDirection() != 'D') {
                        gameLogic.setDirection('U');
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (gameLogic.isRunning() && gameLogic.getDirection() != 'U') {
                        gameLogic.setDirection('D');
                    }
                    break;
            }
        }
    }
}


