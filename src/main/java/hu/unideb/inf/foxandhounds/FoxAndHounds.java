package hu.unideb.inf.foxandhounds;

import hu.unideb.inf.foxandhounds.game.FoxGame;
import hu.unideb.inf.foxandhounds.game.Vector2;
import org.tinylog.Logger;

import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 * A konzolos verzió kiírását vezérlő class.
 */
@ExcludeFromGeneratedReport
public class FoxAndHounds {
    private FoxGame game;

    /**
     * A játék konzolos verziójának indítását vezérlő konstruktor.
     */
    public FoxAndHounds() {
        game = new FoxGame();
        Scanner s = new Scanner(System.in);
        while(!game.won) {
            printBoard();
            System.out.println("Input formátum: x y x2 y2 (pl. 2 0 1 1)");
            System.out.println("Következik: " + (game.turn == FoxGame.FOX ? "Róka" : "Kutya"));
            String input = s.nextLine();
            int[] inputs = new int[4];
            for(int i = 0; i < 4; i++) {
                inputs[i] = Integer.parseInt(input.split(" ")[i]);
            }
            game.movePlayer(inputs[0], inputs[1], inputs[2], inputs[3]);
        }
        printBoard();
        System.out.println("Vége a játéknak!");
        System.out.println("Nyertes: " + (game.winner == FoxGame.FOX ? "Róka" : "Kutya"));
    }

    /**
     * A játék táblájának kiírása a {@link #game} objektum alapján a konzolra.
     */
    private void printBoard() {
        int[][] board = game.getBoard();
        char[][] printBoard = new char[board.length][board.length];
        for(int j = 0; j < board.length; j++) {
            for(int i = 0; i < board[j].length; i++) {
                char c = board[i][j] == 0 ? '.' : board[i][j] == 1 ? 'R' : 'K';
                printBoard[i][j] = c;
            }
        }
        for(int j = 0; j < board.length; j++) {
            for(int i = 0; i < board[j].length; i++) {
                List<Vector2> moves = game.getPossiblePositions(i, j);
                final int[] finalCoords = new int[] {i, j};
                if(moves != null) {
                    moves.forEach(v -> {
                        // ha nem final, nem engedi
                        if(printBoard[v.x()][v.y()] != '#')
                            printBoard[v.x()][v.y()] = board[finalCoords[0]][finalCoords[1]] == FoxGame.FOX ? '*' : '#';
                    });
                }
            }
        }

        System.out.print("- ");
        for(int i = 0; i < printBoard.length; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for(int j = 0; j < printBoard.length; j++) {
            System.out.print(j + " ");
            for (int i = 0; i < printBoard[j].length; i++) {
                System.out.print(printBoard[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        new FoxAndHounds();
    }
}
