public class PointSET {
    private SET<Point2D> set;

    // construct an empty set of points
    public PointSET() {
    }

    // is the set empty?
    public boolean isEmpty() {
        return set.isEmpty();
    }

    // number of points in the set
    public int size() {
        return set.size();
    }

    // add the point p to the set (if it is not
    // already in the set)
    public void insert(Point2D p) {
        set.add(p);
    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {
        return set.contains(p);
    }

    // draw all of the points to standard draw
    public void draw() {
        for (Point2D p : set) {
            p.draw();
        }
    }

    // all points in the set that
    // are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> result = new Queue<Point2D>();
        for (Point2D p : set) {
            if (rect.contains(p))
                result.enqueue(p);
        }
        return result;
    }

    // a nearest neighbor in the set to p;
    // null if set is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty())
            return null;
        Point2D point = new Point2D(3, 3);
        for (Point2D p1 : set) {
            if (point.distanceTo(p) > p1.distanceTo(p1))
                point = p1;
        }

        return point;
    }
}
