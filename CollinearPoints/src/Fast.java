import java.util.Arrays;

public class Fast {

    private Point[] points;

    public Fast(Point[] points) {
        this.points = points;
    }

    public void sort() {
        Arrays.sort(points);
    }

    public void printLineSegments() {
        sort();
        int n = points.length;
        for (int i = 0; i < n - 3; i++) {
            Point[] ps = Arrays.copyOfRange(points, i + 1, n);
            Arrays.sort(ps, points[i].SLOPE_ORDER);
            int num = 1;
            for (int j = 1; j < ps.length; j++) {

                if (points[i].slopeTo(ps[j - 1]) == points[i].slopeTo(ps[j])) {
                    num++;
                    if (j == ps.length - 1) {
                        if (num > 2) {
                            Arrays.sort(ps, j - num + 1, j + 1);
                            points[i].drawTo(ps[j]);
                        }
                    }
                } else {
                    if (num > 2) {
                        Arrays.sort(ps, j - num, j - 1);
                        points[i].drawTo(ps[j - 1]);
                    }
                    num = 1;
                }

            }
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        String inputFileName = args[0];
        In in = new In(inputFileName);
        int n = Integer.parseInt(in.readLine());
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            Point p = new Point(in.readInt(), in.readInt());
            points[i] = p;
            p.draw();
        }

        Fast fast = new Fast(points);
        fast.printLineSegments();

    }

}
