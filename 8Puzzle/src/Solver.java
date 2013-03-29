public class Solver {
    private SearchNode initialSN;
    private MinPQ<SearchNode> pq;
    private MinPQ<SearchNode> pq2;
    private boolean isSolvable = false;
    private boolean isSearched = false;

    public Solver(Board initial) // find a solution to the initial board (using
                                 // the A* algorithm)
    {
        initialSN = new SearchNode(initial);
        pq = new MinPQ<SearchNode>();
        pq.insert(initialSN);
        SearchNode root2 = new SearchNode(initial.twin());
        pq2 = new MinPQ<SearchNode>();
        pq2.insert(root2);
    }

    public boolean isSolvable() // is the initial board solvable?
    {
        if (!isSearched)
            bestFirstSearch();
        return isSolvable;

    }

    public int moves() // min number of moves to solve initial board; -1 if no
                       // solution
    {
        if (isSolvable()) {
            return initialSN.moves;
        }
        return -1;
    }

    public Iterable<Board> solution() // sequence of boards in a shortest
                                      // solution; null if no solution
    {
        Stack<Board> stack = new Stack<Board>();
        if (isSolvable()) {
            if (stack.size() == 0) {
                SearchNode sn = initialSN;
                stack.push(sn.board);
                while (sn.pre != null) {
                    sn = sn.pre;
                    stack.push(sn.board);
                }
            }

            return stack;
        }
        return null;
    }

    private void bestFirstSearch() {
        SearchNode n1 = pq.delMin();
        SearchNode n2 = pq2.delMin();

        while (!n1.board.isGoal() && !n2.board.isGoal()) {
            // StdOut.println(n1.board.toString());
            for (Board b : n1.board.neighbors()) {
                if (n1.pre != null) {
                    if (!n1.pre.board.equals(b)) {
                        SearchNode sn = new SearchNode(b, n1);
                        pq.insert(sn);
                    }
                } else {
                    SearchNode sn = new SearchNode(b, n1);
                    pq.insert(sn);
                }

            }
            n1 = pq.delMin();
            // StdOut.println(n1.board.toString());

            for (Board b : n2.board.neighbors()) {
                if (n2.pre != null) {
                    if (!b.equals(n2.pre.board)) {
                        SearchNode sn = new SearchNode(b, n2);
                        pq2.insert(sn);
                    }
                } else {
                    SearchNode sn = new SearchNode(b, n2);
                    pq2.insert(sn);
                }

            }
            n2 = pq2.delMin();
        }

        if (n1.board.isGoal()) {
            isSolvable = true;
            initialSN = n1;
        }

        else
            isSolvable = false;
        isSearched = true;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private class SearchNode implements Comparable<Object> {
        private Board board;
        private int moves;
        private SearchNode pre;

        public SearchNode(Board b) {
            board = b;
        }

        public SearchNode(Board b, SearchNode sn) {
            board = b;
            if (sn != null) {
                moves = sn.moves + 1;
                pre = sn;
            }
        }

        @Override
        public int compareTo(Object that) {
            if (that == null)
                throw new NullPointerException();
            if (!(that instanceof SearchNode))
                throw new java.lang.IllegalArgumentException();

            SearchNode t = (SearchNode) that;
            return board.manhattan() + moves - t.board.manhattan() - t.moves;
        }

    }
}
