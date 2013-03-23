public class PercolationStats {
    private int N, T;
    private double[] thresholds;
    private double stddev;
    private double mean;
    private double confidenceLo;
    private double confidenceHi;

    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        thresholds = new double[T];
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException();
        this.N = N;
        this.T = T;
        computeT();
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold

    public double stddev() {
        return stddev;
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        return confidenceLo;
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        return confidenceHi;
    }

    private double compute() {
        Percolation percolation = new Percolation(N);
        int count = (int) N * N;

        int k = 1;
        while (!percolation.percolates()) {
            int i = StdRandom.uniform(N) + 1;
            int j = StdRandom.uniform(N) + 1;
            if (!percolation.isOpen(i, j)) {
                percolation.open(i, j);
                k++;
            }
        }

        return (double) k / (double) count;
    }

    private void computeT() {
        for (int i = 0; i < T; i++) {
            thresholds[i] = compute();
        }
        mean = StdStats.mean(thresholds);
        stddev = StdStats.stddev(thresholds);
        confidenceLo = mean - (1.96 * stddev) / Math.sqrt(T);
        confidenceHi = mean + (1.96 * stddev) / Math.sqrt(T);

    }

    // test client, described below
    public static void main(String[] args) {

        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        if (N <= 0 || T <= 1)
            throw new IllegalArgumentException();
        PercolationStats p = new PercolationStats(N, T);

        System.out.println(String.format("java PercolationStats %1$d %2$d", N,
                T));
        System.out.println(String.format("mean\t\t\t = %f", p.mean()));
        System.out.println(String.format("mean\t\t\t = %f", p.stddev()));
        System.out.println(String.format(
                "95%% confidence interval\t = %1$f %2$f", p.confidenceLo(),
                p.confidenceHi()));

    }
}