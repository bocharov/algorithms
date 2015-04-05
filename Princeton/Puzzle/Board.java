import java.util.Arrays;

public class Board {
    private final int N;
    private final int[][] tiles;

    public Board(int[][] blocks) {
        N = blocks.length;
        tiles = deepCopy(blocks, N);
    }

    private int[][] deepCopy(int[][] y, int M) {
        int[][] out = new int[M][M];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < M; j++) {
                out[i][j] = y[i][j];
            }
        }
        return out;
    }

    public int dimension() {
        return N;
    }

    public int hamming() {
        int numOfDiffBlocks = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] != j + i * N  + 1) {
                    numOfDiffBlocks++;
                }
            }
        }
        return --numOfDiffBlocks;
    }

    private int singleMan(int i, int j) {
        int goalI = tiles[i][j] / N;
        int goalJ = tiles[i][j] % N;
        if (goalJ == 0) {
            goalI -= 1;
            goalJ = N - 1;
        } else {
            goalJ -= 1;
        }
        return Math.abs(i - goalI) + Math.abs(j - goalJ);
    }

    public int manhattan() {
        int manDist = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] != j + i * N  + 1 && tiles[i][j] != 0) {
                    manDist += singleMan(i, j);
                }
            }
        }
        return manDist;
    }

    public boolean isGoal() {
        return manhattan() == 0;
    }

    private void swapBlocks(int[][] x, int i, int j, int m, int n) {
        int swap;
        swap = x[i][j];
        x[i][j] = x[m][n];
        x[m][n] = swap;
    }

    public Board twin() {
        int[][] aTwin = deepCopy(tiles, N);
        if (aTwin[0][0] * aTwin[0][1] == 0) {
            swapBlocks(aTwin, 1, 0, 1, 1);
        } else {
            swapBlocks(aTwin, 0, 0, 0, 1);
        }
        return new Board(aTwin);
    }

    public boolean equals(Object y) {
        if (y == this) return true;

        if (!(y instanceof Board)) return false;

        Board that = (Board) y;
        return (this.N == that.N) && (Arrays.deepEquals(this.tiles, that.tiles));

    }

    private void pushBoardOntoStack(Stack<Board> s, int x, int y, int m, int n) {
        int[][] neighborTiles = deepCopy(tiles, N);
        swapBlocks(neighborTiles, x, y, m, n);
        s.push(new Board(neighborTiles));
    }

    public Iterable<Board> neighbors() {
        Stack<Board> boards = new Stack<Board>();
        boolean blankFound = false;
        for (int i = 0; i < N && !blankFound; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] == 0) {
                    if (i > 0) pushBoardOntoStack(boards, i - 1, j, i, j);
                    if (j > 0) pushBoardOntoStack(boards, i, j - 1, i, j);
                    if (i < N - 1) pushBoardOntoStack(boards, i + 1, j, i, j);
                    if (j < N - 1) pushBoardOntoStack(boards, i, j + 1, i, j);
                    blankFound = true;
                    break;
                }
            }
        }
        return boards;
    }

    public String toString() {
        StringBuffer s = new StringBuffer();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        int[][] blocks = {{8, 1, 3}, {4, 2, 0}, {7, 6, 5}};
        Board a = new Board(blocks);
        StdOut.println(a.dimension());
        StdOut.println(a.hamming());
        StdOut.println(a.manhattan());
        StdOut.println(a);
        StdOut.println(a.twin());
        StdOut.println(a);
        for (Board board: a.neighbors()) {
            StdOut.println(board);
        }
    }
}
