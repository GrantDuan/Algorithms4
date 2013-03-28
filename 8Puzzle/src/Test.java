public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {

        testMan("puzzle04.txt", 4);
        testMan("puzzle00.txt", 0);
        testMan("puzzle27.txt", 17);

        testHan("puzzle04.txt", 4);
        testHan("puzzle00.txt", 0);
        testHan("puzzle27.txt", 7);

        testSolvable("puzzle04.txt");
        testSolvable("puzzle00.txt");
        testSolvable("puzzle27.txt");
        testSolvable("puzzle20.txt");
        testSolvable("puzzle21.txt");
        testSolvable("puzzle3x3-unsolvable2.txt");
        testSolvable("puzzle4x4-80.txt");
        testSolvable("puzzle4x4-hard1.txt");
        
        

    }

    public static void testMan(String file, int pri) {
        // create initial board from file
        In in = new In(file);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        if (initial.manhattan() == pri)
            StdOut.println(file + " manhattan success " + pri);
        else
            StdOut.println(file + " manhattan fail expect: " + pri + "actual: "
                    + initial.manhattan());
    }

    public static void testHan(String file, int pri) {
        // create initial board from file
        In in = new In(file);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        if (initial.hamming() == pri)
            StdOut.println(file + " hamming success " + pri);
        else
            StdOut.println(file + " hamming  fail expect: " + pri + "actual: "
                    + initial.hamming());
    }

    public static void testSolvable(String file) {
        // create initial board from file
        In in = new In(file);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        Solver s = new Solver(initial);

        if (!file.contains("unsolvable")) {
            if (s.moves() != -1)
                StdOut.println(file + " is solvable success with " + s.moves()
                        + " steps");
            else
                StdOut.println(file + " is solvable fail ");
        } else {
            if (s.moves() == -1)
                StdOut.println(file + " is unsolvable success ");
            else
                StdOut.println(file + " is unsolvable fail ");
        }

    }

}
