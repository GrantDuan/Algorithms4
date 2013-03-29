import java.util.ArrayList;

public class Board {
    private int[][] blocks;
    private int x, y;
    private String bString;
    private int hamming;
    private int manhattan;

    public Board(int[][] blocks) // construct a board from an N-by-N array of
                                 // blocks
    {
        bString = blocks.length + System.getProperty("line.separator");

        this.blocks = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {

                // clone blocks
                this.blocks[i][j] = blocks[i][j];

                // blank square position
                if (blocks[i][j] == 0) {
                    x = i;
                    y = j;
                }

                // hamming
                if (blocks[i][j] != 0) {
                    if (i != (blocks[i][j] - 1) / blocks.length
                            || j != (blocks[i][j] - 1) % blocks.length)
                        hamming++;
                }

                // manhattan
                if (blocks[i][j] != 0) {
                    manhattan += Math.abs((blocks[i][j] - 1) / blocks.length
                            - i);
                    manhattan += Math.abs((blocks[i][j] - 1) % blocks.length
                            - j);
                }

                // toString
                // string representation of the board
                bString += blocks[i][j];
                if (j < blocks.length - 1)
                    bString += "\t";
                else
                    bString += System.getProperty("line.separator");

            }
        }
    }    


    // (where blocks[i][j] = block in row i, column j)
    public int dimension() // board dimension N
    {
        return blocks.length;
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
        boolean isBlank;
        for (int i = 0; i < blocks.length; i++) {
            isBlank = true;
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[i][j] != 0 && !isBlank)
                    return twin(i, j, i, j - 1);
                if (blocks[i][j] == 0)
                    isBlank = true;
                else
                    isBlank = false;

            }
        }
        return null;
    }

    private Board twin(int x1, int y1, int x2, int y2) {
        if (x1 < 0 || y1 < 0 || x2 < 0 || y2 < 0 || x1 >= blocks.length
                || y1 >= blocks.length || x2 >= blocks.length
                || y2 >= blocks.length)
            throw new IndexOutOfBoundsException();
        int[][] newblocks = blocks.clone();
        for (int i = 0; i < blocks.length; i++) {
            newblocks[i] = blocks[i].clone();
        }
        int temp = newblocks[x1][y1];
        newblocks[x1][y1] = newblocks[x2][y2];
        newblocks[x2][y2] = temp;
        Board twin = new Board(newblocks);
        return twin;
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
        if (x < blocks.length - 1) {
            neighbors.add(twin(x, y, x + 1, y));
        }
        if (y > 0) {
            neighbors.add(twin(x, y, x, y - 1));
        }
        if (y < blocks.length - 1) {
            neighbors.add(twin(x, y, x, y + 1));
        }
        return neighbors;
    }

    public String toString() {
        return bString;
    }
}
