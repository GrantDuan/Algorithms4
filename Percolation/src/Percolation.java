public class Percolation {
    private boolean[][] flags;
    private int length;
    private WeightedQuickUnionUF weightedQuickUnion;
    private int weightedQuickUnionLength;

    public Percolation(int N) // create N-by-N grid, with all sites blocked
    {
        if (N < 1)
            throw new IllegalArgumentException();
        length = N;
        weightedQuickUnionLength = (int) N * N + 2;
        flags = new boolean[N][N]; // (1, 1) is the upper-left site
        weightedQuickUnion = new WeightedQuickUnionUF(weightedQuickUnionLength);
    }

    /*
     * open site (row i, column j) if it is not already
     */
    public void open(int i, int j) {
        int index = getIndex(i, j);
        flags[i - 1][j - 1] = true;

        // left
        if (j - 1 > 0) {
            if (isOpen(i, j - 1)) {
                weightedQuickUnion.union(index - 1, index);
            }
        }

        // right
        if (j < length) {
            if (isOpen(i, j + 1)) {
                weightedQuickUnion.union(index + 1, index);
            }
        }

        // over
        if (i - 1 > 0) {
            if (isOpen(i - 1, j)) {
                weightedQuickUnion.union(index - length, index);
            }
        } else if (i - 1 == 0)
            weightedQuickUnion.union(0, index);

        // below
        if (i < length) {
            if (isOpen(i + 1, j)) {
                weightedQuickUnion.union(index + length, index);
            }
        }
        // else if (i == length)
        // weightedQuickUnion.union(weightedQuickUnionLength - 1, index);
    }

    private int getIndex(int i, int j) {
        validateIndex(i, j);
        return (i - 1) * length + j;
    }

    private boolean validateIndex(int i, int j) {
        if (i < 1 || i > length || j < 1 || j > length)
            throw new IndexOutOfBoundsException();
        return true;
    }

    public boolean isOpen(int i, int j) // is site (row i, column j) open?
    {
        validateIndex(i, j);
        return flags[i - 1][j - 1];
    }

    /*
     * is site (row i, column j) full?
     */
    public boolean isFull(int i, int j) {
        int index = getIndex(i, j);
        return weightedQuickUnion.connected(0, index);
    }

    public boolean percolates() // does the system percolate?
    {
        for (int j = 1; j < length + 1; j++) {
            if (flags[length - 1][j - 1]) {
                int index = getIndex(length, j);
                if (weightedQuickUnion.connected(0, index))
                    return true;
            }
        }
        return false;
        // return weightedQuickUnion.connected(0, weightedQuickUnionLength - 1);
    }

}
