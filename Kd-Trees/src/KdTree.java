import java.util.ArrayList;

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
    public void insert(Point2D p) {
        if (size == 0) {
            root = new Node() {
            };
            root.isVertical = true;
            root.p = p;
            root.rect = new RectHV(0, 0, 1, 1);
            size++;
        } else {
            if (root.Insert(p))
                size++;
        }

    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {
        if (root == null)
            return false;
        else if (p == null)
            return false;
        return root.Contain(p);

    }

    // draw all of the points to standard draw
    public void draw() {
        if (root == null)
            return;
        else
            root.Draw();
    }

    // all points in the set that
    // are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (root == null)
            return new ArrayList<Point2D>();
        return root.range(rect);
    }

    // a nearest neighbor in the set to p;
    // null if set is empty
    public Point2D nearest(Point2D p) {
        if (root == null || p == null)
            return null;
        return root.nearest(p);
    }

    private static class Node {
        private Point2D p; // the point
        private RectHV rect; // the axis-aligned rectangle corresponding to this
                             // node
        private Node lb; // the left/bottom subtree
        private Node rt; // the right/top subtree
        private boolean isVertical; // true: separate by x

        private boolean Insert(Point2D p1) {
            if (p1 == null)
                return false;
            if (p.equals(p1))
                return false;
            if (isVertical) {
                if (p1.x() < p.x()) {
                    if (lb == null) {
                        Node n = new Node();
                        n.isVertical = !isVertical;
                        n.p = p1;
                        n.rect = new RectHV(rect.xmin(), rect.ymin(), p.x(),
                                rect.ymax());
                        lb = n;

                        return true;
                    } else {
                        return lb.Insert(p1);
                    }
                } else {
                    if (rt == null) {
                        Node n = new Node();
                        n.isVertical = !isVertical;
                        n.p = p1;
                        n.rect = new RectHV(p.x(), rect.ymin(), rect.xmax(),
                                rect.ymax());
                        rt = n;

                        return true;
                    } else {
                        return rt.Insert(p1);
                    }
                }
            } else {
                if (p1.y() < p.y()) {
                    if (lb == null) {
                        Node n = new Node();
                        n.isVertical = !isVertical;
                        n.p = p1;
                        n.rect = new RectHV(rect.xmin(), rect.ymin(),
                                rect.xmax(), p.y());
                        lb = n;

                        return true;
                    } else {
                        return lb.Insert(p1);
                    }
                } else {
                    if (rt == null) {
                        Node n = new Node();
                        n.isVertical = !isVertical;
                        n.p = p1;
                        n.rect = new RectHV(rect.xmin(), p.y(), rect.xmax(),
                                rect.ymax());
                        rt = n;
                        return true;
                    } else {
                        return rt.Insert(p1);
                    }
                }
            }

        }

        private boolean Contain(Point2D p1) {
            if (p1 == null)
                return false;
            if (p.equals(p1))
                return true;

            if (isVertical) {
                if (p1.x() < p.x()) {
                    if (lb == null) {
                        return false;
                    } else {
                        return lb.Contain(p1);
                    }
                } else {
                    if (rt == null) {
                        return false;
                    } else {
                        return rt.Contain(p1);
                    }
                }
            } else {
                if (p1.y() < p.y()) {
                    if (lb == null) {
                        return false;
                    } else {
                        return lb.Contain(p1);
                    }
                } else {
                    if (rt == null) {
                        return false;
                    } else {
                        return rt.Contain(p1);
                    }
                }
            }
        }

        private void Draw() {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.01);
            p.draw();

            if (isVertical) {
                Point2D bottom = new Point2D(p.x(), rect.ymin());
                Point2D top = new Point2D(p.x(), rect.ymax());
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.setPenRadius();
                bottom.drawTo(top);

            } else {
                Point2D left = new Point2D(rect.xmin(), p.y());
                Point2D right = new Point2D(rect.xmax(), p.y());
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.setPenRadius();
                left.drawTo(right);

            }
            if (lb != null)
                lb.Draw();
            if (rt != null)
                rt.Draw();
        }

        private ArrayList<Point2D> range(RectHV rect) {
            ArrayList<Point2D> result = new ArrayList<Point2D>();
            if (rect.contains(p))
                result.add(p);
            if (isVertical) {
                if (p.x() > rect.xmax() && lb != null)
                    result.addAll(lb.range(rect));
                else if (p.x() <= rect.xmin() && rt != null)
                    result.addAll(rt.range(rect));
                else {
                    if (lb != null)
                        result.addAll(lb.range(rect));
                    else if (rt != null)
                        result.addAll(rt.range(rect));

                }
            } else {
                if (p.y() > rect.ymax() && lb != null)
                    result.addAll(lb.range(rect));
                else if (p.y() <= rect.ymin() && rt != null)
                    result.addAll(rt.range(rect));
                else {
                    if (lb != null)
                        result.addAll(lb.range(rect));
                    else if (rt != null)
                        result.addAll(rt.range(rect));
                }
            }

            return result;
        }

        private Point2D nearest(Point2D qry) {
            double dst = p.distanceTo(qry);
            Point2D lp = p, rp = p; //nearest point of subtrees;
            if (lb == null && rt == null)
                return p;

            // query point at left/bottom
            if (((p.x() > qry.x() && isVertical))
                    || (!isVertical && p.y() > qry.y())) {
                if (lb != null) {
                    lp = lb.nearest(qry);
                    if (dst > lp.distanceTo(qry))
                        dst = lp.distanceTo(qry);

                }

                if (rt != null) {
                    if (rt.rect.distanceTo(qry) < dst) {
                        rp = rt.nearest(qry);
                        if (dst > rt.p.distanceTo(qry))
                            dst = rt.p.distanceTo(qry);
                    }
                }

            }
            // query point at right/top
            else {
                if (rt != null) {
                    rp = rt.nearest(qry);
                    if (dst > rp.distanceTo(qry))
                        dst = rp.distanceTo(qry);

                }

                if (lb != null) {
                    if (lb.rect.distanceTo(qry) < dst) {
                        lp = lb.nearest(qry);
                        if (dst > lp.distanceTo(qry))
                            dst = lp.distanceTo(qry);
                    }
                }
            }

            Point2D result = p;
            if (lp.distanceTo(qry) == dst)
                result = lp;
            else if (rp.distanceTo(qry) == dst)
                result = rp;

            return result;

        }

    }
}
