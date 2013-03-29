import java.util.ArrayList;

public class Board {
    private int[][] blocks;
    private int x, y;
    private int N;
    private String bString;
    private int hamming;
    private int manhattan;

    public Board(int[][] blocks) // construct a board from an N-by-N array of
                                 // blocks
    {
        N = blocks.length;
        bString = N + System.getProperty("line.separator");

        this.blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {

                // clone blocks
                this.blocks[i][j] = blocks[i][j];

                // blank square position
                if (blocks[i][j] == 0) {
                    x = i;
                    y = j;
                }

                // hamming
                if (blocks[i][j] != 0) {
                    if (i != (blocks[i][j] - 1) / N
                            || j != (blocks[i][j] - 1) % N)
                        hamming++;
                }

                // manhattan
                if (blocks[i][j] != 0) {
                    manhattan += Math.abs((blocks[i][j] - 1) / N - i);
                    manhattan += Math.abs((blocks[i][j] - 1) % N - j);
                }

                // toString
                // string representation of the board
                bString += blocks[i][j];
                if (j < N - 1)
                    bString += "\t";
                else
                    bString += System.getProperty("line.separator");

            }
        }
    }

    // (where blocks[i][j] = block in row i, column j)
    public int dimension() // board dimension N
    {
        return N;
    }

    public int hamming() // number of blocks out of place
    {
        return hamming;
    }

    public int manhattan() // sum of Manhattan distances between blocks and goal
    {
        return manhattan;
    }

    public boolean isGoal() // is this board the goal board?
    {
        return (hamming == 0);
    }

    public Board twin() // a board obtained by exchanging two adjacent
                        // (non-blank) blocks in
                        // the same row
    {

        if (x - 1 >= 0) {
            if (y + 1 < N)
                return twin(x - 1, y, x - 1, y + 1);
            else
                return twin(x - 1, y, x - 1, y - 1);
        } else if (x + 1 < N) {

            if (y + 1 < N)
                return twin(x + 1, y, x + 1, y + 1);
            else
                return twin(x + 1, y, x + 1, y - 1);
        }

        return null;
    }

    private Board twin(int x1, int y1, int x2, int y2) {
        exchange(x1, y1, x2, y2);
        Board twin = new Board(blocks);
        exchange(x1, y1, x2, y2);
        return twin;
    }

    private void exchange(int x1, int y1, int x2, int y2) {
        int temp = blocks[x1][y1];
        blocks[x1][y1] = blocks[x2][y2];
        blocks[x2][y2] = temp;
    }

    public boolean equals(Object that) // does this board equal y?
    {
         if (that == null)
         return false;
         if (this == that)
         return true;
         else if (!(that instanceof Board))
         return false;
        return ((Board) that).toString().equals(toString());

    }

    public Iterable<Board> neighbors() // all neighboring boards
    {
        ArrayList<Board> neighbors = new ArrayList<Board>();
        if (x > 0) {
            neighbors.add(twin(x, y, x - 1, y));
        }
        if (x < N - 1) {
            neighbors.add(twin(x, y, x + 1, y));
        }
        if (y > 0) {
            neighbors.add(twin(x, y, x, y - 1));
        }
        if (y < N - 1) {
            neighbors.add(twin(x, y, x, y + 1));
        }
        return neighbors;
    }

    public String toString() {
        return bString;
    }
}
