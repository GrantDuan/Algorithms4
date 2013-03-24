import java.util.Arrays;

public class Fast {

    private Point[] points;

    public Fast() {

    }

    private Fast(Point[] points) {
        this.points = points;
    }

    private void sort() {
        Arrays.sort(points);
    }

    private void printLineSegments() {
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
                            String out = points[i].toString();
                            for(int k = j-num +1; k<j+1; k++)
                            {
                                out = out + " -> " + ps[k].toString();
                            }
                            StdOut.println(out);
                        }
                    }
                } else {
                    if (num > 2) {
                        Arrays.sort(ps, j - num, j);
                        points[i].drawTo(ps[j - 1]);
                        
                        String out = points[i].toString();
                        for(int k = j-num; k<j; k++)
                        {
                            out = out + " -> " + ps[k].toString();
                        }
                        StdOut.println(out);
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
        int n = in.readInt();
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
