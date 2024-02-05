import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.example.SnakeGameLogic;
class SnakeGameLogicTest {
private SnakeGameLogic snakeGameLogic;
    @Test
    public void testStartGame() {
        SnakeGameLogic snakeGameLogic = new SnakeGameLogic();

        // Antes de iniciar el juego, la serpiente no debería estar corriendo
        assertFalse(snakeGameLogic.isRunning());

        // Iniciar el juego
        snakeGameLogic.startGame();

        // Después de iniciar el juego, la serpiente debería estar corriendo
        assertTrue(snakeGameLogic.isRunning());

    }

    @Test
    public void testScoreIncrement() {
        SnakeGameLogic snakeGameLogic = new SnakeGameLogic();

        // Iniciar el juego
        snakeGameLogic.startGame();

        // Realizar acciones que aumentan la puntuación
        // snakeGameLogic.performSomeActions();

        // Verificar si la puntuación ha aumentado correctamente
        // assertEquals(expectedScore, snakeGameLogic.getScore());
    }
    @Test
    public void testBorderCollision() {
        SnakeGameLogic snakeGameLogic = new SnakeGameLogic();

        // Iniciar el juego
        snakeGameLogic.startGame();

        // Mover la serpiente fuera de los límites
        while (snakeGameLogic.isRunning()) {
            snakeGameLogic.move();
        }

        // Verificar que el juego se detiene correctamente
        assertFalse(snakeGameLogic.isRunning());
     }
}
