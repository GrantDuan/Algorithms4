public class KdTree {
    private Node root;
    private int size;
    
    // construct an empty set of points
    public KdTree() {
    }

    // is the set empty?
    public boolean isEmpty() {
        return false;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point p to the set (if it is not
    // already in the set)
    public void insert(Point2D p)
    {
        if(size== 0)
        {
            root = new Node(){};
            root.isVertical = true;
            root.p = p;
            root.rect = new RectHV(0,0, 1,1);
        }
        
        
    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {
        Node t = root;
        while(t != null)
        {
            if(t.equals(p))
                return true;
                
        }
        return false;
    }

    // draw all of the points to standard draw
    public void draw() {
    }

    // all points in the set that
    // are inside the rectangle
    public Iterable<Point2D> range(RectHV rect)
    {
        return null;
    }

    // a nearest neighbor in the set to p;
    // null if set is empty
    public Point2D nearest(Point2D p)
    {
        return null;
    }
    
    
    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private boolean isVertical;
     }
}
