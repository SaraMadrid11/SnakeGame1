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
}