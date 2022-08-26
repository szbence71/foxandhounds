package hu.unideb.inf.foxandhounds.game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A játék logikáját végző osztály.
 */
public class FoxGame {
    private int[][] board;

    /**
     * Üres mező indexe.
     */
    public static final int EMPTY = 0;

    /**
     * Róka indexe.
     */
    public static final int FOX = 1;

    /**
     * Kutya indexe.
     */
    public static final int HOUND = 2;

    /**
     * Következő játékos indexe.
     */
    public int turn;

    /**
     * Vége-e a játéknak(valaki nyert-e).
     */
    public boolean won;

    /**
     * Nyertes indexe.
     */
    public int winner;

    /**
     * A játék sakktáblájának automatikus létrehozása.
     */
    public FoxGame() {
        this(new int[8][8]);

        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board.length; j++) {
                board[i][j] = EMPTY;
            }
        }

        board[2][0] = FOX;
        board[1][7] = HOUND;
        board[3][7] = HOUND;
        board[5][7] = HOUND;
        board[7][7] = HOUND;
    }

    /**
     * A játék sakktáblája megadott tábla alapján történő létrehozása.
     * @param board A játék sakktáblája
     */
    public FoxGame(int[][] board) {
        this.board = board;
        turn = FOX;
        won = false;
        winner = 0;
    }

    /**
     * A sakktábla és benne lévő figurák koordinátáit írja le.
     * @return A sakktábla tömbformában
     */
    public int[][] getBoard() {
        return board;
    }

    /**
     * Az adott koordinátán lévő figura összes lehetséges lépéseit listában adja vissza.
     * @param x Az x koordináta
     * @param y Az y koordináta
     * @return A figura lehetséges lépéseit tartalmazó lista
     */
    public List<Vector2> getPossiblePositions(int x, int y) {
        int piece = board[x][y];
        if(piece == EMPTY) return null;
        List<Vector2> possiblePositions = new ArrayList<>();
        possiblePositions.add(new Vector2(x + -1, y + -1));
        possiblePositions.add(new Vector2(x + 1, y + -1));
        if(piece == FOX) {
            possiblePositions.add(new Vector2(x + 1, y + 1));
            possiblePositions.add(new Vector2(x + -1, y + 1));

            List<Vector2> badPositions = new ArrayList<>();
            for(int i = 0; i < board.length; i++) {
                for(int j = 0; j < board.length; j++) {
                    if(board[i][j] == HOUND) {
                        badPositions.addAll(getPossiblePositions(i, j));
                        badPositions.add(new Vector2(i, j));
                    }
                }
            }
            possiblePositions.removeAll(badPositions);
        }
        return possiblePositions.stream().filter(v -> (v.x() >= 0 && v.x() < board.length && v.y() >= 0 && v.y() < board.length && board[v.x()][v.y()] != board[x][y])).collect(Collectors.toList());
    }


    /**
     * A megadott figura lépését végzi el a megadott kordinátába.
     * @param x A figura x koordinátája
     * @param y A figura y koordinátája
     * @param newX A figura új x koordinátája
     * @param newY A figura új y koordinátája
     * @return A lépés sikerességét adja vissza
     */
    public boolean movePlayer(int x, int y, int newX, int newY) {
        return movePlayer(x, y, newX, newY, true);
    }

    /**
     * A megadott figura lépését végzi el a megadott kordinátába.
     * @param x A figura x koordinátája
     * @param y A figura y koordinátája
     * @param newX A figura új x koordinátája
     * @param newY A figura új y koordinátája
     * @param forceTurn Ha igaz, akkor sikeres a játékos lépése és a következő játékosra kerül sor automatikusan
     * @return A lépés sikerességét adja vissza
     */
    public boolean movePlayer(int x, int y, int newX, int newY, boolean forceTurn) {
        if(forceTurn && board[x][y] != turn) return false;
        List<Vector2> moves = getPossiblePositions(x, y);
        if(moves == null) return false;
        if(!moves.contains(new Vector2(newX, newY))) return false;
        int piece = board[x][y];
        board[newX][newY] = piece;
        board[x][y] = EMPTY;
        if(forceTurn) {
            turn = turn == FOX ? HOUND : FOX;
        }
        checkWinCondition();
        return true;
    }

    /**
     * Lefutáskor ellenőrzi, hogy a játék véget ért-e.
     */
    public void checkWinCondition() {
        // róka check 1
        {
            int foxY = 0;
            int furthestHoundY = 0;

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (board[i][j] == FOX) {
                        foxY = j;
                    } else if(board[i][j] == HOUND) {
                        furthestHoundY = Math.max(j , furthestHoundY);
                    }
                }
            }
            if(foxY >= furthestHoundY) {
                won = true;
                winner = FOX;
                return;
            }
        }
        // róka check 2
        {
            int houndMoves = 0;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (board[i][j] == HOUND) {
                        houndMoves += getPossiblePositions(i, j).size();
                    }
                }
            }
            if(houndMoves == 0) {
                won = true;
                winner = FOX;
                return;
            }
        }
        // hound check
        {
            int foxMoves = 0;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (board[i][j] == FOX) {
                        foxMoves = getPossiblePositions(i, j).size();
                        break;
                    }
                }
            }
            if(foxMoves == 0) {
                won = true;
                winner = HOUND;
                return;
            }
        }
    }
}
