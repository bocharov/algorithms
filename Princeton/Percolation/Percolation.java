public class Percolation {
    private boolean[][] grid;
    private int gridSize;
    private WeightedQuickUnionUF wquf;
    private int virtualTop;
    private boolean[] connectedBottom;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N should be larger than 0!");
        }

        gridSize = N;
        wquf = new WeightedQuickUnionUF(N * N + 1);
        virtualTop = 0;
        grid = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = false;
            }
        }

        connectedBottom = new boolean[N * N + 1];

        for (int i = 0; i <= N * (N-1); i++) {
            connectedBottom[i] = false;
        }

        for (int i = N * (N - 1) + 1; i <= N * N; i++) {
            connectedBottom[i] = true;
        }
    }

    private boolean isValidIndices(int i, int j) {
        return (i > 0 && i <= gridSize && j > 0 && j <= gridSize);
    }

    private void validateIndices(int i, int j) {
        if (!isValidIndices(i, j)) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    private int[][] getNeighbors(int i, int j) {
        return new int[][]{{i - 1, j}, {i, j + 1}, {i + 1, j}, {i, j - 1}};
    }

    private int getUnionFindIndex(int i, int j) {
        return 1 + (i - 1) * gridSize + (j - 1);
    }

    public void open(int i, int j) {
        validateIndices(i, j);
        if (!isOpen(i, j)) {
            grid[i - 1][j - 1] = true;
            int thisUFIndex = getUnionFindIndex(i, j);
            if (i == 1) wquf.union(thisUFIndex, virtualTop);

            for (int[] row : getNeighbors(i, j)) {
                if (isValidIndices(row[0], row[1]) && isOpen(row[0], row[1])) {
                    int thatUFIndex = getUnionFindIndex(row[0], row[1]);
                    boolean connectBottom = connectedBottom[wquf.find(thisUFIndex)]
                            || connectedBottom[wquf.find(thatUFIndex)];
                    wquf.union(thisUFIndex, thatUFIndex);
                    if (connectBottom) connectedBottom[wquf.find(thisUFIndex)] = true;
                }
            }
        }
    }

    public boolean isOpen(int i, int j) {
        return grid[i - 1][j - 1];
    }

    public boolean isFull(int i, int j) {
        validateIndices(i, j);
        return wquf.connected(virtualTop, getUnionFindIndex(i, j));
    }

    public boolean percolates() {
        return connectedBottom[wquf.find(0)];
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(5);
        StdOut.println(p.isFull(1, 1));
        StdOut.println(p.isOpen(2, 1));
        StdOut.println(p.isFull(2, 1));
        p.open(3, 4);
        StdOut.println(p.isOpen(3, 4));
        StdOut.println(p.isFull(3, 4));
        StdOut.println(p.percolates());
        StdOut.println(p.isValidIndices(6, 9));
    }
}
