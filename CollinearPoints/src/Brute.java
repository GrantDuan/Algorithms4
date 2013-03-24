import java.util.Arrays;;

public class Brute {

    private Point[] points;

    public Brute(Point[] points) {
        this.points = points;
    }

    public void sort() {
        Arrays.sort(points);
    }

    public void printLineSegments() {
        sort();
        int n = points.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int l = k + 1; l < n; l++) {
                        if (points[i].slopeTo(points[j]) == points[i]
                                .slopeTo(points[k])
                                && points[i].slopeTo(points[j]) == points[i]
                                        .slopeTo(points[l])) {
                            StdOut.println(points[i].toString() + " -> "
                                    + points[j].toString() + " -> "
                                    + points[k].toString() + " -> "
                                    + points[l].toString());
                            points[i].drawTo(points[l]);
                        }
                    }
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

        Brute brute = new Brute(points);
        brute.printLineSegments();

    }

}
