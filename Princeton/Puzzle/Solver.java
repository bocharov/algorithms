public class Solver {
    private boolean isSolvable;
    private int totalMoves;
    private SearchNode last;

    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int moves;
        private SearchNode prevNode;
        private int priority;


        public SearchNode(Board b, int m, SearchNode p) {
            board = b;
            moves = m;
            prevNode = p;
            priority = b.manhattan() + moves;
        }

        public int compareTo(SearchNode other) {
            if (this.priority > other.priority) return 1;
            if (this.priority < other.priority) return -1;
            return 0;
        }
    }

    private void enqueueNodes(SearchNode node, MinPQ<SearchNode> pq) {
        for (Board nextBoard: node.board.neighbors()) {
            if ((node.prevNode == null) || (!nextBoard.equals(node.prevNode.board))) {
                pq.insert(new SearchNode(nextBoard, node.moves + 1, node));
            }
        }
    }

    public Solver(Board initial) {
        MinPQ<SearchNode> searchNodeMinPQ = new MinPQ<SearchNode>();
        MinPQ<SearchNode> searchNodeTwinMinPQ = new MinPQ<SearchNode>();
        SearchNode start = new SearchNode(initial, 0, null);
        SearchNode startTwin = new SearchNode(initial.twin(), 0, null);
        searchNodeMinPQ.insert(start);
        searchNodeTwinMinPQ.insert(startTwin);
        while (true) {
            SearchNode node = searchNodeMinPQ.delMin();
            SearchNode nodeTwin = searchNodeTwinMinPQ.delMin();
            if (node.board.isGoal()) {
                last = node;
                isSolvable = true;
                totalMoves = node.moves;
                break;
            }
            if (nodeTwin.board.isGoal()) {
                isSolvable = false;
                totalMoves = -1;
                break;
            }
            enqueueNodes(node, searchNodeMinPQ);
            enqueueNodes(nodeTwin, searchNodeTwinMinPQ);
        }
    }

    public boolean isSolvable() {
        return isSolvable;
    }

    public int moves() {
        return totalMoves;
    }

    public Iterable<Board> solution() {
        Stack<Board> solution = new Stack<Board>();
        if (!isSolvable) {
            solution = null;
        } else {
            SearchNode s = last;
            while (s != null) {
                solution.push(s.board);
                s = s.prevNode;
            }
        }
        return solution;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        Solver solver = new Solver(initial);

        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
