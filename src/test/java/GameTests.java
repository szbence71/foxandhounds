import hu.unideb.inf.foxandhounds.game.FoxGame;
import hu.unideb.inf.foxandhounds.game.Vector2;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTests {
    @Test
    public void testPossibleMovements() {
        {
            int[][] board = createEmptyBoard();
            board[4][4] = FoxGame.FOX;
            board[0][7] = FoxGame.HOUND;
            FoxGame game = new FoxGame(board);
            {
                List<Vector2> moves = game.getPossiblePositions(4, 4);
                assertEquals(moves.size(), 4);
                assertTrue(moves.contains(new Vector2(3, 3)));
                assertTrue(moves.contains(new Vector2(3, 5)));
                assertTrue(moves.contains(new Vector2(5, 3)));
                assertTrue(moves.contains(new Vector2(5, 5)));
            }
            {
                List<Vector2> moves = game.getPossiblePositions(0, 7);
                assertEquals(moves.size(), 1);
                assertTrue(moves.contains(new Vector2(1, 6)));
            }
        }
        {
            int[][] board = createEmptyBoard();
            board[1][5] = FoxGame.FOX;
            board[3][5] = FoxGame.HOUND;
            board[4][6] = FoxGame.HOUND;
            board[6][6] = FoxGame.HOUND;
            board[7][5] = FoxGame.HOUND;
            FoxGame game = new FoxGame(board);

            List<Vector2> moves = game.getPossiblePositions(1, 5);
            assertEquals(moves.size(), 3);
            assertTrue(moves.contains(new Vector2(0, 4)));
            assertTrue(moves.contains(new Vector2(0, 6)));
        }
    }

    @Test
    public void testWin() {
        {
            int[][] board = createEmptyBoard();
            board[0][7] = FoxGame.FOX;
            board[1][4] = FoxGame.HOUND;
            board[1][6] = FoxGame.HOUND;
            board[2][3] = FoxGame.HOUND;
            board[2][5] = FoxGame.HOUND;

            FoxGame game = new FoxGame(board);
            game.checkWinCondition();
            assertTrue(game.won);
            assertEquals(game.winner, FoxGame.FOX);
        }
        {
            int[][] board = createEmptyBoard();
            board[0][0] = FoxGame.FOX;
            board[1][3] = FoxGame.HOUND;
            board[2][2] = FoxGame.HOUND;
            board[2][4] = FoxGame.HOUND;
            board[3][1] = FoxGame.HOUND;

            FoxGame game = new FoxGame(board);
            game.checkWinCondition();
            assertTrue(game.won);
            assertEquals(game.winner, FoxGame.HOUND);
        }
        {
            int[][] board = createEmptyBoard();
            board[0][0] = FoxGame.FOX;
            board[4][0] = FoxGame.HOUND;
            board[5][1] = FoxGame.HOUND;
            board[6][0] = FoxGame.HOUND;
            board[7][1] = FoxGame.HOUND;

            FoxGame game = new FoxGame(board);

            game.checkWinCondition();
            assertTrue(game.won);
            assertEquals(game.winner, FoxGame.FOX);
        }
    }

    @Test
    public void testBoard() {
        FoxGame game1 = new FoxGame();
        int[][] board = game1.getBoard();
        FoxGame game2 = new FoxGame(board);

        assertEquals(game1.getBoard(), game2.getBoard());
    }

    @Test
    public void testMovement() {
        int[][] board = createEmptyBoard();
        board[0][0] = FoxGame.FOX;
        board[1][7] = FoxGame.HOUND;
        board[3][7] = FoxGame.HOUND;
        board[5][7] = FoxGame.HOUND;
        board[7][7] = FoxGame.HOUND;

        FoxGame game = new FoxGame(board);
        assertTrue(game.movePlayer(0, 0, 1, 1, false));
        assertEquals(game.getBoard()[1][1], FoxGame.FOX);
        assertEquals(game.getBoard()[0][0], FoxGame.EMPTY);
        assertTrue(game.movePlayer(1, 7, 0, 6, false));
        assertEquals(game.getBoard()[0][6], FoxGame.HOUND);
        assertEquals(game.getBoard()[1][7], FoxGame.EMPTY);

        assertFalse(game.movePlayer(0, 6, 0, 0, false));
        assertEquals(game.getBoard()[0][0], FoxGame.EMPTY);
        assertEquals(game.getBoard()[0][6], FoxGame.HOUND);

        game.turn = FoxGame.FOX;
        assertTrue(game.movePlayer(1, 1, 0, 0));
        assertEquals(game.turn, FoxGame.HOUND);
        assertEquals(game.getBoard()[0][0], FoxGame.FOX);
        assertEquals(game.getBoard()[1][1], FoxGame.EMPTY);

        assertTrue(game.movePlayer(0, 6, 1, 5));
        assertEquals(game.turn, FoxGame.FOX);
        assertEquals(game.getBoard()[1][5], FoxGame.HOUND);
        assertEquals(game.getBoard()[0][6], FoxGame.EMPTY);

        assertTrue(game.movePlayer(0, 0, 1, 1));
        assertEquals(game.turn, FoxGame.HOUND);
        assertEquals(game.getBoard()[1][1], FoxGame.FOX);
        assertEquals(game.getBoard()[0][0], FoxGame.EMPTY);
    }

    private int[][] createEmptyBoard() {
        int[][] board = new int[8][8];
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                board[i][j] = FoxGame.EMPTY;
            }
        }
        return board;
    }
}
