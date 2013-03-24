import java.util.Arrays;

public class Fast {

    private Point[] points;

    public Fast(Point[] points) {
        this.points = points;
    }

    public void Sort() {
        Arrays.sort(points);
    }
    
    public void PrintLineSegments() {        
        int n = points.length;
        for (int i = 0; i < n-3; i++) {
          Point[] ps = Arrays.copyOfRange(points, n-i-1, n-1);
          Arrays.sort(ps, points[i].SLOPE_ORDER);
          int num = 1;
          for(int j = 1; j< ps.length;j++)
          {              
              
              if(points[i].slopeTo(ps[j-1]) == points[i].slopeTo(ps[j]))
              {
                  num++;
              }
              else
              {
                  if(num>3)
                  {
                      Point[] line = new Point[num];
                      line[0] = points[i];
                      for(int k = 0;k<num;k++)
                      {
                          line[k +1] = ps[j-k];
                      }
                      
                      Arrays.sort(line);
                      line[0].drawTo(line[num-1]);
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
        fast.Sort();

    }

}
