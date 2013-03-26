import java.util.ArrayList;

public class Board {
    private int[][] blocks;
    private int x, y;

    public Board(int[][] blocks) // construct a board from an N-by-N array of
                                 // blocks
    {
        this.blocks = blocks;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[i][j] == 0) {
                    x = i;
                    y = j;
                }
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
        int number = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[i][j] == 0) {
                    if (i != blocks.length - 1 || j != blocks.length - 1)
                        number++;
                } else {
                    if (i != (blocks[i][j] - 1) / blocks.length
                            || j != blocks[i][j] % blocks.length - 1)
                        number++;
                }
            }
        }
        return number;
    }

    public int manhattan() // sum of Manhattan distances between blocks and goal
    {
        int number = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[i][j] == 0) {
                    number += blocks.length * 2 - 2 - i - j;
                } else {
                    number += Math.abs((blocks[i][j] - 1) / blocks.length - i);
                    number += Math.abs((blocks[i][j]) % blocks.length - 1 - j);
                }
            }
        }
        return number;
    }

    public boolean isGoal() // is this board the goal board?
    {
        return (hamming() == 0);
    }

    public Board twin() // a board obtained by exchanging two adjacent blocks in
                        // the same row
    {
        return twin(0, 0, 0, 1);
    }

    private Board twin(int x1, int y1, int x2, int y2) {
        if (x1 < 0 || y1 < 0 || x2 < 0 || y2 < 0 || x1 >= blocks.length
                || y1 >= blocks.length || x2 >= blocks.length
                || y2 >= blocks.length)
            throw new IndexOutOfBoundsException();
        int[][] newblocks = blocks.clone();
        int temp = newblocks[x1][y1];
        newblocks[x1][y1] = newblocks[x2][y2];
        newblocks[x2][y2] = temp;
        Board twin = new Board(newblocks);
        return twin;
    }

    public boolean equals(Object y) // does this board equal y?
    {
        if (y == null)
            return false;
        else if (!(y instanceof Board))
            return false;
        else if (((Board) y).dimension() != dimension()) {
            return false;
        } else if (((Board) y).hamming() != hamming())
            return false;
        else if (((Board) y).manhattan() != manhattan())
            return false;
        return true;

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
        if (x < blocks.length - 1) {
            neighbors.add(twin(x, y, x, y + 1));
        }
        return neighbors;
    }

    public String toString() // string representation of the board (in the
                             // output format specified below)
    {
        String result = blocks.length + System.getProperty("line.separator");
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                result += blocks[i][j];

                if (j < blocks.length - 1)
                    result += "\t";
                else
                    result += System.getProperty("line.separator");
            }
        }
        return result;
    }
}